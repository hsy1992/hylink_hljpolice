package cn.net.hylink.hljpolice;

import android.content.Context;
import android.content.SharedPreferences;
import android.text.TextUtils;

import com.google.gson.Gson;

import java.lang.reflect.Type;

/**
 * @author haosiyuan
 * @date 2020/9/18 9:42 AM
 * info :
 */
class AppPreferences {

    /**
     * 修改
     */
    private SharedPreferences.Editor editor;
    /**
     * 存储数据
     */
    private SharedPreferences sharedPreferences;

    private Gson gson;

    /**
     * 构造方法
     * @param context
     */
    public AppPreferences(Context context) {
        sharedPreferences = context.getSharedPreferences("credential", 0);
        editor = sharedPreferences.edit();
        gson = new Gson();
    }

    public void putObject(String key, Object value) {
        this.editor.putString(key, gson.toJson(value)).commit();
    }

    public void putBoolean(String key, boolean value) {
        this.editor.putBoolean(key, value).commit();
    }

    public void putFloat(String key, float value) {
        this.editor.putFloat(key, value).commit();
    }

    public void putInt(String key, int value) {
        this.editor.putInt(key, value).commit();
    }

    public void putLong(String key, long value) {
        this.editor.putLong(key, value).commit();
    }

    public void putString(String key, String value) {
        this.editor.putString(key, value).commit();
    }

    public boolean getBoolean(String key, boolean value) {
        return this.sharedPreferences.getBoolean(key, value);
    }

    public float getFloat(String key, float value) {
        return this.sharedPreferences.getFloat(key, value);
    }

    public int getInt(String key, int value) {
        return this.sharedPreferences.getInt(key, value);
    }

    public long getLong(String key, long value) {
        return this.sharedPreferences.getLong(key, value);
    }

    public String getString(String key, String value) {
        return this.sharedPreferences.getString(key, value);
    }

    public <T> T getObject(String key, Type type) {
        String json = getString(key, "");
        return gson.fromJson(json, type);
    }

    public void clearObject(String key) {
        this.editor.remove(key).commit();
    }

    /**
     * 清除
     */
    public void clear() {
        this.editor.clear().commit();
    }
}
