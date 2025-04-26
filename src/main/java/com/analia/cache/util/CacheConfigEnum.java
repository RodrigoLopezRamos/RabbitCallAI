package com.analia.cache.util;

public enum CacheConfigEnum {
    TRADES(1, "TRADES"),
    TRADE_ADDITIONAL_DATA(2, "TRADE_ADDITIONAL_DATA"),
    TRADE_TAGS(3, "TRADE_TAGS"),
    ZONES(4, "ZONES"),
    SETTINGS(5, "SETTINGS"),
    VOUCHER(6, "VOUCHER"),
    WORDPRESS(7, "WORDPRESS_POST"),
    WOOCOMMERCE(8, "WOOCOMMERCE");


    private final int id;
    private String name;

    CacheConfigEnum(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public int getId() {
        return id;
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


    /**
     * @param calendarDayId
     * @return
     * @throws IOException
     */
    public CacheConfigEnum getCacheConfig(int cacheConfigId) {
        for (CacheConfigEnum cacheConfigEnum : CacheConfigEnum.values()) {
            if (cacheConfigEnum.getId() == cacheConfigId) {
                return cacheConfigEnum;
            }
        }
        throw new IllegalArgumentException("CacheConfigEnum not found");
    }

}
