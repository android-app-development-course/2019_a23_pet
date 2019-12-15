package com.example.gohome.Member.Fragment;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.gohome.Entity.AdoptAppliment;
import com.example.gohome.Entity.HelpAppliment;
import com.example.gohome.Entity.ResponseAdoptAppliment;
import com.example.gohome.Entity.ResponseHelpAppliment;
import com.example.gohome.Member.Adapter.MemberCheckUndoFoldingCellAdapter;
import com.example.gohome.R;
import com.google.gson.Gson;
import com.jcodecraeer.xrecyclerview.ProgressStyle;
import com.jcodecraeer.xrecyclerview.XRecyclerView;
import com.nightonke.boommenu.BoomButtons.HamButton;
import com.nightonke.boommenu.BoomButtons.OnBMClickListener;
import com.nightonke.boommenu.BoomMenuButton;
import com.nightonke.boommenu.ButtonEnum;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class MemberCheckUndoFragment extends Fragment {

    //记录提交结果
    public static final int SUCCESS = 1;
    public static final int FAIL = 0;
    public static final int ZERO = 2;//记录请求回来的数据条数是否为零


    //记录当前页码与每次请求的数量
    private int curPageNumAdopt = 1; //默认页码数为第一页
    private int curPageNumHelp = 1; //默认页码数为第一页

    public static final int PAGE_SIZE = 5;

    XRecyclerView xrv_memberCheckUndo;

    final int itemLimit = 5;  //限制最多显示5条记录

    private MemberCheckUndoFoldingCellAdapter memberCheckUndoFoldingCellAdapter;
    //加载的信息列表
    private List<ResponseAdoptAppliment.responseAdoptAppliment> adoptApplimentList = new ArrayList<>();
    private List<ResponseHelpAppliment.responseHelpAppliment> helpApplimentList = new ArrayList<>();

    private int type = 0;    //设置recyclerView显示的信息类型，0为领养信息，1为救助信息

    BoomMenuButton bmb;




    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState ){
        View view = inflater.inflate(R.layout.fragment_member_check_undo,null);
        ButterKnife.bind(this, view);

        xrv_memberCheckUndo = view.findViewById(R.id.xrv_memberCheckUndo);
        bmb = view.findViewById(R.id.bmb_memberCheckUndo);

        init();
        return view;
    }


    private void init(){

        //初始化模拟数据
        initAdoptAppliment();
        initHelpAppliment();

        //管理器
        LinearLayoutManager layoutManager = new LinearLayoutManager(this.getContext());
        //适配器

        if(type == 0 ){
            memberCheckUndoFoldingCellAdapter = new MemberCheckUndoFoldingCellAdapter(this.getContext(), adoptApplimentList,type);
        }else{
            memberCheckUndoFoldingCellAdapter = new MemberCheckUndoFoldingCellAdapter(this.getContext(), helpApplimentList,type);
        }


        memberCheckUndoFoldingCellAdapter.setClickCallBack(new MemberCheckUndoFoldingCellAdapter.ItemClickCallBack() {
            @Override
            public void onItemClick(int pos) {
                System.out.println("点击了第："+pos+"个item");
            }
        });

        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        xrv_memberCheckUndo.setAdapter(memberCheckUndoFoldingCellAdapter);

        xrv_memberCheckUndo.setLayoutManager(layoutManager);

        xrv_memberCheckUndo.setRefreshProgressStyle(ProgressStyle.BallSpinFadeLoader);
        xrv_memberCheckUndo.setLoadingMoreProgressStyle(ProgressStyle.BallRotate);
        xrv_memberCheckUndo.setArrowImageView(R.drawable.iconfont_downgrey);

        xrv_memberCheckUndo
                .getDefaultRefreshHeaderView()
                .setRefreshTimeVisible(true);

        xrv_memberCheckUndo.setClipChildren(false);
        xrv_memberCheckUndo.setClipToPadding(false);



        xrv_memberCheckUndo.setLoadingListener(new XRecyclerView.LoadingListener() {
            //下拉刷新
            @Override
            public void onRefresh() {

                //创建Handler，在子线程中使用handler发message给主线程
                @SuppressLint("HandlerLeak") Handler pullDownHandle = new Handler() {
                    @Override
                    public void handleMessage(Message msg) {
                        switch (msg.what) {
                            case SUCCESS: {
                                //加载完成
                                memberCheckUndoFoldingCellAdapter.notifyDataSetChanged(); //更新列表
                                if(xrv_memberCheckUndo != null)
                                    xrv_memberCheckUndo.refreshComplete();
                                System.out.println("成功啦啦啦啦啦！！");
                                break;
                            }
                            case FAIL:
                            {
                                Toast.makeText(getContext(),"刷新失败！",Toast.LENGTH_LONG).show();
                                break;
                            }
                            case ZERO:
                            {
                                Toast.makeText(getContext(),"没有更多数据啦！",Toast.LENGTH_LONG).show();
                                xrv_memberCheckUndo.setNoMore(true);
                                memberCheckUndoFoldingCellAdapter.notifyDataSetChanged();
                                break;
                            }

                        }
                    }
                };

                //新建线程，下拉刷新，请求第一页，同时请求adoptappliment和helpappliment的信息
                new Thread(
                        new Runnable() {
                            @Override
                            public void run() {
                                //建立client
                                final OkHttpClient[] client = {new OkHttpClient()};

                                //请求
                                Request requestAdopt=new Request.Builder()
                                        .url(getResources().getString(R.string.serverBasePath)+getResources().getString(R.string.queryAdoptApplimentByState)+ "/?state=0&pageSize="+PAGE_SIZE+"&pageNum=1")
                                        .get()
                                        .build();
                                //新建call联结client和request
                                Call callAdopt= client[0].newCall(requestAdopt);
                                //新建Message通过Handle与主线程通信
                                Message msgAdopt = new Message();
                                callAdopt.enqueue(new Callback() {
                                    @Override
                                    public void onFailure(Call call, IOException e) {
                                        //请求失败的处理
                                        Log.i("RESPONSE:","fail"+e.getMessage());
                                        msgAdopt.what = FAIL;
                                        pullDownHandle.sendMessage(msgAdopt);
                                        Log.i("result的值", String.valueOf(false));
                                    }
                                    @Override
                                    public void onResponse(Call call, Response response) throws IOException {

                                        //GSON直接解析成对象
                                        //注意，这个response.body().string()好像只能解析一次
                                        ResponseAdoptAppliment responseAdoptAppliment = new Gson().fromJson(response.body().string(), ResponseAdoptAppliment.class);
                                        //对象中拿到集合
                                        List<ResponseAdoptAppliment.responseAdoptAppliment> responseAdoptApplimentList = responseAdoptAppliment.getResponseAdoptApplimentList();
                                        //将后端的返回值保存到前端的实体类中，在handle中实现列表更新UI效果
                                        adoptApplimentList.clear();
                                        for(ResponseAdoptAppliment.responseAdoptAppliment i:responseAdoptApplimentList){
                                            adoptApplimentList.add(i);
                                        }
                                        System.out.println("list:"+ responseAdoptAppliment);
                                        if(responseAdoptAppliment.getPageSize() == 0){
                                            msgAdopt.what = ZERO;
                                        }else{
                                            msgAdopt.what = SUCCESS;
                                        }
                                        pullDownHandle.sendMessage(msgAdopt);
                                        Log.i("result的值", String.valueOf(true));
                                    }

                                });

                                //请求
                                Request requestHelp=new Request.Builder()
                                        .url(getResources().getString(R.string.serverBasePath)+getResources().getString(R.string.queryHelpApplimentByState)+ "/?state=0&pageSize="+PAGE_SIZE+"&pageNum=1")
                                        .get()
                                        .build();
                                //新建call联结client和request
                                Call callHelp= client[0].newCall(requestHelp);
                                //新建Message通过Handle与主线程通信
                                Message msgHelp = new Message();
                                callHelp.enqueue(new Callback() {
                                    @Override
                                    public void onFailure(Call call, IOException e) {
                                        //请求失败的处理
                                        Log.i("RESPONSE:","fail"+e.getMessage());
                                        msgHelp.what = FAIL;
                                        pullDownHandle.sendMessage(msgHelp);
                                        Log.i("result的值", String.valueOf(false));
                                    }
                                    @Override
                                    public void onResponse(Call call, Response response) throws IOException {

                                        //GSON直接解析成对象
                                        //注意，这个response.body().string()好像只能解析一次
                                        ResponseHelpAppliment responseHelpAppliment = new Gson().fromJson(response.body().string(), ResponseHelpAppliment.class);
                                        //对象中拿到集合
                                        List<ResponseHelpAppliment.responseHelpAppliment> responseHelpApplimentList = responseHelpAppliment.getResponseHelpApplimentList();
                                        //将后端的返回值保存到前端的实体类中，在handle中实现列表更新UI效果
                                        helpApplimentList.clear();
                                        for(ResponseHelpAppliment.responseHelpAppliment i:responseHelpApplimentList){
                                            helpApplimentList.add(i);
                                        }

                                        System.out.println("list:"+ responseHelpAppliment);
                                        if(responseHelpAppliment.getPageSize() == 0){
                                            msgHelp.what = ZERO;
                                        }else{
                                            msgHelp.what = SUCCESS;
                                        }
                                        pullDownHandle.sendMessage(msgHelp);
                                        Log.i("result的值", String.valueOf(true));
                                    }

                                });
                            }
                        }).start();

            }

            //上拉加载
            @Override
            public void onLoadMore() {

                //创建Handler，在子线程中使用handler发message给主线程
                @SuppressLint("HandlerLeak") Handler loadMoreHandle = new Handler() {
                    @Override
                    public void handleMessage(Message msg) {
                        switch (msg.what) {
                            case SUCCESS: {
                                //加载完成
                                memberCheckUndoFoldingCellAdapter.notifyDataSetChanged(); //更新列表
                                if(xrv_memberCheckUndo != null)
                                    xrv_memberCheckUndo.refreshComplete();
                                System.out.println("成功啦啦啦啦啦！！");
                                break;
                            }
                            case FAIL:
                            {
                                Toast.makeText(getContext(),"刷新失败！",Toast.LENGTH_LONG).show();
                                break;
                            }
                            case ZERO:
                            {
                                Toast.makeText(getContext(),"没有更多数据啦！",Toast.LENGTH_LONG).show();
                                xrv_memberCheckUndo.setNoMore(true);
                                memberCheckUndoFoldingCellAdapter.notifyDataSetChanged();
                                break;
                            }


                        }
                    }
                };

                //新建线程，上拉加载
                new Thread(
                        new Runnable() {
                            @Override
                            public void run() {
                                //建立client
                                final OkHttpClient[] client = {new OkHttpClient()};
                                System.out.println("type:"+type);
                                //类型为0，请求领养申请信息
                                if(type == 0){
                                    //请求
                                    Request request=new Request.Builder()
                                            .url(getResources().getString(R.string.serverBasePath)+getResources().getString(R.string.queryAdoptApplimentByState)+ "/?state=0&pageSize="+PAGE_SIZE+"&pageNum="+ (curPageNumAdopt+1))
                                            .get()
                                            .build();
                                    //新建call联结client和request
                                    Call call= client[0].newCall(request);
                                    //新建Message通过Handle与主线程通信
                                    Message msg = new Message();
                                    call.enqueue(new Callback() {
                                        @Override
                                        public void onFailure(Call call, IOException e) {
                                            //请求失败的处理
                                            Log.i("RESPONSE:","fail"+e.getMessage());
                                            msg.what = FAIL;
                                            loadMoreHandle.sendMessage(msg);
                                            Log.i("result的值", String.valueOf(false));
                                        }
                                        @Override
                                        public void onResponse(Call call, Response response) throws IOException {

                                            //GSON直接解析成对象
                                            //注意，这个response.body().string()好像只能解析一次
                                            ResponseAdoptAppliment responseAdoptAppliment = new Gson().fromJson(response.body().string(), ResponseAdoptAppliment.class);
                                            //对象中拿到集合
                                            List<ResponseAdoptAppliment.responseAdoptAppliment> responseAdoptApplimentList = responseAdoptAppliment.getResponseAdoptApplimentList();

                                            System.out.println("list:"+ responseAdoptAppliment);
                                            System.out.println("curPageNum:"+ curPageNumAdopt);
                                            System.out.println("number:"+((curPageNumAdopt-1) * 5 + responseAdoptAppliment.getPageSize()));

                                            if((curPageNumAdopt-1)*PAGE_SIZE+responseAdoptAppliment.getPageSize() < responseAdoptAppliment.getTotal()){
                                                msg.what = ZERO;  //表示不能再继续发起请求
                                                for(ResponseAdoptAppliment.responseAdoptAppliment i:responseAdoptApplimentList){
                                                    adoptApplimentList.add(i);
                                                }
                                            }else{ //可以继续发请求
                                                curPageNumAdopt++;
                                                //将后端的返回值保存到前端的实体类中，在handle中实现列表更新UI效果
                                                for(ResponseAdoptAppliment.responseAdoptAppliment i:responseAdoptApplimentList){
                                                    adoptApplimentList.add(i);
                                                }
                                                msg.what = SUCCESS;
                                            }
                                            loadMoreHandle.sendMessage(msg);
                                            Log.i("result的值", String.valueOf(true));
                                        }

                                    });

                                }
                                else{  //请求救助申请信息
                                    // 请求
                                    Request request=new Request.Builder()
                                            .url(getResources().getString(R.string.serverBasePath)+getResources().getString(R.string.queryHelpApplimentByState)+ "/?state=0&pageSize="+PAGE_SIZE+"&pageNum="+ (curPageNumHelp+1))
                                            .get()
                                            .build();
                                    //新建call联结client和request
                                    Call call= client[0].newCall(request);
                                    //新建Message通过Handle与主线程通信
                                    Message msg = new Message();
                                    call.enqueue(new Callback() {
                                        @Override
                                        public void onFailure(Call call, IOException e) {
                                            //请求失败的处理
                                            Log.i("RESPONSE:","fail"+e.getMessage());
                                            msg.what = FAIL;
                                            loadMoreHandle.sendMessage(msg);
                                            Log.i("result的值", String.valueOf(false));
                                        }
                                        @Override
                                        public void onResponse(Call call, Response response) throws IOException {

                                            //GSON直接解析成对象
                                            //注意，这个response.body().string()好像只能解析一次
                                            ResponseHelpAppliment responseHelpAppliment = new Gson().fromJson(response.body().string(), ResponseHelpAppliment.class);
                                            //对象中拿到集合
                                            List<ResponseHelpAppliment.responseHelpAppliment> responseHelpApplimentList = responseHelpAppliment.getResponseHelpApplimentList();

                                            System.out.println("list:"+ responseHelpAppliment);
                                            System.out.println("curPageNum:"+ curPageNumHelp);
                                            System.out.println("number:"+((curPageNumHelp-1) * 5 + responseHelpAppliment.getPageSize()));

                                            if((curPageNumHelp-1)*PAGE_SIZE+responseHelpAppliment.getPageSize() < responseHelpAppliment.getTotal()){
                                                msg.what = ZERO;  //表示不能再继续发起请求
                                                for(ResponseHelpAppliment.responseHelpAppliment i:responseHelpApplimentList){
                                                    helpApplimentList.add(i);
                                                }
                                            }else{ //可以继续发请求
                                                curPageNumHelp++;
                                                //将后端的返回值保存到前端的实体类中，在handle中实现列表更新UI效果
                                                for(ResponseHelpAppliment.responseHelpAppliment i:responseHelpApplimentList){
                                                    helpApplimentList.add(i);
                                                }
                                                msg.what = SUCCESS;
                                            }
                                            loadMoreHandle.sendMessage(msg);
                                            Log.i("result的值", String.valueOf(true));
                                        }

                                    });

                                }
                            }
                        }).start();

            }
        });

        //设置子元素类型
        bmb.setButtonEnum(ButtonEnum.Ham);
        //领养信息筛选
        HamButton.Builder adoptBuilder = new HamButton.Builder()
                .normalImageRes(R.drawable.home_selected)
                .subNormalTextRes(R.string.floatMenuAdopt)
                .subTextSize(17)
                .textGravity(Gravity.CENTER)
                .listener(new OnBMClickListener() {
                    @Override
                    public void onBoomButtonClick(int index) {
                        // When the boom-button corresponding this builder is clicked.
                        Toast.makeText(getActivity(), "点击了: " + index, Toast.LENGTH_SHORT).show();
                        type = 0;
                        memberCheckUndoFoldingCellAdapter = new MemberCheckUndoFoldingCellAdapter(getContext(),adoptApplimentList,type);
                        memberCheckUndoFoldingCellAdapter.setClickCallBack(new MemberCheckUndoFoldingCellAdapter.ItemClickCallBack() {
                            @Override
                            public void onItemClick(int pos) {
                                System.out.println("点击了第："+pos+"个item");
                            }
                        });
                        xrv_memberCheckUndo.setAdapter(memberCheckUndoFoldingCellAdapter);
                    }
                })
                .normalColor(getResources().getColor(R.color.yellow));

        //救助信息筛选
        HamButton.Builder helpBuilder = new HamButton.Builder()
                .normalImageRes(R.drawable.help_selected)
                .subNormalTextRes(R.string.floatMenuHelp)
                .subTextSize(17)
                .textGravity(Gravity.CENTER)
                .listener(new OnBMClickListener() {
                    @Override
                    public void onBoomButtonClick(int index) {
                        // When the boom-button corresponding this builder is clicked.
                        Toast.makeText(getActivity(), "点击了: " + index, Toast.LENGTH_SHORT).show();
                        type = 1;
                        memberCheckUndoFoldingCellAdapter = new MemberCheckUndoFoldingCellAdapter(getContext(),helpApplimentList,type);
                        memberCheckUndoFoldingCellAdapter.setClickCallBack(new MemberCheckUndoFoldingCellAdapter.ItemClickCallBack() {
                            @Override
                            public void onItemClick(int pos) {
                                System.out.println("点击了第："+pos+"个item");
                            }
                        });
                        xrv_memberCheckUndo.setAdapter(memberCheckUndoFoldingCellAdapter);
                    }
                })
                .normalColor(getResources().getColor(R.color.yellow)) ;

        bmb.addBuilder(adoptBuilder);
        bmb.addBuilder(helpBuilder);

        bmb.setNormalColor(getResources().getColor(R.color.orange));
        bmb.setHighlightedColor(getResources().getColor(R.color.orange));



    }

    private void initAdoptAppliment() {

        //创建Handler，在子线程中使用handler发message给主线程
        @SuppressLint("HandlerLeak") Handler initHandle = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                switch (msg.what) {
                    case SUCCESS: {
                        //加载完成
                        memberCheckUndoFoldingCellAdapter.notifyDataSetChanged(); //更新列表
                        if(xrv_memberCheckUndo != null)
                            xrv_memberCheckUndo.refreshComplete();
                        System.out.println("成功啦啦啦啦啦！！");
                        break;
                    }
                    case FAIL:
                    {
                        Toast.makeText(getContext(),"获取信息失败！",Toast.LENGTH_LONG).show();
                        break;
                    }
                    case ZERO:
                    {
                        Toast.makeText(getContext(),"没有更多数据啦！",Toast.LENGTH_LONG).show();
                        xrv_memberCheckUndo.setNoMore(true);
                        memberCheckUndoFoldingCellAdapter.notifyDataSetChanged();
                        break;
                    }


                }
            }
        };

        //新建线程，试下下拉刷新，请求第一页，同时请求adoptappliment和helpappliment的信息
        new Thread(
                new Runnable() {
                    @Override
                    public void run() {
                        //建立client
                        final OkHttpClient[] client = {new OkHttpClient()};

                        //请求
                        Request request=new Request.Builder()
                                .url(getResources().getString(R.string.serverBasePath)+getResources().getString(R.string.queryAdoptApplimentByState)+ "/?state=0&pageSize="+PAGE_SIZE+"&pageNum=1")
                                .get()
                                .build();
                        //新建call联结client和request
                        Call call= client[0].newCall(request);
                        //新建Message通过Handle与主线程通信
                        Message msg = new Message();
                        call.enqueue(new Callback() {
                            @Override
                            public void onFailure(Call call, IOException e) {
                                //请求失败的处理
                                Log.i("RESPONSE:","fail"+e.getMessage());
                                msg.what = FAIL;
                                initHandle.sendMessage(msg);
                                Log.i("result的值", String.valueOf(false));
                            }
                            @Override
                            public void onResponse(Call call, Response response) throws IOException {

                                //GSON直接解析成对象
                                //注意，这个response.body().string()好像只能解析一次
                                ResponseAdoptAppliment responseAdoptAppliment = new Gson().fromJson(response.body().string(), ResponseAdoptAppliment.class);
                                //对象中拿到集合
                                List<ResponseAdoptAppliment.responseAdoptAppliment> responseAdoptApplimentList = responseAdoptAppliment.getResponseAdoptApplimentList();
                                //将后端的返回值保存到前端的实体类中，在handle中实现列表更新UI效果
                                adoptApplimentList.clear();
                                for(ResponseAdoptAppliment.responseAdoptAppliment i:responseAdoptApplimentList){
                                    adoptApplimentList.add(i);
                                }
                                if(responseAdoptAppliment.getPageSize() == 0){
                                    msg.what = ZERO;
                                }else{
                                    msg.what = SUCCESS;
                                }
                                initHandle.sendMessage(msg);
                                Log.i("result的值", String.valueOf(true));
                            }

                        });
                    }
                }).start();

    }

    private void initHelpAppliment() {

        //创建Handler，在子线程中使用handler发message给主线程
        @SuppressLint("HandlerLeak") Handler initHandle = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                switch (msg.what) {
                    case SUCCESS: {
                        //加载完成
                        memberCheckUndoFoldingCellAdapter.notifyDataSetChanged(); //更新列表
                        if(xrv_memberCheckUndo != null)
                            xrv_memberCheckUndo.refreshComplete();
                        System.out.println("成功啦啦啦啦啦！！");
                        break;
                    }
                    case FAIL:
                    {
                        Toast.makeText(getContext(),"获取信息失败！",Toast.LENGTH_LONG).show();
                        break;
                    }
                    case ZERO:
                    {
                        Toast.makeText(getContext(),"没有更多数据啦！",Toast.LENGTH_LONG).show();
                        xrv_memberCheckUndo.setNoMore(true);
                        memberCheckUndoFoldingCellAdapter.notifyDataSetChanged();
                        break;
                    }


                }
            }
        };

        //新建线程，下拉刷新，请求第一页，同时请求adoptappliment和helpappliment的信息
        new Thread(
                new Runnable() {
                    @Override
                    public void run() {
                        //建立client
                        final OkHttpClient[] client = {new OkHttpClient()};

                        //请求
                        Request requestHelp=new Request.Builder()
                                .url(getResources().getString(R.string.serverBasePath)+getResources().getString(R.string.queryHelpApplimentByState)+ "/?state=0&pageSize="+PAGE_SIZE+"&pageNum=1")
                                .get()
                                .build();
                        //新建call联结client和request
                        Call callHelp= client[0].newCall(requestHelp);
                        //新建Message通过Handle与主线程通信
                        Message msgHelp = new Message();
                        callHelp.enqueue(new Callback() {
                            @Override
                            public void onFailure(Call call, IOException e) {
                                //请求失败的处理
                                Log.i("RESPONSE:","fail"+e.getMessage());
                                msgHelp.what = FAIL;
                                initHandle.sendMessage(msgHelp);
                                Log.i("result的值", String.valueOf(false));
                            }
                            @Override
                            public void onResponse(Call call, Response response) throws IOException {

                                //GSON直接解析成对象
                                //注意，这个response.body().string()好像只能解析一次
                                ResponseHelpAppliment responseHelpAppliment = new Gson().fromJson(response.body().string(), ResponseHelpAppliment.class);
                                //对象中拿到集合
                                List<ResponseHelpAppliment.responseHelpAppliment> responseHelpApplimentList = responseHelpAppliment.getResponseHelpApplimentList();
                                //将后端的返回值保存到前端的实体类中，在handle中实现列表更新UI效果
                                helpApplimentList.clear();
                                for(ResponseHelpAppliment.responseHelpAppliment i:responseHelpApplimentList){
                                    helpApplimentList.add(i);
                                }
                                if(responseHelpAppliment.getPageSize() == 0){
                                    msgHelp.what = ZERO;
                                }else{
                                    msgHelp.what = SUCCESS;
                                }
                                initHandle.sendMessage(msgHelp);
                                Log.i("result的值", String.valueOf(true));
                            }

                        });
                    }
                }).start();

    }



}

