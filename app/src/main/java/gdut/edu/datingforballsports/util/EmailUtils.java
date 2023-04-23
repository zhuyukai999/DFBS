package gdut.edu.datingforballsports.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class EmailUtils {
    public static final Pattern p = Pattern.compile("\\w+([-+.]\\w+)*@\\w+([-.]\\w+)*\\.\\w+([-.]\\w+)*");
    public static boolean isEmail(String email) {
        if (null == email || "".equals(email)) return false;
        Matcher m = p.matcher(email);
        return m.matches();
    }
}
