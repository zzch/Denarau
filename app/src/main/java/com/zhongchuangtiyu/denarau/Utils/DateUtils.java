package com.zhongchuangtiyu.denarau.Utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

/**
 * Created by wangm on 2015/11/1.
 */
public class DateUtils
{

    private static SimpleDateFormat sf = null;

    /*获取系统时间 格式为："yyyy/MM/dd "*/
    public static String getCurrentDate()
    {
        Date d = new Date();
        sf = new SimpleDateFormat("yyyy年MM月dd日");
        return sf.format(d);
    }

    /*时间戳转换成字符串*/
    public static String getDateToString(long time)
    {
        Date d = new Date(time);
        sf = new SimpleDateFormat("yyyy年MM月dd日");
        return sf.format(d);
    }

    /*将字符串转为时间戳*/
    public static long getStringToDate(String time)
    {
        sf = new SimpleDateFormat("yyyy年MM月dd日");
        Date date = new Date();
        try
        {
            date = sf.parse(time);
        } catch (ParseException e)
        {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return date.getTime();
    }
    /*时间戳转换成字符串MM dd*/
    public static String getDateToString1(long time)
    {
        Date d = new Date(time * 1000);
        sf = new SimpleDateFormat("MM月dd日");
//        sf.setTimeZone(TimeZone.getTimeZone("GMT"));
        return sf.format(d);
    }
    /*时间戳转换成字符串 HH-mm*/
    public static String getDateToString2(long time)
    {
        Date d = new Date(time * 1000);
        sf = new SimpleDateFormat("HH:mm");
//        sf.setTimeZone(TimeZone.getTimeZone("GMT"));
        return sf.format(d);
    }
    public static String getDateToString3(long time)
    {
        Date d = new Date(time * 1000);
        sf = new SimpleDateFormat("MM-dd");
        return sf.format(d);
    }
//    yyyy.mm.dd
public static String getDateToString4(long time)
{
    Date d = new Date(time);
    sf = new SimpleDateFormat("yyyy.MM.dd");
    return sf.format(d);
}
//    yyyy/mm/dd
public static String getDateToString8(long time)
{
    Date d = new Date(time);
    sf = new SimpleDateFormat("yyyy/MM/dd");
    return sf.format(d);
}
//    MM
public static String getDateToString5(long time)
{
    Date d = new Date(time * 1000);
    sf = new SimpleDateFormat("MM月");
    return sf.format(d);
}
//    dd
public static String getDateToString6(long time) {
    Date d = new Date(time * 1000);
    sf = new SimpleDateFormat("dd");
    return sf.format(d);
}
//   MMddHHmm
public static String getDateToString7(long time)
{
    Date d = new Date(time * 1000);
    sf = new SimpleDateFormat("MM月dd日 HH:ss");
    return sf.format(d);
}
}