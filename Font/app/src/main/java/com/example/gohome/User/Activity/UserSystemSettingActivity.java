package com.example.gohome.User.Activity;

import android.os.Bundle;
import android.view.MenuItem;
import android.view.WindowManager;
import android.widget.Toast;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import com.example.gohome.R;

import br.com.simplepass.loadingbutton.customViews.CircularProgressButton;
import co.ceryle.radiorealbutton.RadioRealButtonGroup;

public class UserSystemSettingActivity extends AppCompatActivity {
    private RadioRealButtonGroup radGro_sound;
    private RadioRealButtonGroup radGro_vibration;
    private CircularProgressButton btn_modifySystemSetting;

    //声音、振动
    int sound, vibration;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_system_setting);

        setTitle("系统设置");
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setHomeButtonEnabled(true);
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

        radGro_sound = findViewById(R.id.user_radGro_sound);
        radGro_vibration = findViewById(R.id.user_radGro_vibration);
        btn_modifySystemSetting = findViewById(R.id.user_btn_modifySystemSetting);

        radGro_sound.setOnClickedButtonListener((btn, pos) -> sound = pos);

        radGro_sound.setOnPositionChangedListener((btn, curPos, lastPos) -> sound = curPos);

        radGro_vibration.setOnClickedButtonListener((btn, pos) -> vibration = pos);

        radGro_vibration.setOnPositionChangedListener((btn, curPos, lastPos) -> vibration = curPos);

        btn_modifySystemSetting.setOnClickListener(view -> Toast.makeText(UserSystemSettingActivity.this, "修改成功！", Toast.LENGTH_SHORT).show());
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