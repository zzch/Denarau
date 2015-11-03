package com.zhongchuangtiyu.denarau.Entities;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.Serializable;
import java.util.List;

/**
 * 作者：WangMeng on 2015/11/3 14:09
 * 邮箱：wangmeng.wiz@foxmail.com
 */
public class Promotions implements Serializable
{

    /**
     * uuid : de905491-6cc8-43a7-ba5b-1655ef7f6cbf
     * image : http://seminole-staging.oss-cn-beijing.aliyuncs.com/assets/w600_h200_fl_q80_1b6a2df77323a0fd2208eab6c623c6b2.jpg
     * published_at : 1446169619
     */

    private String uuid;
    private String image;
    private int published_at;

    public void setUuid(String uuid)
    {
        this.uuid = uuid;
    }

    public void setImage(String image)
    {
        this.image = image;
    }

    public void setPublished_at(int published_at)
    {
        this.published_at = published_at;
    }

    public String getUuid()
    {
        return uuid;
    }

    public String getImage()
    {
        return image;
    }

    public int getPublished_at()
    {
        return published_at;
    }
    public static List<Promotions> instance(String str)
    {

        Gson gson = new Gson();
        return gson.fromJson(str, new TypeToken<List<Promotions>>() {
        }.getType());
    }
}
