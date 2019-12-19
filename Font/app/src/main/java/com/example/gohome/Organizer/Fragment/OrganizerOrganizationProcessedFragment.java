package com.example.gohome.Organizer.Fragment;

import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.gohome.Entity.JoinAppliment;
import com.example.gohome.Organizer.Adapter.OrganizationProcessedFoldingCellAdapter;
import com.example.gohome.R;
import com.jcodecraeer.xrecyclerview.ProgressStyle;
import com.jcodecraeer.xrecyclerview.XRecyclerView;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import butterknife.ButterKnife;

public class OrganizerOrganizationProcessedFragment extends Fragment {

    //记录提交结果
    public static final int SUCCESS = 1;
    public static final int FAIL = 0;
    public static final int ZERO = 2;//记录请求回来的数据条数是否为零

    //记录当前页码与每次请求的数量
    private int curPageNumAdopt = 1; //默认页码数为第一页
    private int curPageNumHelp = 1; //默认页码数为第一页

    public static final int PAGE_SIZE = 5;

    private XRecyclerView xrv_organizerCheckDoing;
    private int times = 0;

    private OrganizationProcessedFoldingCellAdapter organizationProcessedFoldingCellAdapter;
    private List<JoinAppliment> joinApplimentList;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState ){
        View view = inflater.inflate(R.layout.fragment_organizer_organization_processed, null);
        ButterKnife.bind(this, view);

        xrv_organizerCheckDoing = view.findViewById(R.id.processed_xrecyclerview);

        init();
        return view;
    }

    private void init(){

        joinApplimentList = new ArrayList<>();

        //初始化数据
        initJoinAppliment();

        //管理器
        LinearLayoutManager layoutManager = new LinearLayoutManager(this.getContext());
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        xrv_organizerCheckDoing.setLayoutManager(layoutManager);
        //适配器
        organizationProcessedFoldingCellAdapter = new OrganizationProcessedFoldingCellAdapter(this.getContext(), joinApplimentList);
        organizationProcessedFoldingCellAdapter.setClickCallBack(new OrganizationProcessedFoldingCellAdapter.ItemClickCallBack() {
            @Override
            public void onItemClick(int pos) {
                System.out.println("点击了第："+pos+"个item");
            }
        });


        xrv_organizerCheckDoing.setAdapter(organizationProcessedFoldingCellAdapter);
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
                times = 0;
                new Handler().postDelayed(new Runnable(){
                    public void run() {
//                        infoList.clear();
                        //模拟加载后的数据
//                        for(int i = 0; i < itemLimit ;i++){
//                            infoList.user_add("item" + i + "after " + refreshTime + " times of refresh");
//                        }
                        //加载完成
                        organizationProcessedFoldingCellAdapter.notifyDataSetChanged(); //更新列表
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

//                            if (times == 0){
//                                    add1();
//                                    organizationProcessingFoldingCellAdapter.notifyDataSetChanged();
//                                    xrv_organizerCheckDoing.loadMoreComplete();
//                                }
//                                if(times == 1){
//                                    add2();
//                                    organizationProcessingFoldingCellAdapter.notifyDataSetChanged();
//                                    xrv_organizerCheckDoing.loadMoreComplete();
//                                }

                            //显示加载完成
                            if(xrv_organizerCheckDoing != null) {
                                xrv_organizerCheckDoing.loadMoreComplete();
                                organizationProcessedFoldingCellAdapter.notifyDataSetChanged();
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
                                organizationProcessedFoldingCellAdapter.notifyDataSetChanged();
                            }
                        }
                    }, 1000);
                }
                times ++;
            }
        });
    }

    private void initJoinAppliment(){
        joinApplimentList.add(new JoinAppliment(Integer.valueOf(1), new Date(), "小小", "13445456576", "广州市天河区", "blablabla", "xiaoxiao", R.drawable.cat, new Date()));
        joinApplimentList.add(new JoinAppliment(Integer.valueOf(1), new Date(), "大大","13124545434","星河楼", "blablabla", "dada", R.drawable.dog, new Date()));
        joinApplimentList.add(new JoinAppliment(Integer.valueOf(2), new Date(), "种种","13456565444","西一宿舍", "blablabla", "zhzh", R.drawable.dog1, new Date()));
        joinApplimentList.add(new JoinAppliment(Integer.valueOf(2), new Date(), "呵呵","13456567876","越秀区", "blablabla", "hehe", R.drawable.cat1, new Date()));
        joinApplimentList.add(new JoinAppliment(Integer.valueOf(1), new Date(), "一会吧","13556765434","校区", "blablabla", "yiyi", R.drawable.cat3, new Date()));
        joinApplimentList.add(new JoinAppliment(Integer.valueOf(2), new Date(), "KIKI","1567656543","番禺区", "blablabla", "haha", R.drawable.dog4, new Date()));
        joinApplimentList.add(new JoinAppliment(Integer.valueOf(1), new Date(), "妮妮","14565678765","白云区", "blablabla", "xixi", R.drawable.dog5, new Date()));
        joinApplimentList.add(new JoinAppliment(Integer.valueOf(2), new Date(), "aaaa", "13445456576", "广州市天河区", "blablabla", "xiaoxiao", R.drawable.cat1, new Date()));
    }
}
