package gdut.edu.datingforballsports.util;

import java.util.ArrayList;
import java.util.List;

public class TextUtils {
    public static boolean isEmpty(String s) {
        if (s == null || s == "") {
            return true;
        }
        return false;
    }

    public static <T> List<T> castList(Object obj, Class<T> clazz){
        List<T> result = new ArrayList<>();
        if(obj instanceof List<?>){
            for (Object o : (List<?>) obj){
                result.add(clazz.cast(o));
            }
            return result;
        }
        return new ArrayList<>();
    }
}
