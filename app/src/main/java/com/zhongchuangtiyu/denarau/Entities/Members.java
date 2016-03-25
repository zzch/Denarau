package com.zhongchuangtiyu.denarau.Entities;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.Serializable;
import java.util.List;

/**
 * 作者：WangMeng on 2016/3/22 17:33
 * 邮箱：wangmeng.wiz@foxmail.com
 */
public class Members implements Serializable
{

    /**
     * uuid : abbda298-6384-4299-82fa-022e46036863
     * type : expenditure
     * action : consumption
     * tab : {"uuid":"00f33d7e-1557-4ed4-af8e-a76e269baa67","sequence":"No. 000002"}
     * amount : 608.0
     * items : [{"type":"playing"},{"type":"provision"},{"type":"extra"}]
     */

    private String uuid;
    private String type;
    private String action;
    private String created_at;
    /**
     * uuid : 00f33d7e-1557-4ed4-af8e-a76e269baa67
     * sequence : No. 000002
     */

    private TabEntity tab;
    private String amount;

    public String getCreated_at()
    {
        return created_at;
    }

    public void setCreated_at(String created_at)
    {
        this.created_at = created_at;
    }

    /**
     * type : playing
     */

    private List<ItemsEntity> items;

    public void setUuid(String uuid)
    {
        this.uuid = uuid;
    }

    public void setType(String type)
    {
        this.type = type;
    }

    public void setAction(String action)
    {
        this.action = action;
    }

    public void setTab(TabEntity tab)
    {
        this.tab = tab;
    }

    public void setAmount(String amount)
    {
        this.amount = amount;
    }

    public void setItems(List<ItemsEntity> items)
    {
        this.items = items;
    }

    public String getUuid()
    {
        return uuid;
    }

    public String getType()
    {
        return type;
    }

    public String getAction()
    {
        return action;
    }

    public TabEntity getTab()
    {
        return tab;
    }

    public String getAmount()
    {
        return amount;
    }

    public List<ItemsEntity> getItems()
    {
        return items;
    }

    public static class TabEntity
    {
        private String uuid;
        private String sequence;

        public void setUuid(String uuid)
        {
            this.uuid = uuid;
        }

        public void setSequence(String sequence)
        {
            this.sequence = sequence;
        }

        public String getUuid()
        {
            return uuid;
        }

        public String getSequence()
        {
            return sequence;
        }
    }

    public static class ItemsEntity
    {
        private String type;

        public void setType(String type)
        {
            this.type = type;
        }

        public String getType()
        {
            return type;
        }
    }
    public static List<Members>  instance(String str)
    {

        Gson gson = new Gson();
        return gson.fromJson(str, new TypeToken<List<Members>>() {
        }.getType());
    }
}
