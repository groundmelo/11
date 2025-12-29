package com.example.listviewdemo;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import android.Manifest;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    // 定义动物名称数组和对应图片资源ID数组
    private String[] animalNames = {"Lion", "Tiger", "Monkey", "Dog", "Cat", "Elephant"};
    private int[] animalImages = {R.drawable.lion, R.drawable.tiger, R.drawable.monkey,
            R.drawable.dog, R.drawable.cat, R.drawable.elephant};
    // 通知渠道ID（Android 8.0及以上必须）
    private static final String CHANNEL_ID = "animal_notification_channel";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // 1. 初始化ListView
        ListView lvAnimal = findViewById(R.id.lv_animal);

        // 2. 准备数据：用List<Map>存储每个列表项的图片和文字
        List<Map<String, Object>> dataList = new ArrayList<>();
        for (int i = 0; i < animalNames.length; i++) {
            Map<String, Object> map = new HashMap<>();
            map.put("image", animalImages[i]);  // 键"image"对应图片资源
            map.put("name", animalNames[i]);    // 键"name"对应动物名称
            dataList.add(map);
        }

        // 3. 创建SimpleAdapter，绑定数据到列表项
        SimpleAdapter adapter = new SimpleAdapter(
                this,  // 上下文
                dataList,  // 数据源
                R.layout.item_list,  // 列表项布局文件
                new String[]{"image", "name"},  // 数据源的键（与map的键对应）
                new int[]{R.id.iv_animal, R.id.tv_animal}  // 列表项布局的控件ID（与键一一对应）
        );

        // 4. 给ListView设置适配器
        lvAnimal.setAdapter(adapter);

        // 5. 初始化通知渠道（Android 8.0及以上）
        createNotificationChannel();

        // 6. 设置ListView点击事件（Toast提示+发送通知）
        lvAnimal.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                // 获取选中项的动物名称
                String selectedAnimal = animalNames[position];

                // ① 显示Toast提示选中信息
                Toast.makeText(MainActivity.this, "你选中了：" + selectedAnimal, Toast.LENGTH_SHORT).show();

                // ② 发送通知
                sendNotification(selectedAnimal);
            }
        });
    }

    /**
     * 创建通知渠道（Android 8.0及以上必须）
     */
    private void createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            CharSequence channelName = "动物列表通知";  // 渠道名称（用户可见）
            String channelDescription = "显示选中的动物列表项信息";  // 渠道描述
            int importance = NotificationManager.IMPORTANCE_DEFAULT;  // 通知重要性（默认）

            // 创建渠道
            NotificationChannel channel = new NotificationChannel(CHANNEL_ID, channelName, importance);
            channel.setDescription(channelDescription);

            // 注册渠道到系统
            NotificationManager notificationManager = getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }
    }

    /**
     * 发送通知
     * @param selectedAnimal 选中的动物名称
     */
    private void sendNotification(String selectedAnimal) {
        // 构建通知内容
        NotificationCompat.Builder builder = new NotificationCompat.Builder(this, CHANNEL_ID)
                .setSmallIcon(R.drawable.ic_android_black_24dp)  // 通知小图标
                .setContentTitle("列表项选中通知")  // 通知标题
                .setContentText("你刚刚选中了：" + selectedAnimal)  // 通知内容
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)  // 通知优先级
                .setAutoCancel(true);  // 点击通知后自动取消

        // 发送通知
        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(this);
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.POST_NOTIFICATIONS) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        notificationManager.notify(1, builder.build());  // 1为通知ID（可自定义）
    }
}