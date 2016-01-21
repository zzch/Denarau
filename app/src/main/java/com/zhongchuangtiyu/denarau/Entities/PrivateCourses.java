package com.zhongchuangtiyu.denarau.Entities;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * 作者：WangMeng on 2016/1/21 12:08
 * 邮箱：wangmeng.wiz@foxmail.com
 */
public class PrivateCourses implements Serializable
{

    /**
     * name : 高尔夫初级课程
     * description : <p>从零开始，一点点教会你打球</p>

     * coach : {"name":"巴巴沃森","title":"主教练","portrait":"http://seminole-staging.oss-cn-beijing.aliyuncs.com/assets/w300_h400_fl_q80_0f5f5cc5bcf4ccf62affb2583da7572c.jpg"}
     * recently_schedule : [{"date":1453305600,"schedule":[{"time":"08:00","state":"unavailable"},{"time":"08:15","state":"available"},{"time":"08:30","state":"available"},{"time":"08:45","state":"available"},{"time":"09:00","state":"available"},{"time":"09:15","state":"unavailable"},{"time":"09:30","state":"available"},{"time":"09:45","state":"unavailable"},{"time":"10:00","state":"unavailable"},{"time":"10:15","state":"available"},{"time":"10:30","state":"available"},{"time":"10:45","state":"unavailable"},{"time":"11:00","state":"available"},{"time":"11:15","state":"available"},{"time":"11:30","state":"unavailable"},{"time":"11:45","state":"available"},{"time":"12:00","state":"unavailable"},{"time":"12:15","state":"available"},{"time":"12:30","state":"available"},{"time":"12:45","state":"available"},{"time":"13:00","state":"unavailable"},{"time":"13:15","state":"unavailable"},{"time":"13:30","state":"available"},{"time":"13:45","state":"available"},{"time":"14:00","state":"available"},{"time":"14:15","state":"available"},{"time":"14:30","state":"available"},{"time":"14:45","state":"unavailable"},{"time":"15:00","state":"unavailable"},{"time":"15:15","state":"available"},{"time":"15:30","state":"available"},{"time":"15:45","state":"unavailable"},{"time":"16:00","state":"available"},{"time":"16:15","state":"available"},{"time":"16:30","state":"available"},{"time":"16:45","state":"available"},{"time":"17:00","state":"unavailable"},{"time":"17:15","state":"available"},{"time":"17:30","state":"available"},{"time":"17:45","state":"unavailable"},{"time":"18:00","state":"available"},{"time":"18:15","state":"available"},{"time":"18:30","state":"available"},{"time":"18:45","state":"available"},{"time":"19:00","state":"available"},{"time":"19:15","state":"available"},{"time":"19:30","state":"unavailable"},{"time":"19:45","state":"available"},{"time":"20:00","state":"unavailable"},{"time":"20:15","state":"available"},{"time":"20:30","state":"available"},{"time":"20:45","state":"unavailable"},{"time":"21:00","state":"unavailable"},{"time":"21:15","state":"available"},{"time":"21:30","state":"unavailable"},{"time":"21:45","state":"available"}]},{"date":1453392000,"schedule":[{"time":"08:00","state":"available"},{"time":"08:15","state":"available"},{"time":"08:30","state":"unavailable"},{"time":"08:45","state":"available"},{"time":"09:00","state":"available"},{"time":"09:15","state":"unavailable"},{"time":"09:30","state":"available"},{"time":"09:45","state":"unavailable"},{"time":"10:00","state":"available"},{"time":"10:15","state":"available"},{"time":"10:30","state":"available"},{"time":"10:45","state":"unavailable"},{"time":"11:00","state":"available"},{"time":"11:15","state":"available"},{"time":"11:30","state":"available"},{"time":"11:45","state":"available"},{"time":"12:00","state":"available"},{"time":"12:15","state":"unavailable"},{"time":"12:30","state":"available"},{"time":"12:45","state":"available"},{"time":"13:00","state":"available"},{"time":"13:15","state":"unavailable"},{"time":"13:30","state":"unavailable"},{"time":"13:45","state":"available"},{"time":"14:00","state":"available"},{"time":"14:15","state":"available"},{"time":"14:30","state":"available"},{"time":"14:45","state":"available"},{"time":"15:00","state":"available"},{"time":"15:15","state":"available"},{"time":"15:30","state":"available"},{"time":"15:45","state":"available"},{"time":"16:00","state":"available"},{"time":"16:15","state":"available"},{"time":"16:30","state":"available"},{"time":"16:45","state":"available"},{"time":"17:00","state":"unavailable"},{"time":"17:15","state":"unavailable"},{"time":"17:30","state":"unavailable"},{"time":"17:45","state":"available"},{"time":"18:00","state":"available"},{"time":"18:15","state":"available"},{"time":"18:30","state":"available"},{"time":"18:45","state":"available"},{"time":"19:00","state":"available"},{"time":"19:15","state":"unavailable"},{"time":"19:30","state":"available"},{"time":"19:45","state":"available"},{"time":"20:00","state":"available"},{"time":"20:15","state":"available"},{"time":"20:30","state":"available"},{"time":"20:45","state":"available"},{"time":"21:00","state":"available"},{"time":"21:15","state":"available"},{"time":"21:30","state":"available"},{"time":"21:45","state":"available"}]},{"date":1453478400,"schedule":[{"time":"08:00","state":"available"},{"time":"08:15","state":"available"},{"time":"08:30","state":"available"},{"time":"08:45","state":"available"},{"time":"09:00","state":"available"},{"time":"09:15","state":"unavailable"},{"time":"09:30","state":"available"},{"time":"09:45","state":"available"},{"time":"10:00","state":"unavailable"},{"time":"10:15","state":"available"},{"time":"10:30","state":"available"},{"time":"10:45","state":"unavailable"},{"time":"11:00","state":"available"},{"time":"11:15","state":"unavailable"},{"time":"11:30","state":"unavailable"},{"time":"11:45","state":"available"},{"time":"12:00","state":"unavailable"},{"time":"12:15","state":"available"},{"time":"12:30","state":"available"},{"time":"12:45","state":"unavailable"},{"time":"13:00","state":"available"},{"time":"13:15","state":"unavailable"},{"time":"13:30","state":"available"},{"time":"13:45","state":"available"},{"time":"14:00","state":"unavailable"},{"time":"14:15","state":"unavailable"},{"time":"14:30","state":"available"},{"time":"14:45","state":"available"},{"time":"15:00","state":"unavailable"},{"time":"15:15","state":"available"},{"time":"15:30","state":"available"},{"time":"15:45","state":"available"},{"time":"16:00","state":"available"},{"time":"16:15","state":"unavailable"},{"time":"16:30","state":"available"},{"time":"16:45","state":"available"},{"time":"17:00","state":"unavailable"},{"time":"17:15","state":"available"},{"time":"17:30","state":"available"},{"time":"17:45","state":"unavailable"},{"time":"18:00","state":"available"},{"time":"18:15","state":"available"},{"time":"18:30","state":"unavailable"},{"time":"18:45","state":"available"},{"time":"19:00","state":"available"},{"time":"19:15","state":"available"},{"time":"19:30","state":"available"},{"time":"19:45","state":"available"},{"time":"20:00","state":"available"},{"time":"20:15","state":"available"},{"time":"20:30","state":"unavailable"},{"time":"20:45","state":"available"},{"time":"21:00","state":"available"},{"time":"21:15","state":"available"},{"time":"21:30","state":"unavailable"},{"time":"21:45","state":"available"}]}]
     */

