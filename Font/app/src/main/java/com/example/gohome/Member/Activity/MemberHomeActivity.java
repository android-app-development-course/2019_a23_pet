package com.example.gohome.Member.Activity;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;

import com.aurelhubert.ahbottomnavigation.AHBottomNavigation;
import com.aurelhubert.ahbottomnavigation.AHBottomNavigationItem;
import com.example.gohome.Member.Adapter.MemberHomeViewPagerAdapter;
import com.example.gohome.Component.NoScrollViewPager;
import com.example.gohome.Member.Fragment.MemberCheckFragment;
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

        setTitle("待领养宠物");


        bottomNavigation = (AHBottomNavigation) findViewById(R.id.btmNav_mem);
        viewPager = findViewById(R.id.vp_home);
        //设置viewPager不可左右滑动
        viewPager.setScanScroll(false);

        // 创建ViewPager适配器
        MemberHomeViewPagerAdapter memberHomeViewPagerAdapter = new MemberHomeViewPagerAdapter(getSupportFragmentManager());

        // 给ViewPager设置适配器
        viewPager.setAdapter(memberHomeViewPagerAdapter);

        viewPager.setCurrentItem(0);

        // Create items
        AHBottomNavigationItem item1 = new AHBottomNavigationItem("领养", R.drawable.home_selected,R.color.yellow);
        AHBottomNavigationItem item2 = new AHBottomNavigationItem("救助", R.drawable.help_selected,R.color.yellow);
        AHBottomNavigationItem item3 = new AHBottomNavigationItem("发布", R.drawable.edit_selected,R.color.yellow);
        AHBottomNavigationItem item4 = new AHBottomNavigationItem("审核", R.drawable.check_selected,R.color.yellow);
        AHBottomNavigationItem item5 = new AHBottomNavigationItem("我的", R.drawable.mine_selected,R.color.yellow);

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

      // Set listeners
        bottomNavigation.setOnTabSelectedListener(new AHBottomNavigation.OnTabSelectedListener() {
            @Override
            public boolean onTabSelected(int position, boolean wasSelected) {
                // Do something cool here...
                viewPager.setCurrentItem(position);
                switch (position){
                    case 0:
                        setTitle("待领养宠物");
                        break;
                    case 1:
                        setTitle("填写救助申请");
                        break;
                    case 2:
                        setTitle("发布领养宠物");
                        break;
                    case 3:
                        setTitle("审核信息");
                        break;
                    case 4:
                        setTitle("我的页面");
                        break;
                }
                return true;
            }
        });
        bottomNavigation.setOnNavigationPositionListener(new AHBottomNavigation.OnNavigationPositionListener() {
            @Override public void onPositionChange(int y) {
                // Manage the new y position
            }
        });
    }

    //activity跳转到fragment
    @Override
    protected void onResume() {
        int idFirst = getIntent().getIntExtra("idFirst", 0);
        int idSecond = getIntent().getIntExtra("inSecond",0);
        if (idFirst == 4) {
            Fragment fragmen = new MemberCheckFragment();
            FragmentManager fmanger = getSupportFragmentManager();
            FragmentTransaction transaction = fmanger.beginTransaction();
            transaction.replace(R.id.vp_home, fragmen);
            transaction.commit();
            viewPager.setCurrentItem(3);//
            bottomNavigation.setCurrentItem(3);
            //帮助跳转到指定子fragment
            Intent i=new Intent();
            i.setClass(MemberHomeActivity.this,MemberCheckFragment.class);
            i.putExtra("id",idSecond);
        }
        super.onResume();
    }
}
