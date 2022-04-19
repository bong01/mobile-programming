package dev.bong.mobileprogramming.week03

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import android.widget.MultiAutoCompleteTextView
import android.widget.Toast
import dev.bong.mobileprogramming.R

class AutoCompleteActivity : AppCompatActivity() {
    val countries = arrayOf(
        "Afghanistan", "Albania", "Algeria", "American Samoa", "Andorra",
        "Angola", "Anguilla", "Antarctica", "Antigua and Barbuda", "Argentina",
        "Armenia", "Aruba", "Australia", "Austria", "Azerbaijan",
        "Bahrain", "Bangladesh", "Barbados", "Belarus", "Belgium"
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_auto_complete)
        initLayout()
    }

    private fun initLayout() {
        //AutoCompleteTextView에 적용할 어댑터 정의
        //인자로 ArrayList 전달
        val adapter = ArrayAdapter<String>(
            this,
            android.R.layout.simple_dropdown_item_1line,
            countries
        )

        val autoCompleteTextView = findViewById<AutoCompleteTextView>(R.id.autoCompleteTextView)
        val multiAutoCompleteTextView = findViewById<MultiAutoCompleteTextView>(R.id.multiAutoCompleteTextView)

        //AutoCompleteTextView에 어댑터 정의
        autoCompleteTextView.setAdapter(adapter)

        //클릭 리스너
        autoCompleteTextView.setOnItemClickListener { adapterView, view, i, l ->
            val item = adapterView.getItemAtPosition(i).toString()
            Toast.makeText(this, item + " 선택함", Toast.LENGTH_SHORT).show()
        }

        //strings.xml에서 정의한 Array 불러오기
        val greenjoa = resources.getStringArray(R.array.countries_array)

        //어댑터 정의
        //인자로 Array 전달
        val adapter2 = ArrayAdapter<String>(
            this,
            android.R.layout.simple_dropdown_item_1line,
            greenjoa
        )

        //어댑터 적용
        multiAutoCompleteTextView.setAdapter(adapter2)
        //MultiAutoCompleteTextView일 경우 구분자 설정하기
        multiAutoCompleteTextView.setTokenizer(MultiAutoCompleteTextView.CommaTokenizer())
    }
}