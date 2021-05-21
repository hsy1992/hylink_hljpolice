package cn.net.hylink.hljpolice.bean;

import java.util.List;
import java.util.UUID;

/**
 * @author haosiyuan
 * @date 2020/9/18 2:09 PM
 * info :
 */
public class RequestBean {

//    {
//        "messageId": "",
//            "parameter": {
//        "condition": {
//            "keyValueList": [{
//                "key": "",
//                        "relationOperator": "=",
//                        "value": "dsf"
//            }],
//            "logicalOperate": "and"
//        },
//        "dataObjId": "",
//                "fields": "sdf",
//                "networkCode": "3",
//                "orderBy": null,
//                "page": {
//            "pageNo": 1,
//                    "pageSize": 1
//        },
//        "regionalismCode": "12"
//    },
//        "version": "1.0"
//
//    }

    /**
     * messageId :
     * parameter : {"condition":{"keyValueList":[{"key":"","relationOperator":"=","value":"dsf"}],"logicalOperate":"and"},"dataObjId":"","fields":"sdf","networkCode":"3","orderBy":null,"page":{"pageNo":1,"pageSize":1},"regionalismCode":"12"}
     * version : 1.0
     */

    private String messageId = UUID.randomUUID().toString();
    private ParameterBean parameter;
    private String version = "1.0";

    public String getMessageId() {
        return messageId;
    }

    public void setMessageId(String messageId) {
        this.messageId = messageId;
    }

    public ParameterBean getParameter() {
        return parameter;
    }

    public void setParameter(ParameterBean parameter) {
        this.parameter = parameter;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public static class ParameterBean {
        /**
         * condition : {"keyValueList":[{"key":"","relationOperator":"=","value":"dsf"}],"logicalOperate":"and"}
         * dataObjId :
         * fields : sdf
         * networkCode : 3
         * orderBy : null
         * page : {"pageNo":1,"pageSize":1}
         * regionalismCode : 12
         */

        private ConditionBean condition;
        private String dataObjId;
        private String fields;
        private String networkCode;
        private String orderBy = null;
        private PageBean page = new PageBean(1, 1);
        private String regionalismCode;

        public ParameterBean(ConditionBean condition, String dataObjId, String fields, String networkCode, String regionalismCode) {
            this.condition = condition;
            this.dataObjId = dataObjId;
            this.fields = fields;
            this.networkCode = networkCode;
            this.regionalismCode = regionalismCode;
        }

        public ConditionBean getCondition() {
            return condition;
        }

        public void setCondition(ConditionBean condition) {
            this.condition = condition;
        }

        public String getDataObjId() {
            return dataObjId;
        }

        public void setDataObjId(String dataObjId) {
            this.dataObjId = dataObjId;
        }

        public String getFields() {
            return fields;
        }

        public void setFields(String fields) {
            this.fields = fields;
        }

        public String getNetworkCode() {
            return networkCode;
        }

        public void setNetworkCode(String networkCode) {
            this.networkCode = networkCode;
        }

        public String getOrderBy() {
            return orderBy;
        }

        public void setOrderBy(String orderBy) {
            this.orderBy = orderBy;
        }

        public PageBean getPage() {
            return page;
        }

        public void setPage(PageBean page) {
            this.page = page;
        }

        public String getRegionalismCode() {
            return regionalismCode;
        }

        public void setRegionalismCode(String regionalismCode) {
            this.regionalismCode = regionalismCode;
        }

        public static class ConditionBean {
            /**
             * keyValueList : [{"key":"","relationOperator":"=","value":"dsf"}]
             * logicalOperate : and
             */

            private String logicalOperate = "and";
            private List<KeyValueListBean> keyValueList;

            public ConditionBean(List<KeyValueListBean> keyValueList) {
                this.keyValueList = keyValueList;
            }

            public String getLogicalOperate() {
                return logicalOperate;
            }

            public void setLogicalOperate(String logicalOperate) {
                this.logicalOperate = logicalOperate;
            }

            public List<KeyValueListBean> getKeyValueList() {
                return keyValueList;
            }

            public void setKeyValueList(List<KeyValueListBean> keyValueList) {
                this.keyValueList = keyValueList;
            }

            public static class KeyValueListBean {
                /**
                 * key :
                 * relationOperator : =
                 * value : dsf
                 */

                private String key;
                private String relationOperator = "=";
                private String value;

                public KeyValueListBean(String key, String value) {
                    this.key = key;
                    this.value = value;
                }

                public String getKey() {
                    return key;
                }

                public void setKey(String key) {
                    this.key = key;
                }

                public String getRelationOperator() {
                    return relationOperator;
                }

                public void setRelationOperator(String relationOperator) {
                    this.relationOperator = relationOperator;
                }

                public String getValue() {
                    return value;
                }

                public void setValue(String value) {
                    this.value = value;
                }
            }
        }

        public static class PageBean {
            /**
             * pageNo : 1
             * pageSize : 1
             */

            private int pageNo;
            private int pageSize;

            public PageBean(int pageNo, int pageSize) {
                this.pageNo = pageNo;
                this.pageSize = pageSize;
            }

            public int getPageNo() {
                return pageNo;
            }

            public void setPageNo(int pageNo) {
                this.pageNo = pageNo;
            }

            public int getPageSize() {
                return pageSize;
            }

            public void setPageSize(int pageSize) {
                this.pageSize = pageSize;
            }
        }
    }
}
