package com.cocofarm.webpage.controller;

import java.util.ArrayList;

import org.apache.commons.mail.EmailException;
import org.apache.commons.mail.SimpleEmail;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cocofarm.webpage.PhoneHyphen;
import com.cocofarm.webpage.domain.BoardVO;
import com.cocofarm.webpage.domain.MemberVO;
import com.cocofarm.webpage.service.MemberService;
import com.google.gson.Gson;

@RestController
public class MemberController {

    @Autowired
    MemberService service;

    @RequestMapping(value = "login")
    public String login(String email, String password) {
        MemberVO vo = new MemberVO();
        vo.setEmail(email);
        vo.setPassword(password);
        System.out.println(email);

        return new Gson().toJson(service.login(vo));
    }

    @RequestMapping(value = "join")
    public int join(String email, String password, String nickname, String phonenumber, String address) {
        MemberVO join_vo = new MemberVO();
        join_vo.setEmail(email);
        join_vo.setPassword(password);
        join_vo.setNickname(nickname);
        String hypen_phone = PhoneHyphen.convertTelNo(phonenumber);
        join_vo.setPhonenumber(hypen_phone);
        // join_vo.setPhonenumber(phonenumber);
        join_vo.setAddress(address);

        return service.join(join_vo);
    }

    @RequestMapping(value = "am.modify")
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


    @RequestMapping(value = "away")
    public int away(String email) {
        MemberVO vo = new MemberVO();
        vo.setEmail(email);

        return service.away(email);
    }

    @PostMapping(value = "/myboard", produces = "text/html;charset=utf-8")
    public String myboard(int member_no) {
        BoardVO vo = new BoardVO();
        vo.setMember_no(member_no);
        System.out.println(member_no);

        return new Gson().toJson(service.myboard(vo));
    }

    @RequestMapping(value = "email")
    public String sendEmail(String confirm_text, String email){
                SimpleEmail mail = new SimpleEmail();
                mail.setHostName("smtp.naver.com");
                mail.setCharset("utf-8");
                mail.setDebug(true);
               
                mail.setAuthentication("hanul_test", "hanul301");
                mail.setSSLOnConnect(true);

                //송신인
                try {
                    mail.setFrom("hanul_test@naver.com" , "Cocofarm");
                    mail.addTo(email, "코코팜 인증");
                    mail.setSubject("코코팜 가입 인증 메일입니다.");
                    mail.setMsg("안녕하세요, 대한민국 No.1 스마트팜 브랜드 '코코팜' 입니다.\n\n아래 인증번호를 화면에 입력해주세요.\n인증번호 : " + confirm_text);

                    mail.send();
                } catch (EmailException e) {
                    e.printStackTrace();
                } catch (Exception e) {
                   e.printStackTrace();
                }
                return "ok";
    }
}

