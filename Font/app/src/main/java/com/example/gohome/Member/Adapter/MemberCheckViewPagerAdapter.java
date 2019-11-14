package com.example.gohome.Member.Adapter;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.example.gohome.Member.Fragment.MemberCheckDoingFragment;
import com.example.gohome.Member.Fragment.MemberCheckDoneFragment;
import com.example.gohome.Member.Fragment.MemberCheckUndoFragment;

import java.util.ArrayList;
import java.util.List;

public class MemberCheckViewPagerAdapter extends FragmentPagerAdapter {

    /**
     * The m fragment list.
     */
    private List<Fragment> mFragmentList = null;

    private String[] titles;

    private final int PAGER_COUNT = 3;


    /**
     * Instantiates a new ab fragment pager adapter.
     *
     * @param mFragmentManager the m fragment manager
     * @param fragmentList     the fragment list
     */
    public MemberCheckViewPagerAdapter(FragmentManager mFragmentManager,ArrayList<Fragment> fragmentList) {
        super(mFragmentManager);
        mFragmentList = fragmentList;
    }


    public MemberCheckViewPagerAdapter(FragmentManager mFragmentManager) {
        super(mFragmentManager);
    }

    /**
     * titles是给TabLayout设置title用的
     *
     * @param mFragmentManager
     * @param titles
     */
    public MemberCheckViewPagerAdapter(FragmentManager mFragmentManager, String[] titles) {
        super(mFragmentManager);
        this.titles = titles;
    }

    /**
     * 描述：获取数量.
     *
     * @return the count
     */
    @Override
    public int getCount() {
        return PAGER_COUNT;
    }

    /**
     * 描述：获取索引位置的Fragment.
     *
     * @param position the position
     * @return the item
     */
    @Override
    public Fragment getItem(int position) {

        System.out.println("position:"+position);
        Fragment fragment = null;
        switch (position) {
            case 0:
                fragment = new MemberCheckUndoFragment();
                break;
            case 1:
                fragment = new MemberCheckDoingFragment();
                break;
            case 2:
                fragment = new MemberCheckDoneFragment();
                break;
        }
        return fragment;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        if (titles != null && titles.length > 0)
            return titles[position];
        return null;
    }
}