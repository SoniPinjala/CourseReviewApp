package com.example.bcra;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class DataBaseHelper extends SQLiteOpenHelper {
    public static final String  DATABASE_NAME="register.db";
    public static final String  TABLE_NAME="registeruser";
    public static final String  COL_SNO="SNO";
    public static final String  COL_USERNAME="USERNAME";
    public static final String  COL_PASSWORD="PASSWORD";
    public static final String  COL_MAILID="MAILID";
    public static final String  COL_MEMBER="MEMBER";

    public DataBaseHelper(Context context) {
        super(context, DATABASE_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {

        sqLiteDatabase.execSQL("CREATE TABLE registeruser(ID INTEGER PRIMARY KEY AUTOINCREMENT, USERNAME TEXT,PASSWORD TEXT,MAILID TEXT,MEMBER TEXT)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

        sqLiteDatabase.execSQL(" DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(sqLiteDatabase);
    }

    /*  public String get_member(String user, String password,SQLiteDatabase sqLiteDatabase){
          String m="";

          return m;
      }*/
    public long addUser(String user, String password,String member,String mail){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("username",user);
        contentValues.put("password",password);
        contentValues.put("member",member);
        contentValues.put("mailid",mail);
        long res = db.insert("registeruser",null,contentValues);
        db.close();
        return  res;
        //return 1 if correctly inserted
        //return 0 if the username already exists.
    }

    public String checkUser(String username, String password)//String member
    {
        Log.d("CheckUser","Checking.......");
        String member;
        String[] columns = { COL_USERNAME };
        String[] columns_member = { COL_MEMBER };
        SQLiteDatabase db = getReadableDatabase();
        String selection = COL_USERNAME + "=?" + " and " + COL_PASSWORD + "=?";
        String[] selectionArgs = { username, password };

        Cursor cursor = db.query(TABLE_NAME,columns,selection,selectionArgs,null,null,null);
        int count = cursor.getCount();
        cursor.close();

        Cursor c=db.query(TABLE_NAME,columns_member,selection,selectionArgs,null,null,null);
        c.moveToFirst();
        //member=c.getString(0);
        db.close();

        //Log.w("Member:",member);
        //member.trim();
        if(count>0)
        {   c.moveToFirst();
            member=c.getString(0);
            return  member;}
        else

            return  "NOT EXIST";
    }

    //user to enter atleast 1 Uppercase, 1 Number and 1 Symbol
    public  boolean isValidPassword(final String password) {

        Pattern pattern;
        Matcher matcher;
        final String PASSWORD_PATTERN = "^(?=.*[0-9])(?=.*[A-Z])(?=.*[@#$%^&+=!])(?=\\S+$).{4,}$";
        pattern = Pattern.compile(PASSWORD_PATTERN);
        matcher = pattern.matcher(password);

        return matcher.matches();

    }

    public long UserNameExists(String user)
    {

        String[] columns = { COL_USERNAME };
        SQLiteDatabase db = getReadableDatabase();
        String selection = COL_USERNAME + "=?" ;
        String[] selectionArgs = { user};
        Cursor c=db.query(TABLE_NAME,columns,selection,selectionArgs,null,null,null);
        c.moveToFirst();
        db.close();
        return c.getCount();
    }

}
