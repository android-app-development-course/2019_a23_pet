package com.example.gohome.User.Activity;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;

import com.example.gohome.Entity.ProcessInfo;
import com.example.gohome.Entity.ResponseAdoptInfo;
import com.example.gohome.Entity.ResponseProcessInfo;
import com.example.gohome.R;
import com.example.gohome.User.Adapter.ProcessRecyclerViewAdapter;
import com.google.gson.Gson;
import com.jcodecraeer.xrecyclerview.ProgressStyle;
import com.jcodecraeer.xrecyclerview.XRecyclerView;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class UserSendProActivity extends AppCompatActivity {
    private static final int SUCCESS = 1;
    private static final int FAIL = -1;
    private static final int ZERO = 0; //记录请求回来的数据条数是否为零
    private static final int PAGE_SIZE = 5;
    private static int CUR_PAGE_NUM = 1;

    private XRecyclerView recyclerView;
    private ProcessRecyclerViewAdapter adapter;
    private List<ProcessInfo> infoList;

    SharedPreferences sharedPreferences;
    Integer userId;

//    private int[] processState1 = {1, 1, 0};
//    private int[] processState2 = {1, 1, 1};
//    private int[] processState3 = {1, 0, 0};
//    private int[] processState4 = {1, 2, 0};
//    private String[] userText = {"性格乖巧，比较黏人，已驱虫。希望找一个爱它的主人，有责任心不抛弃，接受定期回访。",
//            "有偿领养，疫苗已打，未绝育，有证，定期内外驱虫。性格活泼，能吃好动。希望能找到一个对它好的有爱心有经验的铲屎官。",
//            "要求：有时间陪伴，有病治病，吃安全狗粮，有耐心。基本情况：不在家大小便，必须出去上，不破坏东西，很听话。",
//            "身体健康，做了狂犬和驱虫，性格乖巧粘人。最近在发情期还未绝育，希望你能带去配种或绝育。"};

//    // 记录加载的次数
//    private int times = 0;
//    //记录是否已加载
//    boolean flag1 = true, flag2 = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_adopt_pro);

        setTitle("送养进度");
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setHomeButtonEnabled(true);
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

        sharedPreferences = getSharedPreferences("userInfo", Context.MODE_PRIVATE);
        userId = sharedPreferences.getInt("userId", -1);

        //initPetInfo();
        getData();
        initRecyclerView();
    }

