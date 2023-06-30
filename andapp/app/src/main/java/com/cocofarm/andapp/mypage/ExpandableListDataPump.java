package com.cocofarm.andapp.mypage;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class ExpandableListDataPump {
    public static HashMap<String, List<String>> getData() {
        HashMap<String, List<String>> expandableListDetail = new HashMap<>();

        List<String> payment = new ArrayList<>();
        payment.add("코코몰은 무통장 입금만 가능합니다.");

        List<String> charge = new ArrayList<>();
        charge.add("코코팜 상품은 전부 무료배송입니다.");

        List<String> delivery = new ArrayList<>();
        delivery.add("상품 배송 기간은 배송 유형에 따라 출고 일자 차이가 있습니다. 자세한 사항은 고객센터로 전화주시면 안내 가능합니다.\n\n※ 주말, 공휴일은 배송 업무를 진행하지 않습니다.");

        List<String> overseas = new ArrayList<>();
        overseas.add("현재는 국내배송만 진행하고 있습니다.");

        List<String> refund = new ArrayList<>();
        refund.add("상품을 수령하신 후 7일 이내에 교환, 반품이 가능하며, 고객님의 변심에 의한 교환/반품의 경우 배송 비용이 부과될 수 있습니다.\n\n" +
                "※ 단, 아래의 경우 교환/반품이 불가능합니다.\n" +
                "- 고객님의 책임 사유로 인해 상품 등이 멸실 또는 훼손된 경우\n" +
                "- 개봉 및 포장의 훼손으로 상품가치가 현저히 상실된 경우\n" +
                "- 시간 경과에 의해 재판매가 어려울정도로 상품 가치가 현저히 저하된 경우");

        List<String> want = new ArrayList<>();
        want.add("코코팜은 지정일 배송 서비스를 지원하고 있지 않습니다.");

        List<String> rejoin = new ArrayList<>();
        rejoin.add("코코팜 재가입은 언제든지 가능합니다.");

        List<String> pw = new ArrayList<>();
        pw.add("마이페이지 프로필 바로 아래 [개인정보 > 내 정보 > 정보 수정하기] 페이지에서 비밀번호 변경이 가능합니다.");

        List<String> facebook = new ArrayList<>();
        facebook.add("페이스북 로그인은 사용이 불가능합니다. 고객님들의 편의를 위해 페이스북 로그인이 가능하도록 현재 개발 중에 있습니다.");

        List<String> modify = new ArrayList<>();
        modify.add("마이페이지 프로필 바로 아래 [개인정보 > 내 정보 > 정보 수정하기] 페이지에서 회원 정보 수정이 가능합니다.");

        expandableListDetail.put("결제 방법은 어떤 것이 있나요?", payment);
        expandableListDetail.put("배송비는 얼마인가요?", charge);
        expandableListDetail.put("배송은 얼마나 걸리나요?", delivery);
        expandableListDetail.put("해외배송이 가능한가요?", overseas);
        expandableListDetail.put("제품의 교환 또는 반품을 할 수 있나요?", refund);
        expandableListDetail.put("원하는 날짜로 맞춰서 배송을 받을 수 있나요?", want);
        expandableListDetail.put("회원탈퇴 후 재가입이 가능한가요?", rejoin);
        expandableListDetail.put("비밀번호 변경은 어떻게하나요?", pw);
        expandableListDetail.put("코코팜 앱에서는 페이스북 로그인이 가능한가요?", facebook);
        expandableListDetail.put("회원정보를 수정하고 싶은데 어떻게 해야하나요?", modify);

        return expandableListDetail;
    }
}
