package com.example.gohome.User;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.example.gohome.R;

import java.util.Objects;

public class UserHomeActivity extends AppCompatActivity
        implements View.OnClickListener {
    private RelativeLayout container;
    private OpenMenu openMenu;
    private LinearLayout bl_menu;
    private BottomLayout bl_home, bl_me;

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_home);

        Objects.requireNonNull(getSupportActionBar()).hide();
        //状态栏透明
        getWindow().getDecorView().setSystemUiVisibility(
                View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN |
                        View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
        getWindow().setStatusBarColor(Color.TRANSPARENT);

        container = findViewById(R.id.rel_container);

        openMenu = new OpenMenu(this);
        openMenu.init(container);

        initBottom();
    }

    private void showMenu() {
        if(openMenu == null){
            openMenu = new OpenMenu(this);
            openMenu.init(container);
        }
        openMenu.show(container);
    }

    public void onClick(View view){
        switch (view.getId()) {
            case R.id.bl_home:
                bl_home.setFocused(true);
                bl_me.setFocused(false);
                break;
            case R.id.bl_menu:
                showMenu();
                break;
            case R.id.bl_me:
                bl_home.setFocused(false);
                bl_me.setFocused(true);
                break;
        }
    }

    private void initBottom(){
        bl_menu = findViewById(R.id.bl_menu);
        bl_menu.setOnClickListener(this);

        bl_home = findViewById(R.id.bl_home);
        bl_home.setNormalIcon(R.drawable.user_home_normal);
        bl_home.setFocusIcon(R.drawable.user_home);
        bl_home.setIconText("首页");
        bl_home.setFocused(true);
        bl_home.setOnClickListener(this);

        bl_me = findViewById(R.id.bl_me);
        bl_me.setNormalIcon(R.drawable.user_me_normal);
        bl_me.setFocusIcon(R.drawable.user_me);
        bl_me.setIconText("我的");
        bl_me.setFocused(false);
        bl_me.setOnClickListener(this);
    }
}
