package com.example.group7_hw02;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class ViewDrinksActivity extends AppCompatActivity {

    public static final String KEY_NAME = "VIEW_DRINKS";
    TextView textViewDrinkNumber;
    TextView textViewAlcSize;
    TextView textViewAlcPercentage;
    TextView textViewTimeStamp;
    ArrayList<Drink> drinkArrayList;
    int i;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_drinks);
        setTitle("View Drinks");

        textViewDrinkNumber = findViewById(R.id.textViewDrinkNumber);
        textViewAlcSize = findViewById(R.id.textViewAlcSize);
        textViewAlcPercentage = findViewById(R.id.textViewAlcPercentage);
        textViewTimeStamp = findViewById(R.id.textViewTimeStamp);
        i = 1;

        if (getIntent() != null && getIntent().getExtras() != null && getIntent().hasExtra(MainActivity.KEY_NAME)) {

            drinkArrayList = getIntent().getParcelableArrayListExtra(MainActivity.KEY_NAME);
            textViewDrinkNumber.setText("Drink 1 out of " + drinkArrayList.size());
            textViewAlcSize.setText(drinkArrayList.get(0).drinkSize + " oz");
            textViewAlcPercentage.setText(drinkArrayList.get(0).alcoholPercentage + "% Alcohol");
            textViewTimeStamp.setText("Added " + drinkArrayList.get(0).drinkAddedDate);

            findViewById(R.id.buttonNext).setOnClickListener(view -> {
                if(i == drinkArrayList.size()){
                    i = 0;
                }
                i++;
                textViewDrinkNumber.setText("Drink " + i + " out of " + drinkArrayList.size());
                textViewAlcSize.setText(drinkArrayList.get(i-1).drinkSize + " oz");
                textViewAlcPercentage.setText(drinkArrayList.get(i-1).alcoholPercentage + "% Alcohol");
                textViewTimeStamp.setText("Added " + drinkArrayList.get(i-1).drinkAddedDate);
            });

            findViewById(R.id.buttonPrevious).setOnClickListener(view -> {
                if(i <= 1){
                    i = drinkArrayList.size()+1;
                }
                i--;
                textViewDrinkNumber.setText("Drink " + i + " out of " + drinkArrayList.size());
                textViewAlcSize.setText(drinkArrayList.get(i-1).drinkSize + " oz");
                textViewAlcPercentage.setText(drinkArrayList.get(i-1).alcoholPercentage + "% Alcohol");
                textViewTimeStamp.setText("Added " + drinkArrayList.get(i-1).drinkAddedDate);
            });

            findViewById(R.id.buttonTrash).setOnClickListener(view -> {
                if(drinkArrayList.size() == 1){
                    Intent intent = new Intent(ViewDrinksActivity.this, MainActivity.class);
                    drinkArrayList.remove(0);
                    intent.putExtra(KEY_NAME, drinkArrayList);
                    setResult(RESULT_OK, intent);
                    finish();
                } else {
                    drinkArrayList.remove(i - 1);
                    if(i <= 1){
                        i = drinkArrayList.size()+1;
                    }
                    i--;
                    textViewDrinkNumber.setText("Drink " + i + " out of " + drinkArrayList.size());
                    textViewAlcSize.setText(drinkArrayList.get(i - 1).drinkSize + " oz");
                    textViewAlcPercentage.setText(drinkArrayList.get(i - 1).alcoholPercentage + "% Alcohol");
                    textViewTimeStamp.setText("Added " + drinkArrayList.get(i - 1).drinkAddedDate);
                }
            });

            findViewById(R.id.buttonClose).setOnClickListener(view -> {
                Intent intent = new Intent(ViewDrinksActivity.this, MainActivity.class);
                intent.putExtra(KEY_NAME, drinkArrayList);
                setResult(RESULT_OK, intent);
                finish();
            });
        }
    }
}