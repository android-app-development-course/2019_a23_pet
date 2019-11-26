package com.example.gohome.User.Activity;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.gohome.R;
import com.example.gohome.User.TitleBar;

public class UserModifyNicknameActivity extends AppCompatActivity {
    private EditText etNickname;
    private String nickname;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_modify_nickname);

        setTitle("修改昵称");
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setHomeButtonEnabled(true);
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

        Intent receivedIntent = getIntent();
        nickname = receivedIntent.getStringExtra("oldNickname");
        etNickname = (EditText) findViewById(R.id.user_et_nickname);
        etNickname.setText(nickname);
        etNickname.setSelection(etNickname.length());
        findViewById(R.id.user_btn_modifyNickname).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                nickname = etNickname.getText().toString().trim();

                if (nickname.length() > 0 && nickname.length() <= 10) {
                    Intent backIntent = new Intent();
                    backIntent.putExtra("newNickname", nickname);
                    setResult(1, backIntent);
                    Toast.makeText(UserModifyNicknameActivity.this, "修改成功！", Toast.LENGTH_SHORT).show();
                    UserModifyNicknameActivity.this.finish();
                } else
                    Toast.makeText(UserModifyNicknameActivity.this, "昵称长度为1～10，请重新输入！", Toast.LENGTH_SHORT).show();
            }
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
