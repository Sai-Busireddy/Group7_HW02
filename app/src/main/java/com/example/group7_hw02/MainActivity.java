package com.example.group7_hw02;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    public static final String KEY_NAME = "MAIN_KEY";
    Button buttonViewDrinks, buttonGoToAddDrink;
    TextView textNumOfDrinksValue, textBACLevelValue, textViewYourStatusValue, textViewWeightDisplay;
    ArrayList<Drink> arrayListDrinks = new ArrayList<>();

    ActivityResultLauncher<Intent> profileStartForResult = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), new ActivityResultCallback<ActivityResult>() {
        @Override
        public void onActivityResult(ActivityResult result) {
            if(result != null && result.getResultCode() == RESULT_OK){
                if (result.getData() != null && result.getData().getParcelableExtra(SetProfileActivity.KEY_NAME) != null){

                    Profile profile = result.getData().getParcelableExtra(SetProfileActivity.KEY_NAME);
                    String weightGender = profile.weight + " lbs (" + profile.gender + ")";
                    textViewWeightDisplay = findViewById(R.id.textViewWeightDisplay);
                    textViewWeightDisplay.setText(weightGender);
                    resetActivity();
                    findViewById(R.id.buttonViewDrinks).setEnabled(true);
                    findViewById(R.id.buttonGoToAddDrink).setEnabled(true);
                }
            }
        }
    });

    ActivityResultLauncher<Intent> viewDrinkStartForResult = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), result -> {
        if(result != null && result.getResultCode() == RESULT_OK){
            if (result.getData() != null && result.getData().getParcelableArrayListExtra(ViewDrinksActivity.KEY_NAME) != null){
                arrayListDrinks = result.getData().getParcelableArrayListExtra(ViewDrinksActivity.KEY_NAME);
                CalculateBAC();
            }
        } else {
            Toast.makeText(MainActivity.this, "Trouble to update drinks", Toast.LENGTH_SHORT).show();
        }
    });

    ActivityResultLauncher<Intent> addDrinkStartForResult = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), result -> {
        if(result != null && result.getResultCode() == RESULT_OK){
            if (result.getData() != null && result.getData().getParcelableExtra(AddDrinkActivity.KEY_NAME) != null){

                Drink drink = result.getData().getParcelableExtra(AddDrinkActivity.KEY_NAME);
                arrayListDrinks.add(drink);
                CalculateBAC();
            }
        }
    });

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        findViewById(R.id.buttonViewDrinks).setEnabled(false);
        findViewById(R.id.buttonGoToAddDrink).setEnabled(false);

        findViewById(R.id.buttonGoToSet).setOnClickListener(view -> {
            Intent intent = new Intent(MainActivity.this, SetProfileActivity.class);
            profileStartForResult.launch(intent);
        });

        findViewById(R.id.buttonViewDrinks).setOnClickListener(view -> {

            if (arrayListDrinks.size()==0){
                Toast.makeText(MainActivity.this, "User has no drinks", Toast.LENGTH_SHORT).show();
            } else {
                Intent intent = new Intent(MainActivity.this, ViewDrinksActivity.class);
                intent.putExtra(KEY_NAME, arrayListDrinks);
                viewDrinkStartForResult.launch(intent);
            }
        });

        findViewById(R.id.buttonGoToAddDrink).setOnClickListener(view -> addDrinkStartForResult.launch(new Intent(MainActivity.this, AddDrinkActivity.class)));

        findViewById(R.id.buttonReset).setOnClickListener(view -> {

            textViewWeightDisplay = findViewById(R.id.textViewWeightDisplay);
            textViewWeightDisplay.setText("N/A");
            resetActivity();
        });
    }

    protected void CalculateBAC(){

        textBACLevelValue=findViewById(R.id.textBACLevelValue);
        textNumOfDrinksValue = findViewById(R.id.textNumOfDrinksValue);
        textViewYourStatusValue = findViewById(R.id.textViewYourStatusValue);
        buttonGoToAddDrink = findViewById(R.id.buttonGoToAddDrink);
        buttonGoToAddDrink.setEnabled(true);

        String weightGender = String.valueOf(textViewWeightDisplay.getText());
        String gender = weightGender.substring(weightGender.indexOf('(')+1,weightGender.indexOf(')'));
        String weight = weightGender.substring(0,weightGender.indexOf(' '));
        double r = 0, A=0;
        if(gender.equals("Male")){
            r=0.73;
        }
        else if(gender.equals("Female")){
            r=0.66;
        }

        for (Drink d:arrayListDrinks) {
            A = A+(d.drinkSize*d.alcoholPercentage/100.0);
        }
        Double BAC = (A*5.14)/(Double.parseDouble(weight)*r);

        textBACLevelValue.setText(String.format("%.3f",BAC));

        textNumOfDrinksValue.setText(String.valueOf(arrayListDrinks.size()));

        if(BAC <= 0.08){
            textViewYourStatusValue.setText("You're safe");
            textViewYourStatusValue.setBackgroundResource(R.color.Dark_Green);
        }
        else if(BAC > 0.08 && BAC <= 0.2){
            textViewYourStatusValue.setText("Be careful");
            textViewYourStatusValue.setBackgroundResource(R.color.Orange);
        }
        else if(BAC > 0.2){
            textViewYourStatusValue.setText("Over the limit!");
            textViewYourStatusValue.setBackgroundColor(Color.RED);
        }
        if(BAC >= 0.25){
            buttonGoToAddDrink.setEnabled(false);
            Toast.makeText(MainActivity.this, "No more drinks for you.", Toast.LENGTH_SHORT).show();
        }
    }

    protected void resetActivity(){
        arrayListDrinks.clear();
        textNumOfDrinksValue = findViewById(R.id.textNumOfDrinksValue);
        textNumOfDrinksValue.setText("0");
        textBACLevelValue = findViewById(R.id.textBACLevelValue);
        textBACLevelValue.setText("0.000");
        textViewYourStatusValue = findViewById(R.id.textViewYourStatusValue);
        textViewYourStatusValue.setText("You're safe");
        textViewYourStatusValue.setBackgroundResource(R.color.Dark_Green);
        buttonViewDrinks = findViewById(R.id.buttonViewDrinks);
        buttonViewDrinks.setEnabled(false);
        buttonGoToAddDrink = findViewById(R.id.buttonGoToAddDrink);
        buttonGoToAddDrink.setEnabled(false);
    }
}