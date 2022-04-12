package dev.bong.mobileprogramming.week06

import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AlertDialog
import androidx.core.app.ActivityCompat
import dev.bong.mobileprogramming.databinding.ActivityImplicitIntentBinding

class ImplicitIntentActivity : AppCompatActivity() {
    lateinit var binding: ActivityImplicitIntentBinding
    val CALL_REQUEST = 100

    val permissions = arrayOf(android.Manifest.permission.CALL_PHONE, android.Manifest.permission.CAMERA)

    val multiplePermissionLauncher = registerForActivityResult(ActivityResultContracts.RequestMultiplePermissions()) {
        val resultPermission = it.all {
            it.value == true
        }
        if (resultPermission) {
            Toast.makeText(this, "권한이 승인되었습니다.", Toast.LENGTH_SHORT).show()
            callAction()
        } else {
            Toast.makeText(this, "권한이 거부되었습니다.", Toast.LENGTH_SHORT).show()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityImplicitIntentBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initLayout()
    }

    private fun initLayout() {
        with(binding) {
            callBtn.setOnClickListener {
                callAction()
            }

            msgBtn.setOnClickListener {
                val message = Uri.parse("sms:010-1234-1234")
                val messageIntent = Intent(Intent.ACTION_SENDTO, message)
                messageIntent.putExtra("sms_body", "빨리 다음꺼 하자....")
                startActivity(messageIntent)
            }

            webBtn.setOnClickListener {
                val webPage = Uri.parse("http://www.naver.com")
                val webIntent = Intent(Intent.ACTION_VIEW, webPage)
                startActivity(webIntent)
            }

            mapBtn.setOnClickListener {
                val location = Uri.parse("geo:37.543684,127.077130?z=16")
                val mapIntent = Intent(Intent.ACTION_VIEW, location)
                startActivity(mapIntent)
            }
        }
    }

    private fun callAction() {
        val number = Uri.parse("tel:010-1234-1234")
        val callIntent = Intent(Intent.ACTION_CALL, number)
        when {
            (ActivityCompat.checkSelfPermission(
                this,
                android.Manifest.permission.CALL_PHONE
            ) == PackageManager.PERMISSION_GRANTED) && (ActivityCompat.checkSelfPermission(
                this,
                android.Manifest.permission.CAMERA
            ))
                    == PackageManager.PERMISSION_GRANTED -> {
                startActivity(callIntent)
            }
            ActivityCompat.shouldShowRequestPermissionRationale(this, android.Manifest.permission.CALL_PHONE)
            -> {
                //거부했을 경우
                callAlertDlg()
            }
            else -> {
                //처음 실행
                multiplePermissionLauncher.launch(permissions)
//                ActivityCompat.requestPermissions(this, arrayOf(android.Manifest.permission.CALL_PHONE), CALL_REQUEST)
            }
        }
    }

    private fun callAlertDlg() {
        val builder = AlertDialog.Builder(this)
        builder.setMessage("반드시 CALL_PHONE과 CAMERA 권한이 허용되어야 합니다.")
            .setTitle("권한 체크")
            .setPositiveButton("OK") { _, _ ->
                multiplePermissionLauncher.launch(permissions)
//                ActivityCompat.requestPermissions(this, arrayOf(android.Manifest.permission.CALL_PHONE), CALL_REQUEST)
            }
            .setNegativeButton("CANCEL") { dlg, _ ->
                dlg.dismiss()
            }
        val dlg = builder.create()
        dlg.show()
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        when (requestCode) {
            CALL_REQUEST -> {
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    Toast.makeText(this, "권한이 승인되었습니다.", Toast.LENGTH_SHORT).show()
                    callAction()
                } else {
                    Toast.makeText(this, "권한이 승인이 거부 되었습니다.", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

}