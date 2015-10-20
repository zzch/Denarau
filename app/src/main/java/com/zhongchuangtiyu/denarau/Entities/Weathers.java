package com.zhongchuangtiyu.denarau.Entities;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.Serializable;
import java.util.List;

/**
 * Created by WangMeng on 2015/10/19.
 */
public class Weathers implements Serializable
{

    /**
     * date : 1445212800
     * day_of_week : 1
     * content : 多云
     * day_code : 4
     * maximum_temperature : 23
     * minimum_temperature : 11
     * probability_of_precipitation : 0%
     * wind : 微风小于3级转东风3~4级
     */

    private int date;
    private int day_of_week;
    private String content;
    private int day_code;
    private int maximum_temperature;
    private int minimum_temperature;
    private String probability_of_precipitation;
    private String wind;

    public void setDate(int date)
    {
        this.date = date;
    }

    public void setDay_of_week(int day_of_week)
    {
        this.day_of_week = day_of_week;
    }

    public void setContent(String content)
    {
        this.content = content;
    }

    public void setDay_code(int day_code)
    {
        this.day_code = day_code;
    }

    public void setMaximum_temperature(int maximum_temperature)
    {
        this.maximum_temperature = maximum_temperature;
    }

    public void setMinimum_temperature(int minimum_temperature)
    {
        this.minimum_temperature = minimum_temperature;
    }

    public void setProbability_of_precipitation(String probability_of_precipitation)
    {
        this.probability_of_precipitation = probability_of_precipitation;
    }

    public void setWind(String wind)
    {
        this.wind = wind;
    }

    public int getDate()
    {
        return date;
    }

    public int getDay_of_week()
    {
        return day_of_week;
    }

    public String getContent()
    {
        return content;
    }

    public int getDay_code()
    {
        return day_code;
    }

    public int getMaximum_temperature()
    {
        return maximum_temperature;
    }

    public int getMinimum_temperature()
    {
        return minimum_temperature;
    }

    public String getProbability_of_precipitation()
    {
        return probability_of_precipitation;
    }

    public String getWind()
    {
        return wind;
    }
    public static List<Weathers> instance(String str)
    {
        Gson gson = new Gson();
        return gson.fromJson(str,new TypeToken<List<Weathers>>(){}.getType());
    }
}
