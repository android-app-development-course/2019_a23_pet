package com.example.gohome.User.Activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.gohome.Entity.AdoptInfo;
import com.example.gohome.R;

public class UserAdoptActivity extends Activity {
    private ImageView iv_info_photo;
    private TextView tv_info_name;
    private TextView tv_info_desc;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_adopt_info);

        iv_info_photo = findViewById(R.id.user_iv_info_photo);
        tv_info_name = findViewById(R.id.user_tv_info_name);
        tv_info_desc = findViewById(R.id.user_tv_info_desc);

        Intent intent = getIntent();
        AdoptInfo info = (AdoptInfo) intent.getSerializableExtra("info");

        Glide.with(this).load(info.getPhotoId()).into(iv_info_photo);
        tv_info_name.setText(info.getPetName());
        tv_info_desc.setText(info.getDesc());
    }
}
