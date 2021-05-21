package cn.net.hylink.hljpolice.bean;

/**
 * @author haosiyuan
 * @date 2020/9/18 9:45 AM
 * info :
 */
public class CredentialBean {

    private String appCredential;

    private String userCredential;

    private String packageName;

    private String version;

    public String getAppCredential() {
        return appCredential;
    }

    public void setAppCredential(String appCredential) {
        this.appCredential = appCredential;
    }

    public String getUserCredential() {
        return userCredential;
    }

    public void setUserCredential(String userCredential) {
        this.userCredential = userCredential;
    }

    public String getPackageName() {
        return packageName;
    }

    public void setPackageName(String packageName) {
        this.packageName = packageName;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }
}
