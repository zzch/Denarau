package com.zhongchuangtiyu.denarau.Entities;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.List;

/**
 * Created by WangMeng on 2015/10/19.
 */
public class FeedBacks
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
    public static List<FeedBacks> instance(String str)
    {

        Gson gson = new Gson();
        return gson.fromJson(str, new TypeToken<List<FeedBacks>>() {
        }.getType());
    }
}
