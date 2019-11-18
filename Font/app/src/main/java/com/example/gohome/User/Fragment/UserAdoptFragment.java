package com.example.gohome.User.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.gohome.R;
import com.example.gohome.User.Activity.UserAdoptActivity;
import com.example.gohome.User.Adapter.FoldingCellListAdapter;
import com.example.gohome.Entity.AdoptInfo;
import com.qlh.dropdownmenu.DropDownMenu;
import com.qlh.dropdownmenu.view.MultiMenusView;
import com.qlh.dropdownmenu.view.SingleMenuView;
import com.ramotion.foldingcell.FoldingCell;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class UserAdoptFragment extends Fragment {
    private View rootView;

    private List<AdoptInfo> infoList;

    private DropDownMenu mDropDownMenu;
    private MultiMenusView multiMenusView;//多级菜单
    private SingleMenuView singleMenuView1;//单级菜单
    private SingleMenuView singleMenuView2;//单级菜单

    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             Bundle savedInstanceState) {
        if (rootView != null){
            ViewGroup parent = (ViewGroup) rootView.getParent();
            if(parent != null){
                parent.removeView(rootView);
            }
        } else {
            rootView = inflater.inflate(R.layout.fragment_user_adopt, null);
            initDropMenus();
//            initRec();
            initListView();
        }
        return rootView;
    }

