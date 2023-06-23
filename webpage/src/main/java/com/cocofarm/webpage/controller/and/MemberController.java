package com.cocofarm.webpage.controller.and;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttributes;

import com.cocofarm.webpage.PhoneHyphen;
import com.cocofarm.webpage.domain.MemberVO;
import com.cocofarm.webpage.domain.QnaDTO;
import com.cocofarm.webpage.service.MemberService;
import com.google.gson.Gson;

@Controller
@SessionAttributes("userinfo")
public class MemberController {

    @Autowired
    MemberService service;

    @GetMapping(value = "/member/connect")
    public String memberConnect() {
        return "member/connect";
    }

    @ResponseBody
    @PostMapping(value = "/member/login")
    public String login(String email, String password) {
        MemberVO vo = new MemberVO();
        vo.setEmail(email);
        vo.setPassword(password);

        System.out.println(email);

        return new Gson().toJson(service.login(vo));
    }

    @PostMapping(value = "/member/join")
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

    @PostMapping(value = "/member/email_search")
    public String email_search(String email) {
        return service.email_search(email);
    }

    @PostMapping(value = "/member/sns_login")
    public String sns_login(String email) {
        MemberVO vo = new MemberVO();
        vo.setEmail(email);
        return new Gson().toJson(service.sns_login(vo));
    }

    @RequestMapping(value = "/member/modify")
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

    @PostMapping(value = "/member/modifypw")
    public void pw_modify(String email, String password) {
        service.pw_modify(email, password);
    }

    @RequestMapping(value = "/member/away")
    public int away(String email) {
        MemberVO vo = new MemberVO();
        vo.setEmail(email);

        return service.away(email);
    }

    @PostMapping(value = "/member/myboard", produces = "text/html;charset=utf-8")
    public String myboard(int member_no) {
        QnaDTO dto = new QnaDTO();
        dto.setMember_no(member_no);
        System.out.println(member_no);

        return new Gson().toJson(service.myboard(dto));
    }
}
