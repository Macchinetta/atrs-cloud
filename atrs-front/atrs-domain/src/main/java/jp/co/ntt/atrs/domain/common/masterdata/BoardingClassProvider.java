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
package jp.co.ntt.atrs.domain.common.masterdata;

import javax.inject.Inject;

import org.springframework.stereotype.Component;

import jp.co.ntt.atrs.domain.common.masterdata.helper.BoardingClassHelper;
import jp.co.ntt.atrs.domain.model.BoardingClass;
import jp.co.ntt.atrs.domain.model.BoardingClassCd;

/**
 * 搭乗クラス情報を提供するクラス。
 * @author NTT 電電太郎
 */
@Component
public class BoardingClassProvider {

    private static final String CACHE_KEY = "boardingClass";

    /**
     * 搭乗クラス情報ヘルパ。
     */
    @Inject
    BoardingClassHelper boardingClassHelper;

    /**
     * 搭乗クラスコードに該当する搭乗クラス情報を取得する。
     * @param boardingClassCd 搭乗クラスコード
     * @return 搭乗クラス情報。該当する搭乗クラス情報がない場合はnull。
     */
    public BoardingClass getBoardingClass(BoardingClassCd boardingClassCd) {
        return boardingClassHelper.findAll(CACHE_KEY).get(boardingClassCd);
    }

    /**
     * キャッシュのリフレッシュ
     */
    public void cacheRefresh() {
        boardingClassHelper.refresh(CACHE_KEY);
    }
}
