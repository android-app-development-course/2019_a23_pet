package com.example.gohome.Organizer.Fragment;

import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.gohome.Entity.Member;
import com.example.gohome.Organizer.Adapter.MemberListViewAdapter;
import com.example.gohome.Organizer.Component.MemberSideBar;
import com.example.gohome.R;
import com.example.gohome.utils.MemberUserNameComparator;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class OrganizerMemberFragment extends Fragment {


    private ListView memberListView;
    private MemberSideBar memberSideBar;
    private List<Member> memberList;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_organizer_member,null);
        memberListView = view.findViewById(R.id.list_view);
        memberSideBar = view.findViewById(R.id.member_side_bar);

        initList();
        initView();

        return view;
    }

    private void initList() {
        memberList = new ArrayList<>();
        String partait = "https://cn.bing.com/th?id=OIP.rrK2-xNV0Mw0hLDUPffEQgHaG-&pid=Api&rs=1";
        Member member1 = new Member(1, "啦啦", "广州市天河区中山大道西", partait);
        Member member2 = new Member(2, "贝贝", "广州市天河区中山大道东", partait);
        Member member3 = new Member(3, "卡卡", "广州市天河区中山大道西", partait);
        Member member4 = new Member(4, "成成", "广州市天河区中山大道中", partait);
        Member member5 = new Member(5, "东东", "广州市天河区中山大道北", partait);
        Member member6 = new Member(6, "么么", "广州市天河区中山大道上", partait);
        Member member7 = new Member(7, "咕咕", "广州市天河区中山大道下", partait);
        Member member8 = new Member(8, "哈哈", "广州市天河区中山大道西", partait);
        Member member9 = new Member(9, "橘橘", "广州市天河区中山大道南", partait);
        Member member10 = new Member(10, "冰冰", "广州市天河区中山大道中", partait);
        Member member11 = new Member(11, "安安", "广州市天河区中山大道西", partait);
        Member member12 = new Member(12, "方方", "广州市天河区中山大道南", partait);
        memberList.add(member1);
        memberList.add(member2);
        memberList.add(member3);
        memberList.add(member4);
        memberList.add(member5);
        memberList.add(member6);
        memberList.add(member7);
        memberList.add(member8);
        memberList.add(member9);
        memberList.add(member10);
        memberList.add(member11);
        memberList.add(member12);


        //根据拼音排序
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            memberList.sort(new Comparator<Member>() {
                @Override
                public int compare(Member member, Member t1) {
                    return member.getPinyin().compareTo(t1.getPinyin());
                }
            });
            System.out.println(memberList.get(0));
            System.out.println(memberList.get(1));
        } else {
            MemberUserNameComparator comparator = new MemberUserNameComparator();
            Collections.sort(memberList, comparator);
            System.out.println(memberList.get(0));
            System.out.println(memberList.get(1));

        }
    }

    private void initView(){
        //设置列表适配器
        MemberListViewAdapter memberListViewAdapter = new MemberListViewAdapter(this.getContext(), memberList);
        memberListView.setAdapter(memberListViewAdapter);

        //设置字母栏跳转监听
        memberSideBar.setOnStrSelectCallBack(new MemberSideBar.ISideBarSelectCallBack() {
            @Override
            public void onSelectStr(int index, String selectStr) {
                if(memberList != null){
                    for(int i = 0; i < memberList.size(); i++){
                        if(selectStr.equalsIgnoreCase(memberList.get(i).getHeaderWord())){
                            memberListView.setSelection(i);
                            return;
                        }
                    }
                }
            }
        });
    }
}
