package com.zhongchuangtiyu.denarau.Entities;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.Serializable;
import java.util.List;

/**
 * Created by WangMeng on 2015/10/19.
 */
public class UsersDetail implements Serializable
{

    /**
     * name : 王皓
     * gender : male
     * portrait : null
     * birthday : 19843200
     */

    private String name;
    private String gender;
    private String portrait;
    private String birthday;

    public void setName(String name)
    {
        this.name = name;
    }

    public void setGender(String gender)
    {
        this.gender = gender;
    }

    public void setPortrait(String portrait)
    {
        this.portrait = portrait;
    }

    public void setBirthday(String birthday)
    {
        this.birthday = birthday;
    }

    public String getName()
    {
        return name;
    }

    public String getGender()
    {
        return gender;
    }

    public String getPortrait()
    {
        return portrait;
    }

    public String getBirthday()
    {
        return birthday;
    }
    public static UsersDetail instance(String str)
    {

        Gson gson = new Gson();
        return gson.fromJson(str, new TypeToken<UsersDetail>() {
        }.getType());
    }
}
