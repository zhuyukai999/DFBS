package gdut.edu.datingforballsports.view.viewholder;

import android.content.Context;
import android.graphics.Bitmap;
import android.util.SparseArray;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

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

    /**
     * 设置图片
     *
     * @param viewId id
     * @param bmp  图片
     * @return this
     */
    public CommonViewHolder setImageResource(int viewId, Bitmap bmp) {
        ImageView iv = getView(viewId);
        iv.setImageBitmap(bmp);
        return this;
    }

    public void onItemClick(View view, View.OnClickListener listener) {
        view.setOnClickListener(listener);
    }

    public void onItemClick(List<View> list, View.OnClickListener listener) {
        for (Object view : list) {
            ((View)view).setOnClickListener(listener);
        }
    }
}

