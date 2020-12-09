package com.dpad.telematicsclientapp.netlibrary.newapp.entity;



import com.dpad.telematicsclientapp.netlibrary.MainApplicaton;

import java.io.Serializable;
import java.util.List;

/**
 * ================================================
 * 作    者：booob
 * 版    本：1.0
 * 创建日期：2018-12-03-0003 16:21
 * 描    述：上传到服务器的埋点数据
 * 参数说明：
 * distinctId：String，非必传，对用户的标识，对未登录用户，可以填充设备标识、CookieID 等，对于登录用户，则应该填充注册账号
 * <p>
 * executeTime：Date，必传，事件发生的实际时间戳，精确到毫秒，可以是"yyyy-MM-dd HH:mm:ss"，也可以是long类型的时间戳
 * <p>
 * sourceType：String，必传，来源类型：NAC、APP、微信、中台。建议0：东标车主APP；1：东雪车主APP；2: 斑马服务（东雪）；3：东标微信公众号；4：东雪微信公众号；5: 东标T微信公众号；6： 东雪T微信公众号；7：狮友汇官网登录；8：东标助手；9：东雪助手；10：商城；11：NAC；12：呼叫中心；
 * <p>
 * event：String，必传，行为事件名称，比如查询XXX，删除XXX，也可以是访问接口URL
 * <p>
 * description：String，非必传，行为事件的补充描述
 * <p>
 * project：String，必传，所属项目名
 * <p>
 * properties：jsstring，非必传，具体事件信息，比如名字，电话号码，地址
 * 修订历史：
 * ================================================
 */
public class DataStatisticsBean implements Serializable {


    public DataStatisticsBean() {
        this.exchangesName = "en_behavior";
        this.multiple = true;
        this.queueName = "qn_behavior";
        this.routingKey = "rk_behavior";
    }

    /**
     * datas : [{"description":"string","distinctId":"string","event":"string","executeTime":"2018-11-30 02:53:41","project":"string","properties":{},"sourceType":"string"},{"description":"string","distinctId":"string","event":"string","executeTime":"2018-11-30 02:53:41","project":"string","properties":{},"sourceType":"string"}]
     * exchangesName : en_behavior
     * multiple : true
     * queueName : qn_behavior
     * routingKey : rk_behavior
     */


    private String exchangesName;
    private boolean multiple;
    private String queueName;
    private String routingKey;
    private List<DatasBean> datas;


    public String getExchangesName() {
        return exchangesName;
    }

    public void setExchangesName(String exchangesName) {
        this.exchangesName = exchangesName;
    }

    public boolean isMultiple() {
        return multiple;
    }

    public void setMultiple(boolean multiple) {
        this.multiple = multiple;
    }

    public String getQueueName() {
        return queueName;
    }

    public void setQueueName(String queueName) {
        this.queueName = queueName;
    }

    public String getRoutingKey() {
        return routingKey;
    }

    public void setRoutingKey(String routingKey) {
        this.routingKey = routingKey;
    }

    public List<DatasBean> getDatas() {
        return datas;
    }

    public void setDatas(List<DatasBean> datas) {
        this.datas = datas;
    }

    public static class DatasBean implements Serializable {
        /**
         * description : string
         * distinctId : string
         * event : string
         * executeTime : 2018-11-30 02:53:41
         * project : string
         * properties : {}
         * sourceType : string
         */

        private String description;
        private String distinctId;
        private String event;
        private String executeTime;
        private String project;
        private String properties;
        private String sourceType;
        private String userType;

        public DatasBean(Builder builder) {
            this.description = builder.description;
            this.distinctId = builder.distinctId;
            this.event = builder.event;
            this.executeTime = builder.executeTime;
            this.project = builder.project;
            this.properties = builder.properties;
            this.sourceType = builder.sourceType;
            this.userType = builder.userType;
        }

        public static class Builder implements Serializable {
            private String description;
            private String distinctId;
            private String event;
            private String executeTime;
            private String project;
            private String properties;
            private String sourceType;

            public String getUserType() {
                return userType;
            }

            public void setUserType(String userType) {
                this.userType = userType;
            }

            private String userType;

            public Builder(String event, String executeTime) {
                this.event = event;
                this.executeTime = executeTime;
                this.project = "东标车主";
                this.sourceType = "0";
                this.userType = MainApplicaton.getBurialPointUserType();
            }

            public Builder description(String description) {
                this.description = description;
                return this;
            }

            public Builder event(String event) {
                this.event = event;
                return this;
            }

            public Builder distinctId(String distinctId) {
                this.distinctId = distinctId;
                return this;
            }

            public Builder properties(String properties) {
                this.properties = properties;
                return this;
            }
        }

        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
        }

        public String getDistinctId() {
            return distinctId;
        }

        public void setDistinctId(String distinctId) {
            this.distinctId = distinctId;
        }

        public String getEvent() {
            return event;
        }

        public void setEvent(String event) {
            this.event = event;
        }

        public String getExecuteTime() {
            return executeTime;
        }

        public void setExecuteTime(String executeTime) {
            this.executeTime = executeTime;
        }

        public String getProject() {
            return project;
        }

        public void setProject(String project) {
            this.project = project;
        }

        public String getProperties() {
            return properties;
        }

        public void setProperties(String properties) {
            this.properties = properties;
        }

        public String getSourceType() {
            return sourceType;
        }

        public void setSourceType(String sourceType) {
            this.sourceType = sourceType;
        }

        public static class PropertiesBean implements Serializable {
            private String message;

            public String getMessage() {
                return message;
            }

            public void setMessage(String message) {
                this.message = message;
            }
        }
    }
}
