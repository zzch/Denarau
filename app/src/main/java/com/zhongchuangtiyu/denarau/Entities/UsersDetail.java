package com.zhongchuangtiyu.denarau.Entities;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.List;

/**
 * Created by WangMeng on 2015/10/19.
 */
public class UsersDetail
{

    /**
     * name : 王皓
     * gender : male
     * portrait : null
     * birthday : 19843200
     */

    private String name;
    private String gender;
    private Object portrait;
    private int birthday;

    public void setName(String name)
    {
        this.name = name;
    }

    public void setGender(String gender)
    {
        this.gender = gender;
    }

    public void setPortrait(Object portrait)
    {
        this.portrait = portrait;
    }

    public void setBirthday(int birthday)
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

    public Object getPortrait()
    {
        return portrait;
    }

    public int getBirthday()
    {
        return birthday;
    }
    public static List<UsersDetail> instance(String str)
    {

        Gson gson = new Gson();
        return gson.fromJson(str, new TypeToken<List<UsersDetail>>() {
        }.getType());
    }
}
