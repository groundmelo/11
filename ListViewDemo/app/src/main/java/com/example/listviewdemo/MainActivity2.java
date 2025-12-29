package com.example.listviewdemo;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.listviewdemo.R;

public class MainActivity2 extends AppCompatActivity {

    private Button btnOpenDialog;  // 触发对话框的按钮

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        // 1. 绑定主布局中的按钮
        btnOpenDialog = findViewById(R.id.btn_open_dialog);

        // 2. 给按钮设置点击事件：点击打开自定义对话框
        btnOpenDialog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showCustomSignInDialog();  // 打开对话框的核心方法
            }
        });
    }

    /**
     * 显示自定义的登录对话框（完全匹配文档要求）
     */
    private void showCustomSignInDialog() {
        // 步骤1：加载自定义布局文件（dialog_sign_in.xml）
        View dialogView = LayoutInflater.from(this)
                .inflate(R.layout.dialog_sign_in, null);  // 加载布局到View对象

        // 步骤2：绑定对话框布局中的控件（输入框、按钮）
        EditText etUsername = dialogView.findViewById(R.id.et_username);  // Username输入框
        EditText etPassword = dialogView.findViewById(R.id.et_password);  // Password输入框
        Button btnCancel = dialogView.findViewById(R.id.btn_cancel);      // Cancel按钮
        Button btnSignIn = dialogView.findViewById(R.id.btn_sign_in);    // Sign in按钮

        // 步骤3：创建AlertDialog.Builder对象（基础配置）
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setView(dialogView)  // 关键：将自定义布局添加到对话框（文档要求的setView()）
                .setCancelable(false); // 可选：点击对话框外部不关闭（增强登录场景严谨性）

        // 步骤4：创建AlertDialog实例（完成构建）
        AlertDialog signInDialog = builder.create();

        // 步骤5：绑定Cancel按钮点击事件（关闭对话框）
        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signInDialog.dismiss();  // 关闭对话框
            }
        });

        // 步骤6：绑定Sign in按钮点击事件（输入校验+业务逻辑）
        btnSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 获取输入的用户名和密码（去除空格）
                String username = etUsername.getText().toString().trim();
                String password = etPassword.getText().toString().trim();

                // 简单输入校验（可根据需求扩展，如正则匹配、联网验证等）
                if (username.isEmpty() || password.isEmpty()) {
                    // 提示用户输入完整信息
                    Toast.makeText(MainActivity2.this, "Please enter Username and Password", Toast.LENGTH_SHORT).show();
                } else {
                    // 校验通过：提示登录成功（实际项目中可替换为跳转页面、保存用户信息等逻辑）
                    Toast.makeText(MainActivity2.this, "Sign In Success! Welcome, " + username, Toast.LENGTH_SHORT).show();
                    signInDialog.dismiss();  // 关闭对话框
                }
            }
        });

        // 步骤7：显示对话框（最后一步，确保前面配置完成）
        signInDialog.show();
    }
}