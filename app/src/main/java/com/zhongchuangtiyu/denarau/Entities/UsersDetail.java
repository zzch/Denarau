package com.zhongchuangtiyu.denarau.Entities;

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
}
