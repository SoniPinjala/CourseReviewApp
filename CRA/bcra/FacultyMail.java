package com.example.bcra;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class FacultyMail extends AppCompatActivity implements View.OnClickListener {
    EditText e1;
    Button submit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_faculty_mail);

        Toast.makeText(this,"Please enter fields seperated by ,", Toast.LENGTH_LONG).show();
        e1=findViewById(R.id.gmail_id);
        submit=findViewById(R.id.submit_id);
        submit.setOnClickListener(this);


        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation);
        bottomNavigationView.setSelectedItemId(R.id.mail);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                switch (menuItem.getItemId()){
                    case R.id.home:
                        startActivity(new Intent(getApplicationContext(), WorkSpaceFaculty.class));
                        overridePendingTransition(0,0);
                        return true;

                    case R.id.profile:
                        startActivity(new Intent(getApplicationContext(), FacultyProfile.class));
                        overridePendingTransition(0,0);
                        return true;

                    case R.id.mail:
                        return true;
                }
                return false;
            }
        });
    }

    @Override
    public void onClick(View view)
    {
        String gmail=e1.getText().toString().trim();
        for(int i=0;i<gmail.length();i++)
            if(gmail.charAt(i)==' ')
            {
                Toast.makeText(this,"Please Avoid using Space", Toast.LENGTH_LONG).show();
                return;
            }
//        if(gmail.length()>0)
//        {
//            String gm[] = gmail.split(",");
//            for (int i = 0; i < gm.length; i++)
//            {
//                if(!gm[i].contains("@gmail.com"))
//                    gm[i] += "@gmail.com";
//            }
//            String gstr = "";
//            for (int i = 0; i < gm.length; i++)
//                gstr += gm[i] + ",";
//            gstr = gstr.substring(0, gstr.length() - 1);
//            fstr+=gstr+",";
//        }

        String gm[] = gmail.split(",");
        String fstr="";
        for(int i=0;i<gm.length;i++)
        {
            if(!isValid(gm[i]))
            {
                Toast.makeText(this,"MailID no. "+Integer.toString(i+1)+" is Invalid", Toast.LENGTH_LONG).show();
                return;
            }
            else
                fstr+=gm[i]+",";
        }
        fstr=fstr.substring(0,fstr.length()-1);
        if(fstr.length()==0)
        {
            Toast.makeText(this,"Enter atleast one field.", Toast.LENGTH_LONG).show();
            return;
        }
        Intent mailintent=new Intent(this,feedback_activity.class);
        mailintent.putExtra("mails",fstr);
        startActivity(mailintent);
    }

    static boolean isValid(String email) {
//        String emailRegex = "^[a-zA-Z0-9_+&*-]+(?:\\."+
//                "[a-zA-Z0-9_+&*-]+)*@" +
//                "(?:[a-zA-Z0-9-]+\\.)+[a-z" +
//                "A-Z]{2,7}$";
        String emailRegex = "(?:[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:\\.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*|\"(?:[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21\\x23-\\x5b\\x5d-\\x7f]|\\\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])*\")@(?:(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?|\\[(?:(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\\.){3}(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?|[a-z0-9-]*[a-z0-9]:(?:[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21-\\x5a\\x53-\\x7f]|\\\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])+)\\])";

        Pattern pat = Pattern.compile(emailRegex);
        if (email == null)
            return false;
        return pat.matcher(email).matches();
    }
}

