package gdut.edu.datingforballsports.view.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import gdut.edu.datingforballsports.view.viewholder.CommonViewHolder;


public class CommonAdapter<T> extends RecyclerView.Adapter<CommonViewHolder> {

    private final List<T> mList;

    private OnBindDataListener<T> onBindDataListener;
    private OnMoreBindDataListener<T> onMoreBindDataListener;

    // 单类型
    public CommonAdapter(List<T> mList, OnBindDataListener<T> onBindDataListener) {
        this.mList = mList;
        this.onBindDataListener = onBindDataListener;
    }

    // 多类型
    public CommonAdapter(List<T> mList, OnMoreBindDataListener<T> onMoreBindDataListener) {
        this.mList = mList;
        this.onBindDataListener = onMoreBindDataListener;
        this.onMoreBindDataListener = onMoreBindDataListener;
    }

    //绑定数据
    public interface OnBindDataListener<T> {
        void onBindViewHolder(T model, CommonViewHolder viewHolder, int type, int position);

        int getLayoutId(int type);
    }

    //绑定多类型的数据
    public interface OnMoreBindDataListener<T> extends OnBindDataListener<T> {
        int getItemType(int position);
    }

    @Override
    public int getItemViewType(int position) {
        if (onMoreBindDataListener != null) {
            return onMoreBindDataListener.getItemType(position);
        }
        return 0;
    }

    @Override
    public CommonViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        int layoutId = onBindDataListener.getLayoutId(viewType);
        View view = LayoutInflater.from(parent.getContext()).inflate(layoutId, parent, false);
        return CommonViewHolder.getCommonViewHolder(parent.getContext(), view);
    }

    //根据图片数量进行Item选择
    @Override
    public void onBindViewHolder(CommonViewHolder holder, int position) {
        onBindDataListener.onBindViewHolder(mList.get(position), holder, getItemViewType(position), position);
    }

    @Override
    public int getItemCount() {
        return mList == null ? 0 : mList.size();
    }

    // 插入一项数据
    public void insert(T item, int position) {
        mList.add(position, item);
        notifyItemInserted(position);
    }

    // 删除一项数据
    public void remove(int position) {
        mList.remove(position);
        notifyItemRemoved(position);
    }

    public void change(T item,int position) {
        mList.remove(position);
        mList.add(position, item);
        notifyItemChanged(position);
    }

    public void changeAll(List<T> list) {
        mList.removeAll(list);
        mList.addAll(list);
        notifyDataSetChanged();
    }
}

