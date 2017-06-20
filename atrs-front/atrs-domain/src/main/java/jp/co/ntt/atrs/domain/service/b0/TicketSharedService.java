/*
 * Copyright(c) 2017 NTT Corporation.
 */
package jp.co.ntt.atrs.domain.service.b0;

import jp.co.ntt.atrs.domain.model.BoardingClassCd;
import jp.co.ntt.atrs.domain.model.FareType;
import jp.co.ntt.atrs.domain.model.Flight;
import jp.co.ntt.atrs.domain.model.Reservation;

import org.joda.time.LocalDate;
import org.terasoluna.gfw.common.exception.BusinessException;

import java.util.Date;
import java.util.List;

/**
 * チケット予約共通サービスインタフェース。
 * @author NTT 電電次郎
 */
public interface TicketSharedService {

    /**
     * 照会可能限界日付を取得する。
     * @return 照会可能限界日付
     */
    LocalDate getSearchLimitDate();

    /**
     * 選択フライト情報の業務ロジックチェックを行う。
     * @param flightList 選択フライト情報一覧
     * @throws BusinessException チェックに引っかかった場合
     */
    void validateFlightList(List<Flight> flightList) throws BusinessException;

    /**
     * 搭乗日の有効性チェックを実行する。
     * @param departureDate 搭乗日
     * @throws BusinessException 有効性チェックに引っかかった場合
     */
    void validateDepatureDate(Date departureDate) throws BusinessException;

    /**
     * 搭乗日が運賃種別の予約可能時期に含まれるかをチェックする。
     * <p>
     * 予約可能となる条件: 予約可能前日数(始) >= 搭乗日前日数 >= 予約可能前日数(終)
     * </p>
     * @param fareType 運賃種別
     * @param depDate 搭乗日
     * @return 予約可能の場合trueを返却、予約不可の場合はfalseを返却
     */
    boolean isAvailableFareType(FareType fareType, Date depDate);

    /**
     * 基本運賃を計算する。
     * @param basicFareOfRoute 区間の基本運賃
     * @param boardingClassCd 搭乗クラスコード
     * @param depDate 搭乗日
     * @return 基本運賃
     */
    int calculateBasicFare(int basicFareOfRoute,
            BoardingClassCd boardingClassCd, Date depDate);

    /**
     * 運賃を計算する。
     * @param basicFare 基本運賃
     * @param discountRate 割引率
     * @return 運賃
     */
    int calculateFare(int basicFare, int discountRate);

    /**
     * フライト情報の存在をチェックする。
     * @param flight フライト情報
     * @return 存在：true 不存在：false
     */
    boolean existsFlight(Flight flight);

    /**
     * 予約情報を通知する。
     * @param reservation 予約情報
     */
    public void notifyReservation(Reservation reservation);
}
