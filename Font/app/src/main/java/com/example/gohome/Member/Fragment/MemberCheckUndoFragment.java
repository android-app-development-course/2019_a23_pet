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
                if(times < 1){  //模拟上拉加载两次
                    new Handler().postDelayed(new Runnable(){
                        public void run() {
                            //第一次上拉
                                if (times == 0){
                                    if(type == 0){
                                        adoptAdd1();
                                    }else {
                                        helpAdd1();
                                    }
                                    memberCheckUndoFoldingCellAdapter.notifyDataSetChanged();
                                    xrv_memberCheckUndo.loadMoreComplete();
                                }
                                if(times == 1){
                                    if(type == 0){
                                        adoptAdd2();
                                    }else {
                                        helpAdd2();
                                    }
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
                } else {  //两次加载完成
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
        adoptApplimentList = new ArrayList<>();
        adoptApplimentList.add(new AdoptAppliment("山河入梦", "13445456576", "广州白云区", "爸妈在家无聊，养只宠物一起玩", "小橘", "70天", "狗狗", true, true, false, R.drawable.member_cat1, "2019.11.27 14:00", "学生", 1, 0, ""));
        adoptApplimentList.add(new AdoptAppliment("黄小姐", "13124545434", "广州番禺区", "家里的猫猫一只猫在家无聊，养多一个", "兔叽", "1岁半", "猫猫", false, false, true, R.drawable.member_cat2, "2019.11.27 7:29", "职员", 2, 0, ""));
        adoptApplimentList.add(new AdoptAppliment("何先生", "13456565444", "西一宿舍", "想养可爱小猫", "茉莉", "1岁", "狗狗", true, true, true, R.drawable.member_cat3, "2019.11.27 7:22", "老师", 3, 0, ""));
        adoptApplimentList.add(new AdoptAppliment("谭女士", "13456567876", "广州番禺区", "家里养了一只小猫，养多一只有个伴", "阿黑", "11个月", "猫猫", false, true, true, R.drawable.member_cat4, "2019.11.26 22:08", "程序员", 4, 0, ""));
        adoptApplimentList.add(new AdoptAppliment("小兵", "13556765434", "广州荔湾区", "想养只可爱小猫", "橘橘", "5个月", "猫猫", false, true, false, R.drawable.member_cat5, "2019.11.26 19:09", "老师", 5, 0, ""));

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
    private void adoptAdd1(){
        adoptApplimentList.add(new AdoptAppliment("马小姐","13445456576","广州市天河区","找领养 ","猪猪","3岁","狗狗",true,true,true,R.drawable.member_cat6, "2019.11.26 18:35","学生",1,0,""));
        adoptApplimentList.add(new AdoptAppliment("张先生","13445456545","广州市白云区","找领养 ","图图","3岁","狗狗",true,false,false,R.drawable.member_dog1, "2019.11.26 18:01","公司上班",1,0,""));
        adoptApplimentList.add(new AdoptAppliment("邹女士","13998987654","广州市番禺区","想领养","嗯哼","1岁","猫猫",false,true,false,R.drawable.member_dog2, "2019.11.26 17:27","自由职业",1,0,""));
        adoptApplimentList.add(new AdoptAppliment("侯先生","13234345456","广州市南沙区","领养领养啊 ","溜溜","1岁","狗狗",true,true,false,R.drawable.member_cat7, "2019.11.26 14:23","经营餐馆",1,0,""));

    }


    //模拟下拉加载数据
    private void adoptAdd2() {
        adoptApplimentList.add(new AdoptAppliment("小吴","13445456576","广州白云区","找一只狗狗领养","dodo","一岁左右","狗狗",false,true,true,R.drawable.member_dog3, "2019.11.26 12:15","学生",1,0,""));
        adoptApplimentList.add(new AdoptAppliment("小李","13445434543","广州荔湾区","想养一只狗狗","roo","一岁半","狗狗",true,true,true,R.drawable.member_dog4, "2019.11.26 13:11","老师",1,0,""));
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

