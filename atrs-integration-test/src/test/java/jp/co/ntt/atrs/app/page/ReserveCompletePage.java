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
package jp.co.ntt.atrs.app.page;

import static com.codeborne.selenide.Selectors.byId;
import static com.codeborne.selenide.Selenide.$;

import com.codeborne.selenide.ElementsCollection;

/**
 * ReserveCompleteページのページオブジェクトクラス。
 */
public class ReserveCompletePage {

    private ElementsCollection reserveflightlist;

    /**
     * ReserveCompletePageのコンストラクタ。
     */
    public ReserveCompletePage() {
        this.reserveflightlist = $(byId("reserveflightlist")).$$("tr");
    }

    /**
     * 予約したフライトの要素を返却する。
     * @return ElementsCollection
     */
    public ElementsCollection getReserveflightlist() {
        return reserveflightlist;
    }

    /**
     * トップページを表示する。
     * @return TopPage トップページ
     */
    public TopPage toTopPage() {
        $(".forward").click();
        return new TopPage();
    }

}
