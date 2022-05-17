package dev.bong.mobileprogramming.week11

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.addTextChangedListener
import dev.bong.mobileprogramming.R
import dev.bong.mobileprogramming.databinding.ActivityDbBinding
import java.io.FileOutputStream

class DbActivity : AppCompatActivity() {

    lateinit var binding:  ActivityDbBinding
    lateinit var myDBHelper: MyDBHelper
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityDbBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initDB()
        init()
        getAllRecords()
    }

    private fun initDB() {
        val dbFile = getDatabasePath("mydb.db")
        if (!dbFile.parentFile.exists()) {
            dbFile.parentFile.mkdir()
        }
        if (!dbFile.exists()) {
            val file = resources.openRawResource(R.raw.mydb)
            val fileSize = file.available()
            val buffer = ByteArray(fileSize)
            file.read(buffer)
            file.close()
            dbFile.createNewFile()
            val output = FileOutputStream(dbFile)
            output.write(buffer)
            output.close()
        }
    }

    private fun getAllRecords() {
        myDBHelper.getAllRecords()
    }

    private fun clearEditText() {
        binding.apply {
            pIdEdit.text.clear()
            pNameEdit.text.clear()
            pQuantityEdit.text.clear()
        }
    }

    private fun init() {
        myDBHelper = MyDBHelper(this)
        
        binding.apply {
            testSql.addTextChangedListener {
                val pname = it.toString()
                val result = myDBHelper.findProduct2(pname)
            }

            insertBtn.setOnClickListener {
                val name = pNameEdit.text.toString()
                val quantity = pQuantityEdit.text.toString().toInt()
                val product = Product(0, name, quantity)
                val result = myDBHelper.insertProduct(product)
                if (result) {
                    getAllRecords()
                    Toast.makeText(this@DbActivity, "DATA INSERT SUCCESS", Toast.LENGTH_SHORT).show()
                } else {
                    Toast.makeText(this@DbActivity, "DATA INSERT FAILED", Toast.LENGTH_SHORT).show()
                }
                clearEditText()
            }

            findBtn.setOnClickListener {
                val name = pNameEdit.text.toString()
                val result = myDBHelper.findProduct(name)
                if (result) {
                    Toast.makeText(this@DbActivity, "RECORD FOUND", Toast.LENGTH_SHORT).show()
                } else {
                    Toast.makeText(this@DbActivity, "NO MATCH FOUND", Toast.LENGTH_SHORT).show()
                }
            }

            delteBtn.setOnClickListener {
                val pid = pIdEdit.text.toString()
                val result = myDBHelper.deleteProduct(pid)
                if (result) {
                    Toast.makeText(this@DbActivity, "DATA DELETE SUCCESS", Toast.LENGTH_SHORT).show()
                } else {
                    Toast.makeText(this@DbActivity, "DATA DELETE FAILED", Toast.LENGTH_SHORT).show()
                }
                getAllRecords()
                clearEditText()
            }

            updateBtn.setOnClickListener {
                val pid = pIdEdit.text.toString().toInt()
                val name = pNameEdit.text.toString()
                val quantity = pQuantityEdit.text.toString().toInt()
                val product = Product(pid, name, quantity)
                val result = myDBHelper.updateProduct(product)
                if (result) {
                    getAllRecords()
                    Toast.makeText(this@DbActivity, "DATA UPDATE SUCCESS", Toast.LENGTH_SHORT).show()
                } else {
                    Toast.makeText(this@DbActivity, "DATA UPDATE FAILED", Toast.LENGTH_SHORT).show()
                }
                clearEditText()
            }
        }
    }
    
}