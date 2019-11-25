package com.example.gohome.User.Fragment;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.bumptech.glide.Glide;
import com.example.gohome.R;
import com.example.gohome.User.Activity.UserAddGroupActivity;
import com.example.gohome.User.Activity.UserAdoptProActivity;
import com.example.gohome.User.Activity.UserPersonalInforActivity;
import com.example.gohome.User.Activity.UserSendProActivity;
import com.example.gohome.User.Activity.UserSettingCenterActivity;
import com.example.gohome.User.ImageDialog;

public class UserMineFragment extends Fragment implements View.OnClickListener {

    private View rootView;
    private Activity activity;

    private ImageView imPortrait;
    private int portrait=R.drawable.timg;

    private TextView tvNickname;
    private String nickname = "张咩阿";

    private TextView tvPhone;
    private StringBuffer tmpphone = new StringBuffer("15626431234");
    private String phone=tmpphone.substring(0,7)+"****";

    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             Bundle savedInstanceState) {
        if (rootView != null) {
            ViewGroup parent = (ViewGroup) rootView.getParent();
            if (parent != null) {
                parent.removeView(rootView);
            }
        } else {
            rootView = inflater.inflate(R.layout.fragment_user_mine, null);
            activity=getActivity();

            // 在此初始化！！！
            init();
        }
        return rootView;
    }

    public void init(){
        rootView.findViewById(R.id.user_rel_personalInfo).setOnClickListener(this);
        rootView.findViewById(R.id.user_rel_adoptPro).setOnClickListener(this);
        rootView.findViewById(R.id.user_rel_sendPro).setOnClickListener(this);
        rootView.findViewById(R.id.user_rel_addGroup).setOnClickListener(this);
        rootView.findViewById(R.id.user_rel_settingCenter).setOnClickListener(this);

        //初始化头像
        imPortrait = rootView.findViewById(R.id.user_iv_portrait);
        Glide.with(rootView.getContext()).load(portrait).into(imPortrait);
        imPortrait.setOnClickListener(this);

        //初始化昵称
        tvNickname = rootView.findViewById(R.id.user_tv_nickname);
        tvNickname.setText(nickname);

        //初始化手机号
        tvPhone = rootView.findViewById(R.id.user_tv_phone);
        tvPhone.setText(phone);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.user_iv_portrait:
            case R.id.user_rel_personalInfo:
                Intent intent=new Intent(activity, UserPersonalInforActivity.class);
                intent.putExtra("oldPortrait",portrait);
                intent.putExtra("oldNickname",nickname);
                startActivityForResult(intent,0);
                break;
            case R.id.user_rel_adoptPro:
                startActivity(new Intent(activity, UserAdoptProActivity.class));
                break;
            case R.id.user_rel_sendPro:
                startActivity(new Intent(activity, UserSendProActivity.class));
                break;
            case R.id.user_rel_addGroup:
                startActivity(new Intent(activity, UserAddGroupActivity.class));
                break;
            case R.id.user_rel_settingCenter:
                Intent intent4=new Intent(activity, UserSettingCenterActivity.class);
                intent4.putExtra("oldPhone",phone);
                startActivityForResult(intent4,4);
                break;
        }
    }

    @Override
    public void onActivityResult(int requestCode,int resultCode,Intent data){
        super.onActivityResult(requestCode,resultCode,data);

        if(requestCode==0){
            if(resultCode==1){
                portrait=data.getIntExtra("newPortrait",portrait);
                Glide.with(rootView.getContext()).load(portrait).into(imPortrait);
            }
            if(resultCode==2){
                nickname=data.getStringExtra("newNickname");
                tvNickname.setText(nickname);
            }
        }

        if(requestCode==4){
            if(resultCode==1){
                phone=data.getStringExtra("newPhone");
                tvPhone.setText(phone);
            }
            if(resultCode==3){
                //更改头像
                portrait=R.drawable.defaultportrait;
                Glide.with(rootView.getContext()).load(portrait).into(imPortrait);

                //更改昵称
                nickname="立即登录";
                tvNickname.setText(nickname);

                //更改手机号
                phone="请登录";
                tvPhone.setText(phone);
            }
        }

    }
}
