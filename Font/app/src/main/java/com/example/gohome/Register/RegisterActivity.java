package com.example.gohome.Register;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import com.example.gohome.MainActivity;
import com.example.gohome.R;
import com.rengwuxian.materialedittext.MaterialEditText;

import br.com.simplepass.loadingbutton.customViews.CircularProgressButton;
import co.ceryle.radiorealbutton.RadioRealButton;

public class RegisterActivity extends AppCompatActivity {

    private MaterialEditText et_registerUserName,et_registerUserAddress,et_registerUserPassword,et_registerUserTel;
    private RadioRealButton radBut_registerGenderBoy,radBut_registerGenderGirl;
    private CircularProgressButton btn_registerSubmit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        setTitle("注册");
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);


        et_registerUserName = findViewById(R.id.et_registerUserName);
        et_registerUserAddress = findViewById(R.id.et_registerUserAddress);
        et_registerUserPassword = findViewById(R.id.et_registerUserPsd);
        et_registerUserTel = findViewById(R.id.et_registerUserTel);
        btn_registerSubmit = findViewById(R.id.btn_registerSubmit);


        init();
    }

    private void init(){
        btn_registerSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(RegisterActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });
    }

}
