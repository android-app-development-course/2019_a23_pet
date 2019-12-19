package com.example.gohome.Organizer.Fragment;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.gohome.Entity.Member;
import com.example.gohome.Organizer.Adapter.MemberListViewAdapter;
import com.example.gohome.Component.OrganizerMemberSideBar;
import com.example.gohome.R;
import com.example.gohome.Utils.MemberUserNameComparator;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.concurrent.TimeUnit;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class OrganizerMemberFragment extends Fragment {

    public static final int SUCCESS_CODE = 1;
    public static final int FAILURE_CODE = 0;

    private ListView memberListView;
    private MemberListViewAdapter memberListViewAdapter;
    private OrganizerMemberSideBar memberSideBar;
    private List<Member> memberList = null;
    private Object lock = null;

    private Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            try {
                if(msg.what==SUCCESS_CODE){//链接成功
                    JSONObject jsonObject = new JSONObject(msg.getData().getString("response"));
                    Boolean success = jsonObject.getBoolean("success");

                    if(success){
                        String jsonStr = jsonObject.getString("memberMessage");
                        Gson gson = new Gson();
                        JsonParser parser = new JsonParser();
                        JsonArray jsonArray = parser.parse(jsonStr).getAsJsonArray();
                        List<Member> list = new ArrayList<>();
                        for(JsonElement obj : jsonArray){
                            Member member = gson.fromJson(obj, Member.class);
                            System.out.println(member);
                            member.setUserName(member.getUserName());
                            list.add(member);
                        }
                        if(list!=null){
                            //根据拼音排序
                            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                                list.sort(new Comparator<Member>() {
                                    @Override
                                    public int compare(Member member, Member t1) {
                                        return member.getPinyin().compareTo(t1.getPinyin());
                                    }
                                });
                            } else {
                                MemberUserNameComparator comparator = new MemberUserNameComparator();
                                Collections.sort(list, comparator);
                            }
                            synchronized (lock){

                                memberList = list;
                                memberListViewAdapter = new MemberListViewAdapter(getActivity(), memberList);
                                memberListView.setAdapter(memberListViewAdapter);
                                memberListViewAdapter.notifyDataSetChanged();
                            }
                        }
                    }else{
                        throw new RuntimeException(jsonObject.getString("errMsg"));
                    }
                }else{//链接失败或者处理失败
                    Bundle bundle = msg.getData();
                    String errMsg = bundle.getString("errMsg");
                    Toast.makeText(getActivity().getApplicationContext(), errMsg, Toast.LENGTH_SHORT).show();
                }
            }catch (Exception e){
                Log.i("handle member exception", e.getMessage());
            }
        }
    };

    public ListView getMemberListView() {
        return memberListView;
    }

    public void setMemberListView(ListView memberListView) {
        this.memberListView = memberListView;
    }

    public OrganizerMemberSideBar getMemberSideBar() {
        return memberSideBar;
    }

    public void setMemberSideBar(OrganizerMemberSideBar memberSideBar) {
        this.memberSideBar = memberSideBar;
    }

    public List<Member> getMemberList() {
        return memberList;
    }

    public void setMemberList(List<Member> memberList) {
        this.memberList = memberList;
    }

    public Handler getHandler() {
        return handler;
    }

    public void setHandler(Handler handler) {
        this.handler = handler;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_organizer_member,null);
        memberListView = view.findViewById(R.id.list_view);
        memberSideBar = view.findViewById(R.id.member_side_bar);

        lock = new Object();

        initList();
        initView();

        return view;
    }

    private void initList() {
        memberList = new ArrayList<>();
        memberList = new ArrayList<>();
        memberListViewAdapter = new MemberListViewAdapter(this.getContext(), memberList);

        new Thread(new Runnable() {
            @Override
            public void run() {
                Message msg = null;
                try {
                    OkHttpClient client = new OkHttpClient.Builder()
                            .connectTimeout(10, TimeUnit.SECONDS)//设置连接超时时间
                            .readTimeout(20, TimeUnit.SECONDS)//设置读取超时时间
                            .build();
                    //生成json数据
                    Request.Builder reqBuilder = new Request.Builder();
                    HttpUrl.Builder urlBuilder = HttpUrl.parse(getActivity().getResources().getString(R.string.serverBasePath) + getActivity().getResources().getString(R.string.memberMessageByAreaId)).newBuilder();
                    SharedPreferences sharedPreferences = getActivity().getApplicationContext().getSharedPreferences("userInfo", Context.MODE_PRIVATE);
                    int areaId = sharedPreferences.getInt("areaId", 0);
                    urlBuilder.addQueryParameter("areaId", String.valueOf(areaId));
                    //请求
                    reqBuilder.url(urlBuilder.build());
                    Request request = reqBuilder.build();
                    //新建call联结client和request
                    Response response = client.newCall(request).execute();
                    //新建call联结client和request
                    //新建Message通过Handle与主线程通信
                    if(response.isSuccessful()){
                        msg = new Message();
                        msg.what = SUCCESS_CODE;
                        String responseBody = response.body().string();
                        Bundle bundle = new Bundle();
                        bundle.putString("response", responseBody);
                        msg.setData(bundle);
                        handler.sendMessage(msg);
                    }else{
                        String responseBody = response.body().string();
                        JSONObject jsonObject = new JSONObject(responseBody);
                        String errMsg = jsonObject.getString("errMsg");
                        throw new RuntimeException(errMsg);

                    }
                }catch (Exception e){
                    msg = new Message();
                    msg.what = FAILURE_CODE;
                    Bundle bundle = new Bundle();
                    bundle.putString("errMsg", e.getMessage());
                    msg.setData(bundle);
                    handler.sendMessage(msg);
                }
            }
        }).start();

    }

    private void initView(){
        //设置列表适配器
//        memberListView.setAdapter(memberListViewAdapter);

        //设置字母栏跳转监听
        memberSideBar.setOnStrSelectCallBack(new OrganizerMemberSideBar.ISideBarSelectCallBack() {
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
