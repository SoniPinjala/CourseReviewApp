package com.example.bcra;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class HomeActivity extends AppCompatActivity {

    EditText mTextUsername;
    EditText mTextPassword;
    Button mButtonLogin;
    TextView mTextViewRegister;

    DataBaseHelper db=new DataBaseHelper(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        mTextUsername=(EditText)findViewById(R.id.edit_username);
        mTextPassword=(EditText)findViewById(R.id.edit_password);
        mButtonLogin=(Button)findViewById(R.id.login_button);
        mTextViewRegister=(TextView)findViewById(R.id.textview_register);


        mTextUsername.addTextChangedListener(loginTextWatcher);
        mTextPassword.addTextChangedListener(loginTextWatcher);
        mTextViewRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent  registerIntent=new Intent(HomeActivity.this,RegisterActivity.class);
                startActivity(registerIntent);
            }
        });

        mButtonLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String user = mTextUsername.getText().toString().trim();
                String pwd = mTextPassword.getText().toString().trim();
                //String member="";//GETTING ERROR HERE;
                //member=db.getColMember(user);

                Log.w("Username",user);
                Log.w("Password",pwd);
                String res = "hello";
                res= db.checkUser(user, pwd);//member
                if(res.equals("student"))
                {
                    //Toast.makeText(HomeActivity.this,"Login for Student",Toast.LENGTH_SHORT).show();
                    Intent HomePage = new Intent(HomeActivity.this,WorkSpaceStudent.class);
                    startActivity(HomePage);
                }
                else
                if(res.equals("faculty"))
                {
                    Log.w("Member check",res);
                    Intent HomePage = new Intent(HomeActivity.this,WorkSpaceFaculty.class);
                    startActivity(HomePage);
                }
                else
                {
                    Log.w("RES CHECK:",res);
                    Toast.makeText(HomeActivity.this,"Login Error", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }

    private TextWatcher loginTextWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {

            String uname= mTextUsername.getText().toString().trim();
            String pass= mTextPassword.getText().toString().trim();

            mButtonLogin.setEnabled(!uname.isEmpty() && !pass.isEmpty());
        }

        @Override
        public void afterTextChanged(Editable s) {

        }
    };

}

