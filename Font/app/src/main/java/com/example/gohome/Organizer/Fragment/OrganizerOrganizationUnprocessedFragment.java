package com.example.gohome.Organizer.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.gohome.Entity.JoinAppliment;
import com.example.gohome.Organizer.Adapter.OrganizationUnProcessFoldingCellAdapter;
import com.example.gohome.R;
import com.jcodecraeer.xrecyclerview.ProgressStyle;
import com.jcodecraeer.xrecyclerview.XRecyclerView;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import butterknife.ButterKnife;


public class OrganizerOrganizationUnprocessedFragment extends Fragment {

    private XRecyclerView xrv_organizerCheckDoing;
    private int refreshTime = 0;
    private int times = 0;
    final int itemLimit = 5;

    private OrganizationUnProcessFoldingCellAdapter organizationUnProcessFoldingCellAdapter;
    private List<JoinAppliment> joinApplimentList;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState ){
        View view = inflater.inflate(R.layout.fragment_organizer_organization_unprocess, null);
        ButterKnife.bind(this, view);

        xrv_organizerCheckDoing = view.findViewById(R.id.unprocess_xrecyclerview);

        init();
        return view;
    }

    private void init(){

        //初始化模拟数据
        initJoinAppliment();

        //管理器
        LinearLayoutManager layoutManager = new LinearLayoutManager(this.getContext());
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        xrv_organizerCheckDoing.setLayoutManager(layoutManager);
        //适配器
        organizationUnProcessFoldingCellAdapter = new OrganizationUnProcessFoldingCellAdapter(this.getContext(), joinApplimentList);
        organizationUnProcessFoldingCellAdapter.setClickCallBack(new OrganizationUnProcessFoldingCellAdapter.ItemClickCallBack() {
            @Override
            public void onItemClick(int pos) {
                System.out.println("点击了第："+pos+"个item");
            }
        });


        xrv_organizerCheckDoing.setAdapter(organizationUnProcessFoldingCellAdapter);
        xrv_organizerCheckDoing.setRefreshProgressStyle(ProgressStyle.BallSpinFadeLoader);
        xrv_organizerCheckDoing.setLoadingMoreProgressStyle(ProgressStyle.BallRotate);
        xrv_organizerCheckDoing.setArrowImageView(R.drawable.iconfont_downgrey);
        xrv_organizerCheckDoing.getDefaultRefreshHeaderView().setRefreshTimeVisible(true);
        xrv_organizerCheckDoing.setClipChildren(false);
        xrv_organizerCheckDoing.setClipToPadding(false);

        xrv_organizerCheckDoing.setLoadingListener(new XRecyclerView.LoadingListener() {
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
                        organizationUnProcessFoldingCellAdapter.notifyDataSetChanged(); //更新列表
                        if(xrv_organizerCheckDoing != null)
                            xrv_organizerCheckDoing.refreshComplete();
                    }

                }, 1000);            //refresh data here
            }

            @Override
            public void onLoadMore() {
                if(times < 2){  //模拟上拉加载三次
                    new Handler().postDelayed(new Runnable(){
                        public void run() {

//                            //模拟上拉加载的数据
//                            for(int i = 0; i < itemLimit ;i++){
//                                infoList.user_add("item" + (1 + infoList.size() ) );
//                            }

                            //显示加载完成
                            if(xrv_organizerCheckDoing != null) {
                                xrv_organizerCheckDoing.loadMoreComplete();
                                organizationUnProcessFoldingCellAdapter.notifyDataSetChanged();
                            }
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
                            if(xrv_organizerCheckDoing != null) {
                                xrv_organizerCheckDoing.setNoMore(true);
                                organizationUnProcessFoldingCellAdapter.notifyDataSetChanged();
                            }
                        }
                    }, 1000);
                }
                times ++;
            }
        });
    }

    private void initJoinAppliment(){
        joinApplimentList = new ArrayList<>();
        joinApplimentList.add(new JoinAppliment(Integer.valueOf(1), new Date(), "小小", "13445456576", "广州市天河区", "blablabla", "xiaoxiao", R.drawable.cat));
        joinApplimentList.add(new JoinAppliment(Integer.valueOf(1), new Date(), "大大","13124545434","星河楼", "blablabla", "dada", R.drawable.dog));
        joinApplimentList.add(new JoinAppliment(Integer.valueOf(2), new Date(), "种种","13456565444","西一宿舍", "blablabla", "zhzh", R.drawable.dog1));
        joinApplimentList.add(new JoinAppliment(Integer.valueOf(2), new Date(), "呵呵","13456567876","越秀区", "blablabla", "hehe", R.drawable.cat1));
        joinApplimentList.add(new JoinAppliment(Integer.valueOf(1), new Date(), "一会吧","13556765434","校区", "blablabla", "yiyi", R.drawable.cat3));
        joinApplimentList.add(new JoinAppliment(Integer.valueOf(2), new Date(), "KIKI","1567656543","番禺区", "blablabla", "haha", R.drawable.dog4));
        joinApplimentList.add(new JoinAppliment(Integer.valueOf(1), new Date(), "妮妮","14565678765","白云区", "blablabla", "xixi", R.drawable.dog5));
        joinApplimentList.add(new JoinAppliment(Integer.valueOf(2), new Date(), "aaaa", "13445456576", "广州市天河区", "blablabla", "xiaoxiao", R.drawable.cat1));
    }


}
