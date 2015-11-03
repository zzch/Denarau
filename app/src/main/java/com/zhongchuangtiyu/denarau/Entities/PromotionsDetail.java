package com.zhongchuangtiyu.denarau.Entities;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.List;

/**
 * 作者：WangMeng on 2015/11/3 15:35
 * 邮箱：wangmeng.wiz@foxmail.com
 */
public class PromotionsDetail
{

    /**
     * title : 08月12日商城活动公告
     * content : Magni maiores veniam similique dolore id amet doloribus quia et ipsa quibusdam alias qui est ab et possimus minus aut illo quaerat aperiam incidunt ad facere ipsum quam repudiandae vel quia rerum expedita architecto aut recusandae tempora sunt laudantium corrupti quia a et optio iure dolorem deserunt et voluptatem tenetur cum soluta ut ea totam eos velit earum voluptas eius quis vitae quia et perferendis voluptatum accusantium omnis rem in nihil sint aliquid quo perspiciatis voluptas exercitationem aut placeat et.
     * published_at : 1446321495
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

    public static PromotionsDetail instance(String str)
    {

        Gson gson = new Gson();
        return gson.fromJson(str, new TypeToken<PromotionsDetail>() {
        }.getType());
    }
}
