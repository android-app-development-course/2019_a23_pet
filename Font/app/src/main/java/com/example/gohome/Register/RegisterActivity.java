package com.example.gohome.Register;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Toast;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import com.example.gohome.Entity.UserMessage;
import com.example.gohome.MainActivity;
import com.example.gohome.R;
import com.rengwuxian.materialedittext.MaterialEditText;

import br.com.simplepass.loadingbutton.customViews.CircularProgressButton;
import co.ceryle.radiorealbutton.RadioRealButton;

public class RegisterActivity extends AppCompatActivity {

    private MaterialEditText et_registerUserName,et_registerUserAddress,et_registerUserPassword,et_registerUserTel;
    private RadioRealButton radBtn_registerGenderBoy,radBtn_registerGenderGirl;
    private CircularProgressButton btn_registerSubmit;
    private UserMessage userMessage;

    public static final String USERNAME_REX = "^[a-zA-Z_\u4e00-\u9fa5]{2,8}$";
    public static final String PASSWORD_REX = "^[a-zA-Z0-9_]{8,16}$";
    public static final String TELEPHONE_REX = "^((13[0-9])|(14[5|7])|(15([0-3]|[5-9]))|(17[013678])|(18[0,5-9]))\\d{8}$";

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
        radBtn_registerGenderBoy = findViewById(R.id.radBtn_registerGenderBoy);
        radBtn_registerGenderGirl = findViewById(R.id.radBtn_registerGenderGirl);
        btn_registerSubmit = findViewById(R.id.btn_registerSubmit);

        userMessage = new UserMessage();

        init();
    }

    private void init(){

        radBtn_registerGenderBoy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                userMessage.setGender(0);
            }
        });

        radBtn_registerGenderGirl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                userMessage.setGender(1);
            }
        });

        btn_registerSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String username, userPassword,telephone, address;
                username = et_registerUserName.getText().toString();
                userPassword = et_registerUserPassword.getText().toString();
                telephone = et_registerUserTel.getText().toString();
                address = et_registerUserAddress.getText().toString();

                if(!username.matches(USERNAME_REX)){
                    Toast.makeText(v.getContext(), "用户名无效，请重新输入", Toast.LENGTH_SHORT).show();
                    return;
                }
                if(!userPassword.matches(PASSWORD_REX)){
                    Toast.makeText(v.getContext(), "密码无效，请重新输入", Toast.LENGTH_SHORT).show();
                    return;
                }
                if(!telephone.matches(TELEPHONE_REX)){
                    Toast.makeText(v.getContext(), "联系方式无效，请重新输入", Toast.LENGTH_SHORT).show();
                    return;
                }




//                Intent intent = new Intent(RegisterActivity.this, MainActivity.class);
//                startActivity(intent);
            }
        });
    }

}
