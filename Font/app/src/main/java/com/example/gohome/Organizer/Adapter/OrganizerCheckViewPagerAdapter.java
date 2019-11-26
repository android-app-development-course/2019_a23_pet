package com.example.gohome.Organizer.Adapter;


import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.example.gohome.Organizer.Fragment.OrganizerOrganizationProcessedFragment;
import com.example.gohome.Organizer.Fragment.OrganizerOrganizationProcessingFragment;
import com.example.gohome.Organizer.Fragment.OrganizerOrganizationUnprocessedFragment;

import java.util.ArrayList;
import java.util.List;

public class OrganizerCheckViewPagerAdapter extends FragmentPagerAdapter {

    private List<Fragment> fragmentList = null;

    private String[] titles;

    private final int PAGER_COUNT = 3;

    public OrganizerCheckViewPagerAdapter(FragmentManager fragmentManager, ArrayList<Fragment> fragmentList){
        super(fragmentManager);
        this.fragmentList = fragmentList;
    }

    public OrganizerCheckViewPagerAdapter(FragmentManager fragmentManager){
        super(fragmentManager);
    }

    public OrganizerCheckViewPagerAdapter(FragmentManager fragmentManager, String[] titles){
        super(fragmentManager);
        this.titles = titles;
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        Fragment fragment = null;
        switch (position){
            case 0:
                fragment = new OrganizerOrganizationUnprocessedFragment();
                break;
            case 1:
                fragment = new OrganizerOrganizationProcessingFragment();
                break;
            case 2:
                fragment =new OrganizerOrganizationProcessedFragment();
                break;
        }
        return fragment;
    }

    @Override
    public int getCount() {
        return PAGER_COUNT;
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        if(titles != null && titles.length > position){
            return titles[position];
        }
        return null;
    }
}
