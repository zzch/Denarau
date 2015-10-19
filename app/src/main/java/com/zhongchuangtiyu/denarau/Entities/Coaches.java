package com.zhongchuangtiyu.denarau.Entities;

import java.util.List;

/**
 * Created by WangMeng on 2015/10/19.
 */
public class Coaches
{

    /**
     * featured : [{"uuid":"029ba9c6-1886-41e2-bdb9-aa612c34beb5","name":"秦嘉懿","portrait":null,"gender":"female","title":"金牌教练"},{"uuid":"3f16b294-1b1e-4964-a012-9175e24be62f","name":"赵雨泽","portrait":"http://seminole-staging.oss-cn-beijing.aliyuncs.com/assets/w300_h400_fl_q80_23361922a40508c2f55dc15a21667fad.jpg","gender":"female","title":"优秀教练"},{"uuid":"ffa39bb6-3bba-476c-81ab-6d9de82e2f47","name":"王文","portrait":"http://seminole-staging.oss-cn-beijing.aliyuncs.com/assets/w300_h400_fl_q80_921337a99090cb266cfe70b7adff11c7.jpg","gender":"female","title":"金牌教练"},{"uuid":"a9df0a1e-70d5-479c-b069-e00ff9cfe13b","name":"张晟睿","portrait":"http://seminole-staging.oss-cn-beijing.aliyuncs.com/assets/w300_h400_fl_q80_0ad4cde8bbc987889e1de7a0194849f9.jpg","gender":"female","title":"教练"}]
     * normal : [{"uuid":"37427b31-5811-4afa-b11e-6012061a6d2a","name":"魏思聪","portrait":"http://seminole-staging.oss-cn-beijing.aliyuncs.com/assets/w300_h400_fl_q80_8f203837fcc1f02234ea812ebd688467.jpg","gender":"female","title":"优秀教练"},{"uuid":"e2c0ea42-18a9-443f-9452-5e9c99f09ff7","name":"唐伟宸","portrait":"http://seminole-staging.oss-cn-beijing.aliyuncs.com/assets/w300_h400_fl_q80_8a4b306a4b415553d0d2b88cb881be83.jpg","gender":"male","title":"精英教练"},{"uuid":"280e2bd6-5d5f-4bc6-9884-b72a999e06d5","name":"方致远","portrait":"http://seminole-staging.oss-cn-beijing.aliyuncs.com/assets/w300_h400_fl_q80_351357d518f5c7a7f35f74fb0b920616.jpg","gender":"female","title":"教练"},{"uuid":"1f00c766-b763-4ad7-bdf8-29bbac224a0d","name":"薛鹏","portrait":"http://seminole-staging.oss-cn-beijing.aliyuncs.com/assets/w300_h400_fl_q80_c103f6a40af8c47b7f6d6f00bbcc8cfd.jpg","gender":"female","title":"优秀教练"},{"uuid":"8919b66d-c269-468a-b5c6-e1e3a50d7a5d","name":"邹智渊","portrait":null,"gender":"male","title":"优秀教练"},{"uuid":"40c0e790-dc05-4745-a04b-6311995193cb","name":"邓钰轩","portrait":"http://seminole-staging.oss-cn-beijing.aliyuncs.com/assets/w300_h400_fl_q80_7b965c30bc8511e862f524d46f202c0a.jpg","gender":"male","title":"精英教练"},{"uuid":"efcb152b-7b72-41ce-9ea4-07edc53046ad","name":"许雨泽","portrait":"http://seminole-staging.oss-cn-beijing.aliyuncs.com/assets/w300_h400_fl_q80_8013c624851be514f30eb7d6e4ba6887.jpg","gender":"male","title":"国家级教练"},{"uuid":"617662c1-c451-42f8-adcf-a06fcc60f28c","name":"唐语堂","portrait":null,"gender":"female","title":"优秀教练"},{"uuid":"fc50a036-067b-4e7f-9b25-bd5b4f5dad4b","name":"崔明辉","portrait":null,"gender":"male","title":"教练"},{"uuid":"0cc65eeb-85ca-4022-8294-2d48217472c2","name":"郑昊焱","portrait":"http://seminole-staging.oss-cn-beijing.aliyuncs.com/assets/w300_h400_fl_q80_ac33d95b2596da3aef26f40b0197d685.jpg","gender":"female","title":"国家级教练"},{"uuid":"6ac3f3cd-f9ec-4985-b1a1-96b797535aa1","name":"龙哲瀚","portrait":null,"gender":"male","title":"优秀教练"},{"uuid":"2770d7d4-4dac-411c-938e-e394fe182e8c","name":"许烨华","portrait":null,"gender":"male","title":"金牌教练"},{"uuid":"619573c5-13c2-46d8-9624-f7fcf029b2a9","name":"龚旭尧","portrait":"http://seminole-staging.oss-cn-beijing.aliyuncs.com/assets/w300_h400_fl_q80_a168eaa29ec4e020e4cbf316b6f3b0bd.jpg","gender":"male","title":"教练"},{"uuid":"620eb35c-521a-49a4-9821-d09e1f597893","name":"罗擎宇","portrait":null,"gender":"male","title":"五星教练"},{"uuid":"b475a497-a30e-4766-a5c2-accd3021804f","name":"姜烨霖","portrait":"http://seminole-staging.oss-cn-beijing.aliyuncs.com/assets/w300_h400_fl_q80_81156e9c7eb3c26f11e90b518aa2dcd5.jpg","gender":"male","title":"优秀教练"},{"uuid":"b9d2e32b-5248-417c-80ee-824001e9868d","name":"程博涛","portrait":"http://seminole-staging.oss-cn-beijing.aliyuncs.com/assets/w300_h400_fl_q80_5e55132da84a0ce54b60c5dc7573ad1a.jpg","gender":"female","title":"教练"},{"uuid":"f20effc5-43fb-4612-afc7-4609ba72e89e","name":"朱思源","portrait":"http://seminole-staging.oss-cn-beijing.aliyuncs.com/assets/w300_h400_fl_q80_dcfdfcf477698b9dfd7422b3e4152028.jpg","gender":"male","title":"优秀教练"},{"uuid":"74479a4e-2b95-4ac5-812a-8f201a444d64","name":"谢鹏煊","portrait":null,"gender":"female","title":"精英教练"},{"uuid":"bf0a6756-bab3-4c96-845e-e707cfd83c8d","name":"胡乐驹","portrait":"http://seminole-staging.oss-cn-beijing.aliyuncs.com/assets/w300_h400_fl_q80_2c1c130c9b49d899bb422c7c24fcf7f9.jpg","gender":"male","title":"五星教练"},{"uuid":"d611e051-b6c1-47e1-889b-f502c9c6f330","name":"何立轩","portrait":null,"gender":"female","title":"教练"},{"uuid":"2375e795-97d5-475a-bf14-6c80fa9b5546","name":"黎鑫磊","portrait":"http://seminole-staging.oss-cn-beijing.aliyuncs.com/assets/w300_h400_fl_q80_e16b1be21462d58c4f76a522fa1fdb35.jpg","gender":"female","title":"五星教练"},{"uuid":"3e628b71-0084-4892-b001-16ab664914d9","name":"严越彬","portrait":"http://seminole-staging.oss-cn-beijing.aliyuncs.com/assets/w300_h400_fl_q80_aea5df6b678343b12e62f281c5e5e030.jpg","gender":"male","title":"国家级教练"},{"uuid":"aa1d7ed7-4d52-41e4-867e-9c34cf27938a","name":"宋鹤轩","portrait":"http://seminole-staging.oss-cn-beijing.aliyuncs.com/assets/w300_h400_fl_q80_1b41e2dca1a6e8bab4e39f9dead3975e.jpg","gender":"male","title":"金牌教练"}]
     */

    private List<FeaturedEntity> featured;
    private List<NormalEntity> normal;

    public void setFeatured(List<FeaturedEntity> featured)
    {
        this.featured = featured;
    }

    public void setNormal(List<NormalEntity> normal)
    {
        this.normal = normal;
    }

    public List<FeaturedEntity> getFeatured()
    {
        return featured;
    }

    public List<NormalEntity> getNormal()
    {
        return normal;
    }

    public static class FeaturedEntity
    {
        /**
         * uuid : 029ba9c6-1886-41e2-bdb9-aa612c34beb5
         * name : 秦嘉懿
         * portrait : null
         * gender : female
         * title : 金牌教练
         */

        private String uuid;
        private String name;
        private Object portrait;
        private String gender;
        private String title;

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
    }

    public static class NormalEntity
    {
        /**
         * uuid : 37427b31-5811-4afa-b11e-6012061a6d2a
         * name : 魏思聪
         * portrait : http://seminole-staging.oss-cn-beijing.aliyuncs.com/assets/w300_h400_fl_q80_8f203837fcc1f02234ea812ebd688467.jpg
         * gender : female
         * title : 优秀教练
         */

        private String uuid;
        private String name;
        private String portrait;
        private String gender;
        private String title;

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
    }
}
