package com.example.lekhn.findyourdoctor;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

public class BMIActivity extends AppCompatActivity {

    Toolbar toolbar;
    EditText height_text;
    EditText weight_text;
    TextView result_text;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bmi);
        toolbar=(Toolbar) findViewById(R.id.toolbar_layout);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayShowTitleEnabled(false); //to remove app title from toolbar

        TextView title=(TextView)findViewById(R.id.title);
        title.setText("BMI Calculator");
        ImageView imageView=(ImageView)findViewById(R.id.back);
        imageView.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View view) {
                Intent intent=new Intent(BMIActivity.this, MainActivity.class);
                startActivity(intent);
                BMIActivity.this.finish();
            }
        });

        height_text = (EditText) findViewById(R.id.edit_height);
        weight_text = (EditText) findViewById(R.id.edit_weight);

        result_text = (TextView) findViewById(R.id.result);
        Button calBtn = (Button) findViewById(R.id.calculate);

        //to hide keyboard when touch outside the edit text
        findViewById(R.id.linearLayout).setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {

                InputMethodManager imm = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
                return true;
            }
        });


        calBtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                //to hide keyboard when click on button
                InputMethodManager inputManager = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);

                inputManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(),InputMethodManager.HIDE_NOT_ALWAYS);

                String your_height = height_text.getText().toString();
                if(TextUtils.isEmpty(your_height)) {
                    height_text.setError("Enter Height");
                    return;
                }
                String your_weight=weight_text.getText().toString();
                if (TextUtils.isEmpty(your_weight)){
                    weight_text.setError("Enter Weight");
                    return;
                }
                float heightInft = Float.parseFloat(height_text.getText().toString());
                float height= (float) (heightInft*0.3048);
                float weight = Float.parseFloat(weight_text.getText().toString());
                float answer=weight/height;
                float bmi=answer/height;
                if (bmi<15){
                    result_text.setText("You are very severely Under Weight person");
                    result_text.postDelayed(new Runnable() {
                        public void run() {
                            result_text.setVisibility(View.INVISIBLE);
                        }
                    }, 3000);
                    result_text.postDelayed(new Runnable() {
                        public void run() {
                            result_text.setVisibility(View.VISIBLE);
                        }
                    }, 1);
                }
                else if (bmi>=15 && bmi<16) {
                    result_text.setText("You are severely Under Weight person");
                    result_text.postDelayed(new Runnable() {
                        public void run() {
                            result_text.setVisibility(View.INVISIBLE);
                        }
                    }, 3000);
                    result_text.postDelayed(new Runnable() {
                        public void run() {
                            result_text.setVisibility(View.VISIBLE);
                        }
                    }, 1);
                }
                else if (bmi>=16 && bmi<18.5) {
                    result_text.setText("You Under Weight person");
                    result_text.postDelayed(new Runnable() {
                        public void run() {
                            result_text.setVisibility(View.INVISIBLE);
                        }
                    }, 3000);
                    result_text.postDelayed(new Runnable() {
                        public void run() {
                            result_text.setVisibility(View.VISIBLE);
                        }
                    }, 1);
                }
                else if (bmi>18.5 && bmi<25) {
                    result_text.setText("You are normal healthy Weight person");
                    result_text.postDelayed(new Runnable() {
                        public void run() {
                            result_text.setVisibility(View.INVISIBLE);
                        }
                    }, 3000);
                    result_text.postDelayed(new Runnable() {
                        public void run() {
                            result_text.setVisibility(View.VISIBLE);
                        }
                    }, 1);
                }
                else if (bmi>=25 && bmi<30) {
                    result_text.setText("You are Over Weight person");
                    result_text.postDelayed(new Runnable() {
                        public void run() {
                            result_text.setVisibility(View.INVISIBLE);
                        }
                    }, 3000);
                    result_text.postDelayed(new Runnable() {
                        public void run() {
                            result_text.setVisibility(View.VISIBLE);
                        }
                    }, 1);
                }
                else if (bmi>=30 && bmi<35) {
                    result_text.setText("You are moderately obese Weight person");
                    result_text.postDelayed(new Runnable() {
                        public void run() {
                            result_text.setVisibility(View.INVISIBLE);
                        }
                    }, 3000);
                    result_text.postDelayed(new Runnable() {
                        public void run() {
                            result_text.setVisibility(View.VISIBLE);
                        }
                    }, 1);
                }
                else if (bmi>=35 && bmi<40) {
                    result_text.setText("You severely obese Weight person");
                    result_text.postDelayed(new Runnable() {
                        public void run() {
                            result_text.setVisibility(View.INVISIBLE);
                        }
                    }, 3000);
                    result_text.postDelayed(new Runnable() {
                        public void run() {
                            result_text.setVisibility(View.VISIBLE);
                        }
                    }, 1);
                }
                else if (bmi>=40) {
                    result_text.setText("You are very severely Obese Weight person");
                    result_text.postDelayed(new Runnable() {
                        public void run() {
                            result_text.setVisibility(View.INVISIBLE);
                        }
                    }, 3000);
                    result_text.postDelayed(new Runnable() {
                        public void run() {
                            result_text.setVisibility(View.VISIBLE);
                        }
                    }, 1);
                }
                else {
                    result_text.setText("Please! enter suitable value");
                    result_text.postDelayed(new Runnable() {
                        public void run() {
                            result_text.setVisibility(View.INVISIBLE);
                        }
                    }, 3000);
                    result_text.postDelayed(new Runnable() {
                        public void run() {
                            result_text.setVisibility(View.VISIBLE);
                        }
                    }, 1);

                }
            }
        });
    }
    @Override
    public void onBackPressed() {
        Intent homeIntent=new Intent(BMIActivity.this, MainActivity.class);
        startActivity(homeIntent);
        finish();
    }
}