package com.zhongchuangtiyu.denarau.Entities;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.Serializable;
import java.util.List;

/**
 * 作者：WangMeng on 2016/1/26 15:51
 * 邮箱：wangmeng.wiz@foxmail.com
 */
public class MyCourses implements Serializable
{

    /**
     * uuid : 9b356c46-2710-4c3c-8848-0c2b97f972d4
     * club_name : 中创高尔夫
     * lesson : {"name":"如何选择击球方向（上）","started_at":1455178500,"finished_at":1455185700,"course":{"coach":{"name":"麦克罗伊","title":"主教练","portrait":null},"type":"open","name":"如何选择击球方向","description":"<p>如何选择击球方向的公开课<\/p>\r\n"}}
     * rating : null
     * state : progressing
     */

    private String uuid;
    private String club_name;
    /**
     * name : 如何选择击球方向（上）
     * started_at : 1455178500
     * finished_at : 1455185700
     * course : {"coach":{"name":"麦克罗伊","title":"主教练","portrait":null},"type":"open","name":"如何选择击球方向","description":"<p>如何选择击球方向的公开课<\/p>\r\n"}
     */

    private LessonEntity lesson;
    private Object rating;
    private String state;


    public void setUuid(String uuid)
    {
        this.uuid = uuid;
    }

    public void setClub_name(String club_name)
    {
        this.club_name = club_name;
    }

    public void setLesson(LessonEntity lesson)
    {
        this.lesson = lesson;
    }

    public void setRating(Object rating)
    {
        this.rating = rating;
    }

    public void setState(String state)
    {
        this.state = state;
    }

    public String getUuid()
    {
        return uuid;
    }

    public String getClub_name()
    {
        return club_name;
    }

    public LessonEntity getLesson()
    {
        return lesson;
    }

    public Object getRating()
    {
        return rating;
    }

    public String getState()
    {
        return state;
    }

    public static class LessonEntity implements Serializable
    {
        private String name;
        private int started_at;
        private int finished_at;
        /**
         * coach : {"name":"麦克罗伊","title":"主教练","portrait":null}
         * type : open
         * name : 如何选择击球方向
         * description : <p>如何选择击球方向的公开课</p>

         */

        private CourseEntity course;

        public void setName(String name)
        {
            this.name = name;
        }

        public void setStarted_at(int started_at)
        {
            this.started_at = started_at;
        }

        public void setFinished_at(int finished_at)
        {
            this.finished_at = finished_at;
        }

        public void setCourse(CourseEntity course)
        {
            this.course = course;
        }

        public String getName()
        {
            return name;
        }

        public int getStarted_at()
        {
            return started_at;
        }

        public int getFinished_at()
        {
            return finished_at;
        }

        public CourseEntity getCourse()
        {
            return course;
        }

        public static class CourseEntity implements Serializable
        {
            /**
             * name : 麦克罗伊
             * title : 主教练
             * portrait : null
             */

            private CoachEntity coach;
            private String type;
            private String name;
            private String description;

            public void setCoach(CoachEntity coach)
            {
                this.coach = coach;
            }

            public void setType(String type)
            {
                this.type = type;
            }

            public void setName(String name)
            {
                this.name = name;
            }

            public void setDescription(String description)
            {
                this.description = description;
            }

            public CoachEntity getCoach()
            {
                return coach;
            }

            public String getType()
            {
                return type;
            }

            public String getName()
            {
                return name;
            }

            public String getDescription()
            {
                return description;
            }

            public static class CoachEntity implements Serializable
            {
                private String name;
                private String title;
                private String portrait;

                public void setName(String name)
                {
                    this.name = name;
                }

                public void setTitle(String title)
                {
                    this.title = title;
                }

                public void setPortrait(String portrait)
                {
                    this.portrait = portrait;
                }

                public String getName()
                {
                    return name;
                }

                public String getTitle()
                {
                    return title;
                }

                public String getPortrait()
                {
                    return portrait;
                }
            }
        }

    }
    public static List<MyCourses> instance(String str)
    {

        Gson gson = new Gson();
        return gson.fromJson(str, new TypeToken<List<MyCourses>>() {
        }.getType());
    }
}
