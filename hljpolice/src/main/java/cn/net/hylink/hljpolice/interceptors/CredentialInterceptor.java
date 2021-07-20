package cn.net.hylink.hljpolice.interceptors;

import android.text.TextUtils;

import com.google.gson.Gson;

import java.io.IOException;
import java.net.URLEncoder;
import java.nio.charset.Charset;
import java.util.Arrays;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import cn.net.hylink.hljpolice.CredentialUtil;
import cn.net.hylink.hljpolice.bean.AddressResponseBean;
import cn.net.hylink.hljpolice.bean.CredentialBean;
import cn.net.hylink.hljpolice.bean.RequestBean;
import cn.net.hylink.hljpolice.bean.ResponseBean;
import cn.net.hylink.hljpolice.bean.UrlConfigBean;
import cn.net.hylink.hljpolice.bean.UrlResourceBean;
import okhttp3.Interceptor;
import okhttp3.MediaType;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.ResponseBody;
import okio.Buffer;

/**
 * @author haosiyuan
 * @date 2020/9/18 11:08 AM
 * info :
 */
public class CredentialInterceptor implements Interceptor {

    private static final MediaType MEDIA_TYPE = MediaType.parse("application/json; charset=UTF-8");

    private static final Charset UTF_8 = Charset.forName("UTF-8");

    private Map<String, AddressResponseBean> map = new ConcurrentHashMap<>();

    private Gson gson;

    private static final String KEY = "pd";

    private static final String SUCCESS = "200";

    public CredentialInterceptor() {
        gson = new Gson();
    }

    @Override
    public Response intercept(Chain chain) throws IOException {

        Request request = chain.request();

        if (CredentialUtil.getInstance().getAddressMap() != null &&
                CredentialUtil.getInstance().getConfigFileBean() != null &&
                CredentialUtil.getInstance().getThisCredentialBean() != null
        ) {

            //构建请求头 及参数
            AddressResponseBean addressResponseBean = findUrlFromResource(request.url().encodedPath());

            if (addressResponseBean != null) {
                UrlConfigBean urlConfig = CredentialUtil.getInstance().getConfigFileBean().getUrlConfigBean();
                CredentialBean credential = CredentialUtil.getInstance().getThisCredentialBean();
                Request.Builder builder = request.newBuilder().url(addressResponseBean.getResourceAddress())
                        .addHeader("appCredential", URLEncoder.encode(credential.getAppCredential(), "UTF-8"))
                        .addHeader("userCredential", URLEncoder.encode(credential.getUserCredential(), "UTF-8"));

                Buffer sink = new Buffer();
                request.body().writeTo(sink);
                String value = sink.readString(UTF_8);
                RequestBean requestBean = new RequestBean();
                RequestBean.ParameterBean parameterBean = new RequestBean.ParameterBean(new RequestBean.ParameterBean.ConditionBean(
                        Arrays.asList(new RequestBean.ParameterBean.ConditionBean.KeyValueListBean(KEY, value))),
                        addressResponseBean.getResourceId(), "data", urlConfig.getNetworkCode(), urlConfig.getRegionalismCode());
                requestBean.setParameter(parameterBean);

                request = builder.post(RequestBody.create(MEDIA_TYPE, gson.toJson(requestBean))).build();

                //解析返回参数
                Response response = chain.proceed(request);
                ResponseBean responseBean = gson.fromJson(response.body().string(), ResponseBean.class);

                try {
                    if (SUCCESS.equals(responseBean.getCode())) {
                        //成功返回
                        String responseStr = responseBean.getData().getDataList().get(0).getFieldValues().get(0).getValue();
                        return response.newBuilder().body(ResponseBody.create(MEDIA_TYPE, responseStr)).build();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }

                return response.newBuilder().code(500).build();
            }
        }

        return chain.proceed(request);
    }

    /**
     * 找寻地址id
     *
     * @param path
     * @return
     */
    private AddressResponseBean findUrlFromResource(String path) {

        if (map.get(path) != null) {
            return map.get(path);
        }

        String resourceId = "";

        for (UrlResourceBean urlResourceBean : CredentialUtil.getInstance().getConfigFileBean().getList()) {
            if (path.contains(urlResourceBean.getUrl())) {
                resourceId = urlResourceBean.getResourceId();
            }
        }

        if (!TextUtils.isEmpty(resourceId)) {
            AddressResponseBean addressBean = CredentialUtil.getInstance().getAddressMap().get(resourceId);
            if (addressBean != null) {
                map.put(path, addressBean);
                return addressBean;
            }
        }

        return null;
    }
}
