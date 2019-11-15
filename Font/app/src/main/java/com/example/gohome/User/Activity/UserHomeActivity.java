package com.example.gohome.User.Activity;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.example.gohome.Component.NoScrollViewPager;
import com.example.gohome.R;
import com.example.gohome.User.Adapter.ViewPagerAdapter;
import com.example.gohome.User.BottomLayout;
import com.example.gohome.User.OpenMenu;
import com.qlh.dropdownmenu.DropDownMenu;
import com.qlh.dropdownmenu.view.MultiMenusView;
import com.qlh.dropdownmenu.view.SingleMenuView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

public class UserHomeActivity extends AppCompatActivity implements View.OnClickListener {
    private NoScrollViewPager viewPager;

    private RelativeLayout container;
    private OpenMenu openMenu;
    private BottomLayout btm_home, btm_mine;

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_home);

        Objects.requireNonNull(getSupportActionBar()).hide();
        //状态栏透明
//        getWindow().getDecorView().setSystemUiVisibility(
//                View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN |
//                        View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
        getWindow().setStatusBarColor(getResources().getColor(R.color.yellow));

        initView();

    }

    private void initView() {
        viewPager = findViewById(R.id.user_home_vp);

        // 设置viewPager不可左右滑动
        viewPager.setScanScroll(false);

        // 创建ViewPager适配器
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());

        // 给ViewPager设置适配器
        viewPager.setAdapter(adapter);

        viewPager.setCurrentItem(0);

//        initNav(); // 初始化底部栏

        initBottom();
    }

    private void initBottom() {
        container = findViewById(R.id.rel_container);
        openMenu = new OpenMenu(this);
        openMenu.init(container);

        LinearLayout btm_more = findViewById(R.id.btm_more);
        btm_more.setOnClickListener(this);

        btm_home = findViewById(R.id.btm_home);
        btm_home.setNormalIcon(R.drawable.user_home_normal);
        btm_home.setFocusIcon(R.drawable.user_home);
        btm_home.setIconText("首页");
        btm_home.setFocused(true);
        btm_home.setOnClickListener(this);

        btm_mine = findViewById(R.id.btm_mine);
        btm_mine.setNormalIcon(R.drawable.user_me_normal);
        btm_mine.setFocusIcon(R.drawable.user_me);
        btm_mine.setIconText("我的");
        btm_mine.setFocused(false);
        btm_mine.setOnClickListener(this);
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btm_home:
                btm_home.setFocused(true);
                btm_mine.setFocused(false);
                viewPager.setCurrentItem(0);
                break;
            case R.id.btm_more:
                openMenu.show(container);
                break;
            case R.id.btm_mine:
                btm_home.setFocused(false);
                btm_mine.setFocused(true);
                viewPager.setCurrentItem(2);
                break;
        }
    }

}
