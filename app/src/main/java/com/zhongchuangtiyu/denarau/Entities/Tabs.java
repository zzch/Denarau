package com.zhongchuangtiyu.denarau.Entities;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.List;

/**
 * 作者：WangMeng on 2015/11/10 14:48
 * 邮箱：wangmeng.wiz@foxmail.com
 */
public class Tabs
{

    /**
     * uuid : abb28134-0226-41ad-a9d3-5392cf372e8f
     * sequence : No. 000040
     * reception_payment : 650.00元
     * entrance_time : 1423272060
     * departure_time : 1423281180
     * items : [{"name":"3打位","total_price":"-","payment_method":"credit_card"},{"name":"鱼香肉丝x1","total_price":"42.00元","payment_method":"credit_card"},{"name":"鱼香肉丝x2","total_price":"84.00元","payment_method":"cash"},{"name":"宫保鸡丁x3","total_price":"144.00元","payment_method":"cash"}]
     */

    private String uuid;
    private String sequence;
    private String reception_payment;
    private int entrance_time;
    private int departure_time;
    /**
     * name : 3打位
     * total_price : -
     * payment_method : credit_card
     */

    private List<ItemsEntity> items;

    public void setUuid(String uuid)
    {
        this.uuid = uuid;
    }

    public void setSequence(String sequence)
    {
        this.sequence = sequence;
    }

    public void setReception_payment(String reception_payment)
    {
        this.reception_payment = reception_payment;
    }

    public void setEntrance_time(int entrance_time)
    {
        this.entrance_time = entrance_time;
    }

    public void setDeparture_time(int departure_time)
    {
        this.departure_time = departure_time;
    }

    public void setItems(List<ItemsEntity> items)
    {
        this.items = items;
    }

    public String getUuid()
    {
        return uuid;
    }

    public String getSequence()
    {
        return sequence;
    }

    public String getReception_payment()
    {
        return reception_payment;
    }

    public int getEntrance_time()
    {
        return entrance_time;
    }

    public int getDeparture_time()
    {
        return departure_time;
    }

    public List<ItemsEntity> getItems()
    {
        return items;
    }

    public static class ItemsEntity
    {
        private String name;
        private String total_price;
        private String payment_method;

        public void setName(String name)
        {
            this.name = name;
        }

        public void setTotal_price(String total_price)
        {
            this.total_price = total_price;
        }

        public void setPayment_method(String payment_method)
        {
            this.payment_method = payment_method;
        }

        public String getName()
        {
            return name;
        }

        public String getTotal_price()
        {
            return total_price;
        }

        public String getPayment_method()
        {
            return payment_method;
        }
    }
    public static List<Tabs> instance(String str)
    {

        Gson gson = new Gson();
        return gson.fromJson(str, new TypeToken<List<Tabs>>() {
        }.getType());
    }
}
