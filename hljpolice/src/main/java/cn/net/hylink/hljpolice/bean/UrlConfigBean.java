package cn.net.hylink.hljpolice.bean;

/**
 * @author haosiyuan
 * @date 2020/9/17 3:13 PM
 * info :
 */
public class UrlConfigBean {

    private String version;
    private String appId;
    private String orgId;
    private String networkAreaCode;
    private String packageName;
    private String regionalismCode;
    private String networkCode;

    public String getNetworkCode() {
        return networkCode;
    }

    public void setNetworkCode(String networkCode) {
        this.networkCode = networkCode;
    }

    public String getRegionalismCode() {
        return regionalismCode;
    }

    public void setRegionalismCode(String regionalismCode) {
        this.regionalismCode = regionalismCode;
    }

    public String getVersion() {
        return version;
    }

    public String getAppId() {
        return appId;
    }

    public String getOrgId() {
        return orgId;
    }

    public String getNetworkAreaCode() {
        return networkAreaCode;
    }

    public String getPackageName() {
        return packageName;
    }


    public void setVersion(String version) {
        this.version = version;
    }

    public void setAppId(String appId) {
        this.appId = appId;
    }

    public void setOrgId(String orgId) {
        this.orgId = orgId;
    }

    public void setNetworkAreaCode(String networkAreaCode) {
        this.networkAreaCode = networkAreaCode;
    }

    public void setPackageName(String packageName) {
        this.packageName = packageName;
    }

}
