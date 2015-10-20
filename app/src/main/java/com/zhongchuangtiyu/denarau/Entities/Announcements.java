package com.zhongchuangtiyu.denarau.Entities;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.Serializable;
import java.util.List;

/**
 * Created by wangm on 2015/10/20.
 */
public class Announcements implements Serializable
{

    /**
     * uuid : 429bb8c3-ba72-4b6e-8aea-b6767b44e0f4
     * title : 关于2015全国高尔夫球团体赛的补充通知
     * summary : Illum amet natus saepe sint ut et corporis error quidem hic debitis a ducimus voluptatibus ea non quia possimus eum.
     * published_at : 1442578361
     */

    private String uuid;
    private String title;
    private String summary;
    private int published_at;

    public void setUuid(String uuid)
    {
        this.uuid = uuid;
    }

    public void setTitle(String title)
    {
        this.title = title;
    }

    public void setSummary(String summary)
    {
        this.summary = summary;
    }

    public void setPublished_at(int published_at)
    {
        this.published_at = published_at;
    }

    public String getUuid()
    {
        return uuid;
    }

    public String getTitle()
    {
        return title;
    }

    public String getSummary()
    {
        return summary;
    }

    public int getPublished_at()
    {
        return published_at;
    }
    public static List<Announcements> instance(String str)
    {

        Gson gson = new Gson();
        return gson.fromJson(str, new TypeToken<List<Announcements>>() {
        }.getType());
    }
}
