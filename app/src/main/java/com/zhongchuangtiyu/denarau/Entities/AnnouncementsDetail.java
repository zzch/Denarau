package com.zhongchuangtiyu.denarau.Entities;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.Serializable;
import java.util.List;

/**
 * Created by WangMeng on 2015/10/19.
 */
public class AnnouncementsDetail implements Serializable
{

    /**
     * title : 致会员们的一封信
     * content : Praesentium eos voluptas nobis tempore asperiores accusamus optio magnam et sint perspiciatis qui ipsum voluptatem fugit dolore beatae rerum voluptatem enim non.
     * published_at : 1443528823
     */

    private String title;
    private String content;
    private int published_at;

    public void setTitle(String title)
    {
        this.title = title;
    }

    public void setContent(String content)
    {
        this.content = content;
    }

    public void setPublished_at(int published_at)
    {
        this.published_at = published_at;
    }

    public String getTitle()
    {
        return title;
    }

    public String getContent()
    {
        return content;
    }

    public int getPublished_at()
    {
        return published_at;
    }
    public static AnnouncementsDetail instance(String str)
    {

        Gson gson = new Gson();
        return gson.fromJson(str, new TypeToken<AnnouncementsDetail>() {
        }.getType());
    }
}