//    private void initPetInfo() {
//        infoList = new ArrayList<>();
//        infoList.add(new ProcessInfo(R.drawable.timg, "一一", userText[0], processState1));
//        infoList.add(new ProcessInfo(R.drawable.cat, "二二", userText[1], processState3));
//        infoList.add(new ProcessInfo(R.drawable.dog, "三三", userText[3], processState4));
//        infoList.add(new ProcessInfo(R.drawable.cat1, "四四", userText[2], processState2));
//        infoList.add(new ProcessInfo(R.drawable.dog1, "五五", userText[0], processState3));
//        infoList.add(new ProcessInfo(R.drawable.cat2, "六六", userText[3], processState4));
//        infoList.add(new ProcessInfo(R.drawable.dog2, "七七", userText[2], processState1));
//    }

    private void getData() {
        infoList = new ArrayList<>();

        @SuppressLint("HandlerLeak")
        Handler handler = new Handler() {
            public void handleMessage(Message message) {
                switch (message.what) {
                    case SUCCESS:
                        Log.i("获取: ", "成功");
                        adapter.setList(infoList);
                        adapter.notifyDataSetChanged();
                        break;

                    case FAIL:
                        Log.i("获取: ", "失败");
                        break;

                    case ZERO:
                        Log.i("获取: ", "0");
                        break;
                }
            }
        };

        new Thread(() -> {
            Request request = new Request.Builder()
                    .url(getResources().getString(R.string.serverBasePath) +
                            getResources().getString(R.string.queryAdoptApplimentByUserId2)
                            + "/?pageNum=1&pageSize=" + PAGE_SIZE + "&userId=" + userId)
                    .get()
                    .build();
            Message msg = new Message();
            OkHttpClient okHttpClient = new OkHttpClient();
            Call call = okHttpClient.newCall(request);
            call.enqueue(new Callback() {
                @Override
                public void onFailure(Call call, IOException e) {
                    Log.i("获取: ", e.getMessage());
                    msg.what = FAIL;
                    handler.sendMessage(msg);
                }

                @Override
                public void onResponse(Call call, Response response) throws IOException {

                    ResponseProcessInfo processInfoMessage = new Gson().fromJson(response.body().string(),
                            ResponseProcessInfo.class);
                    infoList = processInfoMessage.getProcessInfoList();
                    if (infoList.size() == 0) {
                        msg.what = ZERO;
                    } else {
                        msg.what = SUCCESS;
                    }
                    handler.sendMessage(msg);
                    Log.i("获取: ", String.valueOf(infoList.size()));
                }
            });
        }).start();
    }

    private void initRecyclerView() {
        recyclerView = findViewById(R.id.recyclerView);
        adapter = new ProcessRecyclerViewAdapter(infoList, this);
        adapter.getCardViewOnClickListener(view -> {
            Intent intent = new Intent(this, UserSendDetailActivity.class);
            startActivity(intent);
        });
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

        //设置刷新和加载的动作
        recyclerView.setLoadingListener(new XRecyclerView.LoadingListener() {
            @Override
            public void onRefresh() {
                new Handler().postDelayed(() -> {
                    @SuppressLint("HandlerLeak")
                    Handler handler = new Handler() {
                        @Override
                        public void handleMessage(Message msg) {
                            switch (msg.what) {
                                case SUCCESS:
                                    Log.i("刷新", "成功");
                                    adapter.setList(infoList);
                                    adapter.notifyDataSetChanged();
                                    break;
                                case FAIL:
                                    Log.i("刷新", "失败");
                                    break;
                                case ZERO:
                                    Log.i("刷新", "0");
                                    break;
                            }
                            recyclerView.refreshComplete();
                        }
                    };

                    new Thread(() -> {
                        CUR_PAGE_NUM = 1;
                        Request request = new Request.Builder()
                                .url(getResources().getString(R.string.serverBasePath) +
                                        getResources().getString(R.string.queryAdoptApplimentByUserId2)
                                        + "/?pageNum=" + CUR_PAGE_NUM + "&pageSize=" + PAGE_SIZE + "&userId=" + userId)
                                .get()
                                .build();
                        Message msg = new Message();
                        OkHttpClient okHttpClient = new OkHttpClient();
                        Call call = okHttpClient.newCall(request);
                        call.enqueue(new Callback() {
                            @Override
                            public void onFailure(Call call, IOException e) {
                                Log.i("获取: ", e.getMessage());
                                msg.what = FAIL;
                                handler.sendMessage(msg);
                            }

                            @Override
                            public void onResponse(Call call, Response response) throws IOException {

                                ResponseProcessInfo processInfoMessage = new Gson().fromJson(response.body().string(),
                                        ResponseProcessInfo.class);
                                infoList = processInfoMessage.getProcessInfoList();
                                if (infoList.size() == 0) {
                                    msg.what = ZERO;
                                } else {
                                    msg.what = SUCCESS;
                                }
                                handler.sendMessage(msg);
                                Log.i("获取: ", String.valueOf(infoList.size()));
                            }
                        });
                    }).start();
                }, 1500);
            }

            @Override
            public void onLoadMore() {
                new Handler().postDelayed(() -> {
                    @SuppressLint("HandlerLeak")
                    Handler handler = new Handler() {
                        @Override
                        public void handleMessage(Message msg) {
                            switch (msg.what) {
                                case SUCCESS:
                                    Log.i("加载", "成功");
                                    recyclerView.refreshComplete();
                                    adapter.notifyDataSetChanged();
                                    break;
                                case FAIL:
                                    Log.i("加载", "失败");
                                    break;
                                case ZERO:
                                    Log.i("加载", "0");
                                    recyclerView.setNoMore(true);
                                    break;
                            }
                        }
                    };

                    new Thread(() -> {
                        CUR_PAGE_NUM++;
                        Request request = new Request.Builder()
                                .url(getResources().getString(R.string.serverBasePath) +
                                        getResources().getString(R.string.queryAdoptApplimentByUserId2)
                                        + "/?pageNum=" + CUR_PAGE_NUM + "&pageSize=" + PAGE_SIZE + "&userId=" + userId)
                                .get()
                                .build();
                        Message msg = new Message();
                        OkHttpClient okHttpClient = new OkHttpClient();
                        Call call = okHttpClient.newCall(request);
                        call.enqueue(new Callback() {
                            @Override
                            public void onFailure(Call call, IOException e) {
                                Log.i("获取: ", e.getMessage());
                                msg.what = FAIL;
                                handler.sendMessage(msg);
                            }

                            @Override
                            public void onResponse(Call call, Response response) throws IOException {

                                ResponseProcessInfo processInfoMessage = new Gson().fromJson(response.body().string(),
                                        ResponseProcessInfo.class);
                                infoList.addAll(processInfoMessage.getProcessInfoList());
                                if ((CUR_PAGE_NUM - 2) * PAGE_SIZE + processInfoMessage.getPageSize() <
                                        processInfoMessage.getTotal()) {
                                    msg.what = ZERO;
                                } else {
                                    msg.what = SUCCESS;
                                }
                                handler.sendMessage(msg);
                                Log.i("获取: ", String.valueOf(infoList.size()));
                            }
                        });
                    }).start();
                }, 1500);
            }
        });
    }

