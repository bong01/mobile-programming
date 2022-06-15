package dev.bong.mobileprogramming.week14

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.telephony.SmsMessage
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MyReceiver : BroadcastReceiver() {

    val pattern1 = Regex("""^\d{2}/\d{2}\s\d{2}:\d{2}""")
    val pattern2 = Regex("""\d{3}원$""")
    val scope = CoroutineScope(Dispatchers.IO)

    override fun onReceive(context: Context, intent: Intent) {
        val pendingIntent = goAsync()

        scope.launch {
            if (intent.action.equals("android.provider.Telephony.SMS_RECEIVED")) {
                val bundle = intent.extras
                val objects = bundle?.get("pdus") as Array<Any>
                val sms = objects[0] as ByteArray
                val format = bundle.getString("format")
                val message = SmsMessage.createFromPdu(sms, format)

                val msgBody = message.messageBody
                if (msgBody.contains("건국카드")) {
                    val tmpStr = msgBody.split("\n")
                    var result = false

                    for (str in tmpStr.subList(1, tmpStr.size)) {
                        if (pattern1.containsMatchIn(str) && pattern2.containsMatchIn(str)) {
                            result = true
                            break
                        }
                    }

                    if (result) {
                        val newIntent = Intent(context, BroadcastActivity::class.java)
                        newIntent.flags =
                            Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_SINGLE_TOP or Intent.FLAG_ACTIVITY_CLEAR_TOP
                        newIntent.putExtra("msgSender", message.originatingAddress)
                        newIntent.putExtra("msgBody", message.messageBody)
                        context.startActivity(newIntent)
                    }
                }
            }
        }

        pendingIntent.finish()
    }

}