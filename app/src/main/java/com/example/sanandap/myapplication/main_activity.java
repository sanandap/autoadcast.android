package com.example.sanandap.myapplication;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.EditText;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Calendar;

import static com.example.sanandap.myapplication.R.id;
public class main_activity extends Activity
{

    public static int attempt = 1;

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_activity);
        final EditText editTextPassword = (EditText) findViewById(R.id.EditText_Password);
        CheckBox showPasswordCheckBox = (CheckBox)findViewById(R.id.CheckBox_ShowPassword);

        showPasswordCheckBox.setOnCheckedChangeListener(new OnCheckedChangeListener()
        {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                // TODO Auto-generated method stub
                if(!isChecked )
                {
                    editTextPassword.setTransformationMethod(PasswordTransformationMethod.getInstance());
                }
                else
                {
                    editTextPassword.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                }
            }
        });
    }

    public void ButtonClickLogin(View v)
    {
        if(attempt < 4)
        {
            String timeStamp = new SimpleDateFormat("MM-dd-yyyy HH:mm a").format(Calendar.getInstance().getTime());

            EditText EditTextUserId = (EditText)findViewById(id.EditText_UserId);
            EditText EditTextPassword = (EditText)findViewById(id.EditText_Password);
            TextView textViewErrorMessage = (TextView)findViewById(id.textViewErrorMessage);

            if ("".equals(EditTextUserId.getText().toString()) && "".equals(EditTextPassword.getText().toString()))
            {
                EditTextUserId.requestFocus();
                textViewErrorMessage.setTextColor(Color.argb(255, 255, 0, 0));
                textViewErrorMessage.setText("Warning: Both the User ID and Password is missing.");
            }
            else if ("".equals(EditTextUserId.getText().toString()))
            {
                EditTextUserId.requestFocus();
                textViewErrorMessage.setTextColor(Color.argb(255, 255, 0, 0));
                textViewErrorMessage.setText("Warning: User ID is missing.");
            }
            else if ("".equals(EditTextPassword.getText().toString()))
            {
                EditTextPassword.requestFocus();
                textViewErrorMessage.setTextColor(Color.argb(255, 255, 0, 0));
                textViewErrorMessage.setText("Warning: Password is missing");
            }
            else
            {
                //Connect to the database and get the connection
                //Assign values from database to below variables
                String dbUserID = "admin";
                String dbPassword = "admin";
                String dbLocation = "location";

                if(!dbUserID.equals(EditTextUserId.getText().toString()) || !dbPassword.equals(EditTextPassword.getText().toString()))
                {
                    EditTextUserId.setText("");
                    EditTextPassword.setText("");
                    EditTextUserId.requestFocus();
                    textViewErrorMessage.setTextColor(Color.argb(255, 255, 0, 0));
                    if(attempt == 3)
                    {
                        textViewErrorMessage.setText("Warning:\nIncorrect User ID or Password.\n\n" +
                                "Seems you don't remember your login credentials. \n\n" +
                                "Please reset  your password at http://www.autoadcast.com");
                    }
                    else
                    {
                        textViewErrorMessage.setText("Warning: \nIncorrect User ID or Password. Please try again.\n\n" +
                                "Attempt " + attempt + " of 3. \n\n" +
                                "If you don't remember your Password, please reset your password at http://www.autoadcast.com and reset the password.");
                    }
                    attempt++;
                }
                else
                {

                    //Have to save the user id, and location id SQLite
                    Intent intent = new Intent(main_activity.this, DashboardActivity.class);
                    startActivity(intent);
                    main_activity.this.finish();
                }
            }
        }
    }
}