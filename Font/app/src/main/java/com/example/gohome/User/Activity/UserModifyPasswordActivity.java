package com.example.gohome.User.Activity;

import android.os.Bundle;
import android.view.MenuItem;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import com.example.gohome.R;

public class UserModifyPasswordActivity extends AppCompatActivity {
    String oldPassword = "123456789", newPassword;
    EditText edOPW, edNPW, edNPW2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_modify_password);

        setTitle("修改密码");
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setHomeButtonEnabled(true);
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

        edOPW = findViewById(R.id.user_ed_oldPW);
        edNPW = findViewById(R.id.user_ed_newPW);
        edNPW2 = findViewById(R.id.user_ed_newPW2);

        findViewById(R.id.user_btn_modifyPassword).setOnClickListener(view -> {
            if (oldPassword == edOPW.getText().toString()) {
                //首先判断密码是否合法（未实现）

                newPassword = edNPW.getText().toString();
                if (newPassword == edNPW2.getText().toString()) {
                    //更新数据库的密码
                    oldPassword=newPassword;

                    Toast.makeText(UserModifyPasswordActivity.this, "密码修改成功！", Toast.LENGTH_SHORT).show();
                } else
                    Toast.makeText(UserModifyPasswordActivity.this, "两次填写的密码不一致！", Toast.LENGTH_SHORT).show();
            } else
                Toast.makeText(UserModifyPasswordActivity.this, "原密码填写错误！", Toast.LENGTH_SHORT).show();
        });
        findViewById(R.id.user_tv_forgetPW).setOnClickListener(view -> {
            //跳转界面，利用手机验证码修改密码
        });
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
}