package com.cocofarm.andapp.common;

import com.cocofarm.andapp.member.MemberVO;
import com.cocofarm.andapp.order.CartDTO;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class CommonVal {
    public static MemberVO loginMember = null;
    public static ArrayList<CartDTO> cart = new ArrayList<>();
    public static String boardselectedImage = "";
    public static boolean isCheckLogout = false;

    public static SimpleDateFormat Md = new SimpleDateFormat("M월 d일");
    public static SimpleDateFormat yyyyMMdd = new SimpleDateFormat("yyyyMMdd");
    public static SimpleDateFormat yyyyMMddHHmmss = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    public static SimpleDateFormat HHmmss = new SimpleDateFormat("HH:mm:ss");

    public static boolean isToday(Date date) {
        return yyyyMMdd.format(date).equals(yyyyMMdd.format(new Date()));
    }

    public static String comma(int money) {
        return "￦ " + new DecimalFormat("###,###").format(money) + "원";
    }

}
