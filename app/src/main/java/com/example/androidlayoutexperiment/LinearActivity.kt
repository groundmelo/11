package com.example.androidlayoutexperiment

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class LinearActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // 可选：若不需要沉浸式边缘，可删除 enableEdgeToEdge() 及以下3行代码
        enableEdgeToEdge()
        setContentView(R.layout.activity_linear) // 确保布局文件名与实际一致（activity_linear.xml）

        // 关键：findViewById(R.id.main) 引用的ID已在布局中定义（android:id="@+id/main"）
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }
}