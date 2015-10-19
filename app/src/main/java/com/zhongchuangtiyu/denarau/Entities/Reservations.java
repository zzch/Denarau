package com.zhongchuangtiyu.denarau.Entities;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.List;

/**
 * Created by wangm on 2015/10/20.
 */
public class Reservations
{

    /**
     * result : success
     */

    private String result;

    public void setResult(String result)
    {
        this.result = result;
    }

    public String getResult()
    {
        return result;
    }
    public static List<Reservations> instance(String str)
    {

        Gson gson = new Gson();
        return gson.fromJson(str, new TypeToken<List<Reservations>>() {
        }.getType());
    }
}
