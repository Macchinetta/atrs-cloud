/*
 * Copyright 2014-2017 NTT Corporation.
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
package jp.co.ntt.atrs.domain.service.c2;

import java.io.IOException;

import jp.co.ntt.atrs.domain.model.Member;

/**
 * 会員情報変更を行うServiceインタフェース。
 * @author NTT 電電花子
 */
public interface MemberUpdateService {

    /**
     * お客様番号に該当する会員情報を取得する。
     * @param customerNo お客様番号
     * @return Member お客様番号に該当するユーザの会員情報
     * @throws IOException
     */
    Member findMember(String customerNo) throws IOException;

    /**
     * 会員情報を更新する。
     * @param member 会員情報
     * @throws IOException
     */
    void updateMember(Member member) throws IOException;

    /**
     * 引数に渡されたパスワードがDBに登録されているパスワードと同一かチェックする。
     * @param password チェックするパスワード
     * @param customerNo パスワードを確認する会員のお客様番号
     * @throws IOException
     */
    void checkMemberPassword(String password, String customerNo) throws IOException;
}