    private String name;
    private String description;
    private String date;
    private List<RecentlyScheduleEntity.ScheduleEntity> scheduleEntity;
    /**
     * name : 巴巴沃森
     * title : 主教练
     * portrait : http://seminole-staging.oss-cn-beijing.aliyuncs.com/assets/w300_h400_fl_q80_0f5f5cc5bcf4ccf62affb2583da7572c.jpg
     */

    private CoachEntity coach;
    /**
     * date : 1453305600
     * schedule : [{"time":"08:00","state":"unavailable"},{"time":"08:15","state":"available"},{"time":"08:30","state":"available"},{"time":"08:45","state":"available"},{"time":"09:00","state":"available"},{"time":"09:15","state":"unavailable"},{"time":"09:30","state":"available"},{"time":"09:45","state":"unavailable"},{"time":"10:00","state":"unavailable"},{"time":"10:15","state":"available"},{"time":"10:30","state":"available"},{"time":"10:45","state":"unavailable"},{"time":"11:00","state":"available"},{"time":"11:15","state":"available"},{"time":"11:30","state":"unavailable"},{"time":"11:45","state":"available"},{"time":"12:00","state":"unavailable"},{"time":"12:15","state":"available"},{"time":"12:30","state":"available"},{"time":"12:45","state":"available"},{"time":"13:00","state":"unavailable"},{"time":"13:15","state":"unavailable"},{"time":"13:30","state":"available"},{"time":"13:45","state":"available"},{"time":"14:00","state":"available"},{"time":"14:15","state":"available"},{"time":"14:30","state":"available"},{"time":"14:45","state":"unavailable"},{"time":"15:00","state":"unavailable"},{"time":"15:15","state":"available"},{"time":"15:30","state":"available"},{"time":"15:45","state":"unavailable"},{"time":"16:00","state":"available"},{"time":"16:15","state":"available"},{"time":"16:30","state":"available"},{"time":"16:45","state":"available"},{"time":"17:00","state":"unavailable"},{"time":"17:15","state":"available"},{"time":"17:30","state":"available"},{"time":"17:45","state":"unavailable"},{"time":"18:00","state":"available"},{"time":"18:15","state":"available"},{"time":"18:30","state":"available"},{"time":"18:45","state":"available"},{"time":"19:00","state":"available"},{"time":"19:15","state":"available"},{"time":"19:30","state":"unavailable"},{"time":"19:45","state":"available"},{"time":"20:00","state":"unavailable"},{"time":"20:15","state":"available"},{"time":"20:30","state":"available"},{"time":"20:45","state":"unavailable"},{"time":"21:00","state":"unavailable"},{"time":"21:15","state":"available"},{"time":"21:30","state":"unavailable"},{"time":"21:45","state":"available"}]
     */

