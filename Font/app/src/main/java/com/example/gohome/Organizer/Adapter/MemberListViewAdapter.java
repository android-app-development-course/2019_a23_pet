package com.example.gohome.Organizer.Adapter;

import android.content.Context;
import android.database.DataSetObserver;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.gohome.Entity.Member;
import com.example.gohome.R;

import java.util.Date;
import java.util.List;

public class MemberListViewAdapter extends BaseAdapter {

    private List<Member> memberList;
    private LayoutInflater inflater;

    public List<Member> getMemberList() {
        return memberList;
    }

    public void setMemberList(List<Member> memberList) {
        this.memberList = memberList;
    }

    private class ViewHolder{
        private ImageView iv_protrait;
        private TextView tv_userName;
        private TextView tv_address;
        private TextView tv_telephone;
        private TextView tv_gender;
        private TextView tv_memberCreated;
//        private String tv_pinyin;
        private TextView tv_headerWord;
    }

    public MemberListViewAdapter(Context context, List<Member> memberList){
        inflater = LayoutInflater.from(context);
        this.memberList = memberList;
    }

    @Override
    public int getCount() {
        return memberList.size();
    }

    @Override
    public Object getItem(int i) {
        if(i < memberList.size()){
            return memberList.get(i);
        }else{
            return null;
        }
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        ViewHolder viewHolder;
        if(view == null){
            //初始化viewHolder
            viewHolder = new ViewHolder();
            view = inflater.inflate(R.layout.organizer_member_list_item, null);
            viewHolder.iv_protrait = (ImageView)view.findViewById(R.id.image_view_protrait);
            viewHolder.tv_userName = (TextView)view.findViewById(R.id.text_view_username);
            viewHolder.tv_address = (TextView)view.findViewById(R.id.text_view_address);
//            viewHolder.tv_gender= (TextView)view.findViewById(R.id.text_view_gender);
//            viewHolder.tv_telephone = (TextView)view.findViewById(R.id.text_view_telephone);
//            viewHolder.tv_memberCreated = (TextView)view.findViewById(R.id.text_view_member_created);
            viewHolder.tv_headerWord = (TextView)view.findViewById(R.id.text_view_member_headerword);

            view.setTag(viewHolder);
        }else{
            viewHolder = (ViewHolder)view.getTag();
        }

        //赋值
        Member member = memberList.get(i);
        String word = member.getPinyin();
        viewHolder.tv_userName.setText(member.getUserName());
        Glide.with(view.getContext()).load(member.getPortrait()).override(60, 60).centerCrop().into(viewHolder.iv_protrait);
        viewHolder.tv_address.setText(member.getAddress());//            viewHolder.tv_pinyin = member.getPinyin();
        viewHolder.tv_headerWord.setText(word);

        //将相同字母开头的合并在一起
        if(i == 0){
            viewHolder.tv_headerWord.setVisibility(View.VISIBLE);
        }else{
            String headWord = memberList.get(i - 1).getHeaderWord();
            if(headWord.equals(word)){
                viewHolder.tv_headerWord.setVisibility(View.GONE);
            }else{
                viewHolder.tv_headerWord.setVisibility(View.VISIBLE);
            }
        }

        return view;
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    public int getPositionForSection(String catalog){
        for(int i = 0; i < getCount(); i++){
            String sortStr = memberList.get(i).getHeaderWord();
            if(catalog.equalsIgnoreCase(sortStr)){
                return i;
            }
        }
        return -1;
    }


}
