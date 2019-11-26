package com.example.gohome.User.Activity;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.example.gohome.Component.NoScrollViewPager;
import com.example.gohome.R;
import com.example.gohome.User.Adapter.ViewPagerAdapter;
import com.example.gohome.User.ButtonLayout;
import com.example.gohome.User.PopupMenu;

import java.util.Objects;

public class UserHomeActivity extends AppCompatActivity implements View.OnClickListener {
    private NoScrollViewPager viewPager;

    private RelativeLayout container;
    private PopupMenu popupMenu;
    private ButtonLayout btm_home, btm_mine;

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

        // 初始化底部栏
        initBottom();
    }

    private void initBottom() {
        container = findViewById(R.id.rel_container);
        popupMenu = new PopupMenu(this);
        popupMenu.init(container);

        LinearLayout btm_more = findViewById(R.id.btm_more);
        btm_more.setOnClickListener(this);

        btm_home = findViewById(R.id.btm_home);
        btm_home.setNormalIcon(R.drawable.home);
        btm_home.setFocusIcon(R.drawable.home_selected);
        btm_home.setIconText("首页");
        btm_home.setFocused(true);
        btm_home.setOnClickListener(this);

        btm_mine = findViewById(R.id.btm_mine);
        btm_mine.setNormalIcon(R.drawable.mine);
        btm_mine.setFocusIcon(R.drawable.mine_selected);
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
                popupMenu.show();
                break;
            case R.id.btm_mine:
                btm_home.setFocused(false);
                btm_mine.setFocused(true);
                viewPager.setCurrentItem(2);
                break;
        }
    }

}
