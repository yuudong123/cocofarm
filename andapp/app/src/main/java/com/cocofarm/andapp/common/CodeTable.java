package com.cocofarm.andapp.common;

public class CodeTable {
    public static final int MEMBER_TYPE_ADMIN = 101;
    public static final int MEMBER_TYPE_NORMAL = 102;
//    public static final int MEMBER_TYPE_VIP = 103; 얘 딱히 필요없으면 사용 안하고, DB에서도 지우기

    public static final int BOARD_CATEGORY_QNA = 201;
    public static final int BOARD_CATEGORY_NOTICE = 202;
    public static final int BOARD_CATEGORY_REVIEW = 203;
    public static final int BOARD_CATEGORY_EVENT = 204;

    public static final int ORDER_STATUS_CANCEL = 300; //결제취소

    public static final int ORDER_STATUS_ONREADY = 301;     //배송준비
    public static final int ORDER_STATUS_ONDELIVERY = 302;  //배송중
    public static final int ORDER_STATUS_ARRIVED = 303;     //배송완료
    public static final int ORDER_STATUS_RETURN_REQ = 304;  //반품신청
    public static final int ORDER_STATUS_REFUND_REQ = 305;  //환불신청
    public static final int ORDER_STATUS_RETURN_OK = 306;   //반품완료
    public static final int ORDER_STATUS_REFUND_OK = 307;   //환불완료

    public static final int PRODUCT_CATEGORY_PLANT = 401;   //식물류
    public static final int PRODUCT_CATEGORY_DEVICE = 402;  //기기류
}
