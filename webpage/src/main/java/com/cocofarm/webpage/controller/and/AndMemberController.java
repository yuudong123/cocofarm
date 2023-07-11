package com.cocofarm.webpage.controller.and;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cocofarm.webpage.PhoneHyphen;
import com.cocofarm.webpage.domain.MemberVO;
import com.cocofarm.webpage.domain.QnaDTO;
import com.cocofarm.webpage.service.MemberService;
import com.google.gson.Gson;

@RestController
public class AndMemberController {

    @Autowired
    MemberService service;

    @PostMapping(value = "/member/login.and")
    public String login(String email, String password) {
        MemberVO vo = new MemberVO();
        vo.setEmail(email);
        vo.setPassword(password);

        System.out.println(email);

        return new Gson().toJson(service.login(vo));
    }

    @PostMapping(value = "/member/join.and")
    public int join(String email, String password, String nickname, String phonenumber, String address, String sns) {
        MemberVO join_vo = new MemberVO();
        join_vo.setEmail(email);
        join_vo.setPassword(password);
        join_vo.setNickname(nickname);
        if (phonenumber.equals("")) {
            join_vo.setPhonenumber(phonenumber);
        } else {
            String hypen_phone = PhoneHyphen.convertTelNo(phonenumber);
            join_vo.setPhonenumber(hypen_phone);
        }
        join_vo.setAddress(address);
        join_vo.setSns(sns);

        return service.join(join_vo);
    }

    @PostMapping(value = "/member/email_search.and")
    public String email_search(String email) {
        return service.email_search(email);
    }

    @PostMapping(value = "/member/sns_login.and")
    public String sns_login(String email) {
        MemberVO vo = new MemberVO();
        vo.setEmail(email);
        return new Gson().toJson(service.sns_login(vo));
    }

    @RequestMapping(value = "/member/modify.and")
    public String am_modify(String password, String nickname, String phonenumber, String email, String address) {

        MemberVO vo = new MemberVO();
        vo.setPassword(password);
        vo.setNickname(nickname);
        vo.setPhonenumber(phonenumber);
        vo.setEmail(email);
        vo.setAddress(address);
        service.am_modify(vo);

        return new Gson().toJson(service.login(vo));
    }

    @PostMapping(value = "/member/modifypw.and")
    public void pw_modify(String email, String password) {
        service.pw_modify(email, password);
    }

    @RequestMapping(value = "/member/away.and")
    public int away(String email) {
        MemberVO vo = new MemberVO();
        vo.setEmail(email);

        return service.away(email);
    }

    @PostMapping(value = "/member/myboard.and", produces = "text/html;charset=utf-8")
    public String myboard(int member_no) {
        QnaDTO dto = new QnaDTO();
        dto.setMember_no(member_no);
        System.out.println(member_no);

        return new Gson().toJson(service.myboard(dto));
    }

    // 내가 쓴 리뷰 보기.
    @PostMapping(value = "/member/myreviewboard.and", produces = "text/html;charset=utf-8")

    public String myreviewboard(MemberVO vo) {
        return new Gson().toJson(service.myreviewboard(vo));
    }
}
