package com.example.gohome.user;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.RelativeLayout;

import com.example.gohome.R;

import java.util.Objects;

public class UserHomeActivity extends AppCompatActivity {
    private RelativeLayout container;
    private OpenMenu openMenu;

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
        findViewById(R.id.lin_tab_menu).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showMenu();
            }
        });
    }
    private void showMenu() {
        if(openMenu == null){
            openMenu = new OpenMenu(this);
            openMenu.init(container);
        }
        openMenu.show(container);
    }
}