//    private void initRec() {
//        initAdoptInfo();
//
//        RecyclerView recyclerView = rootView.findViewById(R.id.recyclerView);
//
//        RecyclerViewAdapter adapter = new RecyclerViewAdapter(this.getContext(), infoList);
//        recyclerView.setAdapter(adapter);
//
//        recyclerView.setHasFixedSize(true);
//        //设置列表默认动画效果
//        recyclerView.setItemAnimator(new DefaultItemAnimator());
//        //设置列表显示方式
//        recyclerView.setLayoutManager(new LinearLayoutManager(this.getContext()));
//    }

    private void initListView(){
        initAdoptInfo();

        ListView listView = rootView.findViewById(R.id.user_lv_adopt);

        // 创建保存元素及其状态的自定义适配器(我们需要保存可重用元素的展开元素的id)
        final FoldingCellListAdapter adapter = new FoldingCellListAdapter(this.getContext(), infoList);

        adapter.setDefaultRequestBtnClickListener(view -> {
            Intent intent = new Intent(this.getContext(), UserAdoptActivity.class);
            startActivity(intent);
        });

        listView.setAdapter(adapter);

        listView.setOnItemClickListener((adapterView, view, i, l) -> {
            listView.setSelection(i);
            // 切换已单击的单元格状态
            ((FoldingCell) view).toggle(false);
            // 在适配器中注册所选单元格的状态已被切换
            adapter.registerToggle(i);
        });
    }

    private void initAdoptInfo() {
        infoList = new ArrayList<>();
        infoList.add(new AdoptInfo(R.drawable.timg,"一一","狗狗",1,"2岁","豫章故郡，洪都新府。星分翼轸，地接衡庐。襟三江而带五湖，控蛮荆而引瓯越。物华天宝，龙光射牛斗之墟；人杰地灵，徐孺下陈蕃之榻。雄州雾列，俊采星驰。台隍枕夷夏之交，宾主尽东南之美。都督阎公之雅望，棨戟遥临；宇文新州之懿范，襜帷暂驻。十旬休假，胜友如云；千里逢迎，高朋满座。腾蛟起凤，孟学士之词宗；紫电青霜，王将军之武库。家君作宰，路出名区；童子何知，躬逢胜饯。","广东广州","5小时前",1,0,"木鱼水心"));
        infoList.add(new AdoptInfo(R.drawable.cat2,"二二","猫猫",0,"1岁半","时维九月，序属三秋。潦水尽而寒潭清，烟光凝而暮山紫。俨骖騑于上路，访风景于崇阿；临帝子之长洲，得天人之旧馆。层峦耸翠，上出重霄；飞阁流丹，下临无地。鹤汀凫渚，穷岛屿之萦回；桂殿兰宫，即冈峦之体势。","四川成都","8小时前",1,0,"木鱼水心"));
        infoList.add(new AdoptInfo(R.drawable.dog2,"三三","狗狗",1,"1岁","披绣闼，俯雕甍，山原旷其盈视，川泽纡其骇瞩。闾阎扑地，钟鸣鼎食之家；舸舰弥津，青雀黄龙之舳。云销雨霁，彩彻区明。落霞与孤鹜齐飞，秋水共长天一色。渔舟唱晚，响穷彭蠡之滨；雁阵惊寒，声断衡阳之浦。","辽宁铁岭","15小时前",1,0,"木鱼水心"));
        infoList.add(new AdoptInfo(R.drawable.cat,"四四", "猫猫",0,"11个月","遥襟甫畅，逸兴遄飞。爽籁发而清风生，纤歌凝而白云遏。睢园绿竹，气凌彭泽之樽；邺水朱华，光照临川之笔。四美具，二难并。穷睇眄于中天，极娱游于暇日。天高地迥，觉宇宙之无穷；兴尽悲来，识盈虚之有数。望长安于日下，目吴会于云间。地势极而南溟深，天柱高而北辰远。关山难越，谁悲失路之人？萍水相逢，尽是他乡之客。怀帝阍而不见，奉宣室以何年？","内蒙古鄂尔多斯","1天前",1,0,"木鱼水心"));
        infoList.add(new AdoptInfo(R.drawable.cat3,"五五","猫猫",0,"5个月","嗟乎！时运不齐，命途多舛。冯唐易老，李广难封。屈贾谊于长沙，非无圣主；窜梁鸿于海曲，岂乏明时？所赖君子见机，达人知命。老当益壮，宁移白首之心？穷且益坚，不坠青云之志。酌贪泉而觉爽，处涸辙以犹欢。北海虽赊，扶摇可接；东隅已逝，桑榆非晚。孟尝高洁，空余报国之情；阮籍猖狂，岂效穷途之哭！","浙江杭州","1天前",1,0,"木鱼水心"));
        infoList.add(new AdoptInfo(R.drawable.dog1,"六六","狗狗",1,"8个月","勃，三尺微命，一介书生。无路请缨，等终军之弱冠；有怀投笔，慕宗悫之长风。舍簪笏于百龄，奉晨昏于万里。非谢家之宝树，接孟氏之芳邻。他日趋庭，叨陪鲤对；今兹捧袂，喜托龙门。杨意不逢，抚凌云而自惜；钟期既遇，奏流水以何惭？","上海普陀","2天前",1,0,"木鱼水心"));
        infoList.add(new AdoptInfo(R.drawable.cat1,"七七","猫猫",1,"1岁7个月","呜乎！胜地不常，盛筵难再；兰亭已矣，梓泽丘墟。临别赠言，幸承恩于伟饯；登高作赋，是所望于群公。敢竭鄙怀，恭疏短引；一言均赋，四韵俱成。请洒潘江，各倾陆海云尔：","湖南长沙","3天前",1,0,"木鱼水心"));
    }

    private void initListener() {
        multiMenusView.setOnSelectListener(showText -> {
            mDropDownMenu.setTabText(showText);
            mDropDownMenu.closeMenu();
        });
        singleMenuView1.setOnSelectListener((position, showText) -> {
            mDropDownMenu.setTabText(showText);
            mDropDownMenu.closeMenu();
        });
        singleMenuView2.setOnSelectListener((position, showText) -> {
            mDropDownMenu.setTabText(showText);
            mDropDownMenu.closeMenu();
        });
    }

    private void initDropMenus() {
        mDropDownMenu = rootView.findViewById(R.id.dropDownMenu);
        //菜单头部选项
        String[] headers = new String[]{"地区", "类型", "性别"};

        //菜单列表视图
        List<View> popupViews = new ArrayList<>();

        //初始化多级菜单
        final String[] levelOneMenu = {"北京市","天津市","上海市","重庆市",
                "辽宁省","吉林省","黑龙江省","内蒙古自治区",
                "河北省","河南省","山东省","山西省",
                "江苏省","浙江省","福建省","安徽省",
                "湖北省","湖南省","江西省",
                "广东省","广西壮族自治区","海南省",
                "四川省","贵州省","云南省",
                "陕西省","甘肃省","宁夏回族自治区","青海省",
                "西藏自治区", "新疆维吾尔自治区",
                "香港特别行政区","澳门特别行政区","台湾省"};
        final String[][] levelTwoMenu = {
                {"东城区","西城区","朝阳区","海淀区","顺义区","通州区","大兴区","房山区","昌平区","密云区","怀柔区","丰台区","延庆区","平谷区","门头沟区","石景山区"},
                {"和平区","河东区","河西区","南开区","河北区","红桥区","滨海新区","东丽区","西青区","津南区","北辰区","武清区","宝坻区","宁河区","静安区","蓟州区"},
                {"黄浦区","徐汇区","长宁区","静安区","普陀区","蛇口区","杨浦区","闵行区","宝山区","嘉定区","浦东新区","金山区","松江区","青浦区","奉贤区","崇明区"},
                {"渝中区","万州区","涪陵区","江北区","大渡口区","沙坪坝区","九龙坡区","南岸区","北碚区","綦江区","大足区","渝北区","巴南区","黔江区","长寿区","江峰区","合川区","永川区","南川区","璧山区","铜梁区","潼南区","荣昌区","开州区","梁平区","武陵区","城口县","丰都县","垫江县","忠县","云阳县","奉节县","巫山县","巫溪县","石柱土家族自治县","秀山土家族苗族自治县","酉阳土家族苗族自治县","彭水苗族土家族自治县"},

                {"沈阳市","大连市","铁岭市","本溪市","朝阳市","阜新市","辽阳市","盘锦市","营口市","抚顺市","锦州市","鞍山市","丹东市","葫芦岛市"},//辽宁省14个
                {"长春市","吉林市","通化市","四平市","松原市","辽源市","白城市","白山市","延边朝鲜族自治州"},//吉林省9个
                {"哈尔滨市","大庆市","牡丹江市","齐齐哈尔市","绥化市","鸡西市","双鸭山市","鹤岗市","七台河市","伊春市","佳木斯市","黑河市","大兴安岭地区"},//黑龙江省13个
                {"呼和浩特市","包头市","赤峰市","通辽市","乌海市","呼伦贝尔市","鄂尔多斯市","巴彦淖尔市","乌兰察布市","兴安盟","锡林郭勒盟","阿拉善盟"},//内蒙古自治区12个

                {"石家庄市","保定市","沧州市","邯郸市","唐山市","承德市","邢台市","衡水市","廊坊市","张家口市","秦皇岛市"},//河北省11个
                {"郑州市","开封市","洛阳市","南阳市","信阳市","濮阳市","新乡市","商丘市","周口市","焦作市","漯河市","许昌市","安阳市","济源市","鹤壁市","驻马店市","三门峡市","平顶山市"},//河南省18个
                {"济南市","德州市","济宁市","临沂市","青岛市","泰安市","威海市","淄博市","菏泽市","烟台市","莱芜市","滨州市","东营市","聊城市","枣庄市","日照市","潍坊市"},//山东省17个
                {"太原市","大同市","忻州市","阳泉市","长治市","晋中市","临汾市","运城市","朔州市","晋城市","吕梁市"},//山西省11个

                {"南京市","常州市","徐州市","淮安市","南通市","宿迁市","无锡市","扬州市","盐城市","苏州市","泰州市","镇江市","连云港市"},//江苏省13个
                {"杭州市","湖州市","嘉兴市","金华市","丽水市","宁波市","衢州市","绍兴市","台州市","温州市","舟山市"},//浙江省11个
                {"福州市","宁德市","泉州市","厦门市","莆田市","南平市","龙岩市","三明市","漳州市"},//福建省9个
                {"合肥市","蚌埠市","阜阳市","淮北市","六安市","滁州市","宿州市","淮南市","安庆市","池州市","亳州市","黄山市","宣城市","芜湖市","铜陵市","马鞍山市"},//安徽省16个

                {"武汉市","荆门市","黄石市","荆州市","宜昌市","孝感市","襄阳市","咸宁市","仙桃市","黄冈市","十堰市","随州市","潜江市","天门市","鄂州市","恩施土家族苗族自治州","神农架林区"},//湖北省17个
                {"长沙市","岳阳市","郴州市","衡阳市","娄底市","邵阳市","湘潭市","益阳市","株洲市","常德市","永州市","怀化市","张家界市","湘西土家族苗族自治州"},//湖南省14个
                {"南昌市","赣州市","九江市","上饶市","鹰潭市","吉安市","萍乡市","宜春市","新余市","抚州市","景德镇市"},//江西省11个

                {"广州市","深圳市","佛山市","东莞市","中山市","珠海市","惠州市","韶关市","河源市","梅州市","潮州市","揭阳市","汕头市","汕尾市","清远市","云浮市","江门市","茂名市","阳江市","肇庆市","湛江市"},//广东省21个
                {"南宁市","崇左市","桂林市","贺州市","钦州市","梧州市","百色市","来宾市","河池市","北海市","玉林市","柳州市","贵港市","防城港市"},//广西壮族自治区14个
                {"海口市","三亚市","文昌市","儋州市","琼海市","万宁市","五指山市","东方市","澄迈县","屯昌县","定安县","临高县","乐东黎族自治县","陵水黎族自治县","白沙黎族自治县","昌江黎族自治县","保亭黎族苗族自治县","琼中黎族苗族自治县","三沙市"},//海南省19个

                {"成都市","广元市","绵阳市","南充市","宜宾市","内江市","自贡市","广安市","遂宁市","巴中市","德阳市","乐山市","眉山市","雅安市","资阳市","泸州市","达州市","攀枝花市","甘孜藏族自治州","凉山彝族自治州","阿坝藏族羌族自治州"},//四川省21个
                {"贵阳市","铜仁市","遵义市","安顺市","毕节市","六盘水市","黔南布依族苗族自治州","黔西南布依族苗族自治州","黔东南苗族侗族自治州"},//贵州省9个
                {"昆明市","丽江市","玉溪市","昭通市","曲靖市","普洱市","保山市","临沧市","楚雄彝族自治州","大理白族自治州","迪庆藏族自治州","怒江傈僳族自治州","文山壮族苗族自治州","西双版纳傣族自治州","德宏傣族景颇族自治州","红河哈尼族彝族自治州"},//云南省16个

                {"西安市","汉中市","渭南市","延安市","榆林市","咸阳市","宝鸡市","商洛市","铜川市","安康市"},//陕西省10个
                {"兰州市","张掖市","白银市","酒泉市","陇南市","天水市","金昌市","武威市","平凉市","定西市","庆阳市","嘉峪关市","临夏回族自治州","甘南藏族自治州"},//甘肃省14个
                {"银川市","中卫市","吴忠市","固原市","石嘴山市"},//宁夏回族自治区5个
                {"西宁市","玉树藏族自治州","果洛藏族自治州","黄南藏族自治州","海东市","海北藏族自治州","海南藏族自治州","海西蒙古族藏族自治州"},//青海省8个

                {"拉萨市","昌都市","林芝市","山南市","日喀则市","阿里地区","那曲地区"},//西藏自治区7个
                {"乌鲁木齐市","哈密市","北屯市","双河市","吐鲁番市","石河子市","五家渠市","铁门关市","阿拉尔市","克拉玛依市","可克达拉市","图木舒克市","喀什地区","和田地区","塔城地区","阿克苏地区","阿勒泰地区","昌吉回族自治州","伊犁哈萨克自治州","巴音郭楞蒙古自治州","博尔塔拉蒙古自治州","克孜勒苏柯尔克孜自治州"},//新疆维吾尔自治区22个

                {"东区","北区","南区","中西区","湾仔区","荃湾区","元朗区","沙田区","观塘区","离岛区","葵青区","屯门区","西贡区","大埔区","九龙城区","黄大仙区","油尖旺区","深水埗区"},//香港特别行政区18个
                {"澳门半岛","路环","氹仔","路氹城"},//澳门特别行政区4个
                {"台北市","新北市","桃园市","台中市","台南市","高雄市","基隆市","新竹市","嘉义市"},//台湾省20个

        };
        multiMenusView = new MultiMenusView(this.getContext(), levelOneMenu, levelTwoMenu);
        popupViews.add(multiMenusView);

        //初始化单级菜单
        String[] typeArrays = {"不限","猫咪", "狗子", "仓鼠", "乌龟", "其它"};
        String[] genderArrays = {"不限", "公", "母"};

        singleMenuView1 = new SingleMenuView(this.getContext(), typeArrays);
        singleMenuView2 = new SingleMenuView(this.getContext(), genderArrays);

        popupViews.add(singleMenuView1);
        popupViews.add(singleMenuView2);

        //初始化内容视图
        View contentView = LayoutInflater.from(this.getContext()).inflate(R.layout.fragment_user_adopt, null);

        //装载
        mDropDownMenu.setDropDownMenu(Arrays.asList(headers), popupViews, contentView);

        initListener();
    }
}