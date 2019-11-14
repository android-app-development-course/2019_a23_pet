package com.example.gohome.User.Adapter;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.example.gohome.User.Fragment.UserAdoptFragment;
import com.example.gohome.User.Fragment.UserMineFragment;

public class ViewPagerAdapter extends FragmentPagerAdapter {

    public ViewPagerAdapter(FragmentManager fm) { super(fm); }

    @NonNull
    public Fragment getItem(int position){
        Fragment fragment = null;
        switch (position) {
            case 0: fragment = new UserAdoptFragment(); break;
            case 1:
            case 2: fragment = new UserMineFragment(); break;
        }
        return fragment;
    }

    public int getCount() {
        return 3;
    }
}
