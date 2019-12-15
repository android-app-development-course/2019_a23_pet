package com.example.gohome.User.Fragment;

import android.app.Activity;
import android.content.ContentResolver;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.DrawableRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.bumptech.glide.Glide;
import com.example.gohome.R;
import com.example.gohome.User.Activity.UserAddGroupActivity;
import com.example.gohome.User.Activity.UserAdoptProActivity;
import com.example.gohome.User.Activity.UserHomeActivity;
import com.example.gohome.User.Activity.UserModifyNicknameActivity;
import com.example.gohome.User.Activity.UserPersonalInforActivity;
import com.example.gohome.User.Activity.UserSendProActivity;
import com.example.gohome.User.Activity.UserSettingCenterActivity;
import com.example.gohome.User.ImageDialog;
import com.example.gohome.ui.login.LoginActivity;
import com.gitonway.lee.niftymodaldialogeffects.lib.NiftyDialogBuilder;

import java.io.File;

public class UserMineFragment extends Fragment implements View.OnClickListener {

    private View rootView;
    private Activity activity;

    private ImageView imPortrait;
    private Uri portrait;

    private TextView tvNickname;
    private String nickname = "张咩阿";

    private TextView tvPhone;
    private StringBuffer tmpphone = new StringBuffer("15626431234");
    private String phone = tmpphone.substring(0, 7) + "****";

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
            activity = getActivity();

            // 在此初始化！！！
            init();
        }
        return rootView;
    }

    public void init() {
        rootView.findViewById(R.id.user_rel_personalInfo).setOnClickListener(this);
        rootView.findViewById(R.id.user_rel_adoptPro).setOnClickListener(this);
        rootView.findViewById(R.id.user_rel_sendPro).setOnClickListener(this);
        rootView.findViewById(R.id.user_rel_addGroup).setOnClickListener(this);
        rootView.findViewById(R.id.user_rel_settingCenter).setOnClickListener(this);

        //初始化头像
        imPortrait = rootView.findViewById(R.id.user_iv_portrait);
        portrait = getResourcesUri(R.drawable.timg);
        Glide.with(rootView.getContext()).load(portrait).into(imPortrait);

        //初始化昵称
        tvNickname = rootView.findViewById(R.id.user_tv_nickname);
        tvNickname.setText(nickname);
        tvNickname.setOnClickListener(this);

        //初始化手机号
        tvPhone = rootView.findViewById(R.id.user_tv_phone);
        tvPhone.setText(phone);
    }

    @Override
    public void onClick(View v) {
        if (((UserHomeActivity) getActivity()).isLogin == true)
            switch (v.getId()) {
                case R.id.user_rel_personalInfo:
                    Intent intent = new Intent(activity, UserPersonalInforActivity.class);
                    intent.putExtra("oldPortrait", portrait.toString());
                    intent.putExtra("oldNickname", nickname);
                    startActivityForResult(intent, 0);
                    break;
                case R.id.user_rel_adoptPro:
                    startActivity(new Intent(activity, UserAdoptProActivity.class));
                    break;
                case R.id.user_rel_sendPro:
                    startActivity(new Intent(activity, UserSendProActivity.class));
                    break;
                case R.id.user_rel_addGroup:
                    Intent intent3 = new Intent(activity, UserAddGroupActivity.class);
                    intent3.putExtra("group", "");
                    startActivity(intent3);
                    break;
                case R.id.user_rel_settingCenter:
                    Intent intent4 = new Intent(activity, UserSettingCenterActivity.class);
                    intent4.putExtra("oldPhone", phone);
                    startActivityForResult(intent4, 4);
                    break;
            }
        else
            switch (v.getId()) {
                case R.id.user_tv_nickname:
                    startActivityForResult(new Intent(activity, LoginActivity.class), 5);
                    break;
                default:
                    NiftyDialogBuilder dialogBuilderSelect = NiftyDialogBuilder.getInstance(activity);
                    dialogBuilderSelect
                            .withTitle("请登录")
                            .withMessage("立即登录")
                            .withDialogColor(getResources().getColor(R.color.orange))                               //def  | withDialogColor(int resid)
                            .withButton1Text("确定")                                      //def gone
                            .withButton2Text("取消")                                  //def gone
                            .setButton1Click(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    dialogBuilderSelect.dismiss();
                                    startActivityForResult(new Intent(activity, LoginActivity.class), 5);
                                }
                            })
                            .setButton2Click(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    Toast.makeText(activity, "取消登录", Toast.LENGTH_SHORT).show();
                                    dialogBuilderSelect.dismiss();
                                }
                            })
                            .show();
            }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 0) {
            if (resultCode == 1) {
                if (data.getStringExtra("newNickname") != null) {
                    nickname = data.getStringExtra("newNickname");
                    tvNickname.setText(nickname);
                }
                if (data.getStringExtra("newPortrait") != null) {
                    portrait = Uri.parse(data.getStringExtra("newPortrait"));
                    Glide.with(rootView.getContext()).load(portrait).into(imPortrait);
                }
            }
        }

        if (requestCode == 4) {
            if (resultCode == 1) {
                phone = data.getStringExtra("newPhone");
                tvPhone.setText(phone);
            }
            if (resultCode == 3) {
                ((UserHomeActivity) getActivity()).isLogin = false;
                //更改头像
//                portrait = getResourcesUri(R.drawable.defaultportrait);
                Glide.with(rootView.getContext()).load(getResourcesUri(R.drawable.defaultportrait)).into(imPortrait);

                //更改昵称
//                nickname = "立即登录";
                tvNickname.setText("立即登录");

                //更改手机号
//                phone = "请登录";
                tvPhone.setText("请登录");
            }
        }

        if (requestCode == 5) {
            if (resultCode == 1) {
                ((UserHomeActivity) getActivity()).isLogin = true;
                //更改头像
//                portrait = getResourcesUri(R.drawable.timg);
                Glide.with(rootView.getContext()).load(portrait).into(imPortrait);

                //更改昵称
//                nickname = "张咩阿";
                tvNickname.setText(nickname);

                //更改手机号
//                phone = tmpphone.substring(0, 7) + "****";
                tvPhone.setText(phone);
            }
        }
    }

    private Uri getResourcesUri(@DrawableRes int id) {
        Resources resources = getResources();
        Uri drawableUri = Uri.parse(ContentResolver.SCHEME_ANDROID_RESOURCE + "://" +
                resources.getResourcePackageName(id) + "/" +
                resources.getResourceTypeName(id) + "/" +
                resources.getResourceEntryName(id));
        return drawableUri;
    }
}
