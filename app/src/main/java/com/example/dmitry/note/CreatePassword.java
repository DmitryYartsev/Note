package com.example.dmitry.note;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class CreatePassword extends AppCompatActivity {
    String password="1234";
    String second_password="";
    String pressed_password="";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.creat_password);
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

        final Dialog dialog=new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dialog_layout);
        dialog.show();
        TextView button = (TextView)dialog.findViewById(R.id.ok);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                dialog.cancel();
            }});}
    public void update(){
        int lenght=  pressed_password.length();
        View v1=(View)findViewById(R.id.v1);
        View v2=(View)findViewById(R.id.v2);
        View v3=(View)findViewById(R.id.v3);
        View v4=(View)findViewById(R.id.v4);
        Button b_next=(Button)findViewById(R.id.btn_next);
        switch (lenght){
            case 1:
                v1.setBackgroundResource(R.drawable.view_full);
                v2.setBackgroundResource(R.drawable.view_empty);
                v3.setBackgroundResource(R.drawable.view_empty);
                v4.setBackgroundResource(R.drawable.view_empty);
                b_next.setVisibility(View.GONE);
                break;
            case 2:
                v2.setBackgroundResource(R.drawable.view_full);
                v3.setBackgroundResource(R.drawable.view_empty);
                v4.setBackgroundResource(R.drawable.view_empty);
                b_next.setVisibility(View.GONE);
                break;
            case 3:
                v3.setBackgroundResource(R.drawable.view_full);
                v4.setBackgroundResource(R.drawable.view_empty);
                b_next.setVisibility(View.GONE);
                break;
            case 4:
                v4.setBackgroundResource(R.drawable.view_full);
                b_next.setVisibility(View.VISIBLE);
                if(!second_password.equals("")){
                    b_next.setText("Завершить");
                }
                break;
            default:


                v1.setBackgroundResource(R.drawable.view_empty);
                v2.setBackgroundResource(R.drawable.view_empty);
                v3.setBackgroundResource(R.drawable.view_empty);
                v4.setBackgroundResource(R.drawable.view_empty);
                b_next.setVisibility(View.GONE);
                break;
        }

    }

    public void control_press(View view) {
        int length=pressed_password.length();

            switch (view.getId()) {
                case R.id.b1:
                    if(length>=4){}else {
                    pressed_password += "1";
                    update();}
                    break;
                case R.id.b2:
                    if(length>=4){}else {
                        pressed_password += "2";
                        update();}
                    break;
                case R.id.b3:
                    if(length>=4){}else {
                        pressed_password += "3";
                        update();}
                    break;
                case R.id.b4:
                    if(length>=4){}else {
                        pressed_password += "4";
                        update();}
                    break;
                case R.id.b5:
                    if(length>=4){}else {
                        pressed_password += "5";
                        update();}
                    break;
                case R.id.b6:
                    if(length>=4){}else {
                        pressed_password += "6";
                        update();}
                    break;
                case R.id.b7:
                    if(length>=4){}else {
                        pressed_password += "7";
                        update();}
                    break;
                case R.id.b8:
                    if(length>=4){}else {
                        pressed_password += "8";
                        update();}
                    break;
                case R.id.b9:
                    if(length>=4){}else {
                        pressed_password += "9";
                        update();}
                    break;
                case R.id.b0:
                    if(length>=4){}else {
                        pressed_password += "0";
                        update();}
                    break;
                case R.id.bb:
                    int lenght = pressed_password.length();
                    if (lenght > 1) {
                        pressed_password = pressed_password.substring(0, lenght - 1);
                    } else {
                        pressed_password = "";
                    }
                    update();
                    break;
            }

    }

    public void next(View view) {
        Button b_next=(Button)findViewById(R.id.btn_next);

        if(second_password.equals("")){
            Toast.makeText(this,"Повторите пароль",Toast.LENGTH_SHORT).show();
        second_password=pressed_password;
        pressed_password="";
            b_next.setVisibility(View.GONE);
            update();}
        else if(!second_password.equals("")&!second_password.equals(pressed_password))
        {
            b_next.setText("Продолжить");
            Toast.makeText(this,"пароли не совпадают",Toast.LENGTH_SHORT).show();
            pressed_password="";
            second_password="";
            update();

        }
        else {
            b_next.setVisibility(View.GONE);
            SharedPreferences sharedPreferences= getSharedPreferences("password",
                    Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putString("password",pressed_password);
            editor.apply();
            editor.commit();
            startActivity(new Intent(this, Private_list.class));

        }
    }
    @Override
    public void onBackPressed(){
        startActivity(new Intent(getApplicationContext(),Main_list.class));
    }


}
