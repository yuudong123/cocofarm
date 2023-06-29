package com.cocofarm.andapp.common;

import com.cocofarm.andapp.member.MemberVO;
import com.cocofarm.andapp.order.CartDTO;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class CommonVal {

    // 로그인한 사람의 정보가 들어감. 웹에서는 세션 속성으로 userinfo라고 해놨는데
    // 이대로 둘지, 하나로 통일할지, 통일한다면 userinfo로할지 loginmember로 할지 정해야함.
    public static MemberVO loginMember = null;


    public static ArrayList<CartDTO> cart = new ArrayList<>();

    public static boolean isCheckLogout = false;
    public static SimpleDateFormat Md = new SimpleDateFormat("M월 d일");
    public static SimpleDateFormat yyyyMMdd = new SimpleDateFormat("yyyyMMdd");

    public static boolean isToday(Date date) {
        return yyyyMMdd.format(date).equals(yyyyMMdd.format(new Date()));
    }

    public static SimpleDateFormat yyyyMMddHHmmss = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    public static SimpleDateFormat HHmmss = new SimpleDateFormat("HH:mm:ss");

    public static DecimalFormat decimalFormat = new DecimalFormat("###,###");

    public static String comma(int money) {
        return "￦ " + decimalFormat.format(money) + "원";
    }

    public static String boardselectedImage = "";
}
