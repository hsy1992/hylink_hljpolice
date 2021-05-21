package cn.net.hylink.hljpolice.bean;

public class UserInfoDetailBean {


    /**
     * credential : {"head":{"credType":"1","duration":{"endTime":"1602471091008","startTime":"1602463891008"},"token":{"orgId":"230000000000","tokenId":"a380a9de748baa2101751a4a7e431081"},"version":"1.0"},"load":{"userInfo":{"jh":"014867","orgId":"230100230000","sfzh":"230302199110254099","userId":"5915f33edeb148259439d902c59437d5","xm":"刘晨"}},"serverSign":{"alg":"SM3+SM2","signature":"2WIjCJ/+94XmtIei2+/8eV40dxfvQGqV4ws7WDA5iNPc//zeZTFNewibqa8OSKprWq2P6mnWHMkVPYP+Qdl7Huc0qHJMVFUovF+mqtO5pSxrngem5kXmVK9v1NV7OMurPpa1FiQc1uU0A4Zae4ZrpynM2+qEck/D8KSvLvsKkCQ=","sn":"b11000000000bdc","url":"http://192.168.24.108:8080"}}
     */

    private CredentialBean credential;

    public CredentialBean getCredential() {
        return credential;
    }

    public void setCredential(CredentialBean credential) {
        this.credential = credential;
    }

    public static class CredentialBean {
        /**
         * head : {"credType":"1","duration":{"endTime":"1602471091008","startTime":"1602463891008"},"token":{"orgId":"230000000000","tokenId":"a380a9de748baa2101751a4a7e431081"},"version":"1.0"}
         * load : {"userInfo":{"jh":"014867","orgId":"230100230000","sfzh":"230302199110254099","userId":"5915f33edeb148259439d902c59437d5","xm":"刘晨"}}
         * serverSign : {"alg":"SM3+SM2","signature":"2WIjCJ/+94XmtIei2+/8eV40dxfvQGqV4ws7WDA5iNPc//zeZTFNewibqa8OSKprWq2P6mnWHMkVPYP+Qdl7Huc0qHJMVFUovF+mqtO5pSxrngem5kXmVK9v1NV7OMurPpa1FiQc1uU0A4Zae4ZrpynM2+qEck/D8KSvLvsKkCQ=","sn":"b11000000000bdc","url":"http://192.168.24.108:8080"}
         */

        private HeadBean head;
        private LoadBean load;
        private ServerSignBean serverSign;

        public HeadBean getHead() {
            return head;
        }

        public void setHead(HeadBean head) {
            this.head = head;
        }

        public LoadBean getLoad() {
            return load;
        }

        public void setLoad(LoadBean load) {
            this.load = load;
        }

        public ServerSignBean getServerSign() {
            return serverSign;
        }

        public void setServerSign(ServerSignBean serverSign) {
            this.serverSign = serverSign;
        }

        public static class HeadBean {
            /**
             * credType : 1
             * duration : {"endTime":"1602471091008","startTime":"1602463891008"}
             * token : {"orgId":"230000000000","tokenId":"a380a9de748baa2101751a4a7e431081"}
             * version : 1.0
             */

            private String credType;
            private DurationBean duration;
            private TokenBean token;
            private String version;

            public String getCredType() {
                return credType;
            }

            public void setCredType(String credType) {
                this.credType = credType;
            }

            public DurationBean getDuration() {
                return duration;
            }

            public void setDuration(DurationBean duration) {
                this.duration = duration;
            }

            public TokenBean getToken() {
                return token;
            }

            public void setToken(TokenBean token) {
                this.token = token;
            }

            public String getVersion() {
                return version;
            }

            public void setVersion(String version) {
                this.version = version;
            }

            public static class DurationBean {
                /**
                 * endTime : 1602471091008
                 * startTime : 1602463891008
                 */

                private String endTime;
                private String startTime;

                public String getEndTime() {
                    return endTime;
                }

                public void setEndTime(String endTime) {
                    this.endTime = endTime;
                }

                public String getStartTime() {
                    return startTime;
                }

                public void setStartTime(String startTime) {
                    this.startTime = startTime;
                }
            }

            public static class TokenBean {
                /**
                 * orgId : 230000000000
                 * tokenId : a380a9de748baa2101751a4a7e431081
                 */

                private String orgId;
                private String tokenId;

                public String getOrgId() {
                    return orgId;
                }

                public void setOrgId(String orgId) {
                    this.orgId = orgId;
                }

                public String getTokenId() {
                    return tokenId;
                }

                public void setTokenId(String tokenId) {
                    this.tokenId = tokenId;
                }
            }
        }

        public static class LoadBean {
            /**
             * userInfo : {"jh":"014867","orgId":"230100230000","sfzh":"230302199110254099","userId":"5915f33edeb148259439d902c59437d5","xm":"刘晨"}
             */

            private UserInfoBean userInfo;

            public UserInfoBean getUserInfo() {
                return userInfo;
            }

            public void setUserInfo(UserInfoBean userInfo) {
                this.userInfo = userInfo;
            }

            public static class UserInfoBean {
                /**
                 * jh : 014867
                 * orgId : 230100230000
                 * sfzh : 230302199110254099
                 * userId : 5915f33edeb148259439d902c59437d5
                 * xm : 刘晨
                 */

                private String jh;
                private String orgId;
                private String sfzh;
                private String userId;
                private String xm;

                public String getJh() {
                    return jh;
                }

                public void setJh(String jh) {
                    this.jh = jh;
                }

                public String getOrgId() {
                    return orgId;
                }

                public void setOrgId(String orgId) {
                    this.orgId = orgId;
                }

                public String getSfzh() {
                    return sfzh;
                }

                public void setSfzh(String sfzh) {
                    this.sfzh = sfzh;
                }

                public String getUserId() {
                    return userId;
                }

                public void setUserId(String userId) {
                    this.userId = userId;
                }

                public String getXm() {
                    return xm;
                }

                public void setXm(String xm) {
                    this.xm = xm;
                }
            }
        }

        public static class ServerSignBean {
            /**
             * alg : SM3+SM2
             * signature : 2WIjCJ/+94XmtIei2+/8eV40dxfvQGqV4ws7WDA5iNPc//zeZTFNewibqa8OSKprWq2P6mnWHMkVPYP+Qdl7Huc0qHJMVFUovF+mqtO5pSxrngem5kXmVK9v1NV7OMurPpa1FiQc1uU0A4Zae4ZrpynM2+qEck/D8KSvLvsKkCQ=
             * sn : b11000000000bdc
             * url : http://192.168.24.108:8080
             */

            private String alg;
            private String signature;
            private String sn;
            private String url;

            public String getAlg() {
                return alg;
            }

            public void setAlg(String alg) {
                this.alg = alg;
            }

            public String getSignature() {
                return signature;
            }

            public void setSignature(String signature) {
                this.signature = signature;
            }

            public String getSn() {
                return sn;
            }

            public void setSn(String sn) {
                this.sn = sn;
            }

            public String getUrl() {
                return url;
            }

            public void setUrl(String url) {
                this.url = url;
            }
        }
    }
}
