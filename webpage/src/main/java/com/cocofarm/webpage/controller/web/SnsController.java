package com.cocofarm.webpage.controller.web;

import javax.servlet.http.HttpSession;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.servlet.ModelAndView;

import com.cocofarm.webpage.common.RequestApi;
import com.cocofarm.webpage.domain.MemberVO;
import com.cocofarm.webpage.service.MemberService;

@Controller
@SessionAttributes({ "userinfo", "prevPage", "email" })
public class SnsController {

    @Autowired
    MemberService memberService;



    // KAKAO -----------------------------------------------------------------------------------------------------------
    // 카카오 연동페이지 이동
    @GetMapping(value = "/member/kakaologin")
    public String kakao() {
        return "redirect:https://kauth.kakao.com/oauth/authorize?state=aaaa&response_type=code&client_id=a07abf01f02b441c592d150ab0d53577&redirect_uri=http://localhost:9090/member/kakaocallback";
    }  
    // 카카오 콜백
    @RequestMapping("/member/kakaocallback")
    public ModelAndView kakaoCallback(String code, HttpSession session) {
        ModelAndView mav = new ModelAndView();
        RequestApi requestApi = new RequestApi();
        if (code == null) {
            ModelAndView cant = new ModelAndView();
            cant.setViewName("redirect:/");
            return cant;
        }
        System.out.println(code);

        StringBuffer url = new StringBuffer(
                "https://kauth.kakao.com/oauth/token?grant_type=authorization_code");
        url.append("&client_id=").append("a07abf01f02b441c592d150ab0d53577");
        url.append("&code=").append(code);

        String response = requestApi.requestAPI(url, null);

        JSONObject json = new JSONObject(response);
        String token_type = json.getString("token_type");
        String access_token = json.getString("access_token");

        url = new StringBuffer("https://kapi.kakao.com/v2/user/me");
        json = new JSONObject(requestApi.requestAPI(url, token_type + " " + access_token));
        json = json.getJSONObject("kakao_account");
        System.out.println(json.toString());

        MemberVO vo = new MemberVO();
        String searchEmail = memberService.email_search(json.get("email").toString());
        if (searchEmail.equals("1")) {
            // 로그인처리
            vo.setEmail(json.get("email").toString());
            MemberVO userinfo = memberService.sns_login(vo);
            userinfo.setSns("KAKAO");
            session.setAttribute("userinfo", userinfo);
            mav.setViewName("redirect:/");
            return mav;
        } else {
            // 가입처리
            vo.setSns("KAKAO");
            vo.setEmail(json.get("email").toString());
            mav.addObject("vo", vo);
            mav.setViewName("member/snsjoin");
            return mav;
        }
    }
    // ------------------------------------------------------------------------------------------------------------------ KAKAO end 

}
