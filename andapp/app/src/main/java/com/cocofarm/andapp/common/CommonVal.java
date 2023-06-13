package com.cocofarm.andapp.common;

import com.cocofarm.andapp.member.MemberVO;

import java.text.SimpleDateFormat;

public class CommonVal {

    // 로그인한 사람의 정보가 들어감. 웹에서는 세션 속성으로 userinfo라고 해놨는데
    // 이대로 둘지, 하나로 통일할지, 통일한다면 userinfo로할지 loginmember로 할지 정해야함.
    public static MemberVO loginMember = null;

    public static SimpleDateFormat Md = new SimpleDateFormat("M월 d일");
    public static SimpleDateFormat yyyyMMddHHmmss = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    public static SimpleDateFormat HHmmss = new SimpleDateFormat("HH:mm:ss");

    public static String boardselectedImage = "";
}
