package com.example.gohome.Member.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.gohome.Entity.AdoptAppliment;
import com.example.gohome.Entity.AdoptInfo;
import com.example.gohome.Member.Adapter.MemberCheckUndoFoldingCellAdapter;
import com.example.gohome.Member.Adapter.MemberCheckUndoRecyclerViewAdapter;
import com.example.gohome.R;
import com.example.gohome.User.Activity.UserAdoptActivity;
import com.example.gohome.User.Adapter.FoldingCellListAdapter;
import com.jcodecraeer.xrecyclerview.ProgressStyle;
import com.jcodecraeer.xrecyclerview.XRecyclerView;
import com.ramotion.foldingcell.FoldingCell;

import java.time.Duration;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import butterknife.ButterKnife;

public class MemberCheckUndoFragment extends Fragment {


    private List<String> infoList;//显示的数据list

    XRecyclerView xrv_memberCheckUndo;
    MemberCheckUndoRecyclerViewAdapter memberCheckUndoRecyclerViewAdapter;

    private int refreshTime = 0;
    private int times = 0;
    final int itemLimit = 5;  //限制最多显示5条记录

    private MemberCheckUndoFoldingCellAdapter memberCheckUndoFoldingCellAdapter;
    private List<AdoptAppliment> adoptApplimentList;





    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState ){
        View view = inflater.inflate(R.layout.fragment_member_check_undo,null);
        ButterKnife.bind(this, view);

        xrv_memberCheckUndo = view.findViewById(R.id.xrv_memberCheckUndo);


        initList();
        init();

        return view;
    }


    private void init(){
        initAdoptAppliment();


        //管理器
        LinearLayoutManager layoutManager = new LinearLayoutManager(this.getContext());
        //适配器
        memberCheckUndoRecyclerViewAdapter = new MemberCheckUndoRecyclerViewAdapter(this.getContext(),infoList);

        memberCheckUndoFoldingCellAdapter = new MemberCheckUndoFoldingCellAdapter(this.getContext(), adoptApplimentList);

        memberCheckUndoFoldingCellAdapter.setDefaultRequestBtnClickListener(view -> {
            System.out.println("通过审核！！！");
        });

        memberCheckUndoFoldingCellAdapter.setClickCallBack(new MemberCheckUndoFoldingCellAdapter.ItemClickCallBack() {
            @Override
            public void onItemClick(int pos) {
                System.out.println("点击了第："+pos+"个item");
            }
        });

        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
//        xrv_memberCheckUndo.setAdapter(memberCheckUndoRecyclerViewAdapter);
        xrv_memberCheckUndo.setAdapter(memberCheckUndoFoldingCellAdapter);

        xrv_memberCheckUndo.setLayoutManager(layoutManager);

        xrv_memberCheckUndo.setRefreshProgressStyle(ProgressStyle.BallSpinFadeLoader);
        xrv_memberCheckUndo.setLoadingMoreProgressStyle(ProgressStyle.BallRotate);
        xrv_memberCheckUndo.setArrowImageView(R.drawable.iconfont_downgrey);

        xrv_memberCheckUndo
                .getDefaultRefreshHeaderView()
                .setRefreshTimeVisible(true);



        xrv_memberCheckUndo.setLoadingListener(new XRecyclerView.LoadingListener() {
            //下拉刷新
            @Override
            public void onRefresh() {
                refreshTime ++;
                times = 0;
                new Handler().postDelayed(new Runnable(){
                    public void run() {
                        infoList.clear();

                        //模拟加载后的数据
                        for(int i = 0; i < itemLimit ;i++){
                            infoList.add("item" + i + "after " + refreshTime + " times of refresh");
                        }
                        memberCheckUndoRecyclerViewAdapter.notifyDataSetChanged(); //更新列表
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

                            //模拟上拉加载的数据
                            for(int i = 0; i < itemLimit ;i++){
                                infoList.add("item" + (1 + infoList.size() ) );
                            }

                            if(xrv_memberCheckUndo != null) {
                                xrv_memberCheckUndo.loadMoreComplete();
                                memberCheckUndoRecyclerViewAdapter.notifyDataSetChanged();
                            }
                        }
                    }, 1000);
                } else {  //三次加载完成
                    new Handler().postDelayed(new Runnable() {
                        public void run() {
                            //模拟上拉加载的数据
                            for(int i = 0; i < itemLimit ;i++){
                                infoList.add("item" + (1 + infoList.size() ) );
                            }
                            if(xrv_memberCheckUndo != null) {
                                xrv_memberCheckUndo.setNoMore(true);
                                memberCheckUndoRecyclerViewAdapter.notifyDataSetChanged();
                            }
                        }
                    }, 1000);
                }
                times ++;
            }
        });

    }

    private void initAdoptAppliment() {
        adoptApplimentList = new ArrayList<>();
        adoptApplimentList.add(new AdoptAppliment("小小","13445456576","广州市天河区","想领养","一一","2岁","狗狗",1,1,1,R.drawable.timg, new Date(2019,12,11),"学生"));
        adoptApplimentList.add(new AdoptAppliment("大大","13124545434","星河楼","领养","二二","1岁半","猫猫",0,0,1,R.drawable.cat2,new Date(2019,12,11),"职员"));
        adoptApplimentList.add(new AdoptAppliment("种种","13456565444","西一宿舍","领养一只","三三","1岁","狗狗",1,0,1,R.drawable.cat1,new Date(2019,12,11),"老师"));
        adoptApplimentList.add(new AdoptAppliment("呵呵","13456567876","越秀区","领养两只","四四","11个月", "猫猫",0,1,1,R.drawable.cat3,new Date(2019,12,11),"程序员"));
        adoptApplimentList.add(new AdoptAppliment("一会吧","13556765434","校区","领养领养领养领养领养领养","五五","5个月","猫猫",0,1,0,R.drawable.cat3,new Date(2019,12,11),"老师"));
        adoptApplimentList.add(new AdoptAppliment("KIKI","1567656543","番禺区","想领养","六六","8个月","狗狗",1,1,0,R.drawable.cat,new Date(2019,12,11),"学生"));
        adoptApplimentList.add(new AdoptAppliment("妮妮","14565678765","白云区","领养","七七","1岁7个月","猫猫",1,1,0,R.drawable.dog1,new Date(2019,12,11),"老师"));
    }

    private void initList() {
        infoList = new ArrayList<>();
        infoList.add("keke");
        infoList.add("lala");
        infoList.add("alsidfal");
        infoList.add("keke");
        infoList.add("lala");
        infoList.add("alsidfal");
        infoList.add("keke");
        infoList.add("lala");
        infoList.add("alsidfal");
        infoList.add("keke");
        infoList.add("lala");
        infoList.add("alsidfal");
    }


}

