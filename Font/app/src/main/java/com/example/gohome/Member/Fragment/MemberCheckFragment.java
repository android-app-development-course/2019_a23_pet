package com.example.gohome.Member.Fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.google.android.material.tabs.TabLayout;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import com.example.gohome.Member.Adapter.MemberCheckViewPagerAdapter;
import com.example.gohome.R;

import java.util.Objects;

import butterknife.ButterKnife;

public class MemberCheckFragment extends Fragment {


    ViewPager vp_memberCheck;
    TabLayout tl_memberCheck;

    private String[] titles = new String[]{"待审核","处理中","已完成"};



    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState ){
        View view = inflater.inflate(R.layout.fragment_member_check,null);
        ButterKnife.bind(this, view);

        vp_memberCheck = view.findViewById(R.id.vp_memberCheck);
        tl_memberCheck = view.findViewById(R.id.tl_memberCheck);


        init();

        return view;
    }


    private void init(){

        // 创建ViewPager适配器
        MemberCheckViewPagerAdapter memberCheckViewPagerAdapter = new MemberCheckViewPagerAdapter(getChildFragmentManager(),titles);

        // 给ViewPager设置适配器
        vp_memberCheck.setAdapter(memberCheckViewPagerAdapter);

        vp_memberCheck.setCurrentItem(0);

        tl_memberCheck.setupWithViewPager(vp_memberCheck);



    }

    @Override
    public void onResume() {
        int id = getActivity().getIntent().getIntExtra("id", 0);
        if(id == 2){
            vp_memberCheck.setCurrentItem(1);
        }
        super.onResume();
    }



}
