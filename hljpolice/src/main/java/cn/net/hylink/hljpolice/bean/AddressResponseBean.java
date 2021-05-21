package cn.net.hylink.hljpolice.bean;

/**
 * @author haosiyuan
 * @date 2020/9/17 4:09 PM
 * info :
 */
public class AddressResponseBean {

    /**
     * resourceRegionalismCode : 120000000000
     * resourceAddress :
     * resourceId :
     * resourceServiceType : 10
     * resourceRegionalismCode  : 120000000000
     * resourceId  : 120000000000 - 3 - 0100 - 5 b103cc6e245441a9e60a5b4520cc5f3
     * resourceServiceType  : 10
     * resourceAddress  : http: //20.3.1.162: 9105 / drs / ppc / v1 / appQuery.do
     */

    private String resourceRegionalismCode;
    private String resourceAddress;
    private String resourceId;
    private String resourceServiceType;

    public String getResourceRegionalismCode() {
        return resourceRegionalismCode;
    }

    public void setResourceRegionalismCode(String resourceRegionalismCode) {
        this.resourceRegionalismCode = resourceRegionalismCode;
    }

    public String getResourceAddress() {
        return resourceAddress;
    }

    public void setResourceAddress(String resourceAddress) {
        this.resourceAddress = resourceAddress;
    }

    public String getResourceId() {
        return resourceId;
    }

    public void setResourceId(String resourceId) {
        this.resourceId = resourceId;
    }

    public String getResourceServiceType() {
        return resourceServiceType;
    }

    public void setResourceServiceType(String resourceServiceType) {
        this.resourceServiceType = resourceServiceType;
    }
}
