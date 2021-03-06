package com.zhongchuangtiyu.denarau.Entities;

import com.google.gson.Gson;

import java.io.Serializable;

/**
 * Created by WangMeng on 2015/10/19.
 */
public class Sign_In implements Serializable
{

    /**
     * user : {"name":"王萌","gender":"male","portrait":null,"birthday":null,"token":"uuqt55Ip4jyX3t9teN-XTA"}
     * club : {"uuid":"ed38a929-d23d-4172-85cf-2c45cf09b484"}
     */

    private UserEntity user;
    private ClubEntity club;
    /**
     * exception_code : 20003
     * message : 验证码不正确
     */

    private int exception_code;
    private String message;

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

    public void setException_code(int exception_code)
    {
        this.exception_code = exception_code;
    }

    public void setMessage(String message)
    {
        this.message = message;
    }

    public int getException_code()
    {
        return exception_code;
    }

    public String getMessage()
    {
        return message;
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
        private String portrait;
        private String birthday;
        private String token;
        private String uuid;

        public String getUuid()
        {
            return uuid;
        }

        public void setUuid(String uuid)
        {
            this.uuid = uuid;
        }

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

        public String getPortrait()
        {
            return portrait;
        }

        public String getBirthday()
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
    public static Sign_In instance(String string)
    {

        Gson gson = new Gson();
        return gson.fromJson(string,Sign_In.class);
    }
}
