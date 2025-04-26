package com.analia.common.util;


import jakarta.inject.Inject;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;

import java.math.BigInteger;

public final class Base26 {

    private static final int BASE_10 = 10;
    private static final int BASE_26 = 26;


    private Base26() {
        // no instances allowed!
    }

    public static String encode(@NotNull BigInteger value) {
        return value.toString(BASE_26);
    }

    public static BigInteger decode(@NotNull String value) {
        BigInteger result = new BigInteger(value, BASE_26);
        return new BigInteger(result.toString(BASE_10));
    }

    public static void main(String[] args) {

         String VENDOR_ID ="i54";
         String PLATFORM_ID = "1";
         String APP_VERSION ="1.0.1";

         String vendor1    = encode(BigInteger.valueOf(12302));

        String vendor2    = encode(BigInteger.valueOf(4));



        System.out.println(vendor1);

      //  System.out.println(vendor2);


      //  System.out.println(decode("i54"));


    }


}