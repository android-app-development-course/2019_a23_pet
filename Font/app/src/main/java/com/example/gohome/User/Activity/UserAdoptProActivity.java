package com.example.gohome.User.Activity;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.os.Bundle;
import android.os.Handler;
import android.view.MenuItem;
import android.view.WindowManager;

import com.example.gohome.Entity.ProcessInfo;
import com.example.gohome.R;
import com.example.gohome.User.Adapter.ProcessRecyclerViewAdapter;
import com.jcodecraeer.xrecyclerview.ProgressStyle;
import com.jcodecraeer.xrecyclerview.XRecyclerView;

import java.util.ArrayList;
import java.util.List;

public class UserAdoptProActivity extends AppCompatActivity {
    private XRecyclerView recyclerView;
    private ProcessRecyclerViewAdapter adapter;
    private List<ProcessInfo> infoList;
    private int[] processState = {1, 1, 2};
    // 记录加载的次数
    private int times = 0;
    //记录是否已加载
    boolean flag1 = true, flag2 = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_adopt_pro);

        setTitle("领养进度");
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setHomeButtonEnabled(true);
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

        initPetInfo();
        initRecyclerView();
    }

    private void initPetInfo() {
        infoList = new ArrayList<>();
        infoList.add(new ProcessInfo(R.drawable.timg, "一一", "春江潮水连海平，海上明月共潮生。滟滟随波千万里，何处春江无月明！", processState));
        infoList.add(new ProcessInfo(R.drawable.cat, "二二", "江流宛转绕芳甸，月照花林皆似霰；空里流霜不觉飞，汀上白沙看不见。", processState));
        infoList.add(new ProcessInfo(R.drawable.dog, "三三", "江天一色无纤尘，皎皎空中孤月轮。江畔何人初见月？江月何年初照人？", processState));
        infoList.add(new ProcessInfo(R.drawable.cat1, "四四", "人生代代无穷已，江月年年望相似。不知江月待何人，但见长江送流水。", processState));
        infoList.add(new ProcessInfo(R.drawable.dog1, "五五", "白云一片去悠悠，青枫浦上不胜愁。谁家今夜扁舟子？何处相思明月楼？", processState));
        infoList.add(new ProcessInfo(R.drawable.cat2, "六六", "可怜楼上月徘徊，应照离人妆镜台。玉户帘中卷不去，捣衣砧上拂还来。", processState));
        infoList.add(new ProcessInfo(R.drawable.dog2, "七七", "此时相望不相闻，愿逐月华流照君。鸿雁长飞光不度，鱼龙潜跃水成文。", processState));
    }

    private void initRecyclerView() {
        recyclerView = findViewById(R.id.recyclerView);
        adapter = new ProcessRecyclerViewAdapter(infoList, this);
        recyclerView.setAdapter(adapter);

        recyclerView.setHasFixedSize(true);
        //设置列表默认动画效果
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        //设置列表显示方式
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        //设置下拉刷新和上拉加载
        recyclerView.setArrowImageView(R.drawable.iconfont_downgrey);
        recyclerView.setRefreshProgressStyle(ProgressStyle.BallSpinFadeLoader);
        recyclerView.getDefaultRefreshHeaderView().setRefreshTimeVisible(true);
        recyclerView.setLoadingMoreProgressStyle(ProgressStyle.BallRotate);

        //设置加载的动作
        recyclerView.setLoadingListener(new XRecyclerView.LoadingListener() {
            @Override
            public void onRefresh() {
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        recyclerView.refreshComplete();
                        adapter.notifyDataSetChanged();
                    }
                }, 1500);
            }

            @Override
            public void onLoadMore() {
                if (times == 0) {
                    new Handler().postDelayed(() -> {
                        add1();
                        recyclerView.loadMoreComplete();
                        adapter.notifyDataSetChanged();
                    }, 1000);
                } else if (times == 1) {
                    new Handler().postDelayed(() -> {
                        add2();
                        recyclerView.loadMoreComplete();
                        adapter.notifyDataSetChanged();
                    }, 1000);
                } else {
                    new Handler().postDelayed(() -> {
                        recyclerView.loadMoreComplete();
                        recyclerView.setNoMore(true);
                        adapter.notifyDataSetChanged();
                    }, 1000);
                }
                times++;
            }
        });
    }

    private void add1() {
        if (flag1) {
            infoList.add(new ProcessInfo(R.drawable.cat3, "九九", "昨夜闲潭梦落花，可怜春半不还家。江水流春去欲尽，江潭落月复西斜。", processState));
            infoList.add(new ProcessInfo(R.drawable.dog3, "十十", "斜月沉沉藏海雾，碣石潇湘无限路。不知乘月几人归，落月摇情满江树。", processState));
            infoList.add(new ProcessInfo(R.drawable.cat4, "佳佳", "汉家烟尘在东北，汉将辞家破残贼。男儿本自重横行，天子非常赐颜色。", processState));
            infoList.add(new ProcessInfo(R.drawable.dog4, "依依", "摐金伐鼓下榆关，旌旆逶迤碣石间。校尉羽书飞瀚海，单于猎火照狼山。", processState));
            infoList.add(new ProcessInfo(R.drawable.cat5, "冰冰", "山川萧条极边土，胡骑凭陵杂风雨。战士军前半死生，美人帐下犹歌舞。", processState));
            flag1 = false;
        }
    }

    private void add2() {
        if (flag2) {
            infoList.add(new ProcessInfo(R.drawable.dog5, "圆圆", "大漠穷秋塞草腓，孤城落日斗兵稀。身当恩遇恒轻敌，力尽关山未解围。", processState));
            infoList.add(new ProcessInfo(R.drawable.cat6, "扁扁", "铁衣远戍辛勤久，玉箸应啼别离后。少妇城南欲断肠，征人蓟北空回首。", processState));
            infoList.add(new ProcessInfo(R.drawable.dog6, "哼哈", "边庭飘飖哪可度，绝域苍茫更何有？杀气三时作阵云，寒声一夜催刁斗。", processState));
            infoList.add(new ProcessInfo(R.drawable.cat7, "中分", "相看白刃血纷纷，死节从来岂顾勋。君不见沙场征战苦，至今犹忆李将军。", processState));
            flag2 = false;
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                this.finish(); // user_back button
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}