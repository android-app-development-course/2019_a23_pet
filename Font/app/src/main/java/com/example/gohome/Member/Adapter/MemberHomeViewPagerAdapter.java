package com.example.gohome.Member.Adapter;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.example.gohome.Member.Fragment.MemberAdoptFragment;
import com.example.gohome.Member.Fragment.MemberCheckFragment;
import com.example.gohome.Member.Fragment.MemberHelpFragment;
import com.example.gohome.Member.Fragment.MemberMyFragment;
import com.example.gohome.Member.Fragment.MemberReleaseFragment;

import java.util.ArrayList;
import java.util.List;

public class MemberHomeViewPagerAdapter extends FragmentPagerAdapter {


    private List<Fragment> fragmentList;

    private final int PAGER_COUNT = 5;

    public void setFragments(ArrayList<Fragment> fragments) {
        fragmentList = fragments;
    }

    public MemberHomeViewPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        System.out.println("position:"+position);
        Fragment fragment = null;
        switch (position) {
            case 0:
                fragment = new MemberAdoptFragment();
                break;
            case 1:
                fragment = new MemberHelpFragment();
                break;
            case 2:
                fragment = new MemberReleaseFragment();
                break;
            case 3:
                fragment = new MemberCheckFragment();
                break;
            case 4:
                fragment = new MemberMyFragment() ;
                break;
        }
        return fragment;
    }


    @Override
    public int getCount() {
        return PAGER_COUNT;
    }
}
