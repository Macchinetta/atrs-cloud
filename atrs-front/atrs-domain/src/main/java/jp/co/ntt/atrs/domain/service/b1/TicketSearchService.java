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
package jp.co.ntt.atrs.domain.service.b1;

import org.springframework.data.domain.Pageable;
import org.terasoluna.gfw.common.exception.BusinessException;

/**
 * 空席照会サービスインタフェース。
 * @author NTT 電電次郎
 */
public interface TicketSearchService {

    /**
     * 空席照会を行う。
     * <p>
     * 検索条件に合致する便と、その便の運賃種別ごとの運賃・空席数を返却する。 便は出発時刻の昇順でソートされる。
     * </p>
     * @param searchCriteria 空席照会の検索条件
     * @param pageable ページネーション検索条件
     * @return 空席照会の検索結果
     * @throws BusinessException 該当する空席情報存在しない場合の業務例外
     */
    TicketSearchResultDto searchFlight(TicketSearchCriteriaDto searchCriteria,
            Pageable pageable) throws BusinessException;
}