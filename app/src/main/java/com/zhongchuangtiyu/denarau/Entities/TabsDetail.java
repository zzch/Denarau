package com.zhongchuangtiyu.denarau.Entities;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.Serializable;
import java.util.List;

/**
 * 作者：WangMeng on 2016/3/25 17:00
 * 邮箱：wangmeng.wiz@foxmail.com
 */
public class TabsDetail implements Serializable
{

    /**
     * sequence : No. 000004
     * reception_payment : 0.00元
     * entrance_time : 1458872100
     * departure_time : 1458878364
     * club : {"logo":{"url":null,"w600_h600_fl_q80":{"url":null},"w150_h150_fl_q50":{"url":null}},"id":1,"uuid":"1e173f24-49b5-46fe-a384-73364b72a5ae","name":"中创高尔夫","short_name":"","code":"isports","floors":2,"longitude":"119.0","latitude":"36.0","address":"北京市朝阳区星源国际公寓D座","phone_number":"64703688","balls_per_bucket":30,"minimum_charging_minutes":10,"unit_charging_minutes":30,"maximum_discard_minutes":10,"created_at":"2016-03-21T10:16:40.000+08:00","updated_at":"2016-03-21T10:16:40.000+08:00"}
     * items : [{"name":"A8打位","total_price":"200.00元","payment_method":"stored_member"},{"name":"脉动x3","total_price":"60.00元","payment_method":"stored_member"}]
     * state : voided
     */

    private String sequence;
    private String reception_payment;
    private int entrance_time;
    private int departure_time;
    /**
     * logo : {"url":null,"w600_h600_fl_q80":{"url":null},"w150_h150_fl_q50":{"url":null}}
     * id : 1
     * uuid : 1e173f24-49b5-46fe-a384-73364b72a5ae
     * name : 中创高尔夫
     * short_name :
     * code : isports
     * floors : 2
     * longitude : 119.0
     * latitude : 36.0
     * address : 北京市朝阳区星源国际公寓D座
     * phone_number : 64703688
     * balls_per_bucket : 30
     * minimum_charging_minutes : 10
     * unit_charging_minutes : 30
     * maximum_discard_minutes : 10
     * created_at : 2016-03-21T10:16:40.000+08:00
     * updated_at : 2016-03-21T10:16:40.000+08:00
     */

    private ClubEntity club;
    private String state;
    /**
     * name : A8打位
     * total_price : 200.00元
     * payment_method : stored_member
     */

    private List<ItemsEntity> items;

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

    public void setClub(ClubEntity club)
    {
        this.club = club;
    }

    public void setState(String state)
    {
        this.state = state;
    }

