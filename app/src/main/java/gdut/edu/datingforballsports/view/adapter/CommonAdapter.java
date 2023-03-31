package gdut.edu.datingforballsports.view.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import java.util.List;

import gdut.edu.datingforballsports.R;
import gdut.edu.datingforballsports.view.viewholder.ViewHolder;

public abstract class CommonAdapter<T> extends BaseAdapter {
    private Context context;
    private List<T> list;

    public CommonAdapter(Context context, List<T> list) {
        this.context = context;
        this.list = list;
    }

    @Override
    public int getCount() {
        return list == null ? 0 : list.size();
    }

    @Override
    public T getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    //TODO
    //感觉这里太耦合了啊，就是这个R.layout.forum_item
    @Override
    public View getView(int position, View view, ViewGroup viewGroup) {
        ViewHolder holder = ViewHolder.get(view, viewGroup, R.layout.forum_item, position);
        convert(holder, getItem(position));
        return holder.getConvertView();
    }

    public abstract void convert(ViewHolder holder, T item);
}
