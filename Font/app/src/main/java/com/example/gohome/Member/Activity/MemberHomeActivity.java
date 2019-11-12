package com.example.gohome.Member.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.aurelhubert.ahbottomnavigation.AHBottomNavigation;
import com.aurelhubert.ahbottomnavigation.AHBottomNavigationItem;
import com.example.gohome.Member.Adapter.MemberHomeViewPagerAdapter;
import com.example.gohome.Utils.NoScrollViewPager;
import com.example.gohome.R;

public class MemberHomeActivity extends AppCompatActivity {

    AHBottomNavigation bottomNavigation;
    private NoScrollViewPager viewPager;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_member_home);

        //初始化控件
        initView();
    }

    private void initView() {
        bottomNavigation = (AHBottomNavigation) findViewById(R.id.btmNav_mem);
        viewPager = findViewById(R.id.vp_home);
        //设置viewPager不可左右滑动
        viewPager.setScanScroll(false);

        // 创建ViewPager适配器
        MemberHomeViewPagerAdapter memberHomeViewPagerAdapter = new MemberHomeViewPagerAdapter(getSupportFragmentManager());

        // 给ViewPager设置适配器
        viewPager.setAdapter(memberHomeViewPagerAdapter);

        viewPager.setCurrentItem(0);


        //初始化底部栏

        // Create items
        AHBottomNavigationItem item1 = new AHBottomNavigationItem("领养", R.drawable.member_adopt,R.color.yellow);
        AHBottomNavigationItem item2 = new AHBottomNavigationItem("救助", R.drawable.member_help,R.color.yellow);
        AHBottomNavigationItem item3 = new AHBottomNavigationItem("发布", R.drawable.member_release,R.color.yellow);
        AHBottomNavigationItem item4 = new AHBottomNavigationItem("审核", R.drawable.member_check,R.color.yellow);
        AHBottomNavigationItem item5 = new AHBottomNavigationItem("我的", R.drawable.member_me,R.color.yellow);

        // Add items
        bottomNavigation.addItem(item1);
        bottomNavigation.addItem(item2);
        bottomNavigation.addItem(item3);
        bottomNavigation.addItem(item4);
        bottomNavigation.addItem(item5);

        // Set background color
        bottomNavigation.setDefaultBackgroundColor(getResources().getColor(R.color.yellow));

       // Change colors
        bottomNavigation.setAccentColor(getResources().getColor(R.color.orange));
        bottomNavigation.setInactiveColor(getResources().getColor(R.color.inactiveGray));

//        // Set current item programmatically
//        bottomNavigation.setCurrentItem(0);

      // Set listeners
        bottomNavigation.setOnTabSelectedListener(new AHBottomNavigation.OnTabSelectedListener() {
            @Override
            public boolean onTabSelected(int position, boolean wasSelected) {
                // Do something cool here...
                viewPager.setCurrentItem(position);
                return true;
            }
        });
        bottomNavigation.setOnNavigationPositionListener(new AHBottomNavigation.OnNavigationPositionListener() {
            @Override public void onPositionChange(int y) {
                // Manage the new y position
            }
        });





    }


}
