package com.zhongchuangtiyu.denarau.Entities;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.Serializable;
import java.util.List;

/**
 * Created by wangm on 2015/10/20.
 */
public class Reservations implements Serializable
{


    /**
     * name : 中创高尔夫
     */

    private ClubEntity club;
    /**
     * club : {"name":"中创高尔夫"}
     * will_playing_at : 11111
     * state : submitted
     */

    private long will_playing_at;
    private String state;
    public void setClub(ClubEntity club)
    {
        this.club = club;
    }

    public void setWill_playing_at(long will_playing_at)
    {
        this.will_playing_at = will_playing_at;
    }

    public void setState(String state)
    {
        this.state = state;
    }

    public ClubEntity getClub()
    {
        return club;
    }

    public long getWill_playing_at()
    {
        return will_playing_at;
    }

    public String getState()
    {
        return state;
    }

    public static class ClubEntity
    {
        private String name;

        public void setName(String name)
        {
            this.name = name;
        }

        public String getName()
        {
            return name;
        }
    }
    public static List<Reservations> instance(String str)
    {

        Gson gson = new Gson();
        return gson.fromJson(str, new TypeToken<List<Reservations>>() {
        }.getType());
    }
}
