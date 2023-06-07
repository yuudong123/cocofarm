package com.cocofarm.andapp.common;

import com.cocofarm.andapp.member.MemberVO;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class CommonVal {

    // 로그인한 사람의 정보가 들어감. 웹에서는 세션 속성으로 userinfo라고 해놨는데
    // 이대로 둘지, 하나로 통일할지, 통일한다면 userinfo로할지 loginmember로 할지 정해야함.
    // 테스트시 관리자와 일반회원 중 선택해서 사용할 수 있게 추가
    public static MemberVO loginMemberAdmin = new MemberVO(1,"관리자1",101); // 관리자
    public static MemberVO loginMember = new MemberVO(4,"꽃향기",102); // 일반

    public static String parseDate(String date, SimpleDateFormat sdf){
        try {
            return sdf.format(new SimpleDateFormat("yyyy-MM-dd hh:mm:ss").parse(date));
        } catch (ParseException e) {
            e.printStackTrace();
            return "";
        }
    }
    public static SimpleDateFormat Md = new SimpleDateFormat("M월 d일");
//    private void getHashKey(){
//        PackageInfo packageInfo = null;
//        try {
//            packageInfo = getPackageManager().getPackageInfo(getPackageName(), PackageManager.GET_SIGNATURES);
//        } catch (PackageManager.NameNotFoundException e) {
//            e.printStackTrace();
//        }
//        if (packageInfo == null)
//            Log.e("KeyHash", "KeyHash:null");
//
//        for (Signature signature : packageInfo.signatures) {
//            try {
//                MessageDigest md = MessageDigest.getInstance("SHA");
//                md.update(signature.toByteArray());
//                Log.d("KeyHash", Base64.encodeToString(md.digest(), Base64.DEFAULT));
//            } catch (NoSuchAlgorithmException e) {
//                Log.e("KeyHash", "Unable to get MessageDigest. signature=" + signature, e);
//            }
//        }
//    }
}
