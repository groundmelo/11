package com.example.listviewdemo;

import android.os.Bundle;
import android.view.ActionMode;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AbsListView;
import android.widget.ListView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import java.util.ArrayList;
import java.util.List;

public class MainActivity4 extends AppCompatActivity {

    private ListView mListView;
    private SelectableAdapter mAdapter; // 自定义适配器
    private List<String> mListData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main4); // 确保布局文件存在

        initListViewAndData();
        setupListViewMultiChoice();
    }

    // 初始化列表和数据
    private void initListViewAndData() {
        mListView = findViewById(R.id.list_view);
        mListData = new ArrayList<>();

        // 添加测试数据
        mListData.add("Item One");
        mListData.add("Item Two");
        mListData.add("Item Three");
        mListData.add("Item Four");
        mListData.add("Item Five");
        mListData.add("Item Six");
        mListData.add("Item Seven");

        // 初始化自定义适配器
        mAdapter = new SelectableAdapter(
                this,
                R.layout.item_list4, // 列表项布局
                R.id.tv_item_text,   // 文本控件ID
                mListData
        );
        mListView.setAdapter(mAdapter);
    }

    // 设置列表的多选模式
    private void setupListViewMultiChoice() {
        mListView.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE_MODAL);
        mListView.setMultiChoiceModeListener(new AbsListView.MultiChoiceModeListener() {

            @Override
            public void onItemCheckedStateChanged(ActionMode mode, int position,
                                                  long id, boolean checked) {
                // 更新适配器中的选中状态
                mAdapter.setSelected(position, checked);
                // 更新标题显示选中数量
                mode.setTitle(mListView.getCheckedItemCount() + " 已选中");
            }

            @Override
            public boolean onCreateActionMode(ActionMode mode, Menu menu) {
                // 加载多选模式下的菜单
                getMenuInflater().inflate(R.menu.menu_context, menu);
                return true;
            }

            @Override
            public boolean onPrepareActionMode(ActionMode mode, Menu menu) {
                return false; // 不需要额外处理
            }

            @Override
            public boolean onActionItemClicked(ActionMode mode, MenuItem item) {
                // 处理菜单点击事件（例如删除选中项）
                if (item.getItemId() == R.id.action_delete) {
                    deleteSelectedItems();
                    mode.finish(); // 退出多选模式
                    return true;
                }
                return false;
            }

            @Override
            public void onDestroyActionMode(ActionMode mode) {
                // 清除所有选中状态
                mAdapter.clearSelections();
                mListView.clearChoices();
            }
        });
    }

    // 删除选中的项
    private void deleteSelectedItems() {
        // 从后往前删除，避免索引错乱
        for (int i = mListData.size() - 1; i >= 0; i--) {
            if (mAdapter.isSelected(i)) {
                mListData.remove(i);
            }
        }
        mAdapter.notifyDataSetChanged(); // 刷新列表
        Toast.makeText(this, "已删除选中项", Toast.LENGTH_SHORT).show();
    }
}