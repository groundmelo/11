package com.example.listviewdemo;

import androidx.appcompat.app.AppCompatActivity;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.Gravity;
import android.widget.TextView;
import android.widget.Toast;
import com.example.listviewdemo.R;
// 导入 Toolbar 类（用于顶部导航栏）
import androidx.appcompat.widget.Toolbar;
// 导入 ViewGroup 类（用于视图容器操作）
import android.view.ViewGroup;
public class MainActivity3 extends AppCompatActivity {

    // 声明测试用的TextView
    private TextView testTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // 1. 加载 Toolbar 布局
        setContentView(R.layout.toolbar);
        // 2. 设置 Toolbar 为 ActionBar
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        // 3. 初始化 TextView 并添加到布局
        initTestTextView();
        // 4. 将 TextView 添加到当前视图（需用父布局包裹）
        ViewGroup rootView = findViewById(android.R.id.content);
        rootView.addView(testTextView);
    }
    /**
     * 初始化测试文本框：设置内容、位置并显示
     */
    private void initTestTextView() {
        testTextView = new TextView(this);
        testTextView.setText("用于测试的内容");
        testTextView.setTextSize(16);
        testTextView.setTextColor(Color.BLACK);
        testTextView.setGravity(Gravity.CENTER);
        // 移除这行：setContentView(testTextView);
    }

    /**
     * 加载菜单：将menu_main.xml关联到当前活动
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // 加载菜单资源
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true; // 返回true表示菜单显示
    }

    /**
     * 菜单点击事件：处理每个菜单项的逻辑
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int itemId = item.getItemId();
        if (itemId == R.id.size_small) {
            testTextView.setTextSize(10);
            return true;
        } else if (itemId == R.id.size_medium) {
            testTextView.setTextSize(16);
            return true;
        } else if (itemId == R.id.size_large) {
            testTextView.setTextSize(20);
            return true;
        } else if (itemId == R.id.menu_normal) {
            Toast.makeText(this, "您点击了普通菜单项", Toast.LENGTH_SHORT).show();
            return true;
        } else if (itemId == R.id.color_red) {
            testTextView.setTextColor(Color.RED);
            return true;
        } else if (itemId == R.id.color_black) {
            testTextView.setTextColor(Color.BLACK);
            return true;
        } else {
            return super.onOptionsItemSelected(item);
        }
    }
}