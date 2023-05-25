package gdut.edu.datingforballsports.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class TextUtils {
    public static boolean isEmpty(String s) {
        if (s == null || s == "") {
            return true;
        }
        return false;
    }

    public static <T> List<T> castList(Object obj, Class<T> clazz) {
        List<T> result = new ArrayList<>();
        if (obj instanceof List<?>) {
            for (Object o : (List<?>) obj) {
                result.add(clazz.cast(o));
            }
            return result;
        }
        return null;
    }


    public static void copyDir(File srcFile, File destFile) {

        if (!destFile.exists()) {
            destFile.getParentFile().mkdirs();
            try {
                destFile.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            destFile.delete();
        }
        System.out.println("file1:" + destFile.exists());
        FileInputStream in = null;
        FileOutputStream out = null;
        try {
            if (srcFile.exists()) {
                in = new FileInputStream(srcFile);
                out = new FileOutputStream(destFile);
                byte[] bytes = new byte[1024 * 1024];  //一次复制1MB
                int readCount;
                while ((readCount = in.read(bytes)) != -1) {
                    out.write(bytes, 0, readCount);
                }
                in.close();
                out.close();
            }
            System.out.println("file1:" + destFile.exists());
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (in != null) {
                try {
                    in.close(); // 关闭流
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (out != null) {
                try {
                    out.close(); // 关闭流
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }


}
