package dev.bong.mobileprogramming.week14

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.content.pm.PackageManager
import android.os.Bundle
import android.provider.Settings
import android.util.Log
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import dev.bong.mobileprogramming.databinding.ActivityBroadcastBinding

class BroadcastActivity : AppCompatActivity() {

    lateinit var binding: ActivityBroadcastBinding
    lateinit var broadcastReceiver: BroadcastReceiver

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityBroadcastBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initPermission()
        initLayout()
        checkSettingOverlayWindow(intent)
    }

    override fun onNewIntent(intent: Intent?) {
        super.onNewIntent(intent)
        checkSettingOverlayWindow(intent)
    }

    private fun getMessage(intent: Intent?) {
        if (intent != null) {
            if (intent.hasExtra("msgSender") and intent.hasExtra("msgBody")) {
                val msgSender = intent.getStringExtra("msgSender")
                val msgBody = intent.getStringExtra("msgBody")
                Log.d("msg", "보낸 번호: $msgSender\n$msgBody")
                Toast.makeText(this, "보낸 번호: $msgSender\n$msgBody", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun checkSettingOverlayWindow(intent: Intent?) {
        if (Settings.canDrawOverlays(this)) {
            getMessage(intent)
        } else {
            val overlayIntent = Intent(Settings.ACTION_MANAGE_OVERLAY_PERMISSION)
            requestSettingLauncher.launch(overlayIntent)
        }
    }

    val requestSettingLauncher =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
            if (Settings.canDrawOverlays(this)) {
                getMessage(this.intent)
            } else {
                Toast.makeText(this, "권한 승인이 거부되었습니다.", Toast.LENGTH_SHORT).show()
            }
        }

    val requestPermissionLauncher =
        registerForActivityResult(ActivityResultContracts.RequestPermission()) {
            if (it) {
                //권한 승인 시
                initPermission()
            } else {
                //권한 거부 시
                Toast.makeText(this, "권한 승인이 거부되었습니다.", Toast.LENGTH_SHORT).show()
            }
        }

    private fun permissionAlertDlg() {
        val builder = AlertDialog.Builder(this)
        builder.setMessage("반드시 문자 수신 권한이 허용되어야 합니다.")
            .setTitle("권한 체크")
            .setPositiveButton("OK") { _, _ ->
                //다시 권한 요청
                requestPermissionLauncher.launch(android.Manifest.permission.RECEIVE_SMS)
            }
            .setNegativeButton("Cancel") { dlg, _ ->
                dlg.dismiss()
            }
        val dlg = builder.create()
        dlg.show()
    }


    private fun initPermission() {
        when {
            //문자 수신 권한 동의 시
            (ActivityCompat.checkSelfPermission(
                this, android.Manifest.permission.RECEIVE_SMS
            ) == PackageManager.PERMISSION_GRANTED) -> {
                Toast.makeText(this, "문자 수신 동의함", Toast.LENGTH_SHORT).show()
            }

            //명시적으로 거부 시
            ActivityCompat.shouldShowRequestPermissionRationale(
                this, android.Manifest.permission.RECEIVE_SMS
            ) -> {
                permissionAlertDlg()
            }

            //처음일 경우
            else -> {
                requestPermissionLauncher.launch(
                    android.Manifest.permission.RECEIVE_SMS
                )
            }

        }
    }

    private fun initLayout() {

        broadcastReceiver = object : BroadcastReceiver() {
            override fun onReceive(context: Context?, intent: Intent?) {
                if (intent != null) {
                    if (intent.action.equals(Intent.ACTION_POWER_CONNECTED)) {
                        Toast.makeText(context, "충전 시작", Toast.LENGTH_SHORT).show()
                    } else if (intent.action.equals(Intent.ACTION_POWER_DISCONNECTED)) {
                        Toast.makeText(context, "충전 해제", Toast.LENGTH_SHORT).show()
                    }
                }
            }
        }

    }

    override fun onResume() {
        super.onResume()
        //충전 시작 수신
        var intentFilter = IntentFilter(Intent.ACTION_POWER_CONNECTED)
        //충전 해제 수신
        intentFilter.addAction(Intent.ACTION_POWER_DISCONNECTED)

        //수신이 되면 onReceive 동작
        registerReceiver(broadcastReceiver, intentFilter)
    }

    override fun onPause() {
        super.onPause()
        unregisterReceiver(broadcastReceiver)
    }

}