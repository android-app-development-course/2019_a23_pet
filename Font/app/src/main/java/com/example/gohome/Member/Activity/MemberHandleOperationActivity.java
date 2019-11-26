package com.example.gohome.Member.Activity;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.Toast;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import com.example.gohome.R;
import com.sdsmdg.tastytoast.TastyToast;

import br.com.simplepass.loadingbutton.customViews.CircularProgressButton;
import co.ceryle.radiorealbutton.RadioRealButtonGroup;
import fj.edittextcount.lib.FJEditTextCount;

public class MemberHandleOperationActivity extends AppCompatActivity {

    private CircularProgressButton btn_submit;
    private FJEditTextCount et_description;
    private RadioRealButtonGroup radGro_handleOperationResult;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_member_handle_operation);

        setTitle("填写操作信息");
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null){
            actionBar.setHomeButtonEnabled(true);
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

        btn_submit = findViewById(R.id.btn_handleOperationSubmit);
        et_description = findViewById(R.id.et_handleOperationDes);
        radGro_handleOperationResult = findViewById(R.id.radGro_handleOperationResult);



        //初始化控件
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

        Bitmap bitmapDone =  BitmapFactory.decodeResource(getResources(), R.drawable.ic_action_done);
        Bitmap bitmapFail = BitmapFactory.decodeResource(getResources(),R.drawable.ic_action_fail);

        btn_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //开始动画
                btn_submit.startAnimation();


//                btn.setProgress(100000);      //模拟表单提交过程，但是好像并没有显示出来？？
                //设置提交成功的图标和颜色
                btn_submit.doneLoadingAnimation(getResources().getColor(R.color.green), bitmapDone);
                //设置提交失败的图标和颜色
                //btn.doneLoadingAnimation(getResources().getColor(R.color.red), bitmapFail);

                //提示提交成功toast
                Toast toast = TastyToast.makeText(MemberHandleOperationActivity.this, "提交成功!", TastyToast.LENGTH_LONG, TastyToast.SUCCESS);
                //设置toast显示位置
                toast.setGravity(Gravity.CENTER,0,0);
                //调用show使得toast得以显示
                toast.show();

                //清空editText和选择框
                et_description.setText("");
                radGro_handleOperationResult.deselect();

                //还原提交按钮
//                btn.revertAnimation();

            }
        });


    }

}
