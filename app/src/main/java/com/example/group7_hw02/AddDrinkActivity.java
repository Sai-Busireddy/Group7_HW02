package com.example.group7_hw02;

import android.content.Intent;
import android.os.Bundle;
import android.widget.RadioGroup;
import android.widget.SeekBar;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.text.SimpleDateFormat;
import java.util.Date;

public class AddDrinkActivity extends AppCompatActivity {

    public static final String KEY_NAME = "ADD_DRINK";
    SeekBar seekBarAlcoholPercentage;
    RadioGroup radioGroupDrinkSize;
    TextView textSeekPercentageValue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_drink);
        setTitle("Add Drink");
        seekBarAlcoholPercentage = findViewById(R.id.seekBarAlcoholPercentage);
        textSeekPercentageValue = findViewById(R.id.textSeekPercentageValue);
        radioGroupDrinkSize = findViewById(R.id.radioGroupDrinkSize);
        seekBarAlcoholPercentage.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                textSeekPercentageValue.setText(i +"%");
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
            }
        });

        findViewById(R.id.buttonAddDrink).setOnClickListener(view -> {

            String temp= textSeekPercentageValue.getText().toString();

            String seekPercentage=temp.substring(0,temp.indexOf('%'));

            int aPercentage = Integer.parseInt(seekPercentage);

            int aSize = 0;
            if (radioGroupDrinkSize.getCheckedRadioButtonId() == R.id.radioButton1oz)
                aSize = 1;
            else if (radioGroupDrinkSize.getCheckedRadioButtonId() == R.id.radioButton5oz)
                aSize = 5;
            else if (radioGroupDrinkSize.getCheckedRadioButtonId() == R.id.radioButton12oz)
                aSize = 12;
            SimpleDateFormat sdf = new SimpleDateFormat();
            String drinkAddedDate = sdf.format(new Date());
            Drink drink = new Drink(aPercentage, aSize, drinkAddedDate);

            Intent intent = new Intent(AddDrinkActivity.this, MainActivity.class);
            intent.putExtra(KEY_NAME, drink);
            setResult(RESULT_OK, intent);
            finish();
        });

        findViewById(R.id.buttonCancel).setOnClickListener(view -> finish());
    }
}