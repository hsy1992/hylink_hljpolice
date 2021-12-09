package cn.net.hylink.hljpolice;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
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

    private Uri uri = Uri.parse(CREDENTIAL_URI);

    private CredentialBean thisCredentialBean;

    /**
     * 资源id 的映射
     */
    private Map<String, AddressResponseBean> addressMap;

    private Gson gson;
    private AutoParseResource autoParseResource;
    private ConfigFileBean configFileBean;
    private Handler mainHandler = new Handler(Looper.getMainLooper());
    private LoginReceiver loginReceiver;
    private String messageId = UUID.randomUUID().toString();
    private Bundle loginBundle;
    private UrlConfigBean configBean;
    private OnResourceListener onResourceListener;

    private static class Instance {
        private static CredentialUtil credentialUtil = new CredentialUtil();
    }

    public static CredentialUtil getInstance() {
        return Instance.credentialUtil;
    }

    public void init(final Context context, final String fileName) {
        init(context, fileName, null);
    }

    public void init(final Context context, final String fileName, OnResourceListener onResourceListener) {
        if (!checkApplication(context, "com.xdja.safeclient")) return;
        this.onResourceListener = onResourceListener;
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
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction("com.ydjw.ua.ACTION_LOGIN");
        loginReceiver = new LoginReceiver();
        context.registerReceiver(loginReceiver, intentFilter);
    }

    /**
     * 获取应用凭证
     *
     * @param configBean
     * @return
     */
    public CredentialBean getCredential(UrlConfigBean configBean) {
        Log.i(TAG, "getCredential:");
        if (thisCredentialBean != null) {
            return thisCredentialBean;
        }
        this.configBean = configBean;

        loginBundle = new Bundle();
        loginBundle.putString("messageId", UUID.randomUUID().toString());
        loginBundle.putString("version", configBean.getVersion());
        loginBundle.putString("appId", configBean.getAppId());
        loginBundle.putString("orgId", configBean.getOrgId());
        loginBundle.putString("networkAreaCode", configBean.getNetworkAreaCode());
        loginBundle.putString("packageName", configBean.getPackageName());

        Bundle callBack = context.getContentResolver().call(uri, "", null, loginBundle);
        Log.i(TAG, "getCredential:" + callBack);
        if (callBack != null) {
            useCallback(callBack);
        }
        return null;
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

        if (callBack == null) {
            try {
                Thread.sleep(1000L);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            getResourceAddressList(credentialBean);
        } else {

            if (messageId.equals(callBack.getString("messageId"))) {

                String resourceList = callBack.getString("resourceList");
                String message = callBack.getString("message");
                Log.d(TAG, "message---" + message);
                Log.d(TAG, "resourceList---" + resourceList);
                Log.d(TAG, "thread---" + Thread.currentThread().getName());
                int resultCode = callBack.getInt("resultCode");
                if (resultCode == 0) {
                    //寻址成功
                    addressMap = new HashMap<>();
                    Log.d(TAG, "寻址成功 resourceList---" + resourceList);
                    List<AddressResponseBean> addressResponseBeanList = gson.fromJson(resourceList,
                            new TypeToken<List<AddressResponseBean>>() {
                            }.getType());

                    for (AddressResponseBean addressResponseBean : addressResponseBeanList) {
                        addressMap.put(addressResponseBean.getResourceId(), addressResponseBean);
                    }

                    if (onResourceListener != null) {
                        onResourceListener.onResourceSuccess();
                    }

                    return addressMap;
                } else {

                    Log.d(TAG, "获取应用资源地址失败重试");
                    try {
                        Thread.sleep(1000L);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    getResourceAddressList(credentialBean);
                }
            }
        }

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

    private void useCallback(Bundle callBack) {
        String appCredential = callBack.getString("appCredential");
        String userCredential = callBack.getString("userCredential");
        String message = callBack.getString("message");
        Log.d(TAG, "message---" + message);
        Log.d(TAG, "appCredential---" + appCredential);
        Log.d(TAG, "userCredential---" + userCredential);
        if (!TextUtils.isEmpty(callBack.getString("messageId")) &&
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

            if (returnCredentialBean != null) {
                getResourceAddressList(returnCredentialBean);
            }
        } else {
            Log.d(TAG, "获取凭证失败重试:");
        }
    }

    private void toast(final String message) {
        mainHandler.post(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
            }
        });
    }

    class LoginReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(final Context context, final Intent intent) {
            new Thread(new Runnable() {
                @Override
                public void run() {
                    Log.i(TAG, "action:" + intent.getAction());
                    loginBundle.putString("messageId", UUID.randomUUID().toString());
                    Bundle callBack = context.getContentResolver().call(uri, "", null, loginBundle);
                    if (callBack == null) {
                        //失败
                        Log.i(TAG, "callBack == null:");
                        getCredential(configBean);
                        return;
                    }

                    useCallback(callBack);
                }
            }).start();
        }
    }

    /**
     * 判断该包名的应用是否安装
     *
     * @param context     上下文
     * @param packageName 应用包名
     * @return 是否安装
     */
    private boolean checkApplication(Context context, String packageName) {
        if (packageName == null || "".equals(packageName)) {
            return false;
        }
        try {
            context.getPackageManager().getApplicationInfo(packageName, PackageManager.GET_UNINSTALLED_PACKAGES);
            return true;
        } catch (PackageManager.NameNotFoundException e) {
            return false;
        }
    }

}
