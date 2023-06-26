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
    public static final int ORDER_STATUS_RETURN_REQ = 304; // 반품신청
    public static final int ORDER_STATUS_REFUND_REQ = 305; // 환불신청
    public static final int ORDER_STATUS_RETURN_OK = 306; // 반품완료
    public static final int ORDER_STATUS_REFUND_OK = 307; // 환불완료
    public static final int ORDER_STATUS_SUCCESS = 310; // 구매확정

    public static final int ORDER_STATUS_CHANGE_JUST = 341; // 환불이유 마음에 들지 않음.
    public static final int ORDER_STATUS_CHANGE_NOTYET = 342; // 환불 : 상품을 받지 못함
    public static final int ORDER_STATUS_CHANGE_LACK = 343; // 환불 : 상품의 구성품/부속품이 들어있지 않음
    public static final int ORDER_STATUS_CHANGE_ONOTHER = 344;// 환불 : 다른 상품이 배송
    public static final int ORDER_STATUS_CHANGE_BROKEN = 345; // 환불 : 상품이 파손됨
    public static final int ORDER_STATUS_CHANGE_DETAIL = 346; // 환불 : 상세사유 TEXT

    public static final int ORDER_STATUS_REFUND_JUST = 351; // 반품 : 마음에 들지 않음.
    public static final int ORDER_STATUS_REFUND_NOTYET = 352; // 반품 : 상품을 받지 못함
    public static final int ORDER_STATUS_REFUND_LACK = 353; // 반품 : 상품의 구성품/부속품이 들어있지 않음
    public static final int ORDER_STATUS_REFUND_ONOTHER = 354;// 반품 : 다른 상품이 배송
    public static final int ORDER_STATUS_REFUND_BROKEN = 355; // 반품 : 상품이 파손됨
    public static final int ORDER_STATUS_REFUND_DETAIL = 356; // 반품 : 상세사유 TEXT

    public static final int PRODUCT_CATEGORY_PLANT = 401; // 식물류
    public static final int PRODUCT_CATEGORY_DEVICE = 402; // 기기류
}
