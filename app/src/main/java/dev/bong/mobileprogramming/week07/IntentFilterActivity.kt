package dev.bong.mobileprogramming.week07

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import dev.bong.mobileprogramming.databinding.ActivityIntentFilterBinding

class IntentFilterActivity : AppCompatActivity() {
    lateinit var binding: ActivityIntentFilterBinding
    lateinit var adapter: MyAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityIntentFilterBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initRecyclerview()
    }

    private fun initRecyclerview() {
        binding.recyclerView.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)

        adapter = MyAdapter(ArrayList<MyData>())
        val intent = Intent(Intent.ACTION_MAIN)
        intent.addCategory(Intent.CATEGORY_LAUNCHER)
        val applist = packageManager.queryIntentActivities(intent, 0)
        if (applist.size > 0) {
            for (appinfo in applist) {
                val applabel = appinfo.loadLabel(packageManager).toString()
                val appclass = appinfo.activityInfo.name
                val appPackName = appinfo.activityInfo.packageName
                val appicon = appinfo.loadIcon(packageManager)
                adapter.items.add(MyData(applabel, appclass, appPackName, appicon))
            }
        }

        adapter.itemClickListener = object : MyAdapter.OnItemClickListener{
            override fun OnItemClick(data: MyData) {
                val intent = packageManager.getLaunchIntentForPackage(data.apppackname)
                startActivity(intent)
            }

        }
        binding.recyclerView.adapter = adapter
    }
}