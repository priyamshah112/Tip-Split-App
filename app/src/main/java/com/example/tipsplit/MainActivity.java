package com.example.tipsplit;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.TextView;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {

    TextView tipAmountTextView, tipWithTotalTextView, totalAmtPerPersonTextView;
    EditText billTotal, numOfPplEditText;
    RadioGroup tipPercentRg;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        billTotal = findViewById(R.id.billTotal);
        tipPercentRg = findViewById(R.id.tipPercentRg);
        tipAmountTextView = findViewById(R.id.tipAmountTextView);
        tipWithTotalTextView = findViewById(R.id.tipWithTotalTextView);
        totalAmtPerPersonTextView = findViewById(R.id.totalAmtPerPersonTextView);
        numOfPplEditText = findViewById(R.id.numOfPplEditText);

    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        outState.putString("tipAmountTextView",tipAmountTextView.getText().toString() );
        outState.putString("totalWithTip", tipWithTotalTextView.getText().toString());
        outState.putString("totalAmtPerPersonTxt", totalAmtPerPersonTextView.getText().toString());

        super.onSaveInstanceState(outState);
    }

    @Override
    protected void onRestoreInstanceState(@NonNull Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        //Retrieving Values from saved Instance
        tipAmountTextView.setText(savedInstanceState.getString("tipAmountTextView"));
        tipWithTotalTextView.setText(savedInstanceState.getString("totalWithTip"));
        totalAmtPerPersonTextView.setText(savedInstanceState.getString("totalAmtPerPersonTxt"));

    }

    // Function for calculating Tip Amount and Total Bill with Tax
    public void calculateTipAmt(View v) {
        // Getting text from edit text with id - totalBill
        String totalBill = this.billTotal.getText().toString();

        //  Checking empty or null values
        if (totalBill.isEmpty() || totalBill.equals("")) {
            tipPercentRg.clearCheck();
            return;
        }

        Double tip = 0.0;

        //Making a new instance for 2 decimal places pattern.
        DecimalFormat decimalFormat = new DecimalFormat("#.##");

        // Calculate tip with respect of Radio Button
        if (v.getId() == R.id.rb_12) {
            // converting totalBill from double to string.
            String formatedTip = decimalFormat.format((Double.parseDouble(totalBill)) * 0.12);
            tip = Double.parseDouble(formatedTip);
        } else if (v.getId() == R.id.rb_15) {
            String formatedTip = decimalFormat.format((Double.parseDouble(totalBill)) * 0.15);
            tip = Double.parseDouble(formatedTip);
        } else if (v.getId() == R.id.rb_18) {
            String formatedTip = decimalFormat.format((Double.parseDouble(totalBill)) * 0.18);
            tip = Double.parseDouble(formatedTip);
        } else if (v.getId() == R.id.rb_20) {
            String formatedTip = decimalFormat.format((Double.parseDouble(totalBill)) * 0.20);
            tip = Double.parseDouble(formatedTip);
        }

        tipAmountTextView.setText(NumberFormat.getCurrencyInstance(Locale.US).format(tip));
        tip = Double.parseDouble(totalBill) + tip;
        tipWithTotalTextView.setText(NumberFormat.getCurrencyInstance(Locale.US).format(tip));
    }

    public void btnGo(View v) {
        String billTotal = this.billTotal.getText().toString();
        // Checking empty or 0 value
        if (billTotal.isEmpty() || billTotal.equals("0"))  {
            return;
        }
        String pplCount = numOfPplEditText.getText().toString();
        if(pplCount.isEmpty() || pplCount.equals("0")) return;
        int numOfPeople = Integer.parseInt(pplCount);

        double totalAmountWithTip = Double.parseDouble(tipWithTotalTextView.getText().toString().replaceAll(",", "").substring(1)) * 100;
        double upperValue = Math.ceil(totalAmountWithTip / numOfPeople);

        totalAmtPerPersonTextView.setText(NumberFormat.getCurrencyInstance().format(upperValue/100));
    }

    public void clearAll(View v) {
        tipAmountTextView.setText("");
        tipWithTotalTextView.setText("");
        totalAmtPerPersonTextView.setText("");
        billTotal.setText("");
        numOfPplEditText.setText("");
        tipPercentRg.clearCheck();

    }
}