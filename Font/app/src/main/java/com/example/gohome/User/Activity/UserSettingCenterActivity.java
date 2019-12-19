package com.example.gohome.User.Activity;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.gohome.MainActivity;
import com.example.gohome.R;

public class UserSettingCenterActivity extends AppCompatActivity implements View.OnClickListener {
    private TextView tvPhone;
    private String phone;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_setting_center);

        setTitle("设置中心");
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setHomeButtonEnabled(true);
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

        init();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                this.finish(); // user_back button
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void init() {
        findViewById(R.id.user_rel_phone).setOnClickListener(this);
        findViewById(R.id.user_rel_accountPW).setOnClickListener(this);
        findViewById(R.id.user_rel_systemSetting).setOnClickListener(this);
        findViewById(R.id.user_rel_logout).setOnClickListener(this);

        //初始化手机号
        Intent receivedIntent = getIntent();
        phone = receivedIntent.getStringExtra("oldPhone");
        tvPhone = (TextView) findViewById(R.id.user_tv_phone2);
        tvPhone.setText(phone);
    }

    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.user_rel_phone:
                Intent intent = new Intent(this, UserModifyPhoneActivity.class);
                intent.putExtra("oldPhone", phone);
                startActivityForResult(intent, 0);
                break;
            case R.id.user_rel_accountPW:
                startActivity(new Intent(this, UserModifyPasswordActivity.class));
                break;
            case R.id.user_rel_systemSetting:
                startActivity(new Intent(this, UserSystemSettingActivity.class));
                break;
            case R.id.user_rel_logout:
                SharedPreferences sharedPreferences = getApplicationContext().getSharedPreferences("userInfo", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.clear();
                editor.commit();
                Intent backIntent = new Intent(getApplicationContext(), MainActivity.class);
                backIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(backIntent);
                this.finish();
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 0) {
            if (resultCode == 1) {
                phone = data.getStringExtra("newPhone");
                tvPhone.setText(phone);

                Intent backIntent = new Intent();
                backIntent.putExtra("newPhone", phone);
                setResult(1, backIntent);
            }
        }

    }
}
