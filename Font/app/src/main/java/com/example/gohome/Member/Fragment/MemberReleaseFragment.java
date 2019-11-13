package com.example.gohome.Member.Fragment;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;


import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.gohome.R;
import com.sdsmdg.tastytoast.TastyToast;

import br.com.simplepass.loadingbutton.customViews.CircularProgressButton;
import butterknife.ButterKnife;
import co.ceryle.radiorealbutton.RadioRealButton;
import co.ceryle.radiorealbutton.RadioRealButtonGroup;

public class MemberReleaseFragment extends Fragment  {

    CircularProgressButton btn;
    EditText et_releaseName;
    EditText et_releaseAge;
    EditText et_releaseType;
    EditText et_releaseDescription;
    RadioRealButtonGroup radGro_releaseGender;
    RadioRealButtonGroup radGro_releaseSterilizine;
    RadioRealButtonGroup radGro_releaseVaccine;
    RadioRealButton radBut_releaseGenderBoy;
    RadioRealButton radBut_releaseGenderGirl;
    RadioRealButton radBut_releaseSterilizineYes;
    RadioRealButton radBut_releaseSterilizineNo;
    RadioRealButton radBut_releaseVaccineYes;
    RadioRealButton radBut_releaseVaccineNo;

    int Gender;
    int Sterilizine;
    int Vaccine;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState ){
        View view = inflater.inflate(R.layout.fragment_member_release,null);
        ButterKnife.bind(this, view);

//        btn_submit = view.findViewById(R.id.btn_submit);
        btn = view.findViewById(R.id.btn_id);
        et_releaseName = view.findViewById(R.id.et_releaseName);
        et_releaseAge = view.findViewById(R.id.et_releaseAge);
        et_releaseType = view.findViewById(R.id.et_releaseType);
        et_releaseDescription = view.findViewById(R.id.et_releaseDescription);
        radGro_releaseGender = view.findViewById(R.id.radGro_releaseGender);
        radGro_releaseSterilizine = view.findViewById(R.id.radGro_releaseSterilizine);
        radGro_releaseVaccine = view.findViewById(R.id.radGro_releaseVaccine);
        radBut_releaseGenderBoy = view.findViewById(R.id.radBut_releaseGenderBoy);
        radBut_releaseGenderGirl = view.findViewById(R.id.radBut_releaseGenderGirl);
        radBut_releaseSterilizineYes = view.findViewById(R.id.radBut_releaseSterilizineYes);
        radBut_releaseSterilizineNo = view.findViewById(R.id.radBut_releaseSterilizineNo);
        radBut_releaseVaccineYes = view.findViewById(R.id.radBut_releaseVaccineYes);
        radBut_releaseVaccineNo = view.findViewById(R.id.radBut_releaseVaccineNo);


        try {
            init();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return view;
    }


    private void init() throws InterruptedException {

        Bitmap bitmapDone =  BitmapFactory.decodeResource(this.getContext().getResources(), R.drawable.ic_action_done);
        Bitmap bitmapFail = BitmapFactory.decodeResource(this.getContext().getResources(),R.drawable.ic_action_fail);

        // onClickButton listener detects any click performed on buttons by touch
        radGro_releaseGender.setOnClickedButtonListener(new RadioRealButtonGroup.OnClickedButtonListener() {
            @Override
            public void onClickedButton(RadioRealButton button, int position) {
                Toast.makeText(getActivity(), "Clicked! Position: " + position, Toast.LENGTH_SHORT).show();
                Gender = position;
            }
        });
        radGro_releaseSterilizine.setOnClickedButtonListener(new RadioRealButtonGroup.OnClickedButtonListener() {
            @Override
            public void onClickedButton(RadioRealButton button, int position) {
                Toast.makeText(getActivity(), "Clicked! Position: " + position, Toast.LENGTH_SHORT).show();
                Sterilizine = position;
            }
        });
        radGro_releaseVaccine.setOnClickedButtonListener(new RadioRealButtonGroup.OnClickedButtonListener() {
            @Override
            public void onClickedButton(RadioRealButton button, int position) {
                Toast.makeText(getActivity(), "Clicked! Position: " + position, Toast.LENGTH_SHORT).show();
                Vaccine = position;
            }
        });


        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btn.startAnimation();

                //获取表单的数据
                System.out.println("宠物姓名："+et_releaseName.getText());
                System.out.println("宠物年龄："+et_releaseAge.getText());
                System.out.println("宠物类型："+et_releaseType.getText());
                System.out.println("宠物描述："+et_releaseDescription.getText());
                System.out.println("宠物性别："+Gender);
                System.out.println("绝育情况："+Sterilizine);
                System.out.println("疫苗情况："+Vaccine);


//                btn.setProgress(100000);      //模拟表单提交过程，但是好像并没有显示出来？？
                //设置提交成功的图标和颜色
                btn.doneLoadingAnimation(getResources().getColor(R.color.green), bitmapDone);
                //设置提交失败的图标和颜色
                //btn.doneLoadingAnimation(getResources().getColor(R.color.red), bitmapFail);

                //提示提交成功toast
                Toast toast = TastyToast.makeText(getActivity(), "提交成功!", TastyToast.LENGTH_LONG, TastyToast.SUCCESS);
                //设置toast显示位置
                toast.setGravity(Gravity.CENTER,0,0);
                //调用show使得toast得以显示
                toast.show();

                //清空editText和选择框
                et_releaseName.setText("");
                et_releaseAge.setText("");
                et_releaseType.setText("");
                et_releaseDescription.setText("");
                radGro_releaseGender.deselect();
                radGro_releaseVaccine.deselect();
                radGro_releaseSterilizine.deselect();


                //还原提交按钮
//                btn.revertAnimation();

            }
        });

    }
}
