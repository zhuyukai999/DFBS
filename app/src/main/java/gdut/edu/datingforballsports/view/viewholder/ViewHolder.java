package gdut.edu.datingforballsports.view.viewholder;

import android.content.Context;
import android.graphics.Bitmap;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

public class ViewHolder {
    private final SparseArray<View> views;
    private int position;
    private View convertView;
    private Context context;

    private ViewHolder(Context context, ViewGroup parent, int layoutId, int position) {
        this.position = position;
        this.context = context;
        this.views = new SparseArray<>();
        this.convertView = LayoutInflater.from(parent.getContext()).inflate(layoutId, parent, false);
        convertView.setTag(this);
    }

    public static ViewHolder get(View convertView, ViewGroup parent, int layoutId, int position) {
        if (convertView == null) {
            return new ViewHolder(parent.getContext(), parent, layoutId, position);
        }
        return (ViewHolder) convertView.getTag();
    }

    public <T extends View> T getView(int viewId) {
        View view = views.get(viewId);
        if (view == null) {
            view = convertView.findViewById(viewId);
            views.put(viewId, view);
        }
        return (T) view;
    }

    public View getConvertView() {
        return convertView;
    }

    /**
     * 设置字符串
     */
    public ViewHolder setText(int viewId, String text) {
        TextView tv = getView(viewId);
        tv.setText(text);
        return this;
    }

    //TODO
    //设置图片还有很大问题

    /**
     * 设置图片
     */
    public ViewHolder setImageResource(int viewId, int drawableId) {
        ImageView iv = getView(viewId);
        iv.setImageResource(drawableId);
        return this;
    }

    /**
     * 设置图片
     */
    public ViewHolder setImageBitmap(int viewId, Bitmap bitmap) {
        ImageView iv = getView(viewId);
        iv.setImageBitmap(bitmap);
        return this;
    }

    public int getPosition() {
        return position;
    }
}
