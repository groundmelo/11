package com.example.listviewdemo; // 替换为你的包名

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.TextView;

import java.util.List;

public class SelectableAdapter extends ArrayAdapter<String> {
    private boolean[] mSelected; // 记录每个item的选中状态

    public SelectableAdapter(Context context, int resource, int textViewResourceId, List<String> objects) {
        super(context, resource, textViewResourceId, objects);
        mSelected = new boolean[objects.size()]; // 初始化选中状态数组
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = super.getView(position, convertView, parent);

        // 找到复选框并设置状态
        CheckBox checkBox = view.findViewById(R.id.cb_check);
        checkBox.setVisibility(isInSelectMode() ? View.VISIBLE : View.GONE);
        checkBox.setChecked(mSelected[position]);

        return view;
    }

    /**
     * 新增：判断指定位置的item是否被选中
     */
    public boolean isSelected(int position) {
        return mSelected[position];
    }

    /**
     * 更新指定位置的选中状态
     */
    public void setSelected(int position, boolean selected) {
        mSelected[position] = selected;
        notifyDataSetChanged();
    }

    /**
     * 判断是否处于选择模式（有至少一个item被选中）
     */
    public boolean isInSelectMode() {
        for (boolean selected : mSelected) {
            if (selected) return true;
        }
        return false;
    }

    /**
     * 清除所有选中状态
     */
    public void clearSelections() {
        for (int i = 0; i < mSelected.length; i++) {
            mSelected[i] = false;
        }
        notifyDataSetChanged();
    }

    /**
     * 数据更新时同步更新选中状态数组长度（解决数据删除后数组越界问题）
     */
    @Override
    public void notifyDataSetChanged() {
        // 当数据数量变化时，重新初始化选中状态数组
        boolean[] newSelected = new boolean[getCount()];
        System.arraycopy(mSelected, 0, newSelected, 0, Math.min(mSelected.length, newSelected.length));
        mSelected = newSelected;
        super.notifyDataSetChanged();
    }
}