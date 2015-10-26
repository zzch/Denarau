package com.zhongchuangtiyu.denarau.Entities;

import android.content.Context;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.Serializable;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * Created by WangMeng on 2015/10/19.
 */
public class Coaches implements Serializable
{


    private List<FeaturedEntity> featured;
    private List<NormalEntity> normal;

    private String uuid;
    private String name;
    private Object portrait;
    private String gender;
    private String title;

    public final static String FEATURED = "featured";
    public final static String NORMAL = "normal";

    private String coachType = null;



    public void setUuid(String uuid)
    {
        this.uuid = uuid;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public void setPortrait(Object portrait)
    {
        this.portrait = portrait;
    }

    public void setGender(String gender)
    {
        this.gender = gender;
    }

    public void setTitle(String title)
    {
        this.title = title;
    }

    public String getUuid()
    {
        return uuid;
    }

    public String getName()
    {
        return name;
    }

    public Object getPortrait()
    {
        return portrait;
    }

    public String getGender()
    {
        return gender;
    }

    public String getTitle()
    {
        return title;
    }


    public void setFeatured(List<FeaturedEntity> featured)
    {
        this.featured = featured;
    }

    public void setNormal(List<NormalEntity> normal)
    {
        this.normal = normal;
    }

    public List<FeaturedEntity> getFeatured()
    {
        return featured;
    }

    public List<NormalEntity> getNormal()
    {
        return normal;
    }

    public List<Coaches> generateListInfo()
    {
        List<Coaches> result = new ArrayList<>();
        if(getFeatured()!=null&&getFeatured().size()>0)
        {
            for (FeaturedEntity entity :
                    getFeatured())
            {
                Coaches tmp = new Coaches();
                tmp.setName(entity.getName());
                tmp.setGender(entity.getGender());
                tmp.setPortrait(entity.getPortrait());
                tmp.setTitle(entity.getTitle());
                tmp.setUuid(entity.getUuid());
                tmp.setCoachType(Coaches.FEATURED);
                result.add(tmp);
            }
        }
        if(getNormal()!=null&&getNormal().size()>0)
        {
            for (NormalEntity entity :
                    getNormal())
            {
                Coaches tmp = new Coaches();
                tmp.setName(entity.getName());
                tmp.setGender(entity.getGender());
                tmp.setPortrait(entity.getPortrait());
                tmp.setTitle(entity.getTitle());
                tmp.setUuid(entity.getUuid());
                tmp.setCoachType(Coaches.NORMAL);
                result.add(tmp);
            }
        }
        return result;
    }

    public String getCoachType()
    {
        return coachType;
    }

    public void setCoachType(String coachType)
    {
        this.coachType = coachType;
    }


    public static class FeaturedEntity{

        private String uuid;
        private String name;
        private Object portrait;
        private String gender;
        private String title;

        public void setUuid(String uuid)
        {
            this.uuid = uuid;
        }

        public void setName(String name)
        {
            this.name = name;
        }

        public void setPortrait(Object portrait)
        {
            this.portrait = portrait;
        }

        public void setGender(String gender)
        {
            this.gender = gender;
        }

        public void setTitle(String title)
        {
            this.title = title;
        }

        public String getUuid()
        {
            return uuid;
        }

        public String getName()
        {
            return name;
        }

        public Object getPortrait()
        {
            return portrait;
        }

        public String getGender()
        {
            return gender;
        }

        public String getTitle()
        {
            return title;
        }
    }

    public static class NormalEntity
    {
        /**
         * uuid : 37427b31-5811-4afa-b11e-6012061a6d2a
         * name : 魏思聪
         * portrait : http://seminole-staging.oss-cn-beijing.aliyuncs.com/assets/w300_h400_fl_q80_8f203837fcc1f02234ea812ebd688467.jpg
         * gender : female
         * title : 优秀教练
         */

        private String uuid;
        private String name;
        private Object portrait;
        private String gender;
        private String title;

        public void setUuid(String uuid)
        {
            this.uuid = uuid;
        }

        public void setName(String name)
        {
            this.name = name;
        }

        public void setPortrait(String portrait)
        {
            this.portrait = portrait;
        }

        public void setGender(String gender)
        {
            this.gender = gender;
        }

        public void setTitle(String title)
        {
            this.title = title;
        }

        public String getUuid()
        {
            return uuid;
        }

        public String getName()
        {
            return name;
        }

        public Object getPortrait()
        {
            return portrait;
        }

        public String getGender()
        {
            return gender;
        }

        public String getTitle()
        {
            return title;
        }
    }
    public static Coaches  instance(String str)
    {

        Gson gson = new Gson();
        return gson.fromJson(str, new TypeToken<Coaches>() {
        }.getType());
    }
}
