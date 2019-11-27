package com.example.gohome.Organizer;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.os.Bundle;
import android.widget.Toast;

import com.aurelhubert.ahbottomnavigation.AHBottomNavigation;
import com.aurelhubert.ahbottomnavigation.AHBottomNavigationItem;
import com.example.gohome.Organizer.Adapter.OrganizerMainViewPagerAdapter;
import com.example.gohome.R;

public class OrganizerMain extends AppCompatActivity {

    private AHBottomNavigation bottomNavigation;
    private OrganizerViewPager viewPager;
    public Boolean isLogin=true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_organizer_main);
        init();
        //初始化控件
    }

    private void init(){
        //绑定控件
        viewPager = findViewById(R.id.vp_home);
        bottomNavigation = findViewById(R.id.btmNav_mem);

        //设置设配器
        OrganizerMainViewPagerAdapter adapter = new OrganizerMainViewPagerAdapter(getSupportFragmentManager());
        viewPager.setAdapter(adapter);
        viewPager.setCurrentItem(0);
        viewPager.setScanScroll(false);

        //创建items，3个参数分别是item的文字，item的icon，选中item时的整体颜色（该项需要开启）
        AHBottomNavigationItem item1 = new AHBottomNavigationItem("审核", R.drawable.check_selected, R.color.colorPrimary);
        AHBottomNavigationItem item2 = new AHBottomNavigationItem("加入审核", R.drawable.group_selected, R.color.colorPrimary);
        AHBottomNavigationItem item3 = new AHBottomNavigationItem("发布领养", R.drawable.edit_selected, R.color.colorPrimary);
        AHBottomNavigationItem item4 = new AHBottomNavigationItem("组员", R.drawable.member_selected, R.color.colorPrimary);
        AHBottomNavigationItem item5 = new AHBottomNavigationItem("我的", R.drawable.mine_selected, R.color.colorPrimary);

        // Add items
        bottomNavigation.addItem(item1);
        bottomNavigation.addItem(item2);
        bottomNavigation.addItem(item3);
        bottomNavigation.addItem(item4);
        bottomNavigation.addItem(item5);

        //设置整体背景颜色（如果开启了单个的背景颜色，该项将会无效）
        bottomNavigation.setDefaultBackgroundColor(Color.parseColor("#FFFFFF"));

        //设置item被选中和待选时的颜色
        bottomNavigation.setAccentColor(Color.parseColor("#98F5FF"));
        bottomNavigation.setInactiveColor(Color.parseColor("#ffffff"));

        //是否开启切换item切换颜色
        bottomNavigation.setColored(true);

        //设置初始选中的item
        bottomNavigation.setCurrentItem(0);

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
                System.out.println("onTabSelected position: " + position);
                viewPager.setCurrentItem(position);
                return true;
            }
        });
        bottomNavigation.setOnNavigationPositionListener(new AHBottomNavigation.OnNavigationPositionListener() {
            @Override
            public void onPositionChange(int y) {

            }
        });
    }

}
