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
        val adapter = ArrayAdapter<String>(
            this,
            android.R.layout.simple_dropdown_item_1line,
            countries
        )

        val autoCompleteTextView = findViewById<AutoCompleteTextView>(R.id.autoCompleteTextView)
        val multiAutoCompleteTextView = findViewById<MultiAutoCompleteTextView>(R.id.multiAutoCompleteTextView)

        autoCompleteTextView.setAdapter(adapter)
        autoCompleteTextView.setOnItemClickListener { adapterView, view, i, l ->
            val item = adapterView.getItemAtPosition(i).toString()
            Toast.makeText(this, item + " 선택함", Toast.LENGTH_SHORT).show()
        }

        val greenjoa = resources.getStringArray(R.array.countries_array)
        val adapter2 = ArrayAdapter<String>(
            this,
            android.R.layout.simple_dropdown_item_1line,
            greenjoa
        )

        multiAutoCompleteTextView.setAdapter(adapter2)
        multiAutoCompleteTextView.setTokenizer(MultiAutoCompleteTextView.CommaTokenizer())
    }
}