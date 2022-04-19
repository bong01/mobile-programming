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

        //MutableList로 어댑터 정의
        adapter = ArrayAdapter<String>(
            this,
            android.R.layout.simple_dropdown_item_1line,
            countries
        )

        //어댑터 적용
        autoCompleteTextView.setAdapter(adapter)
        //클릭 리스너
        autoCompleteTextView.setOnItemClickListener { adapterView, view, i, l ->
            val item = adapterView.getItemAtPosition(i).toString()
            Toast.makeText(this, "선택된 나라 : $item", Toast.LENGTH_SHORT).show()
        }

        val multiAutoCompleteTextView = findViewById<MultiAutoCompleteTextView>(R.id.multiAutoCompleteTextView)
        //구분자 설정
        multiAutoCompleteTextView.setTokenizer(MultiAutoCompleteTextView.CommaTokenizer())
        //어댑터 적용
        multiAutoCompleteTextView.setAdapter(adapter)

        val editText = findViewById<EditText>(R.id.editText)
        val button = findViewById<Button>(R.id.button)

        /**
         * 오버라이딩 방법 3가지
         */

        // 1. afterTextChanged 오버라이딩 (기본)
        editText.addTextChangedListener {
            val str = it.toString()
            //텍스트가 있으면 버튼 활성화
            button.isEnabled = str.isNotEmpty()
        }

        // 2. 필요한 메서드 오버라이딩
//        editText.addTextChangedListener(
//            afterTextChanged = {
//                val str = it.toString()
//                button.isEnabled = str.isNotEmpty()
//            }
//        )

        // 3. 익명 클래스 사용
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
            //데이터 변경 시 알려줘야 함
            adapter.notifyDataSetChanged()
            //EditText의 text 지우기
            editText.text.clear()
        }

    }
}
