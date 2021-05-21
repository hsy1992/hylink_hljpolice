package cn.net.hylink.hljpolice;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.text.TextUtils;
import android.util.Log;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.xdja.reckon.ReckonAgent;
import com.xdja.reckon.function.StateListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.Executors;

import cn.net.hylink.hljpolice.bean.AddressResponseBean;
import cn.net.hylink.hljpolice.bean.ConfigFileBean;
import cn.net.hylink.hljpolice.bean.CredentialBean;
import cn.net.hylink.hljpolice.bean.UrlConfigBean;
import cn.net.hylink.hljpolice.bean.UserInfoDetailBean;

/**
 * @author haosiyuan
 * @date 2020/9/17 3:02 PM
 * info :
 */
public class CredentialUtil {

    private Context context;

    private String TAG = CredentialUtil.this.getClass().getSimpleName();

    /**
     * 寻址uri
     */
    public static final String CREDENTIAL_URI = "content://com.ydjw.ua.getCredential";

    /**
     * 寻址uri
     */
    public static final String ADDRESS_URI = "content://com.ydjw.rsb.getResourceAddress";

    private CredentialBean thisCredentialBean;

    /**
     * 资源id 的映射
     */
    private Map<String, AddressResponseBean> addressMap;

    private Gson gson;
    private AutoParseResource autoParseResource;
    private ConfigFileBean configFileBean;
    private Handler mainHandler = new Handler(Looper.getMainLooper());

    private static class Instance {
        private static CredentialUtil credentialUtil = new CredentialUtil();
    }

    public static CredentialUtil getInstance() {
        return Instance.credentialUtil;
    }

    public void init(final Context context, final String fileName) {
        this.context = context.getApplicationContext();
        gson = new Gson();
        //初始化去解析文件 自动配置应用凭证等
        autoParseResource = new AutoParseResource();
        Executors.newSingleThreadExecutor().execute(new Runnable() {
            @Override
            public void run() {
                autoParseResource.parse(context.getApplicationContext(), gson, fileName);
            }
        });
    }

    /**
     * 获取应用凭证
     *
     * @param configBean
     * @return
     */
    public CredentialBean getCredential(UrlConfigBean configBean) {

        if (thisCredentialBean != null) {
            return thisCredentialBean;
        }

        Uri uri = Uri.parse(CREDENTIAL_URI);

        String messageId = UUID.randomUUID().toString();
        Bundle bundle = new Bundle();
        bundle.putString("messageId", messageId);
        bundle.putString("version", configBean.getVersion());
        bundle.putString("appId", configBean.getAppId());
        bundle.putString("orgId", configBean.getOrgId());
        bundle.putString("networkAreaCode", configBean.getNetworkAreaCode());
        bundle.putString("packageName", configBean.getPackageName());

        Bundle callBack = context.getContentResolver().call(uri, "", null, bundle);
//        Bundle callBack = testData1();
        if (callBack == null) {
            toast("获取应用凭证失败");
            return null;
        }

        String appCredential = callBack.getString("appCredential");
        String userCredential = callBack.getString("userCredential");
        String message = callBack.getString("message");
        Log.d(TAG, "message---" + message);
        if (messageId.equals(callBack.getString("messageId")) &&
                !TextUtils.isEmpty(appCredential) && !TextUtils.isEmpty(userCredential)) {
            Log.d(TAG, "appCredential---" + appCredential);
            Log.d(TAG, "userCredential---" + userCredential);
            CredentialBean returnCredentialBean = new CredentialBean();
            returnCredentialBean.setAppCredential(appCredential);
            returnCredentialBean.setUserCredential(userCredential);
            returnCredentialBean.setPackageName(configBean.getPackageName());
            returnCredentialBean.setVersion(configBean.getVersion());
            thisCredentialBean = returnCredentialBean;

            UserInfoDetailBean userInfoDetailBean = gson.fromJson(userCredential, UserInfoDetailBean.class);
            ReckonAgent.getInstance(context)
                    .config("20.20.1.40", "8090")
                    .startAnalytics(userInfoDetailBean.getCredential().getLoad().getUserInfo().getUserId())
                    .addStateListener(new StateListener() {
                        @Override
                        public void reportState(String state) {
                            Log.e(">>>>", "reportState: " + state);
                        }
                    });
            ReckonAgent.getInstance(context).stopAnalytics();
            return returnCredentialBean;
        } else {
            toast("获取应用凭证失败");
            return null;
        }
    }

    /**
     * 获取资源地址
     *
     * @return
     */
    public Map<String, AddressResponseBean> getResourceAddressList(CredentialBean credentialBean) {

        if (addressMap != null && addressMap.size() > 0) {
            return addressMap;
        }

        Uri uri = Uri.parse(ADDRESS_URI);

        Bundle params = new Bundle();
        String messageId = UUID.randomUUID().toString();
        params.putString("messageId", messageId);
        params.putString("version", credentialBean.getVersion());
        params.putString("appCredential", credentialBean.getAppCredential());

        Bundle callBack = context.getContentResolver().call(uri, "", null, params);

//        Bundle callBack = testData();
        if (callBack == null) {
            toast("获取应用资源地址失败");
            return null;
        }

        if (messageId.equals(callBack.getString("messageId"))) {

            String resourceList = callBack.getString("resourceList");
            String message = callBack.getString("message");
            Log.d(TAG, "message---" + message);
            int resultCode = callBack.getInt("resultCode");
            if (resultCode == 0) {
                //寻址成功
                addressMap = new HashMap<>();
                Log.d(TAG, "resourceList---" + resourceList);
                List<AddressResponseBean> addressResponseBeanList = gson.fromJson(resourceList,
                        new TypeToken<List<AddressResponseBean>>() {
                        }.getType());

                for (AddressResponseBean addressResponseBean : addressResponseBeanList) {
                    addressMap.put(addressResponseBean.getResourceId(), addressResponseBean);
                }

                return addressMap;
            }
        }

        toast("获取应用资源地址失败");
        return null;
    }

    public CredentialBean getThisCredentialBean() {
        return thisCredentialBean;
    }

    public Map<String, AddressResponseBean> getAddressMap() {
        return addressMap;
    }


    public void setConfigFile(ConfigFileBean configFileBean) {
        this.configFileBean = configFileBean;
    }

    public ConfigFileBean getConfigFileBean() {
        return configFileBean;
    }

    private Bundle testData() {
        Bundle bundle = new Bundle();
        List<AddressResponseBean> addressResponseBeanList = new ArrayList<>();
        AddressResponseBean addressResponseBean = new AddressResponseBean();
        addressResponseBean.setResourceAddress("http://192.168.39.2:8080/endless");
        addressResponseBean.setResourceId("1");
        addressResponseBean.setResourceRegionalismCode("123");
        addressResponseBean.setResourceServiceType("123");
        addressResponseBeanList.add(addressResponseBean);
        bundle.putInt("resultCode", 0);
        bundle.putString("resourceList", gson.toJson(addressResponseBeanList));
        bundle.putString("messageId", "100002");
        return bundle;
    }

    private Bundle testData1() {
        Bundle bundle = new Bundle();
        bundle.putString("messageId", "100001");
        bundle.putString("appCredential", "appCredential");
        bundle.putString("userCredential", "userCredential");
        return bundle;
    }

    private void toast(final String message) {
        mainHandler.post(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
            }
        });
    }
}
