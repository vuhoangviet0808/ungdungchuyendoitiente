package com.example.myapplication

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import java.text.DecimalFormat

class MainActivity : AppCompatActivity() {

    private lateinit var editTextSourceAmount: EditText
    private lateinit var editTextTargetAmount: EditText
    private lateinit var spinnerSourceCurrency: Spinner
    private lateinit var spinnerTargetCurrency: Spinner
    private val currencies = arrayOf("USD", "EUR", "VND", "GBP", "JPY")

    private val exchangeRates = arrayOf(
        doubleArrayOf(1.0, 0.85, 24000.0, 0.73, 150.0),   // USD -> [USD, EUR, VND, GBP, JPY]
        doubleArrayOf(1.18, 1.0, 28000.0, 0.86, 176.0),   // EUR -> [USD, EUR, VND, GBP, JPY]
        doubleArrayOf(0.000041, 0.000036, 1.0, 0.000031, 0.0063),  // VND -> [USD, EUR, VND, GBP, JPY]
        doubleArrayOf(1.37, 1.17, 32000.0, 1.0, 205.0),   // GBP -> [USD, EUR, VND, GBP, JPY]
        doubleArrayOf(0.0067, 0.0057, 160.0, 0.0049, 1.0) // JPY -> [USD, EUR, VND, GBP, JPY]
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        editTextSourceAmount = findViewById(R.id.editTextSourceAmount)
        editTextTargetAmount = findViewById(R.id.editTextTargetAmount)
        spinnerSourceCurrency = findViewById(R.id.spinnerSourceCurrency)
        spinnerTargetCurrency = findViewById(R.id.spinnerTargetCurrency)


        val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, currencies)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)

        spinnerSourceCurrency.adapter = adapter
        spinnerTargetCurrency.adapter = adapter


        editTextSourceAmount.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                convertCurrency()
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
        })


        spinnerSourceCurrency.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                convertCurrency()
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {}
        }


        spinnerTargetCurrency.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                convertCurrency()
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {}
        }
    }


    private fun convertCurrency() {
        val sourceAmount = if (editTextSourceAmount.text.isNotEmpty()) {
            editTextSourceAmount.text.toString().toDouble()
        } else {
            0.0
        }


        val sourceIndex = spinnerSourceCurrency.selectedItemPosition
        val targetIndex = spinnerTargetCurrency.selectedItemPosition
        val rate = exchangeRates[sourceIndex][targetIndex]
        val convertedAmount = sourceAmount * rate
        val decimalFormat = DecimalFormat("#.##")
        editTextTargetAmount.setText(decimalFormat.format(convertedAmount))
    }
}


