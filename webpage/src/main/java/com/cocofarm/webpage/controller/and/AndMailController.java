package com.cocofarm.webpage.controller.and;

import org.apache.commons.mail.EmailException;
import org.apache.commons.mail.SimpleEmail;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AndMailController {

    @PostMapping(value = "/email/send.and")
    public String sendEmail(String confirm_text, String email) {
        SimpleEmail mail = new SimpleEmail();
        mail.setHostName("smtp.naver.com");
        mail.setCharset("utf-8");
        mail.setDebug(true);

        mail.setAuthentication("hanul_test", "hanul301");
        mail.setSSLOnConnect(true);

        // 송신인
        try {
            mail.setFrom("hanul_test@naver.com", "Cocofarm");
            mail.addTo(email, "코코팜 인증");
            mail.setSubject("[코코팜] 가입 인증 메일입니다.");
            mail.setMsg("안녕하세요, 대한민국 No.1 스마트팜 브랜드 '코코팜' 입니다.\n\n아래 인증번호를 화면에 입력해주세요.\n인증번호 : " + confirm_text);

            mail.send();
        } catch (EmailException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "ok";
    }

    @PostMapping(value = "/email/findpw.and")
    public String findPw(String confirm_text, String email) {
        SimpleEmail mail = new SimpleEmail();
        mail.setHostName("smtp.naver.com");
        mail.setCharset("utf-8");
        mail.setDebug(true);

        mail.setAuthentication("hanul_test", "hanul301");
        mail.setSSLOnConnect(true);

        // 송신인
        try {
            mail.setFrom("hanul_test@naver.com", "Cocofarm");
            mail.addTo(email, "코코팜 인증");
            mail.setSubject("[코코팜] 비밀번호 찾기 인증 메일입니다.");
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
