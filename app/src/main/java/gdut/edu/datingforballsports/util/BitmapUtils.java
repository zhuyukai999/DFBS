package gdut.edu.datingforballsports.util;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

public class BitmapUtils {
    public static Bitmap ratio(String filePath,int pixelW,int pixelH){
        BitmapFactory.Options newOptions = new BitmapFactory.Options();
        newOptions.inJustDecodeBounds = true;
        newOptions.inPreferredConfig = Bitmap.Config.RGB_565;
        //预加载
        BitmapFactory.decodeFile(filePath,newOptions);
        int originalW = newOptions.outWidth;
        int originalH = newOptions.outHeight;
        newOptions.inSampleSize = getSimpleSize(originalW,
                originalH,pixelW,pixelH);
        newOptions.inJustDecodeBounds = false;
        return BitmapFactory.decodeFile(filePath,newOptions);
    }

    private static int getSimpleSize(int originalW, int originalH, int pixelW, int pixelH) {
        int simpleSize = 1;
        if (originalW > pixelW || originalH > pixelH) {
            //计算出实际宽高和目标宽高的比率
            final int widthRatio = Math.round((float) originalW / (float) pixelW);
            final int heightRatio = Math.round((float) originalH / (float) pixelH);
            /*选择宽和高中最小的比率作为inSampleSize的值，这样可以保证最终图片的宽和高
            一定都会大于等于目标的宽和高。*/
            simpleSize = heightRatio < widthRatio ? heightRatio : widthRatio;
        }
        return simpleSize;
    }
}
