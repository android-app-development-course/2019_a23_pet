package com.example.gohome.Member.Fragment;

import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.gohome.Member.Adapter.MemberCheckUndoRecyclerViewAdapter;
import com.example.gohome.R;
import com.jcodecraeer.xrecyclerview.ProgressStyle;
import com.jcodecraeer.xrecyclerview.XRecyclerView;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;

public class MemberCheckUndoFragment extends Fragment {


    private List<String> infoList;//显示的数据list
    XRecyclerView xrv_memberCheckUndo;
    MemberCheckUndoRecyclerViewAdapter memberCheckUndoRecyclerViewAdapter;

    private int refreshTime = 0;
    private int times = 0;
    final int itemLimit = 5;



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

        //管理器
        LinearLayoutManager layoutManager = new LinearLayoutManager(this.getContext());
        //适配器
        memberCheckUndoRecyclerViewAdapter = new MemberCheckUndoRecyclerViewAdapter(this.getContext(),infoList);

        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        xrv_memberCheckUndo.setAdapter(memberCheckUndoRecyclerViewAdapter);
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