//    private void old(){
//        recyclerView.setLoadingListener(new XRecyclerView.LoadingListener() {
//            @Override
//            public void onRefresh() {
//                new Handler().postDelayed(new Runnable() {
//                    @Override
//                    public void run() {
//                        recyclerView.refreshComplete();
//                        adapter.notifyDataSetChanged();
//                    }
//                }, 1500);
//            }
//
//            @Override
//            public void onLoadMore() {
//                if (times == 0) {
//                    new Handler().postDelayed(() -> {
//                        add1();
//                        recyclerView.loadMoreComplete();
//                        adapter.notifyDataSetChanged();
//                    }, 1000);
//                } else if (times == 1) {
//                    new Handler().postDelayed(() -> {
//                        add2();
//                        recyclerView.loadMoreComplete();
//                        adapter.notifyDataSetChanged();
//                    }, 1000);
//                } else {
//                    new Handler().postDelayed(() -> {
//                        recyclerView.loadMoreComplete();
//                        recyclerView.setNoMore(true);
//                        adapter.notifyDataSetChanged();
//                    }, 1000);
//                }
//                times++;
//            }
//        });
//    }
//    private void add1() {
//        if (flag1) {
//            infoList.add(new ProcessInfo(R.drawable.timg, "大大", userText[0], processState1));
//            infoList.add(new ProcessInfo(R.drawable.dog3, "小小", userText[2], processState2));
//            infoList.add(new ProcessInfo(R.drawable.cat3, "开开", userText[3], processState3));
//            infoList.add(new ProcessInfo(R.drawable.dog4, "明明", userText[1], processState1));
//            infoList.add(new ProcessInfo(R.drawable.cat4, "白白", userText[0], processState4));
//            flag1 = false;
//        }
//    }
//    private void add2() {
//        if (flag2) {
//            infoList.add(new ProcessInfo(R.drawable.cat5, "九九", userText[0], processState1));
//            infoList.add(new ProcessInfo(R.drawable.dog5, "十十", userText[2], processState2));
//            infoList.add(new ProcessInfo(R.drawable.cat6, "佳佳", userText[3], processState3));
//            infoList.add(new ProcessInfo(R.drawable.dog6, "依依", userText[1], processState1));
//            infoList.add(new ProcessInfo(R.drawable.cat7, "冰冰", userText[0], processState4));
//            flag2 = false;
//        }
//    }

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