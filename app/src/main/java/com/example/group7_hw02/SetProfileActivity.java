package com.example.group7_hw02;

import android.content.Intent;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class SetProfileActivity extends AppCompatActivity {

    public static final String KEY_NAME = "PROFILE";
    RadioGroup radioGroupGender;
    EditText editTextWeight;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_set_profile);
        setTitle("Set Weight/Gender");

        radioGroupGender = findViewById(R.id.radioGroupGender);
        editTextWeight = findViewById(R.id.editTextWeight);

        findViewById(R.id.buttonSet).setOnClickListener(view -> {
            try{
                String gender = "";
                if (radioGroupGender.getCheckedRadioButtonId() == R.id.radioButtonMale) {
                    gender = "Male";
                } else if (radioGroupGender.getCheckedRadioButtonId() == R.id.radioButtonFemale) {
                    gender = "Female";
                }
                Double weight = Double.valueOf(editTextWeight.getText().toString());
                if (weight < 0){
                    throw new Exception();
                }
                if(radioGroupGender.getCheckedRadioButtonId() == -1){
                    throw new RadioEmptyException();
                }

                Profile profile = new Profile();
                profile.weight = weight;
                profile.gender = gender;
                Intent intent = new Intent();
                intent.putExtra(KEY_NAME, profile);
                setResult(RESULT_OK, intent);
                finish();
            }
            catch (Exception e){
                Toast.makeText(SetProfileActivity.this, "Enter Valid Weight", Toast.LENGTH_SHORT).show();
            }
            catch (RadioEmptyException re){
                Toast.makeText(SetProfileActivity.this, "Select Gender", Toast.LENGTH_SHORT).show();
            }
        });

        findViewById(R.id.buttonWeightCancel).setOnClickListener(view -> {
            setResult(RESULT_CANCELED);
            finish();
        });

    }

    private static class RadioEmptyException extends Throwable {
    }
}