package com.neuedu.utils;

import java.math.BigDecimal;

public class BigDecimalUtils {

    public static BigDecimal add(double d1,double d2)
    {
        BigDecimal bigDecimal=new BigDecimal(String.valueOf(d1));
        BigDecimal bigDecimal1=new BigDecimal(String.valueOf(d2));
        return bigDecimal.add(bigDecimal1);
    }
    public static BigDecimal sub(double d1,double d2)
    {
        BigDecimal bigDecimal=new BigDecimal(String.valueOf(d1));
        BigDecimal bigDecimal1=new BigDecimal(String.valueOf(d2));
        return bigDecimal.subtract(bigDecimal1);
    }
    public static BigDecimal mul(double d1,double d2)
    {
        BigDecimal bigDecimal=new BigDecimal(String.valueOf(d1));
        BigDecimal bigDecimal1=new BigDecimal(String.valueOf(d2));
        return bigDecimal.multiply(bigDecimal1);
    }
    public static BigDecimal div(double d1,double d2)
    {
        BigDecimal bigDecimal=new BigDecimal(String.valueOf(d1));
        BigDecimal bigDecimal1=new BigDecimal(String.valueOf(d2));
        return bigDecimal.divide(bigDecimal1,2,BigDecimal.ROUND_HALF_UP);
    }

    public static void main(String[] args) {
        BigDecimal a=new BigDecimal("0");
        BigDecimal b=new BigDecimal("200");
        BigDecimal c=BigDecimalUtils.add(a.doubleValue(),b.doubleValue());
        System.out.println(c);
    }
}