    private List<RecentlyScheduleEntity> recently_schedule;

    public List<RecentlyScheduleEntity.ScheduleEntity> getScheduleEntity()
    {
        return scheduleEntity;
    }

    public void setScheduleEntity(List<RecentlyScheduleEntity.ScheduleEntity> scheduleEntity)
    {
        this.scheduleEntity = scheduleEntity;
    }

    public String getDate()
    {
        return date;
    }

    public void setDate(String date)
    {
        this.date = date;
    }
    public void setName(String name)
    {
        this.name = name;
    }

    public void setDescription(String description)
    {
        this.description = description;
    }

    public void setCoach(CoachEntity coach)
    {
        this.coach = coach;
    }

    public void setRecently_schedule(List<RecentlyScheduleEntity> recently_schedule)
    {
        this.recently_schedule = recently_schedule;
    }

    public String getName()
    {
        return name;
    }

    public String getDescription()
    {
        return description;
    }

    public CoachEntity getCoach()
    {
        return coach;
    }

    public List<RecentlyScheduleEntity> getRecently_schedule()
    {
        return recently_schedule;
    }

    public static class CoachEntity
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

    public static class RecentlyScheduleEntity
    {
        private int date;
        /**
         * time : 08:00
         * state : unavailable
         */

        private List<ScheduleEntity> schedule;

        public void setDate(int date)
        {
            this.date = date;
        }

        public void setSchedule(List<ScheduleEntity> schedule)
        {
            this.schedule = schedule;
        }

        public int getDate()
        {
            return date;
        }

        public List<ScheduleEntity> getSchedule()
        {
            return schedule;
        }

        public static class ScheduleEntity
        {
            private String time;
            private String state;

            public void setTime(String time)
            {
                this.time = time;
            }

            public void setState(String state)
            {
                this.state = state;
            }

            public String getTime()
            {
                return time;
            }

            public String getState()
            {
                return state;
            }
        }

    }
    public List<PrivateCourses> generateListTodayInfo()
    {
        List<PrivateCourses> result = new ArrayList<>();
        if (getRecently_schedule() != null && getRecently_schedule().size() > 0)
        {
            for (RecentlyScheduleEntity entity :
                    getRecently_schedule())
            {
                PrivateCourses tmp = new PrivateCourses();
                tmp.setScheduleEntity(entity.getSchedule());
                result.add(tmp);
            }
        }
        return result;
    }
    public static PrivateCourses  instance(String str)
    {
        Gson gson = new Gson();
        return gson.fromJson(str, new TypeToken<PrivateCourses>() {
        }.getType());
    }
}
