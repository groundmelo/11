import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.view.View
import android.widget.ListView
import android.widget.SimpleAdapter
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.NotificationCompat
import com.example.jiemianzujian.R

class MainActivity : AppCompatActivity() {
    // 1. 定义数据：动物名称+图片ID
    private val animalNames = arrayOf("Lion", "Tiger", "Monkey", "Dog", "Cat", "Elephant")
    private val animalImages = intArrayOf(
        R.drawable.lion, R.drawable.tiger, R.drawable.monkey,
        R.drawable.dog, R.drawable.cat, R.drawable.elephant
    )
    private lateinit var lvAnimal: ListView
    private val CHANNEL_ID = "animal_channel" // 通知渠道ID

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // 2. 初始化ListView
        lvAnimal = findViewById(R.id.lv_animal)
        // 3. 准备SimpleAdapter数据（List<Map<String, Any>>）
        val dataList = mutableListOf<HashMap<String, Any>>()
        for (i in animalNames.indices) {
            val map = HashMap<String, Any>()
            map["name"] = animalNames[i]
            map["image"] = animalImages[i]
            dataList.add(map)
        }

        // 4. 创建SimpleAdapter
        val adapter = SimpleAdapter(
            this,
            dataList,
            R.layout.item_list, // 列表项布局
            arrayOf("image", "name"), // map的key
            intArrayOf(R.id.iv_animal, R.id.tv_animal) // 对应布局的控件ID
        )

        // 5. 设置Adapter
        lvAnimal.adapter = adapter

        // 6. 设置ListView点击事件（Toast+通知）
        lvAnimal.setOnItemClickListener { _, _, position, _ ->
            val selectedAnimal = animalNames[position]
            // 显示Toast
            Toast.makeText(this, "你选中了：$selectedAnimal", Toast.LENGTH_SHORT).show()
            // 发送通知
            sendAnimalNotification(selectedAnimal)
        }

        // 7. 创建通知渠道（Android 8.0+）
        createNotificationChannel()
    }

    // 发送通知的方法
    private fun sendAnimalNotification(animal: String) {
        val notificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        // 点击通知跳转回MainActivity
        val intent = Intent(this, MainActivity::class.java)
        val pendingIntent = PendingIntent.getActivity(
            this, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
        )

        // 构建通知
        val notification = NotificationCompat.Builder(this, CHANNEL_ID)
            .setSmallIcon(R.drawable.ic_launcher_foreground) // 应用图标（需替换为自己的图标）
            .setContentTitle("选中通知")
            .setContentText("你刚刚选中了：$animal")
            .setContentIntent(pendingIntent)
            .setAutoCancel(true) // 点击通知后自动消失
            .build()

        // 显示通知（ID=1，可自定义）
        notificationManager.notify(1, notification)
    }

    // 创建通知渠道（Android 8.0+必需）
    private fun createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(
                CHANNEL_ID,
                "动物列表通知", // 渠道名称（用户可见）
                NotificationManager.IMPORTANCE_DEFAULT // 通知重要程度
            ).apply {
                description = "显示ListView选中的动物信息" // 渠道描述
            }
            val notificationManager = getSystemService(NotificationManager::class.java)
            notificationManager.createNotificationChannel(channel)
        }
    }
}