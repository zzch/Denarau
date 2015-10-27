package com.zhongchuangtiyu.denarau.Entities;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by WangMeng on 2015/10/19.
 */
public class Provisions implements Serializable
{

    /**
     * name : 茶类
     * provisions : [{"name":"菊花","image":"http://seminole-staging.oss-cn-beijing.aliyuncs.com/assets/w150_h150_fl_q50_68a54218570b8e131da2c15ae930a409.jpg","price":"38.0"},{"name":"普洱","image":"http://seminole-staging.oss-cn-beijing.aliyuncs.com/assets/w150_h150_fl_q50_6d9c3da36143631da57f567b9dfb0f28.jpg","price":"38.0"},{"name":"金骏眉","image":"http://seminole-staging.oss-cn-beijing.aliyuncs.com/assets/w150_h150_fl_q50_6e6a7ed542a50f41f8cbd1cda4500a93.jpg","price":"68.0"}]
     */

    private String name;
    private List<ProvisionsEntity> provisions;


    public void setName(String name)
    {
        this.name = name;
    }

    public void setProvisions(List<ProvisionsEntity> provisions)
    {
        this.provisions = provisions;
    }

    public String getName()
    {
        return name;
    }

    public List<ProvisionsEntity> getProvisions()
    {
        return provisions;
    }


    public static class ProvisionsEntity
    {
        /**
         * name : 菊花
         * image : http://seminole-staging.oss-cn-beijing.aliyuncs.com/assets/w150_h150_fl_q50_68a54218570b8e131da2c15ae930a409.jpg
         * price : 38.0
         */

        private String name;
        private String image;
        private String price;

        public void setName(String name)
        {
            this.name = name;
        }

        public void setImage(String image)
        {
            this.image = image;
        }

        public void setPrice(String price)
        {
            this.price = price;
        }

        public String getName()
        {
            return name;
        }

        public String getImage()
        {
            return image;
        }

        public String getPrice()
        {
            return price;
        }
    }
    public static List<Provision> instance(String str)
    {

        Gson gson = new Gson();
        List<Provisions> results = gson.fromJson(str, new TypeToken<List<Provisions>>() {
        }.getType());
        List<Provision> res = new ArrayList<>();
        for (Provisions prov : results )
        {
            for (ProvisionsEntity entity : prov.getProvisions())
            {
                Provision tmp = new Provision();
                tmp.setType(prov.getName());
                tmp.setPrice(entity.getPrice());
                tmp.setImage(entity.getImage());
                tmp.setName(entity.getName());
                res.add(tmp);
            }
        }

        return  res;
    }

}
