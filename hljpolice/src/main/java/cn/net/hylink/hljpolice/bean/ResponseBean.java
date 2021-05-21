package cn.net.hylink.hljpolice.bean;

import java.util.List;

/**
 * @author haosiyuan
 * @date 2020/9/18 2:39 PM
 * info :
 */
public class ResponseBean {

//    {
//        "code": "200",
//            "data": {
//        "dataList": [{
//            "fieldValues": [{
//                "field": "",
//                        "value": ""
//            }]
//        }],
//        "page": {
//            "pageNo": 1,
//                    "pageSize": 1,
//                    "total": 1
//        }
//    },
//        "message": "OK",
//            "messageId": "qwqe",
//            "version": "1.0"
//    }

    /**
     * code : 200
     * data : {"dataList":[{"fieldValues":[{"field":"","value":""}]}],"page":{"pageNo":1,"pageSize":1,"total":1}}
     * message : OK
     * messageId : qwqe
     * version : 1.0
     */

    private String code;
    private DataBean data;
    private String message;
    private String messageId;
    private String version;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getMessageId() {
        return messageId;
    }

    public void setMessageId(String messageId) {
        this.messageId = messageId;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public static class DataBean {
        /**
         * dataList : [{"fieldValues":[{"field":"","value":""}]}]
         * page : {"pageNo":1,"pageSize":1,"total":1}
         */

        private PageBean page;
        private List<DataListBean> dataList;

        public PageBean getPage() {
            return page;
        }

        public void setPage(PageBean page) {
            this.page = page;
        }

        public List<DataListBean> getDataList() {
            return dataList;
        }

        public void setDataList(List<DataListBean> dataList) {
            this.dataList = dataList;
        }

        public static class PageBean {
            /**
             * pageNo : 1
             * pageSize : 1
             * total : 1
             */

            private int pageNo;
            private int pageSize;
            private int total;

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

            public int getTotal() {
                return total;
            }

            public void setTotal(int total) {
                this.total = total;
            }
        }

        public static class DataListBean {
            private List<FieldValuesBean> fieldValues;

            public List<FieldValuesBean> getFieldValues() {
                return fieldValues;
            }

            public void setFieldValues(List<FieldValuesBean> fieldValues) {
                this.fieldValues = fieldValues;
            }

            public static class FieldValuesBean {
                /**
                 * field :
                 * value :
                 */

                private String field;
                private String value;

                public String getField() {
                    return field;
                }

                public void setField(String field) {
                    this.field = field;
                }

                public String getValue() {
                    return value;
                }

                public void setValue(String value) {
                    this.value = value;
                }
            }
        }
    }
}
