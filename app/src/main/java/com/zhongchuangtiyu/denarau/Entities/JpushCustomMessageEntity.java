package com.zhongchuangtiyu.denarau.Entities;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

/**
 * 作者：WangMeng on 2015/11/26 16:02
 * 邮箱：wangmeng.wiz@foxmail.com
 */
public class JpushCustomMessageEntity
{


    /**
     * club_uuid : club_uuid
     * redirect_to : redirect_to
     */

    private String club_uuid;
    private String redirect_to;

    public static JpushCustomMessageEntity instance(String str)
    {

        Gson gson = new Gson();
        return gson.fromJson(str, new TypeToken<JpushCustomMessageEntity>() {
        }.getType());
    }

    public void setClub_uuid(String club_uuid)
    {
        this.club_uuid = club_uuid;
    }

    public void setRedirect_to(String redirect_to)
    {
        this.redirect_to = redirect_to;
    }

    public String getClub_uuid()
    {
        return club_uuid;
    }

    public String getRedirect_to()
    {
        return redirect_to;
    }
}
