/*
 * Copyright 2014-2020 NTT Corporation.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 */
package jp.co.ntt.atrs.domain.common.shard.helper;

import java.lang.reflect.Method;
import java.lang.reflect.Parameter;

import org.aopalliance.intercept.MethodInvocation;
import org.springframework.aop.support.AopUtils;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.expression.Expression;
import org.springframework.expression.ExpressionParser;
import org.springframework.expression.spel.standard.SpelExpressionParser;
import org.springframework.util.ReflectionUtils;

import jp.co.ntt.atrs.domain.common.shard.annotation.ShardAccountParam;
import jp.co.ntt.atrs.domain.common.shard.annotation.ShardWithAccount;

/**
 * シャーディングアカウントのヘルパー。
 * @author NTT 電電太郎
 */
public class ShardAccountHelper {

    /**
     * {@link MethodInvocation}から{@link ShardWithAccount}に設定されたパスを元にシャードアカウントを取得する。
     * @param invocation {@link MethodInvocation}
     * @return シャードアカウント
     * @throws Exception
     */
    public String getAccountValue(
            MethodInvocation invocation) throws IllegalArgumentException {
        String ret = null;
        // 実行対象のオブジェクトを取得
        Object target = invocation.getThis();
        if (target == null) {
            return null;
        }
        // 実行対象のクラスを取得
        Class<?> targetClass = AopUtils.getTargetClass(target);
        // 実行対象の引数を取得
        Object[] arguments = invocation.getArguments();
        Class<?>[] classes = null;
        if (null != arguments && arguments.length > 0) {
            classes = invocation.getMethod().getParameterTypes();
        } else {
            return null;
        }
        // 実行対象のメソッドを取得
        Method method = ReflectionUtils.findMethod(targetClass, invocation
                .getMethod().getName(), classes);
        // 実行対象のメソッドに付与されたShardWithAccountアノテーションを取得
        ShardWithAccount shardWithAccount = AnnotationUtils.findAnnotation(
                method, ShardWithAccount.class);
        if (null != shardWithAccount) {
            // ShardWithAccountアノテーションの属性valueの値を取得
            String value = shardWithAccount.value();
            if ("".equals(value)) {
                return null;
            }
            // 属性値を分割
            String[] values = value.split("[.]");
            Object obj = null;
            int argumentsLength = 0;
            if (arguments.length == 1) {
                // 引数が1つ
                obj = arguments[0];
            } else {
                // 引数が複数
                obj = getObjectAtShardAccountParam(method, arguments, values);
            }
            if (null == obj) {
                throw new IllegalArgumentException(String.format(
                        "第[ %d ]引数の値がNULLです。", (argumentsLength + 1)));
            }
            // 対象アカウント値を取得
            ret = getAccountAtShardWithAccount(value, values, obj);
        }
        return ret;
    }

    /**
     * メソッド引数が複数ある場合、ShardAccountParamアノテーションが付与されている引数のオブジェクトを取得する
     * @param method 実行対象のメソッド
     * @param arguments 実行対象の引数
     * @param splitValues valueを分割した属性値
     * @return ShardAccountParamアノテーションが付与されている引数のオブジェクト
     */
    private Object getObjectAtShardAccountParam(Method method,
            Object[] arguments, String[] splitValues) {
        Object obj = null;

        ShardAccountParam shardAccountParam = null;
        Parameter[] parameters = method.getParameters();

        for (int paramIdx = 0; paramIdx < parameters.length; paramIdx++) {
            // 引数からShardAccountParamアノテーションを取得
            shardAccountParam = AnnotationUtils.findAnnotation(
                    parameters[paramIdx], ShardAccountParam.class);
            if (null != shardAccountParam) {
                // ShardAccountParamアノテーションが付与されている引数のオブジェクトを使用
                obj = arguments[paramIdx];
                break;
            }
        }

        if (null == shardAccountParam && splitValues.length > 1) {
            throw new IllegalArgumentException("メソッド引数が複数ありShardWithAccountアノテーションに値を設定した時に、"
                    + "メソッド引数へShardAccountParamアノテーションの付与は必須です。");
        }
        return obj;
    }

    /**
     * 対象アカウント値を取得
     * @param value ShardWithAccountアノテーションの属性valueの値
     * @param splitValues valueを分割した属性値
     * @param argument 実行対象の引数
     * @return 対象アカウント値
     */
    private String getAccountAtShardWithAccount(String value,
            String[] splitValues, Object argument) {
        String ret = null;
        if (splitValues.length == 1) {
            ret = argument.toString();
        } else {
            String exp = value.substring(value.indexOf(".") + 1);
            ExpressionParser expressionParser = new SpelExpressionParser();
            Expression expression = expressionParser.parseExpression(exp);
            ret = expression.getValue(argument, String.class);
        }
        return ret;
    }
}
