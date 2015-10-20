package com.zhongchuangtiyu.denarau.Entities;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.Serializable;
import java.util.List;

/**
 * Created by WangMeng on 2015/10/19.
 */
public class Courses implements Serializable
{

    /**
     * name : 技术课
     * price : 9600.0
     * lessons : 11
     * valid_months : 11
     * maximum_students : 3
     * description : In reprehenderit autem provident et rerum optio et autem veniam omnis facere architecto cupiditate sed esse in porro itaque corporis aut sit harum repellat consequuntur sapiente sint aspernatur voluptas iste id veritatis aut ab id aut quo culpa eum ratione enim natus dicta rerum aliquid qui eum qui eos voluptatibus ea cumque tempora quaerat impedit ea nihil ut consequatur neque nesciunt ut quae consequatur repellendus excepturi soluta voluptatem quo libero voluptatum laborum nulla expedita ad minus quisquam velit deleniti et aut cum repudiandae quos.
     */

    private String name;
    private String price;
    private int lessons;
    private int valid_months;
    private int maximum_students;
    private String description;

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

    public void setValid_months(int valid_months)
    {
        this.valid_months = valid_months;
    }

    public void setMaximum_students(int maximum_students)
    {
        this.maximum_students = maximum_students;
    }

    public void setDescription(String description)
    {
        this.description = description;
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

    public int getValid_months()
    {
        return valid_months;
    }

    public int getMaximum_students()
    {
        return maximum_students;
    }

    public String getDescription()
    {
        return description;
    }
    public static List<Courses> instance(String str)
    {

        Gson gson = new Gson();
        return gson.fromJson(str, new TypeToken<List<Courses>>() {
        }.getType());
    }
}
