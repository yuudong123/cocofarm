package com.cocofarm.webpage.common;

public class CodeTable {
    public static final int MEMBER_TYPE_ADMIN = 101;
    public static final int MEMBER_TYPE_NORMAL = 102;
    // public static final int MEMBER_TYPE_VIP = 103; 얘 딱히 필요없으면 사용 안하고, DB에서도 지우기

    public static final int BOARD_CATEGORY_QNA = 201;
    public static final int BOARD_CATEGORY_NOTICE = 202;
    public static final int BOARD_CATEGORY_REVIEW = 203;
    public static final int BOARD_CATEGORY_EVENT = 204;

    public static final int ORDER_STATUS_CANCEL = 300; // 결제취소
    public static final int ORDER_STATUS_ONREADY = 301; // 배송준비
    public static final int ORDER_STATUS_ONDELIVERY = 302; // 배송중
    public static final int ORDER_STATUS_ARRIVED = 303; // 배송완료
    public static final int ORDER_STATUS_CHANGE_REQ = 304; // 교환신청
    public static final int ORDER_STATUS_REFUND_REQ = 305; // 환불신청
    public static final int ORDER_STATUS_CHANGE_OK = 306; // 교환완료
    public static final int ORDER_STATUS_REFUND_OK = 307; // 환불완료
    public static final int ORDER_STATUS_SUCCESS = 310; // 구매확정

    public static final int REASON_CODE_JUST = 501; // 교환이유 마음에 들지 않음.
    public static final int REASON_CODE_NOTYET = 502; // 교환 : 상품을 받지 못함
    public static final int REASON_CODE_LACK = 503; // 교환 : 상품의 구성품/부속품이 들어있지 않음
    public static final int REASON_CODE_DIFFER = 504; // : 상품이 설명과 다름
    public static final int REASON_CODE_ONOTHER = 505;// 교환 : 다른 상품이 배송
    public static final int REASON_CODE_BROKEN = 506; // 교환 : 상품이 파손됨
    public static final int REASON_CODE_DETAIL = 507; // 교환 : 상세사유 TEXT

    public static final int REASON_CODE_BENEFIT = 511;
    public static final int REASON_CODE_PRIVACY = 512;
    public static final int REASON_CODE_ILLEGAL = 513;
    public static final int REASON_CODE_SENSITIVE = 514;
    public static final int REASON_CODE_ABORT = 515;
    public static final int REASON_CODE_ID_DB_DEAL = 516;
    public static final int REASON_CODE_SPAM = 517;
    public static final int REASON_CODE_ETC = 518;

    public static final int PRODUCT_CATEGORY_PLANT = 401; // 식물류
    public static final int PRODUCT_CATEGORY_DEVICE = 402; // 기기류
}
