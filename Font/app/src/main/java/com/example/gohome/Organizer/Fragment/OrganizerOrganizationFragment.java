package com.example.gohome.Organizer.Fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.google.android.material.tabs.TabLayout;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import com.example.gohome.Organizer.Adapter.OrganizerCheckViewPagerAdapter;
import com.example.gohome.R;

import butterknife.ButterKnife;

public class OrganizerOrganizationFragment extends Fragment {

    private ViewPager viewPager;
    private TabLayout tabLayout;

    private static String[] titles = new String[]{"待审核", "处理中", "已完成"};

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_organizer_organization,null);
        ButterKnife.bind(this, view);

        viewPager = view.findViewById(R.id.check_view_pager);
        tabLayout = view.findViewById(R.id.check_tab_layout);

        init();

        return view;
    }

    private void init(){

        OrganizerCheckViewPagerAdapter organizerCheckViewPagerAdapter = new OrganizerCheckViewPagerAdapter(getChildFragmentManager(), titles);

        viewPager.setAdapter(organizerCheckViewPagerAdapter);

        viewPager.setCurrentItem(0);

        tabLayout.setupWithViewPager(viewPager);
    }
}
