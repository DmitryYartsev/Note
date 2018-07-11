package com.example.dmitry.note;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.SystemClock;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

public class Control extends AppCompatActivity {
     String password;
    String pressed_password="";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_control);
        SharedPreferences sharedPreferences= getSharedPreferences("password",
                Context.MODE_PRIVATE);
        password= sharedPreferences.getString("password","");
        TextView t1 = (TextView)findViewById(R.id.b1);
        TextView t2 = (TextView)findViewById(R.id.b2);
        TextView t3 = (TextView)findViewById(R.id.b3);
        TextView t4 = (TextView)findViewById(R.id.b4);
        TextView t5 = (TextView)findViewById(R.id.b5);
        TextView t6 = (TextView)findViewById(R.id.b6);
        TextView t7 = (TextView)findViewById(R.id.b7);
        TextView t8 = (TextView)findViewById(R.id.b8);
        TextView t9 = (TextView)findViewById(R.id.b9);
        TextView t0 = (TextView)findViewById(R.id.b0);


        Typeface custom_font = Typeface.createFromAsset(getAssets(),  "fonts/light.otf");

        t1.setTypeface(custom_font);
        t2.setTypeface(custom_font);
        t3.setTypeface(custom_font);
        t4.setTypeface(custom_font);
        t5.setTypeface(custom_font);
        t6.setTypeface(custom_font);
        t7.setTypeface(custom_font);
        t8.setTypeface(custom_font);
        t9.setTypeface(custom_font);
        t0.setTypeface(custom_font);



    }
    public void update(){
        int lenght=  pressed_password.length();
        View v1=(View)findViewById(R.id.v1);
        View v2=(View)findViewById(R.id.v2);
        View v3=(View)findViewById(R.id.v3);
        View v4=(View)findViewById(R.id.v4);
        switch (lenght){
            case 1:
                v1.setBackgroundResource(R.drawable.view_full);
                v2.setBackgroundResource(R.drawable.view_empty);
                v3.setBackgroundResource(R.drawable.view_empty);
                v4.setBackgroundResource(R.drawable.view_empty);
                break;
            case 2:
                v2.setBackgroundResource(R.drawable.view_full);
                v3.setBackgroundResource(R.drawable.view_empty);
                v4.setBackgroundResource(R.drawable.view_empty);
                break;
            case 3:
                v3.setBackgroundResource(R.drawable.view_full);
                v4.setBackgroundResource(R.drawable.view_empty);
                break;
            case 4:
                v4.setBackgroundResource(R.drawable.view_full);
                if(pressed_password.equals(password)){
                    pressed_password="";
                    try{
                        Thread.sleep(200);}
                    catch (Exception e) {
                    }
                    startActivity(new Intent(this, Private_list.class));
                   }
                else {
                    pressed_password = "";
                    try{
                    Thread.sleep(200);}
                    catch (Exception e) {
                    }

                    Toast.makeText(this,"Неверный пароль",Toast.LENGTH_SHORT).show();
                }
                v1.setBackgroundResource(R.drawable.view_empty);
                v2.setBackgroundResource(R.drawable.view_empty);
                v3.setBackgroundResource(R.drawable.view_empty);
                v4.setBackgroundResource(R.drawable.view_empty);
                break;
            default:
                v1.setBackgroundResource(R.drawable.view_empty);
                v2.setBackgroundResource(R.drawable.view_empty);
                v3.setBackgroundResource(R.drawable.view_empty);
                v4.setBackgroundResource(R.drawable.view_empty);
                break;
        }

    }

    public void control_press(View view) {
        switch(view.getId()){
            case R.id.b1:
                pressed_password+="1";
                update();
               break;
            case R.id.b2:
                pressed_password+="2";
                update();
                break;
            case R.id.b3:
                pressed_password+="3";
                update();
                break;
            case R.id.b4:
                pressed_password+="4";
                update();
                break;
            case R.id.b5:
                pressed_password+="5";
                update();
                break;
            case R.id.b6:
                pressed_password+="6";
                update();
                break;
            case R.id.b7:
                pressed_password+="7";
                update();
                break;
            case R.id.b8:
                pressed_password+="8";
                update();
                break;
            case R.id.b9:
                pressed_password+="9";
                update();
                break;
            case R.id.b0:
                pressed_password+="0";
                update();
                break;
            case R.id.bb:
              int lenght=  pressed_password.length();
                if(lenght>1){
                pressed_password=pressed_password.substring(0,lenght-1);
                }else{
                    pressed_password="";
                }
                update();
                break;
        }
    }
}
