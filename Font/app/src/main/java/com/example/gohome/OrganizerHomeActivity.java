package com.example.gohome;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.os.Bundle;

import com.aurelhubert.ahbottomnavigation.AHBottomNavigation;
import com.aurelhubert.ahbottomnavigation.AHBottomNavigationItem;

public class OrganizerHomeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_organizer_home);
        //初始化控件
        init();
    }

    private void init(){
        //绑定控件
        AHBottomNavigation bottomNavigation = (AHBottomNavigation) findViewById(R.id.btmNav_org);

        // Create items
        AHBottomNavigationItem item1 = new AHBottomNavigationItem("申请领养", R.drawable.organizer_adopt, Color.parseColor("#455C65"));
        AHBottomNavigationItem item2 = new AHBottomNavigationItem("申请救助", R.drawable.organizer_help, Color.parseColor("#00886A"));
        AHBottomNavigationItem item3 = new AHBottomNavigationItem("发布领养", R.drawable.organizer_release, Color.parseColor("#8B6B62"));
        AHBottomNavigationItem item4 = new AHBottomNavigationItem("审核", R.drawable.organizer_check, Color.parseColor("#8B6B62"));
        AHBottomNavigationItem item5 = new AHBottomNavigationItem("我的", R.drawable.organizer_me, Color.parseColor("#8B6B62"));

        // Add items
        bottomNavigation.addItem(item1);
        bottomNavigation.addItem(item2);
        bottomNavigation.addItem(item3);
        bottomNavigation.addItem(item4);
        bottomNavigation.addItem(item4);
        bottomNavigation.addItem(item5);


        // Set background color
        bottomNavigation.setDefaultBackgroundColor(Color.parseColor("#FFFFFF"));

        // Change colors
        bottomNavigation.setAccentColor(Color.parseColor("#F63D2B"));
        bottomNavigation.setInactiveColor(Color.parseColor("#747474"));

        // Use colored navigation with circle reveal effect
        bottomNavigation.setColored(true);

        // Set listener
        bottomNavigation.setAHBottomNavigationListener(new AHBottomNavigation.AHBottomNavigationListener() {
            @Override
            public void onTabSelected(int position) {
                System.out.println("点击了第："+ position + "个tab");
            }
        });
    }

}
