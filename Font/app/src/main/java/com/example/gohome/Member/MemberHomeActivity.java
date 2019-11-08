package com.example.gohome.Member;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.os.Bundle;
import android.widget.Toast;

import com.aurelhubert.ahbottomnavigation.AHBottomNavigation;
import com.aurelhubert.ahbottomnavigation.AHBottomNavigationItem;
import com.example.gohome.R;

public class MemberHomeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_member_home);
        init();
        //初始化控件
    }

    private void init(){
        //绑定控件
        AHBottomNavigation bottomNavigation = findViewById(R.id.btmNav_mem);

        //创建items，3个参数分别是item的文字，item的icon，选中item时的整体颜色（该项需要开启）
        AHBottomNavigationItem item1 = new AHBottomNavigationItem("首页", R.drawable.member_adopt, R.color.colorPrimary);
        AHBottomNavigationItem item2 = new AHBottomNavigationItem("发布", R.drawable.member_release, R.color.colorPrimary);
        AHBottomNavigationItem item3 = new AHBottomNavigationItem("审核", R.drawable.member_check, R.color.colorPrimary);
        AHBottomNavigationItem item4 = new AHBottomNavigationItem("我的", R.drawable.member_me, R.color.colorPrimary);

        // Add items
        bottomNavigation.addItem(item1);
        bottomNavigation.addItem(item2);
        bottomNavigation.addItem(item3);
        bottomNavigation.addItem(item4);

        //设置整体背景颜色（如果开启了单个的背景颜色，该项将会无效）
        bottomNavigation.setDefaultBackgroundColor(Color.parseColor("#FFFFFF"));

        //设置item被选中和待选时的颜色
        bottomNavigation.setAccentColor(Color.parseColor("#98F5FF"));
        bottomNavigation.setInactiveColor(Color.parseColor("#ffffff"));

        //是否开启切换item切换颜色
        bottomNavigation.setColored(true);

        //设置初始选中的item
        bottomNavigation.setCurrentItem(1);

        // Set listener
//        bottomNavigation.setAHBottomNavigationListener(new AHBottomNavigation.AHBottomNavigationListener() {
//            @Override
//            public void onTabSelected(int position) {
//            }
//        });

        bottomNavigation.setOnTabSelectedListener(new AHBottomNavigation.OnTabSelectedListener() {
            @Override
            public boolean onTabSelected(int position, boolean wasSelected) {
                //点击item时的事件
//                System.out.println("点击了第："+ position + "个tab");
                Toast.makeText(MemberHomeActivity.this, "点击了" + position, Toast.LENGTH_SHORT).show();
                return true;
            }
        });
    }

}
