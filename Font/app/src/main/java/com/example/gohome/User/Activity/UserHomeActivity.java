package com.example.gohome.User.Activity;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.example.gohome.R;
import com.example.gohome.User.Adapter.RecyclerViewAdapter;
import com.example.gohome.User.BottomLayout;
import com.example.gohome.User.Model.AdoptInfo;
import com.example.gohome.User.OpenMenu;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class UserHomeActivity extends AppCompatActivity implements View.OnClickListener {
    private RelativeLayout container;

    //     private LinearLayout container;
    private OpenMenu openMenu;
    private LinearLayout bl_menu;
    private BottomLayout bl_home, bl_me;

    private RecyclerView recyclerView;
    private RecyclerViewAdapter adapter;
    private List<AdoptInfo> infoList;

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_home);

        Objects.requireNonNull(getSupportActionBar()).hide();
        //状态栏透明
        getWindow().getDecorView().setSystemUiVisibility(
                View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN |
                        View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
        getWindow().setStatusBarColor(getResources().getColor(R.color.yellow));

        initBottom(); // 底部导航栏
        initRec(); // recycler view
    }

    private void initBottom() {
        container = findViewById(R.id.rel_container);
        openMenu = new OpenMenu(this);
        openMenu.init(container);

        bl_menu = findViewById(R.id.bl_menu);
        bl_menu.setOnClickListener(this);

        bl_home = findViewById(R.id.bl_home);
        bl_home.setNormalIcon(R.drawable.user_home_normal);
        bl_home.setFocusIcon(R.drawable.user_home);
        bl_home.setIconText("首页");
        bl_home.setFocused(true);
        bl_home.setOnClickListener(this);

        bl_me = findViewById(R.id.bl_me);
        bl_me.setNormalIcon(R.drawable.user_me_normal);
        bl_me.setFocusIcon(R.drawable.user_me);
        bl_me.setIconText("我的");
        bl_me.setFocused(false);
        bl_me.setOnClickListener(this);
    }

    private void initRec() {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);

        initData();

        adapter = new RecyclerViewAdapter(this, infoList);

        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(linearLayoutManager);
    }

    private void initData() {
        infoList = new ArrayList<>();
        infoList.add(new AdoptInfo(R.drawable.dog, "一一", "豫章故郡，洪都新府。星分翼轸，地接衡庐。襟三江而带五湖，控蛮荆而引瓯越。物华天宝，龙光射牛斗之墟；人杰地灵，徐孺下陈蕃之榻。雄州雾列，俊采星驰。台隍枕夷夏之交，宾主尽东南之美。都督阎公之雅望，棨戟遥临；宇文新州之懿范，襜帷暂驻。十旬休假，胜友如云；千里逢迎，高朋满座。腾蛟起凤，孟学士之词宗；紫电青霜，王将军之武库。家君作宰，路出名区；童子何知，躬逢胜饯。"));
        infoList.add(new AdoptInfo(R.drawable.cat2, "二二", "时维九月，序属三秋。潦水尽而寒潭清，烟光凝而暮山紫。俨骖騑于上路，访风景于崇阿；临帝子之长洲，得天人之旧馆。层峦耸翠，上出重霄；飞阁流丹，下临无地。鹤汀凫渚，穷岛屿之萦回；桂殿兰宫，即冈峦之体势。"));
        infoList.add(new AdoptInfo(R.drawable.dog2, "三三", "披绣闼，俯雕甍，山原旷其盈视，川泽纡其骇瞩。闾阎扑地，钟鸣鼎食之家；舸舰弥津，青雀黄龙之舳。云销雨霁，彩彻区明。落霞与孤鹜齐飞，秋水共长天一色。渔舟唱晚，响穷彭蠡之滨；雁阵惊寒，声断衡阳之浦。"));
        infoList.add(new AdoptInfo(R.drawable.cat, "四四", "遥襟甫畅，逸兴遄飞。爽籁发而清风生，纤歌凝而白云遏。睢园绿竹，气凌彭泽之樽；邺水朱华，光照临川之笔。四美具，二难并。穷睇眄于中天，极娱游于暇日。天高地迥，觉宇宙之无穷；兴尽悲来，识盈虚之有数。望长安于日下，目吴会于云间。地势极而南溟深，天柱高而北辰远。关山难越，谁悲失路之人？萍水相逢，尽是他乡之客。怀帝阍而不见，奉宣室以何年？"));
        infoList.add(new AdoptInfo(R.drawable.cat3, "五五", "嗟乎！时运不齐，命途多舛。冯唐易老，李广难封。屈贾谊于长沙，非无圣主；窜梁鸿于海曲，岂乏明时？所赖君子见机，达人知命。老当益壮，宁移白首之心？穷且益坚，不坠青云之志。酌贪泉而觉爽，处涸辙以犹欢。北海虽赊，扶摇可接；东隅已逝，桑榆非晚。孟尝高洁，空余报国之情；阮籍猖狂，岂效穷途之哭！"));
        infoList.add(new AdoptInfo(R.drawable.dog1, "六六", "勃，三尺微命，一介书生。无路请缨，等终军之弱冠；有怀投笔，慕宗悫之长风。舍簪笏于百龄，奉晨昏于万里。非谢家之宝树，接孟氏之芳邻。他日趋庭，叨陪鲤对；今兹捧袂，喜托龙门。杨意不逢，抚凌云而自惜；钟期既遇，奏流水以何惭？"));
        infoList.add(new AdoptInfo(R.drawable.cat1, "七七", "呜乎！胜地不常，盛筵难再；兰亭已矣，梓泽丘墟。临别赠言，幸承恩于伟饯；登高作赋，是所望于群公。敢竭鄙怀，恭疏短引；一言均赋，四韵俱成。请洒潘江，各倾陆海云尔："));
    }

    private void showMenu() {
        if (openMenu == null) {
            openMenu = new OpenMenu(this);
            openMenu.init(container);
        }
        openMenu.show(container);
    }

    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.bl_home:
                bl_home.setFocused(true);
                bl_me.setFocused(false);
                break;
            case R.id.bl_me:
                bl_home.setFocused(false);
                bl_me.setFocused(true);
                break;
            case R.id.bl_menu:
                showMenu();
                break;
        }
    }

}
