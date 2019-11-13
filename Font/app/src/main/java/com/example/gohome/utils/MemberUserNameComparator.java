package com.example.gohome.utils;

import com.example.gohome.Entity.Member;

import java.util.Comparator;

public class MemberUserNameComparator implements Comparator {

    @Override
    public int compare(Object o, Object t1) {
        Member member1 = (Member)o;
        Member member2 = (Member)t1;

        return member1.getPinyin().compareTo(member2.getPinyin());
    }
}
