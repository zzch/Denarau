package com.zhongchuangtiyu.denarau.Entities;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.Serializable;
import java.util.List;

/**
 * Created by WangMeng on 2015/10/19.
 */
public class ClubsHome implements Serializable
{

    /**
     * club : {"name":"北京中创体育高尔夫练习场","logo":"http://seminole-staging.oss-cn-beijing.aliyuncs.com/assets/w150_h150_fl_q50_df4afd19877ec76a9680ae0ce008ea11.jpg"}
     * members : [{"uuid":"7dea4731-5d32-406e-b74f-95b3729ea07d","number":"394198","balance":"N/A","expired_at":1539605609,"card":{"name":"49990元 储值卡","background_color":"227654","font_color":"ac3b80"}},{"uuid":"1fe12b99-b36e-4f2c-bb6b-09be8254bb10","number":"313084","balance":"N/A","expired_at":1508069609,"card":{"name":"12990元 计时卡","background_color":"47d763","font_color":"8928c2"}},{"uuid":"76ed8714-9df0-464f-a9b7-a1fc42b0858b","number":"419440","balance":"N/A","expired_at":1476533609,"card":{"name":"5990元 计时卡","background_color":"53362b","font_color":"c1c1c7"}}]
     * announcements : [{"uuid":"02cde2df-7b7e-49e8-98c7-27cedd45167a","title":"致会员们的一封信","published_at":1443528823},{"uuid":"b1324ccf-15f3-4369-9684-1268792009f5","title":"10月11日-13日球场维护","published_at":1442837623},{"uuid":"e1cd7d7b-8b3d-4491-b5eb-e025befe9dc8","title":"7月到店购卡有好礼相送","published_at":1442751223}]
     * date : 1446595200
     * maximum_temperature : 14
     */

    private ClubEntity club;
    private WeatherEntity weather;
    private List<MembersEntity> members;
    private List<AnnouncementsEntity> announcements;


    public void setClub(ClubEntity club)
    {
        this.club = club;
    }

    public WeatherEntity getWeather()
    {
        return weather;
    }

    public void setWeather(WeatherEntity weatherEntity)
    {
        this.weather = weatherEntity;
    }

    public void setMembers(List<MembersEntity> members)
    {
        this.members = members;
    }

    public void setAnnouncements(List<AnnouncementsEntity> announcements)
    {
        this.announcements = announcements;
    }

    public ClubEntity getClub()
    {
        return club;
    }

    public List<MembersEntity> getMembers()
    {
        return members;
    }

    public List<AnnouncementsEntity> getAnnouncements()
    {
        return announcements;
    }




    public static class ClubEntity
    {
        /**
         * name : 北京中创体育高尔夫练习场
         * logo : http://seminole-staging.oss-cn-beijing.aliyuncs.com/assets/w150_h150_fl_q50_df4afd19877ec76a9680ae0ce008ea11.jpg
         */

        private String name;
        private String logo;

        public void setName(String name)
        {
            this.name = name;
        }

        public void setLogo(String logo)
        {
            this.logo = logo;
        }

        public String getName()
        {
            return name;
        }

        public String getLogo()
        {
            return logo;
        }
    }

    public static class WeatherEntity
    {
        /**
         * date : 1446595200
         * maximum_temperature : 14
         */

        private int date;
        private int maximum_temperature;

        public int getDate()
        {
            return date;
        }

        public void setDate(int date)
        {
            this.date = date;
        }

        public int getMaximum_temperature()
        {
            return maximum_temperature;
        }

        public void setMaximum_temperature(int maximum_temperature)
        {
            this.maximum_temperature = maximum_temperature;
        }
    }

    public static class MembersEntity
    {
        /**
         * uuid : 7dea4731-5d32-406e-b74f-95b3729ea07d
         * number : 394198
         * balance : N/A
         * expired_at : 1539605609
         * card : {"name":"49990元 储值卡","background_color":"227654","font_color":"ac3b80"}
         */

        private String uuid;
        private String number;
        private String balance;
        private int expired_at;
        private CardEntity card;

        public void setUuid(String uuid)
        {
            this.uuid = uuid;
        }

        public void setNumber(String number)
        {
            this.number = number;
        }

        public void setBalance(String balance)
        {
            this.balance = balance;
        }

        public void setExpired_at(int expired_at)
        {
            this.expired_at = expired_at;
        }

        public void setCard(CardEntity card)
        {
            this.card = card;
        }

        public String getUuid()
        {
            return uuid;
        }

        public String getNumber()
        {
            return number;
        }

        public String getBalance()
        {
            return balance;
        }

        public int getExpired_at()
        {
            return expired_at;
        }

        public CardEntity getCard()
        {
            return card;
        }

        public static class CardEntity
        {
            /**
             * name : 49990元 储值卡
             * background_color : 227654
             * font_color : ac3b80
             */

            private String name;
            private String background_color;
            private String font_color;

            public void setName(String name)
            {
                this.name = name;
            }

            public void setBackground_color(String background_color)
            {
                this.background_color = background_color;
            }

            public void setFont_color(String font_color)
            {
                this.font_color = font_color;
            }

            public String getName()
            {
                return name;
            }

            public String getBackground_color()
            {
                return background_color;
            }

            public String getFont_color()
            {
                return font_color;
            }
        }
    }

    public static class AnnouncementsEntity
    {
        /**
         * uuid : 02cde2df-7b7e-49e8-98c7-27cedd45167a
         * title : 致会员们的一封信
         * published_at : 1443528823
         */

        private String uuid;
        private String title;
        private int published_at;

        public void setUuid(String uuid)
        {
            this.uuid = uuid;
        }

        public void setTitle(String title)
        {
            this.title = title;
        }

        public void setPublished_at(int published_at)
        {
            this.published_at = published_at;
        }

        public String getUuid()
        {
            return uuid;
        }

        public String getTitle()
        {
            return title;
        }

        public int getPublished_at()
        {
            return published_at;
        }
    }

    public static ClubsHome instance(String str)
    {

        Gson gson = new Gson();
        return gson.fromJson(str, new TypeToken<ClubsHome>() {
        }.getType());
    }
}
