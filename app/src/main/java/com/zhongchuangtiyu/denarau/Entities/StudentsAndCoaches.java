package com.zhongchuangtiyu.denarau.Entities;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;
import java.util.List;

/**
 * 作者：WangMeng on 2016/1/18 15:43
 * 邮箱：wangmeng.wiz@foxmail.com
 */
public class StudentsAndCoaches
{

    /**
     * course : {"uuid":"ec5c5b36-cedf-4615-9170-8bc02a18ee36","name":"如何选择击球方向","type":"open"}
     * total_lessons : 3
     * expired_at : 1454744328
     */

    private List<StudentsEntity> students;
    /**
     * uuid : 1cfa8acf-79ab-4a1d-9e93-efacc499c5d1
     * name : 侯展鹏
     * portrait : http://seminole-staging.oss-cn-beijing.aliyuncs.com/assets/w300_h400_fl_q80_f1eb13c1edc734164afe18fc91c10beb.jpg
     * gender : male
     * title : 金牌教练
     * starting_price : 9200.0
     * description : null
     */

    private List<FeaturedEntity> featured;
    /**
     * uuid : 7ff29a0b-7956-46ba-ba76-a720c036891a
     * name : 唐彬
     * portrait : null
     * gender : male
     * title : 五星教练
     * starting_price : 11200.0
     * description : Sapiente porro repellendus et eligendi nihil numquam a nobis sit rerum et qui omnis ut molestias voluptas placeat veniam recusandae accusantium dolores iure vel rerum occaecati tempora est vitae ipsum alias rem impedit voluptatibus incidunt omnis est facere temporibus odit nam aliquid maxime debitis dicta nemo odio atque tenetur illo qui delectus repudiandae quos ex qui quam iusto et tempore quia est quia architecto perferendis veritatis quibusdam illum sed sint fugiat in neque quae eaque ullam nisi esse reprehenderit id et necessitatibus eveniet dolores non.
     */

    private List<NormalEntity> normal;

    private String uuid;
    private String name;
    private Object portrait;
    private String gender;
    private String title;
    private String starting_price;
    private String description;
    private String type;
    private String total_lessons;
    private String expired_at;

    public final static String FEATURED = "featured";
    public final static String NORMAL = "normal";
    public final static String STUDENT = "student";
    private String coachType = null;


    public String getUuid()
    {
        return uuid;
    }

    public void setUuid(String uuid)
    {
        this.uuid = uuid;
    }

    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public Object getPortrait()
    {
        return portrait;
    }

    public void setPortrait(Object portrait)
    {
        this.portrait = portrait;
    }

    public String getGender()
    {
        return gender;
    }

    public void setGender(String gender)
    {
        this.gender = gender;
    }

    public String getTitle()
    {
        return title;
    }

    public void setTitle(String title)
    {
        this.title = title;
    }

    public String getStarting_price()
    {
        return starting_price;
    }

    public void setStarting_price(String starting_price)
    {
        this.starting_price = starting_price;
    }

    public String getDescription()
    {
        return description;
    }

    public void setDescription(String description)
    {
        this.description = description;
    }

    public String getType()
    {
        return type;
    }

    public void setType(String type)
    {
        this.type = type;
    }

    public String getTotal_lessons()
    {
        return total_lessons;
    }

    public void setTotal_lessons(String total_lessons)
    {
        this.total_lessons = total_lessons;
    }

    public String getExpired_at()
    {
        return expired_at;
    }

    public void setExpired_at(String expired_at)
    {
        this.expired_at = expired_at;
    }

    public void setStudents(List<StudentsEntity> students)
    {
        this.students = students;
    }

    public void setFeatured(List<FeaturedEntity> featured)
    {
        this.featured = featured;
    }

    public void setNormal(List<NormalEntity> normal)
    {
        this.normal = normal;
    }

    public List<StudentsEntity> getStudents()
    {
        return students;
    }

    public List<FeaturedEntity> getFeatured()
    {
        return featured;
    }

    public List<NormalEntity> getNormal()
    {
        return normal;
    }

    public static class StudentsEntity
    {
        /**
         * uuid : ec5c5b36-cedf-4615-9170-8bc02a18ee36
         * name : 如何选择击球方向
         * type : open
         */

        private CourseEntity course;
        private String total_lessons;
        private String expired_at;

