package com.zhongchuangtiyu.denarau.Entities;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * 作者：WangMeng on 2016/1/19 16:19
 * 邮箱：wangmeng.wiz@foxmail.com
 */
public class OpenCourses implements Serializable
{

    /**
     * name : 如何选择击球方向
     * description : <p>如何选择击球方向的公开课</p>

     * coach : {"name":"麦克罗伊","title":"主教练","portrait":"http://seminole-staging.oss-cn-beijing.aliyuncs.com/assets/w300_h400_fl_q80_8a93103fc35a84038cdb07a0e9d5945c.jpg"}
     * unstarted_lessons : [{"uuid":"79fcd41a-9efb-4d2d-91c1-a65468d267e4","name":"如何选择击球方向（上）","started_at":1455178500,"finished_at":1455185700,"current_students":9,"maximum_students":6,"state":"full"},{"uuid":"4f3bb4de-7e66-4cbd-b16f-40e554516641","name":"如何选择击球方向（下）","started_at":1458018900,"finished_at":1458029700,"current_students":16,"maximum_students":6,"state":"full"}]
     */

    private String name;
    private String description;
    private int started_at;
    private int finished_at;
    private int current_students;
    private int maximum_students;
    private String state;
    private String uuid;
    /**
     * name : 麦克罗伊
     * title : 主教练
     * portrait : http://seminole-staging.oss-cn-beijing.aliyuncs.com/assets/w300_h400_fl_q80_8a93103fc35a84038cdb07a0e9d5945c.jpg
     */

    private CoachEntity coach;
    /**
     * uuid : 79fcd41a-9efb-4d2d-91c1-a65468d267e4
     * name : 如何选择击球方向（上）
     * started_at : 1455178500
     * finished_at : 1455185700
     * current_students : 9
     * maximum_students : 6
     * state : full
     */

    private List<UnstartedLessonsEntity> unstarted_lessons;

    public String getUuid()
    {
        return uuid;
    }

    public void setUuid(String uuid)
    {
        this.uuid = uuid;
    }

    public int getStarted_at()
    {
        return started_at;
    }

    public void setStarted_at(int started_at)
    {
        this.started_at = started_at;
    }

    public int getFinished_at()
    {
        return finished_at;
    }

    public void setFinished_at(int finished_at)
    {
        this.finished_at = finished_at;
    }

    public int getCurrent_students()
    {
        return current_students;
    }

    public void setCurrent_students(int current_students)
    {
        this.current_students = current_students;
    }

    public int getMaximum_students()
    {
        return maximum_students;
    }

    public void setMaximum_students(int maximum_students)
    {
        this.maximum_students = maximum_students;
    }

    public String getState()
    {
        return state;
    }

    public void setState(String state)
    {
        this.state = state;
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

    public void setUnstarted_lessons(List<UnstartedLessonsEntity> unstarted_lessons)
    {
        this.unstarted_lessons = unstarted_lessons;
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

    public List<UnstartedLessonsEntity> getUnstarted_lessons()
    {
        return unstarted_lessons;
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

    public static class UnstartedLessonsEntity
    {
        private String uuid;
        private String name;
        private int started_at;
        private int finished_at;
        private int current_students;
        private int maximum_students;
        private String state;

        public void setUuid(String uuid)
        {
            this.uuid = uuid;
        }

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

        public void setCurrent_students(int current_students)
        {
            this.current_students = current_students;
        }

        public void setMaximum_students(int maximum_students)
        {
            this.maximum_students = maximum_students;
        }

        public void setState(String state)
        {
            this.state = state;
        }

        public String getUuid()
        {
            return uuid;
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

        public int getCurrent_students()
        {
            return current_students;
        }

        public int getMaximum_students()
        {
            return maximum_students;
        }

        public String getState()
        {
            return state;
        }
    }
    public List<OpenCourses> generateListInfo()
    {
        List<OpenCourses> result = new ArrayList<>();
        if (getUnstarted_lessons() != null && getUnstarted_lessons().size() > 0)
        {
            for (UnstartedLessonsEntity entity :
                    getUnstarted_lessons())
            {
                OpenCourses tmp = new OpenCourses();
                tmp.setCurrent_students(entity.getCurrent_students());
                tmp.setMaximum_students(entity.getMaximum_students());
                tmp.setStarted_at(entity.getStarted_at());
                tmp.setFinished_at(entity.getFinished_at());
                tmp.setState(entity.getState());
                tmp.setUuid(entity.getUuid());
                result.add(tmp);
            }
        }
        return result;
    }
    public static OpenCourses  instance(String str)
    {
        Gson gson = new Gson();
        return gson.fromJson(str, new TypeToken<OpenCourses>() {
        }.getType());
    }
}
