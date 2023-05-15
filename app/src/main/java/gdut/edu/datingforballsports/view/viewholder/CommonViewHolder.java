package gdut.edu.datingforballsports.view.viewholder;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.util.SparseArray;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import gdut.edu.datingforballsports.util.GlideEngine;
import gdut.edu.datingforballsports.view.activity.EditPostActivity;

public class CommonViewHolder extends RecyclerView.ViewHolder {

    // 子View的集合
    private SparseArray<View> mViews;
    private Context context;

    public CommonViewHolder(Context context, View itemView) {
        super(itemView);
        this.context = context;
        mViews = new SparseArray<>();
    }

    public static CommonViewHolder getCommonViewHolder(Context context, View itemView) {
        return new CommonViewHolder(context, itemView);
    }

    /**
     * 提供给外部访问 View 的方法
     *
     * @param viewId id
     * @param <T>    泛型
     * @return
     */
    public <T extends View> T getView(int viewId) {
        View view = mViews.get(viewId);
        if (view == null) {
            view = itemView.findViewById(viewId);
            mViews.put(viewId, view);
        }
        return (T) view;
    }

    /**
     * 设置文本
     *
     * @param viewId id
     * @param text   文本
     * @return this
     */
    public CommonViewHolder setText(int viewId, String text) {
        TextView tv = getView(viewId);
        tv.setText(text);
        return this;
    }

    public CommonViewHolder setText(int viewId, Date date) {
        TextView tv = getView(viewId);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String format = sdf.format(date);
        tv.setText(format);
        return this;
    }

    /**
     * 设置图片
     *
     * @param viewId id
     * @param bmp    图片
     * @return this
     */
    public CommonViewHolder setImageResource(int viewId, Bitmap bmp) {
        ImageView iv = getView(viewId);
        iv.setImageBitmap(bmp);
        return this;
    }

    public CommonViewHolder setImageResource(int viewId, String url) {
        ImageView iv = getView(viewId);
        iv.setTag(url);
        GlideEngine.createGlideEngine().loadItemNetImage(context, url, iv);
        return this;
    }

    public CommonViewHolder changeSelect(int viewId){
        View v = getView(viewId);
        v.setSelected(!v.isSelected());
        return this;
    }

    public CommonViewHolder setSelect(int viewId,boolean sel){
        View v = getView(viewId);
        v.setSelected(sel);
        return this;
    }

    public void onItemClick(View view, View.OnClickListener listener) {
        view.setOnClickListener(listener);
    }

    public void onItemClick(List<View> list, View.OnClickListener listener) {
        for (Object view : list) {
            ((View) view).setOnClickListener(listener);
        }
    }

    public <T> void jumpActivity(Class<T> clazz) {
        context.startActivity(new Intent(context, clazz));
    }

}

