package com.example.bcra;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

public class RegisterActivity extends AppCompatActivity {

    EditText mTextUsername;
    EditText mTextPassword;
    EditText getTextCmfPassword;
    EditText getTextMailid;
    Button mButtonRegister;
    TextView mTextViewLogin;
    RadioButton FacultyRadio;
    RadioButton StudentRadio;
    DataBaseHelper db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        db = new DataBaseHelper(this);
        mTextUsername = (EditText) findViewById(R.id.edit_username_reg);
        mTextPassword = (EditText) findViewById(R.id.edit_password_reg);
        getTextCmfPassword = (EditText) findViewById(R.id.edit_cmf_password_reg);
        getTextMailid=(EditText)findViewById(R.id.edit_mail_reg);
        mButtonRegister = (Button) findViewById(R.id.register_button);
        mTextViewLogin = (TextView) findViewById(R.id.textview_login);
        FacultyRadio = (RadioButton) findViewById(R.id.radio_faculty);
        StudentRadio = (RadioButton) findViewById(R.id.radio_student);

        mTextUsername.addTextChangedListener(RegisterTextWatcher);
        mTextPassword.addTextChangedListener(RegisterTextWatcher);
        getTextCmfPassword.addTextChangedListener(RegisterTextWatcher);
        getTextMailid.addTextChangedListener(RegisterTextWatcher);

        mTextViewLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent loginIntent = new Intent(RegisterActivity.this, HomeActivity.class);
                startActivity(loginIntent);
            }
        });


        mButtonRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String user = mTextUsername.getText().toString().trim();
                String pwd = mTextPassword.getText().toString().trim();
                String cnf_pwd = getTextCmfPassword.getText().toString().trim();
                String mail=getTextMailid.getText().toString().trim();

                long res=db.UserNameExists(user);
                if(res==0) {
                    if (pwd.length() < 8 && !db.isValidPassword(pwd)) {
                        Toast.makeText(RegisterActivity.this, "Not Strong Password", Toast.LENGTH_SHORT).show();
                    } else
                        Toast.makeText(RegisterActivity.this, "Valid Password", Toast.LENGTH_SHORT).show();

                    if (pwd.equals(cnf_pwd)) {
                        if (FacultyRadio.isChecked()) {
                            long val = db.addUser(user, pwd, "faculty", mail);
                            if (val > 0) {
                                Toast.makeText(RegisterActivity.this, "(FACULTY)Welcome " + user, Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(RegisterActivity.this, WorkSpaceFaculty.class);
                                startActivity(intent);
                            } else
                                Toast.makeText(RegisterActivity.this, "Registration Error", Toast.LENGTH_SHORT).show();
                        }


                        if (StudentRadio.isChecked()) {
                            long val = db.addUser(user, pwd, "student", mail);
                            if (val > 0) {
                                Toast.makeText(RegisterActivity.this, "(STUDENT)Welcome " + user, Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(RegisterActivity.this, WorkSpaceStudent.class);
                                startActivity(intent);
                            } else
                                Toast.makeText(RegisterActivity.this, "Registration Error", Toast.LENGTH_SHORT).show();
                        }
                    }
                    else {
                        Toast.makeText(RegisterActivity.this, "Password Mismatch", Toast.LENGTH_SHORT).show();
                    }
                }
                else
                {
                    Toast.makeText(RegisterActivity.this, "Username Already Taken!!", Toast.LENGTH_SHORT).show();
                }

            }
        });
    }

    private TextWatcher RegisterTextWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {

            String uname = mTextUsername.getText().toString().trim();
            String pass = mTextPassword.getText().toString().trim();
            String cnfpass = getTextCmfPassword.getText().toString().trim();
            String mailinput = getTextMailid.getText().toString().trim();

            mButtonRegister.setEnabled(!uname.isEmpty() && !pass.isEmpty() && !cnfpass.isEmpty() && !mailinput.isEmpty());
        }

        @Override
        public void afterTextChanged(Editable s) {

        }
    };

}