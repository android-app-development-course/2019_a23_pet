package com.example.gohome.User.Activity;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.gohome.R;

public class UserPersonalInforActivity extends AppCompatActivity implements View.OnClickListener {
    private ImageView imPortrait;
    private int portrait=R.drawable.defaultportrait;
    private TextView tvNickname;
    private String nickname;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_personal_infor);

        setTitle("个人信息");
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null){
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

    private void init(){
        findViewById(R.id.user_rel_portrait).setOnClickListener(this);
        findViewById(R.id.user_rel_nickname).setOnClickListener(this);
        findViewById(R.id.user_rel_address).setOnClickListener(this);

        //初始化头像、昵称
        Intent receivedIntent = getIntent();

        portrait=receivedIntent.getIntExtra("oldPortrait",portrait);
        imPortrait=(ImageView)findViewById(R.id.user_iv_portrait2);
        Glide.with(this).load(portrait).into(imPortrait);

        nickname = receivedIntent.getStringExtra("oldNickname");
        tvNickname=(TextView)findViewById(R.id.user_tv_nickname2);
        tvNickname.setText(nickname);
    }

    @Override
    public void onClick(View v){
        switch (v.getId()){
            case R.id.user_rel_portrait:
                break;
            case R.id.user_rel_nickname:
                Intent intent=new Intent(this,UserModifyNicknameActivity.class);
                intent.putExtra("oldNickname",nickname);
                startActivityForResult(intent,1);
                break;
            case R.id.user_rel_address:
                startActivity(new Intent(this, UserAddressActivity.class));
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode,int resultCode,Intent data){
        super.onActivityResult(requestCode,resultCode,data);

        if(requestCode==0){
            if(resultCode==1){
                portrait=data.getIntExtra("newNickname",portrait);
                Glide.with(this).load(portrait).into(imPortrait);

                Intent backIntent = new Intent();
                backIntent.putExtra("newPortrait", portrait);
                setResult(1, backIntent);
            }
        }

        if(requestCode==1){
            if(resultCode==1){
                nickname=data.getStringExtra("newNickname");
                tvNickname.setText(nickname);

                Intent backIntent = new Intent();
                backIntent.putExtra("newNickname", nickname);
                setResult(2, backIntent);
            }
        }

    }
}
