package com.zhongchuangtiyu.denarau.Entities;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.Serializable;
import java.util.List;

/**
 * Created by WangMeng on 2015/10/19.
 */
public class ClubsMembership implements Serializable
{

    /**
     * uuid : 26868cf8-04b7-4cd6-87e0-c2558a638548
     * name : 北京珀翡高尔夫练习场
     * logo : http://seminole-staging.oss-cn-beijing.aliyuncs.com/assets/w150_h150_fl_q50_982986023f22538f858663fe1bbeda3a.jpg
     */

    private String uuid;
    private String name;
    private String logo;
    private String phone_number;
    private String address;

    public String getPhone_number()
    {
        return phone_number;
    }

    public void setPhone_number(String phone_number)
    {
        this.phone_number = phone_number;
    }

    public String getAddress()
    {
        return address;
    }

    public void setAddress(String address)
    {
        this.address = address;
    }

    public void setUuid(String uuid)
    {
        this.uuid = uuid;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public void setLogo(String logo)
    {
        this.logo = logo;
    }

    public String getUuid()
    {
        return uuid;
    }

    public String getName()
    {
        return name;
    }

    public String getLogo()
    {
        return logo;
    }

    public static List<ClubsMembership> instance(String str)
    {

        Gson gson = new Gson();
        return gson.fromJson(str, new TypeToken<List<ClubsMembership>>() {
        }.getType());
    }
}
