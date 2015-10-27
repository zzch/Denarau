package com.zhongchuangtiyu.denarau.Entities;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.Serializable;
import java.util.List;

/**
 * Created by WangMeng on 2015/10/19.
 */
public class CoachesDetail implements Serializable
{

    /**
     * name : 秦嘉懿
     * portrait : null
     * gender : female
     * title : 金牌教练
     * description : Saepe rerum consectetur ratione sed ut perspiciatis aperiam esse et minus ea reprehenderit nihil consequatur non aut quidem qui corporis accusantium aliquid totam debitis molestiae incidunt quisquam amet est ut nemo corrupti aut enim et ex maiores quaerat sequi quasi autem officiis illo qui officia molestiae culpa nobis vitae velit omnis quam velit accusamus eligendi voluptas repellendus doloribus sed voluptatem tempora non inventore eveniet rerum soluta natus blanditiis labore rem in omnis eos sint itaque nisi cupiditate dicta voluptatem sint et ad fuga ut magnam.
     * courses : [{"uuid":"f178ed1d-e1b8-4b73-8863-42a35f9d6722","name":"心理课","price":"12600.0","lessons":10},{"uuid":"30f739a5-5918-4ac1-8dfb-65858a396b4e","name":"入门课","price":"8700.0","lessons":14},{"uuid":"497d62b8-ca9a-495a-913c-c5cd7b689ce1","name":"综合提升课","price":"14000.0","lessons":9},{"uuid":"1a5b7ba0-ffd9-4722-abc1-f33c60bf66ed","name":"技术课","price":"9600.0","lessons":11}]
     */

    private String name;
    private Object portrait;
    private String gender;
    private String title;
    private String description;
    private List<CoursesEntity> courses;

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

    public void setDescription(String description)
    {
        this.description = description;
    }

    public void setCourses(List<CoursesEntity> courses)
    {
        this.courses = courses;
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

    public String getDescription()
    {
        return description;
    }

    public List<CoursesEntity> getCourses()
    {
        return courses;
    }

    public static class CoursesEntity
    {
        /**
         * uuid : f178ed1d-e1b8-4b73-8863-42a35f9d6722
         * name : 心理课
         * price : 12600.0
         * lessons : 10
         */

        private String uuid;
        private String name;
        private String price;
        private int lessons;

        public void setUuid(String uuid)
        {
            this.uuid = uuid;
        }

        public void setName(String name)
        {
            this.name = name;
        }

        public void setPrice(String price)
        {
            this.price = price;
        }

        public void setLessons(int lessons)
        {
            this.lessons = lessons;
        }

        public String getUuid()
        {
            return uuid;
        }

        public String getName()
        {
            return name;
        }

        public String getPrice()
        {
            return price;
        }

        public int getLessons()
        {
            return lessons;
        }
    }
    public static CoachesDetail instance(String str)
    {

        Gson gson = new Gson();
        return gson.fromJson(str, new TypeToken<CoachesDetail>() {
        }.getType());
    }
}
