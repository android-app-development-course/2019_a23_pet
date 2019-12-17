package com.example.gohome;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.example.gohome.Member.Activity.MemberHomeActivity;
import com.example.gohome.Organizer.OrganizerMain;
import com.example.gohome.User.Activity.UserHomeActivity;
import com.example.gohome.ui.login.LoginActivity;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //初始化控件
        init();
    }

    private void init(){

        SharedPreferences sharedPreferences = getApplication().getSharedPreferences("userInfo", Context.MODE_PRIVATE);
        int userType = sharedPreferences.getInt("userType", -1);
        Log.i("userType", String.valueOf(userType));
        Intent intent;
        switch (userType){
            case -1:
            case 0:
                intent = new Intent(getApplicationContext(), UserHomeActivity.class);
                startActivity(intent);
                finish();
                break;
            case 1:
                intent = new Intent(getApplicationContext(), MemberHomeActivity.class);
                startActivity(intent);
                finish();
                break;
            case 2:
                intent = new Intent(getApplicationContext(), OrganizerMain.class);
                startActivity(intent);
                finish();
                break;
        }
        Button btn_user = findViewById(R.id.btn_user);
        Button btn_organizer = findViewById(R.id.btn_organizer);
        Button btn_member = findViewById(R.id.btn_member);
        Button btn_login = findViewById(R.id.btn_login);

        btn_user.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, UserHomeActivity.class);
                startActivity(intent);
            }
        });
        btn_member.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, MemberHomeActivity.class);
                startActivity(intent);
            }
        });
        btn_organizer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, OrganizerMain.class);
                startActivity(intent);
            }
        });
        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, LoginActivity.class);
                startActivity(intent);
            }
        });
    }
}
