package com.example.gohome.User.Activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.gohome.R;
import com.example.gohome.Entity.AdoptInfo;

public class UserAdoptActivity extends Activity {
    private ImageView iv_photo;
    private TextView tv_petName;
    private TextView tv_desc;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_adopt_info);

        iv_photo = findViewById(R.id.user_iv_info_photo);
        tv_petName = findViewById(R.id.user_tv_info_name);
        tv_desc = findViewById(R.id.user_tv_info_desc);

        Intent intent = getIntent();
        AdoptInfo info = (AdoptInfo) intent.getSerializableExtra("info");

        iv_photo.setImageResource(info.getPhotoId());
        tv_petName.setText(info.getPetName());
        tv_desc.setText(info.getDesc());
    }
}
