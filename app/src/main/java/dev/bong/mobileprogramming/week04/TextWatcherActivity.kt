package dev.bong.mobileprogramming.week04

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.widget.*
import androidx.core.widget.addTextChangedListener
import dev.bong.mobileprogramming.R

class TextWatcherActivity : AppCompatActivity() {
    lateinit var adapter: ArrayAdapter<String>
    val countries = mutableListOf<String>(
        "Afghanistan", "Albania", "Algeria", "American Samoa", "Andorra",
        "Angola", "Anguilla", "Antarctica", "Antigua and Barbuda", "Argentina",
        "Armenia", "Aruba", "Australia", "Austria", "Azerbaijan",
        "Bahrain", "Bangladesh", "Barbados", "Belarus", "Belgium"
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_text_watcher)
        initLayout()
    }


    private fun initLayout() {
        val autoCompleteTextView = findViewById<AutoCompleteTextView>(R.id.autoCompleteTextView)

        adapter = ArrayAdapter<String>(
            this,
            android.R.layout.simple_dropdown_item_1line,
            countries
        )
        autoCompleteTextView.setAdapter(adapter)
        autoCompleteTextView.setOnItemClickListener { adapterView, view, i, l ->
            val item = adapterView.getItemAtPosition(i).toString()
            Toast.makeText(this, "선택된 나라 : $item", Toast.LENGTH_SHORT).show()
        }

        val multiAutoCompleteTextView = findViewById<MultiAutoCompleteTextView>(R.id.multiAutoCompleteTextView)
        multiAutoCompleteTextView.setTokenizer(MultiAutoCompleteTextView.CommaTokenizer())
        multiAutoCompleteTextView.setAdapter(adapter)

        val editText = findViewById<EditText>(R.id.editText)
        val button = findViewById<Button>(R.id.button)

        /**
         * afterTextChanged 오버라이딩
         */
        editText.addTextChangedListener {
            val str = it.toString()
            button.isEnabled = str.isNotEmpty()
        }

        /**
         * 필요한 메서드 오버라이딩
         */
//        editText.addTextChangedListener(
//            afterTextChanged = {
//                val str = it.toString()
//                button.isEnabled = str.isNotEmpty()
//            }
//        )

        /**
         * 익명 클래스 사용
         */
//        editText.addTextChangedListener(object : TextWatcher {
//            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
//
//            }
//
//            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
//            }
//
//            override fun afterTextChanged(s: Editable?) {
//                val str = s.toString()
//                button.isEnabled = str.isNotEmpty()
//            }
//
//        })

        button.setOnClickListener {
            adapter.add(editText.text.toString())
            adapter.notifyDataSetChanged()
            editText.text.clear()
        }

    }
}
