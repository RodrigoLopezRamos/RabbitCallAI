package com.analia.common.util;

import com.analia.common.infrastructure.location.GeoLocation;
import com.analia.common.model.resultset.TradeResultSet;
import com.analia.common.model.resultset.VoucherResultSet;

import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

/**
 * @author Rodrigo Lopez
 * Utility class
 */
public class ArrayUtils {

    /**
     * @param geolocations
     * @return
     */
    public static <T extends GeoLocation> List<T> sortByDistance(List<T> geolocations) {
        Collections.sort(geolocations, new Comparator<T>() {
        
            public int compare(T o1, T o2) {
                return Double.compare(o2.getDistance(), o1.getDistance());
            }
        });
        Collections.reverse(geolocations);
        return geolocations;
    }


    public static List<TradeResultSet> sortTradesByStartDate(List<TradeResultSet> trades) {
        Collections.sort(trades, new Comparator<TradeResultSet>() {
        
            public int compare(TradeResultSet o1, TradeResultSet o2) {
                Date startDate1 = o1.getStartDate();
                Date startDate2 = o2.getStartDate();

                if (startDate1 == null && startDate2 == null) {
                    return Double.compare(o2.getDistance(), o1.getDistance());
                } else if (startDate1 != null && startDate2 != null) {
                    if (startDate1.equals(startDate2)) {
                        return Double.compare(o2.getDistance(), o1.getDistance());
                    } else {
                        return Long.compare(startDate1.getTime(), startDate2.getTime());
                    }
                } else if (startDate1 != null) {
                    return -1;
                } else {
                    return 1;
                }
            }

        });
        return trades;
    }


    public static List<VoucherResultSet> sortVouchersByStartDate(List<VoucherResultSet> vouchers) {
        Collections.sort(vouchers, new Comparator<VoucherResultSet>() {
        
            public int compare(VoucherResultSet o1, VoucherResultSet o2) {
                Date startDate1 = o1.getStartDate();
                Date startDate2 = o2.getStartDate();

                if (startDate1 == null && startDate2 == null) {
                    return Double.compare(o2.getDistance(), o1.getDistance());
                } else if (startDate1 != null && startDate2 != null) {
                    if (startDate1.equals(startDate2)) {
                        return Double.compare(o2.getDistance(), o1.getDistance());
                    } else {
                        return Long.compare(startDate1.getTime(), startDate2.getTime());
                    }
                } else if (startDate1 != null) {
                    return -1;
                } else {
                    return 1;
                }
            }

        });
        return vouchers;
    }
}
