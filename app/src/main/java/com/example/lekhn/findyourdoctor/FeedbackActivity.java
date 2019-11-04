package com.example.lekhn.findyourdoctor;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

public class FeedbackActivity extends AppCompatActivity {

    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feedback);
        toolbar=(Toolbar) findViewById(R.id.toolbar_layout);
        setSupportActionBar(toolbar);
        hideSoftKeyboard();
        getSupportActionBar().setDisplayShowTitleEnabled(false); //to remove app title from toolbar
        TextView title=(TextView)findViewById(R.id.title);
        title.setText("FeedBack");
        ImageView imageView=(ImageView)findViewById(R.id.cross);
        imageView.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View view) {
                overridePendingTransition(R.anim.buttom_up, R.anim.buttom_down);
                Intent intent=new Intent(FeedbackActivity.this, MainActivity.class);
                FeedbackActivity.this.finish();
                startActivity(intent);
            }
        });
    }

    public void sendBtn(View view){
        hideSoftKeyboard();
        EditText nameText = findViewById(R.id.name_Text);
        EditText emailText = findViewById(R.id.email_Text);
        EditText subjectText = findViewById(R.id.subject_Text);
        EditText messageText = findViewById(R.id.message_Text);

        if (nameText.getText().toString().equals("")){
            nameText.setError("Enter Name");
        }
        else if (emailText.getText().toString().equals("")){
            emailText.setError("Enter Email Address");
        }
        else if (subjectText.getText().toString().equals("")){
            subjectText.setError("Enter subject");
        }
        else if (messageText.getText().toString().equals("")){
            messageText.setError("Enter message");
        }
        else {
            ConnectivityManager connectivityManager = (ConnectivityManager)getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo activeNetwork = connectivityManager.getActiveNetworkInfo();
            if(activeNetwork !=null && activeNetwork.isConnected()){
                Intent sendIntent = new Intent(Intent.ACTION_SENDTO);
                sendIntent.setType("plain/text");
                sendIntent.setData(Uri.parse("mailto:"));
                sendIntent.putExtra(Intent.EXTRA_EMAIL, new String[]{"Lekhnathkatuwal21@gmail.com"});
                sendIntent.putExtra(Intent.EXTRA_SUBJECT, subjectText.getText().toString());
                sendIntent.putExtra(Intent.EXTRA_TEXT,"Dear FindYourDoctor, \n"
                        + messageText.getText().toString()+
                        "\n regrads, " + nameText.getText().toString());
                try {
                    startActivity(Intent.createChooser(sendIntent, "Send mail..."));
                }
                catch (android.content.ActivityNotFoundException ex){
                    Toast.makeText(this,"No mail app found",Toast.LENGTH_LONG).show();
                }
                catch (Exception ex){
                    Toast.makeText(this,"Unexpected Error" + ex.toString(), Toast.LENGTH_SHORT).show();
                }
            }
            else {
                RelativeLayout relativeLayout = findViewById(R.id.feedback_layout);
                Snackbar snackbar = Snackbar.make(relativeLayout, "No internet connection!", Snackbar.LENGTH_LONG)
                        .setAction("RETRY", new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                retry(view);
                            }
                        });
                snackbar.setActionTextColor(Color.RED);
                View sbView = snackbar.getView();
                TextView textView = (TextView) sbView.findViewById(android.support.design.R.id.snackbar_text);
                textView.setTextColor(Color.YELLOW);
                snackbar.show();
            }
        }
    }

    private void retry(View view) {
        sendBtn(view);
    }

    @Override
    public void onBackPressed() {
        Intent homeIntent=new Intent(FeedbackActivity.this, MainActivity.class);
        startActivity(homeIntent);
        finish();
    }
    /**
     * Hides the soft keyboard
     */
    public void hideSoftKeyboard() {
        if(getCurrentFocus()!=null) {
            InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
            inputMethodManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
        }
    }

    /**
     * Shows the soft keyboard
     */
    public void showSoftKeyboard(View view) {
        InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
        view.requestFocus();
        inputMethodManager.showSoftInput(view, 0);
    }
}