    public void setItems(List<ItemsEntity> items)
    {
        this.items = items;
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

    public ClubEntity getClub()
    {
        return club;
    }

    public String getState()
    {
        return state;
    }

    public List<ItemsEntity> getItems()
    {
        return items;
    }

    public static class ClubEntity
    {
        /**
         * url : null
         * w600_h600_fl_q80 : {"url":null}
         * w150_h150_fl_q50 : {"url":null}
         */

        private LogoEntity logo;
        private int id;
        private String uuid;
        private String name;
        private String short_name;
        private String code;
        private int floors;
        private String longitude;
        private String latitude;
        private String address;
        private String phone_number;
        private int balls_per_bucket;
        private int minimum_charging_minutes;
        private int unit_charging_minutes;
        private int maximum_discard_minutes;
        private String created_at;
        private String updated_at;

        public void setLogo(LogoEntity logo)
        {
            this.logo = logo;
        }

        public void setId(int id)
        {
            this.id = id;
        }

        public void setUuid(String uuid)
        {
            this.uuid = uuid;
        }

        public void setName(String name)
        {
            this.name = name;
        }

        public void setShort_name(String short_name)
        {
            this.short_name = short_name;
        }

        public void setCode(String code)
        {
            this.code = code;
        }

        public void setFloors(int floors)
        {
            this.floors = floors;
        }

        public void setLongitude(String longitude)
        {
            this.longitude = longitude;
        }

        public void setLatitude(String latitude)
        {
            this.latitude = latitude;
        }

        public void setAddress(String address)
        {
            this.address = address;
        }

        public void setPhone_number(String phone_number)
        {
            this.phone_number = phone_number;
        }

        public void setBalls_per_bucket(int balls_per_bucket)
        {
            this.balls_per_bucket = balls_per_bucket;
        }

        public void setMinimum_charging_minutes(int minimum_charging_minutes)
        {
            this.minimum_charging_minutes = minimum_charging_minutes;
        }

        public void setUnit_charging_minutes(int unit_charging_minutes)
        {
            this.unit_charging_minutes = unit_charging_minutes;
        }

        public void setMaximum_discard_minutes(int maximum_discard_minutes)
        {
            this.maximum_discard_minutes = maximum_discard_minutes;
        }

        public void setCreated_at(String created_at)
        {
            this.created_at = created_at;
        }

        public void setUpdated_at(String updated_at)
        {
            this.updated_at = updated_at;
        }

        public LogoEntity getLogo()
        {
            return logo;
        }

        public int getId()
        {
            return id;
        }

        public String getUuid()
        {
            return uuid;
        }

        public String getName()
        {
            return name;
        }

        public String getShort_name()
        {
            return short_name;
        }

        public String getCode()
        {
            return code;
        }

        public int getFloors()
        {
            return floors;
        }

        public String getLongitude()
        {
            return longitude;
        }

        public String getLatitude()
        {
            return latitude;
        }

        public String getAddress()
        {
            return address;
        }

        public String getPhone_number()
        {
            return phone_number;
        }

        public int getBalls_per_bucket()
        {
            return balls_per_bucket;
        }

        public int getMinimum_charging_minutes()
        {
            return minimum_charging_minutes;
        }

        public int getUnit_charging_minutes()
        {
            return unit_charging_minutes;
        }

        public int getMaximum_discard_minutes()
        {
            return maximum_discard_minutes;
        }

        public String getCreated_at()
        {
            return created_at;
        }

        public String getUpdated_at()
        {
            return updated_at;
        }

        public static class LogoEntity
        {
            private Object url;
            /**
             * url : null
             */

            private W600H600FlQ80Entity w600_h600_fl_q80;
            /**
             * url : null
             */

            private W150H150FlQ50Entity w150_h150_fl_q50;

            public void setUrl(Object url)
            {
                this.url = url;
            }

            public void setW600_h600_fl_q80(W600H600FlQ80Entity w600_h600_fl_q80)
            {
                this.w600_h600_fl_q80 = w600_h600_fl_q80;
            }

            public void setW150_h150_fl_q50(W150H150FlQ50Entity w150_h150_fl_q50)
            {
                this.w150_h150_fl_q50 = w150_h150_fl_q50;
            }

            public Object getUrl()
            {
                return url;
            }

            public W600H600FlQ80Entity getW600_h600_fl_q80()
            {
                return w600_h600_fl_q80;
            }

            public W150H150FlQ50Entity getW150_h150_fl_q50()
            {
                return w150_h150_fl_q50;
            }

            public static class W600H600FlQ80Entity
            {
                private Object url;

                public void setUrl(Object url)
                {
                    this.url = url;
                }

                public Object getUrl()
                {
                    return url;
                }
            }

            public static class W150H150FlQ50Entity
            {
                private Object url;

                public void setUrl(Object url)
                {
                    this.url = url;
                }

                public Object getUrl()
                {
                    return url;
                }
            }
        }
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
    public static TabsDetail instance(String str)
    {

        Gson gson = new Gson();
        return gson.fromJson(str, new TypeToken<TabsDetail>() {
        }.getType());
    }
}
