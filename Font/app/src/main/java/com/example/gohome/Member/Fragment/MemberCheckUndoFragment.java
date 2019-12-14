package com.example.gohome.Member.Fragment;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
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
    private int curPageNum = 1; //默认页码数为第一页
    public static final int PAGE_SIZE = 5;

    XRecyclerView xrv_memberCheckUndo;

    private int times = 0;
    final int itemLimit = 5;  //限制最多显示5条记录

    private MemberCheckUndoFoldingCellAdapter memberCheckUndoFoldingCellAdapter;
    //加载的信息列表
    private List<HelpAppliment> helpApplimentList;
    private List<ResponseAdoptAppliment.responseAdoptAppliment> adoptApplimentList = new ArrayList<>();

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
                times = 0;

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
                                        pullDownHandle.sendMessage(msg);
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
                                            msg.what = ZERO;
                                        }else{
                                            msg.what = SUCCESS;
                                        }
                                        pullDownHandle.sendMessage(msg);
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
                                //类型为0，请求领养申请信息
                                if(type == 0){
                                    //请求
                                    Request request=new Request.Builder()
                                            .url(getResources().getString(R.string.serverBasePath)+getResources().getString(R.string.queryAdoptApplimentByState)+ "/?state=0&pageSize="+PAGE_SIZE+"&pageNum="+ (curPageNum+1))
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
                                            System.out.println("curPageNum:"+ curPageNum);
                                            System.out.println("number:"+((curPageNum-1) * 5 + responseAdoptAppliment.getPageSize()));

                                            if((curPageNum-1)*PAGE_SIZE+responseAdoptAppliment.getPageSize() < responseAdoptAppliment.getTotal()){
                                                msg.what = ZERO;  //表示不能再继续发起请求
                                                for(ResponseAdoptAppliment.responseAdoptAppliment i:responseAdoptApplimentList){
                                                    adoptApplimentList.add(i);
                                                }
                                                System.out.println("faskjdfklasjflk");
                                            }else{ //可以继续发请求
                                                curPageNum++;
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
                .normalColor(getResources().getColor(R.color.yellow)) ;

        //救助信息筛选
        HamButton.Builder helpBuilder = new HamButton.Builder()
                .normalImageRes(R.drawable.help_selected)
                .subNormalTextRes(R.string.floatMenuHelp)
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
                                System.out.println("list:"+ responseAdoptAppliment);
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
        helpApplimentList = new ArrayList<>();
        helpApplimentList.add(new HelpAppliment("2019.11.26 19:09","小红","13136767678","广州市天河区","一一","2岁","狗狗",true,true,false,"很调皮的小狗",R.drawable.member_cat1,1,0,""));
        helpApplimentList.add(new HelpAppliment("2019.11.26 22:08","小黑","13454565434","广州市番禺区","尔尔","4岁","猫猫",true,false,true,"很调皮",R.drawable.member_cat2,1,0,""));
        helpApplimentList.add(new HelpAppliment("2019.11.27 7:22","小百","13126565454","广州市白云区","散散","三个月","小鸟",false,true,false,"很乖",R.drawable.member_cat3,1,0,""));
        helpApplimentList.add(new HelpAppliment("2019.11.27 7:29","小子","13676787888","广州市从化区","思思","一个月","小鸡",true,false,true,"很懒的小狗",R.drawable.member_cat4,1,0,""));
        helpApplimentList.add(new HelpAppliment("2019.11.27 14:00","小吕","13445456656","广州市荔湾区","呜呜","1岁","小羊",true,true,false,"很蠢的小狗",R.drawable.member_cat5,1,0,""));

    }


    //模拟下拉加载数据
    private void helpAdd1(){
        helpApplimentList.add(new HelpAppliment("2019.11.26 14:23","侯先生","13445456545","广州市天河区","溜溜","1岁","小羊",true,true,false,"聪明，可爱",R.drawable.member_cat5,1,0,""));
        helpApplimentList.add(new HelpAppliment("2019.11.26 17:27","邹女士","13234345456","广州市南沙区","嗯哼","1岁","小羊",true,true,false,"活泼好动",R.drawable.member_cat5,1,0,""));
        helpApplimentList.add(new HelpAppliment("2019.11.26 18:01","张先生","13998987654","广州市白云区","图图","1岁","小羊",true,true,false,"不会随地大小便，不会弄坏家里的东西",R.drawable.member_cat5,1,0,""));
        helpApplimentList.add(new HelpAppliment("2019.11.26 18:35","马小姐","13445456656","广州市荔湾区","猪猪","1岁","小羊",true,true,false,"性格乖巧很粘人",R.drawable.member_cat5,1,0,""));

    }


    //模拟下拉加载数据
    private void helpAdd2() {
        helpApplimentList.add(new HelpAppliment("2019.11.26 12:15","吴小姐","13445456576","广州白云区","dodo","一岁左右","狗狗",true,true,false,"笨笨的，但是超可爱",R.drawable.member_dog3,1,0,""));
        helpApplimentList.add(new HelpAppliment("2019.11.26 13:11","小李","13445434543","广州荔湾区","roo","一岁半","狗狗",true,true,false,"笨笨的，但是超可爱",R.drawable.member_dog4,1,0,""));
    }



}

