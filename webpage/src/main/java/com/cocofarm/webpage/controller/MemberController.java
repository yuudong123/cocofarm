package com.cocofarm.webpage.controller;

import org.apache.commons.mail.EmailException;
import org.apache.commons.mail.SimpleEmail;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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

        return new Gson().toJson(service.login(vo));
    }

    @RequestMapping(value = "join")
    public int join(String email, String password, String nickname) {
        MemberVO join_vo = new MemberVO();
        join_vo.setEmail(email);
        join_vo.setPassword(password);
        join_vo.setNickname(nickname);

        return service.join(join_vo);
    }

    @RequestMapping(value = "am.modify")
    public String am_modify(String password, String nickname, String phonenumber, String email) {
        MemberVO vo = new MemberVO();
        vo.setPassword(password);
        vo.setNickname(nickname);
        vo.setPhonenumber(phonenumber);
        vo.setEmail(email);
        service.am_modify(vo);

        return new Gson().toJson(service.login(vo));
    }

    @RequestMapping(value = "email")
    public String sendEmail(String confirm_text, String email){
                SimpleEmail mail = new SimpleEmail();
                mail.setHostName("smtp.naver.com");
                mail.setCharset("utf-8"); //한글깨짐방지
                mail.setDebug(true); //오류를 찾아서 개발을 하고있는 과정인지
               
                mail.setAuthentication("hanul_test", "hanul301");
                mail.setSSLOnConnect(true);
                ///==============고정 어떤메일을 쓰든 smtp서버와 smtp서버를 이용할수있는고객인증

                //송신인
                try {
                    mail.setFrom("hanul_test@naver.com" , "Cocofarm");
                    mail.addTo(email, "코코팜 인증");
                    mail.setSubject("코코팜 이메일 인증 메일입니다.");
                    mail.setMsg("안녕하세요, 코코팜 입니다.\n\n아래 인증번호를 가입 화면에 입력해주세요.\n인증번호 : " + confirm_text);

                    //mail객체가 모든 정보를 가지고 전송할준비를 마침. ↑
                    mail.send();
                } catch (EmailException e) {
                    e.printStackTrace();
                } catch (Exception e) {
                   e.printStackTrace();
                }
                return "ok";
    }
}