        public void setCourse(CourseEntity course)
        {
            this.course = course;
        }

        public void setTotal_lessons(String total_lessons)
        {
            this.total_lessons = total_lessons;
        }

        public void setExpired_at(String expired_at)
        {
            this.expired_at = expired_at;
        }

        public CourseEntity getCourse()
        {
            return course;
        }

        public String getTotal_lessons()
        {
            return total_lessons;
        }

        public String getExpired_at()
        {
            return expired_at;
        }

        public static class CourseEntity
        {
            private String uuid;
            private String name;
            private String type;

            public void setUuid(String uuid)
            {
                this.uuid = uuid;
            }

            public void setName(String name)
            {
                this.name = name;
            }

            public void setType(String type)
            {
                this.type = type;
            }

            public String getUuid()
            {
                return uuid;
            }

            public String getName()
            {
                return name;
            }

            public String getType()
            {
                return type;
            }
        }
    }

    public static class FeaturedEntity
    {
        private String uuid;
        private String name;
        private String portrait;
        private String gender;
        private String title;
        private String starting_price;
        private String description;

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

        public void setStarting_price(String starting_price)
        {
            this.starting_price = starting_price;
        }

        public void setDescription(String description)
        {
            this.description = description;
        }

        public String getUuid()
        {
            return uuid;
        }

        public String getName()
        {
            return name;
        }

        public String getPortrait()
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

        public String getStarting_price()
        {
            return starting_price;
        }

        public String getDescription()
        {
            return description;
        }
    }

    public static class NormalEntity
    {
        private String uuid;
        private String name;
        private Object portrait;
        private String gender;
        private String title;
        private String starting_price;
        private String description;

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

        public void setStarting_price(String starting_price)
        {
            this.starting_price = starting_price;
        }

        public void setDescription(String description)
        {
            this.description = description;
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

        public String getStarting_price()
        {
            return starting_price;
        }

        public String getDescription()
        {
            return description;
        }
    }
    public String getCoachType()
    {
        return coachType;
    }

    public void setCoachType(String coachType)
    {
        this.coachType = coachType;
    }
    public List<StudentsAndCoaches> generateListInfo()
    {
        List<StudentsAndCoaches> result = new ArrayList<>();
        if (getFeatured() != null && getFeatured().size() > 0)
        {
            for (FeaturedEntity entity :
                    getFeatured())
            {
                StudentsAndCoaches tmp = new StudentsAndCoaches();
                tmp.setName(entity.getName());
                tmp.setGender(entity.getGender());
                tmp.setPortrait(entity.getPortrait());
                tmp.setTitle(entity.getTitle());
                tmp.setUuid(entity.getUuid());
                tmp.setStarting_price(entity.getStarting_price());
                tmp.setDescription(entity.getDescription());
                tmp.setCoachType(Coaches.FEATURED);
                result.add(tmp);
            }
        }
        if (getNormal() != null && getNormal().size() > 0)
        {
            for (NormalEntity entity :
                    getNormal())
            {
                StudentsAndCoaches tmp = new StudentsAndCoaches();
                tmp.setName(entity.getName());
                tmp.setGender(entity.getGender());
                tmp.setPortrait(entity.getPortrait());
                tmp.setTitle(entity.getTitle());
                tmp.setUuid(entity.getUuid());
                tmp.setCoachType(Coaches.NORMAL);
                tmp.setStarting_price(entity.getStarting_price());
                tmp.setDescription(entity.getDescription());
                result.add(tmp);
            }
        }
            if (getStudents() != null && getStudents().size() > 0)
            {
                for (StudentsEntity entity :
                        getStudents())
                {
                    StudentsAndCoaches tmp = new StudentsAndCoaches();
                    tmp.setExpired_at(entity.getExpired_at());
                    tmp.setTotal_lessons(entity.getTotal_lessons());
                    tmp.setName(entity.getCourse().getName());
                    tmp.setUuid(entity.getCourse().getUuid());
                    tmp.setType(entity.getCourse().getType());
                    tmp.setCoachType(StudentsAndCoaches.STUDENT);
                    result.add(tmp);
                }
            }
        return result;
    }

    public static StudentsAndCoaches  instance(String str)
    {

        Gson gson = new Gson();
        return gson.fromJson(str, new TypeToken<StudentsAndCoaches>() {
        }.getType());
    }
}
