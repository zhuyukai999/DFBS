package gdut.edu.datingforballsports.util;

import android.content.Context;
import android.content.SharedPreferences;

public class SharedPreferenceUtils {

    private static SharedPreferences sharedPreferences;

    private static SharedPreferences getSharedPreferences(Context context) {
        if (sharedPreferences == null) {
            sharedPreferences = context.getSharedPreferences("SharedPreferenceUtils", Context.MODE_PRIVATE);
        }
        return sharedPreferences;
    }

    /**
     * 存入字符串
     *
     * @param context 上下文
     * @param key     字符串的键
     * @param value   字符串的值
     */
    public static void putString(Context context, String key, String value) {
        SharedPreferences preferences = getSharedPreferences(context);
        //存入数据
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString(key, value);
        editor.commit();

    }

    /**
     * 获取字符串
     *
     * @param context 上下文
     * @param key     字符串的键
     * @param value   字符串的默认值
     * @return 得到的字符串
     */
    public static String getString(Context context, String key, String value) {
        SharedPreferences preferences = getSharedPreferences(context);
        return preferences.getString(key, value);
    }


}
