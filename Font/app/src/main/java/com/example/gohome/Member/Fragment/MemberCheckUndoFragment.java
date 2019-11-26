package com.example.gohome.Member.Fragment;

import android.os.Bundle;
import android.os.Handler;
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
import com.example.gohome.Member.Adapter.MemberCheckUndoFoldingCellAdapter;
import com.example.gohome.Member.Adapter.MemberCheckUndoRecyclerViewAdapter;
import com.example.gohome.R;
import com.jcodecraeer.xrecyclerview.ProgressStyle;
import com.jcodecraeer.xrecyclerview.XRecyclerView;
import com.nightonke.boommenu.BoomButtons.HamButton;
import com.nightonke.boommenu.BoomButtons.OnBMClickListener;
import com.nightonke.boommenu.BoomMenuButton;
import com.nightonke.boommenu.ButtonEnum;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import butterknife.ButterKnife;

public class MemberCheckUndoFragment extends Fragment {


    XRecyclerView xrv_memberCheckUndo;
//    MemberCheckUndoRecyclerViewAdapter memberCheckUndoRecyclerViewAdapter;

    private int refreshTime = 0;
    private int times = 0;
    final int itemLimit = 5;  //限制最多显示5条记录

    private MemberCheckUndoFoldingCellAdapter memberCheckUndoFoldingCellAdapter;
    //模拟加载的信息
    private List<AdoptAppliment> adoptApplimentList;
    private List<HelpAppliment> helpApplimentList;


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
                refreshTime ++;
                times = 0;
                new Handler().postDelayed(new Runnable(){
                    public void run() {
//                        infoList.clear();
                        //模拟加载后的数据
//                        for(int i = 0; i < itemLimit ;i++){
//                            infoList.user_add("item" + i + "after " + refreshTime + " times of refresh");
//                        }
                        //加载完成
                        memberCheckUndoFoldingCellAdapter.notifyDataSetChanged(); //更新列表
                        if(xrv_memberCheckUndo != null)
                            xrv_memberCheckUndo.refreshComplete();
                    }

                }, 1000);            //refresh data here
            }

            //上拉加载
            @Override
            public void onLoadMore() {
                Log.e("aaaaa","call onLoadMore");
                if(times < 2){  //模拟上拉加载三次
                    new Handler().postDelayed(new Runnable(){
                        public void run() {
                            //第一次上拉
                                if (times == 0){
                                    add1();
                                    memberCheckUndoFoldingCellAdapter.notifyDataSetChanged();
                                    xrv_memberCheckUndo.loadMoreComplete();
                                }
                                if(times == 1){
                                    add2();
                                    memberCheckUndoFoldingCellAdapter.notifyDataSetChanged();
                                    xrv_memberCheckUndo.loadMoreComplete();
                                }
//                            //模拟上拉加载的数据
//                            for(int i = 0; i < itemLimit ;i++){
//                                infoList.user_add("item" + (1 + infoList.size() ) );
//                            }

                            //显示加载完成
//                            if(xrv_memberCheckUndo != null) {
//                                xrv_memberCheckUndo.loadMoreComplete();
//                                memberCheckUndoFoldingCellAdapter.notifyDataSetChanged();
//                            }
                        }
                    }, 1000);
                } else {  //三次加载完成
                    new Handler().postDelayed(new Runnable() {
                        public void run() {
                            //模拟上拉加载的数据

//                            for(int i = 0; i < itemLimit ;i++){
//                                infoList.user_add("item" + (1 + infoList.size() ) );
//                            }
                            //显示没有更多数据了
                            if(xrv_memberCheckUndo != null) {
                                xrv_memberCheckUndo.setNoMore(true);
                                memberCheckUndoFoldingCellAdapter.notifyDataSetChanged();
                            }
                        }
                    }, 1000);
                }
                times ++;
            }
        });

        //设置子元素类型
        bmb.setButtonEnum(ButtonEnum.Ham);
        //领养信息筛选
        HamButton.Builder adoptBuilder = new HamButton.Builder()
                .normalImageRes(R.drawable.home)
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
                .normalImageRes(R.drawable.help)
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
        adoptApplimentList = new ArrayList<>();
        adoptApplimentList.add(new AdoptAppliment("小小","13445456576","广州市天河区","想领养","一一","2岁","狗狗",true,true,false,R.drawable.timg, new Date(2019,12,11),"学生",1,0,""));
        adoptApplimentList.add(new AdoptAppliment("大大","13124545434","星河楼","领养","二二","1岁半","猫猫",false,false,true,R.drawable.cat2,new Date(2019,12,11),"职员",2,0,""));
        adoptApplimentList.add(new AdoptAppliment("种种","13456565444","西一宿舍","领养一只","三三","1岁","狗狗",true,true,true,R.drawable.cat1,new Date(2019,12,11),"老师",3,0,""));
        adoptApplimentList.add(new AdoptAppliment("呵呵","13456567876","越秀区","领养两只","四四","11个月", "猫猫",false,true,true,R.drawable.cat3,new Date(2019,12,11),"程序员",4,0,""));
        adoptApplimentList.add(new AdoptAppliment("一会吧","13556765434","校区","领养领养领养领养领养领养","五五","5个月","猫猫",false,true,false,R.drawable.cat3,new Date(2019,12,11),"老师",5,0,""));
        adoptApplimentList.add(new AdoptAppliment("KIKI","1567656543","番禺区","想领养","六六","8个月","狗狗",true,true,false,R.drawable.cat,new Date(2019,12,11),"学生",6,0,""));
        adoptApplimentList.add(new AdoptAppliment("妮妮","14565678765","白云区","领养","七七","1岁7个月","猫猫",true,true,false,R.drawable.dog1,new Date(2019,12,11),"老师",7,0,""));
    }

    private void initHelpAppliment() {
        helpApplimentList = new ArrayList<>();
        helpApplimentList.add(new HelpAppliment(new Date(2019,11,11),"小红","13136767678","广州市天河区","一一","2岁","狗狗",true,true,false,"很调皮的小狗",R.drawable.timg,1,0,""));
        helpApplimentList.add(new HelpAppliment(new Date(2019,11,11),"小黑","13454565434","广州市番禺区","尔尔","4岁","猫猫",true,false,true,"很调皮",R.drawable.cat2,1,0,""));
        helpApplimentList.add(new HelpAppliment(new Date(2019,11,11),"小百","13126565454","广州市白云区","散散","三个月","小鸟",false,true,false,"很乖",R.drawable.timg,1,0,""));
        helpApplimentList.add(new HelpAppliment(new Date(2019,11,11),"小子","13676787888","广州市从化区","思思","一个月","小鸡",true,false,true,"很懒的小狗",R.drawable.timg,1,0,""));
        helpApplimentList.add(new HelpAppliment(new Date(2019,11,11),"小吕","13445456656","广州市荔湾区","呜呜","1岁","小羊",true,true,false,"很蠢的小狗",R.drawable.timg,1,0,""));
        helpApplimentList.add(new HelpAppliment(new Date(2019,11,11),"小懒","13009098987","广州市天河区","六六","3.5岁","小猪",false,false,true,"很聪明的小狗",R.drawable.timg,1,0,""));

    }


    //模拟下拉加载数据
    private void add1(){
        adoptApplimentList.add(new AdoptAppliment("啦啦","13445456576","广州市天河区","找领养","看看","3岁","狗狗",true,true,true,R.drawable.dog5, new Date(2019,12,11),"学生",1,0,""));
        adoptApplimentList.add(new AdoptAppliment("比比","13445456545","广州市白云区","找领养","嘻嘻","3岁","狗狗",true,false,false,R.drawable.dog1, new Date(2019,12,11),"学生",1,0,""));
        adoptApplimentList.add(new AdoptAppliment("囖咯","13998987654","广州市番禺区","想领养","玉玉","1岁","猫猫",false,true,false,R.drawable.dog3, new Date(2019,12,11),"学生",1,0,""));
        adoptApplimentList.add(new AdoptAppliment("jojo","13234345456","广州市南沙区","领养领养啊啊啊啊","提提","1岁","狗狗",true,true,false,R.drawable.dog6, new Date(2019,12,11),"学生",1,0,""));

    }
    //模拟下拉加载数据
    private void add2() {
        adoptApplimentList.add(new AdoptAppliment("聚聚","13445456576","广州市天河区","找领养","看看","3岁","狗狗",false,true,true,R.drawable.cat3, new Date(2019,12,11),"学生",1,0,""));
        adoptApplimentList.add(new AdoptAppliment("迪迪","13445434543","广州市白云区","领养啊啊啊","虚虚","3个月","狗狗",true,true,true,R.drawable.dog2, new Date(2019,12,11),"老师",1,0,""));
        adoptApplimentList.add(new AdoptAppliment("豆豆","13776765656","广州市越秀区","嘻嘻嘻嘻嘻己饥己溺","小TXT","9个月","猫猫",false,true,false,R.drawable.cat1, new Date(2019,12,11),"老师",1,0,""));
        adoptApplimentList.add(new AdoptAppliment("破破","13887876565","广州市荔湾区","阿萨德飞规划局","小二小二","半个月","狗狗",true,false,true,R.drawable.dog3, new Date(2019,12,11),"匿名",1,0,""));
    }





}

