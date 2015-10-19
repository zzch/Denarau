package com.zhongchuangtiyu.denarau.Entities;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.List;

/**
 * Created by WangMeng on 2015/10/19.
 */
public class Sign_In
{

    /**
     * user : {"name":"王萌","gender":"male","portrait":null,"birthday":null,"token":"uuqt55Ip4jyX3t9teN-XTA"}
     * club : {"uuid":"ed38a929-d23d-4172-85cf-2c45cf09b484"}
     */

    private UserEntity user;
    private ClubEntity club;

    public void setUser(UserEntity user)
    {
        this.user = user;
    }

    public void setClub(ClubEntity club)
    {
        this.club = club;
    }

    public UserEntity getUser()
    {
        return user;
    }

    public ClubEntity getClub()
    {
        return club;
    }

    public static class UserEntity
    {
        /**
         * name : 王萌
         * gender : male
         * portrait : null
         * birthday : null
         * token : uuqt55Ip4jyX3t9teN-XTA
         */

        private String name;
        private String gender;
        private Object portrait;
        private Object birthday;
        private String token;

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

        public void setBirthday(Object birthday)
        {
            this.birthday = birthday;
        }

        public void setToken(String token)
        {
            this.token = token;
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

        public Object getBirthday()
        {
            return birthday;
        }

        public String getToken()
        {
            return token;
        }
    }

    public static class ClubEntity
    {
        /**
         * uuid : ed38a929-d23d-4172-85cf-2c45cf09b484
         */

        private String uuid;

        public void setUuid(String uuid)
        {
            this.uuid = uuid;
        }

        public String getUuid()
        {
            return uuid;
        }
    }
    public static List<Sign_In> instance(String str)
    {

        Gson gson = new Gson();
        return gson.fromJson(str, new TypeToken<List<Sign_In>>() {
        }.getType());
    }
}
