package cn.net.hylink.hljpolice.bean;

import java.util.List;

/**
 * @author haosiyuan
 * @date 2020/9/18 10:56 AM
 * info :
 */
public class ConfigFileBean {

    private List<UrlResourceBean> list;

    private UrlConfigBean urlConfigBean;

    public List<UrlResourceBean> getList() {
        return list;
    }

    public void setList(List<UrlResourceBean> list) {
        this.list = list;
    }

    public UrlConfigBean getUrlConfigBean() {
        return urlConfigBean;
    }

    public void setUrlConfigBean(UrlConfigBean urlConfigBean) {
        this.urlConfigBean = urlConfigBean;
    }
}
