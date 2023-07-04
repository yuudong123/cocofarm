package com.cocofarm.webpage.controller.web;

import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.servlet.ModelAndView;

import com.cocofarm.webpage.common.PreviousPageHandler;
import com.cocofarm.webpage.common.RequestApi;
import com.cocofarm.webpage.domain.MemberVO;
import com.cocofarm.webpage.service.MemberService;
import com.google.gson.Gson;

@Controller
@SessionAttributes({ "userinfo", "prevPage", "email" })
public class WebMemberController {

    private final PreviousPageHandler previousPageHandler;

    public WebMemberController(PreviousPageHandler previousPageHandler) {
        this.previousPageHandler = previousPageHandler;
    }

    @Autowired
    MemberService memberService;

    @GetMapping(value = "/member/login")
    public ModelAndView loginGET(HttpServletRequest request) {
        ModelAndView mav = new ModelAndView();
        mav.addObject("prevPage", previousPageHandler.getPreviousPage(request));
        mav.setViewName("member/login");
        return mav;
    }

    @ResponseBody
    @PostMapping(value = "/member/login")
    public String loginPOST(@RequestBody MemberVO emailAndPassword, Model model) {
        MemberVO userinfo = memberService.login(emailAndPassword);
        if (userinfo != null) {
            model.addAttribute("userinfo", userinfo);
            return model.getAttribute("prevPage") + "";
        } else {
            return "false";
        }
    }

    @RequestMapping("/member/kakaocallback")
    public String kakaoCallback(String code, HttpSession session) {
        RequestApi requestApi = new RequestApi();
        if (code == null)
            return "redirect:/";
        System.out.println(code);

        StringBuffer url = new StringBuffer(
                "https://kauth.kakao.com/oauth/token?grant_type=authorization_code");
        url.append("&client_id=").append("a07abf01f02b441c592d150ab0d53577");
        url.append("&code=").append(code);

        String response = requestApi.requestAPI(url, null);
        // String response = requestApi.requestAPI( url.toString());
        // 문자열 --> JSON

        JSONObject json = new JSONObject(response);
        String token_type = json.getString("token_type");
        String access_token = json.getString("access_token");

        url = new StringBuffer("https://kapi.kakao.com/v2/user/me");
        json = new JSONObject(requestApi.requestAPI(url, token_type + " " + access_token));
        json = json.getJSONObject("kakao_account");
        System.out.println(json.toString());
        MemberVO vo = new MemberVO();
        vo.setSns("KAKAO");
        vo.setEmail(json.get("email").toString());
        return "redirect:/";
    }

    @GetMapping(value = "/member/kakaologin")
    public String kakao() {
        return "redirect:https://kauth.kakao.com/oauth/authorize?state=aaaa&response_type=code&client_id=a07abf01f02b441c592d150ab0d53577&redirect_uri=http://localhost:9090/member/kakaocallback";
    }

    @GetMapping(value = "/member/findpw")
    public String findpw() {
        return "member/findpw";
    }

    @GetMapping(value = "/member/modifypw_mp")
    public ModelAndView modifypw_mpGET(Model model) {
        ModelAndView mav = new ModelAndView();
        String email = model.getAttribute("email") + "";
        mav.addObject("email", email);

        mav.setViewName("member/modifypw_mp");
        return mav;
    }

    @GetMapping(value = "/member/modifypw")
    public ModelAndView modifypwGET(Model model) {
        ModelAndView mav = new ModelAndView();
        String email = model.getAttribute("email") + "";
        mav.addObject("email", email);

        mav.setViewName("member/modifypw");
        return mav;
    }

    @ResponseBody
    @PostMapping(value = "/member/modifypw")
    public String modifypw(@RequestBody HashMap<String, String> param, Model model) {
        String email = model.getAttribute("email") + "";
        String password = param.get("password");

        memberService.pw_modify(email, password);
        return "success";
    }


    @ResponseBody
    @PostMapping(value = "/member/logout")
    public String logout(SessionStatus sessionStatus, Model model) {
        sessionStatus.setComplete();
        return "/";
    }

    @GetMapping(value = "/member/pwok")
    public String pwok() {
        return "member/pwok";
    }

    @GetMapping(value = "/member/modifyinfo")
    public String modifyinfo() {
        return "member/modifyinfo";
    }

    @ResponseBody
    @PostMapping(value = "/member/modifyinfo")
    public String modifyinfoPost(@RequestBody HashMap<String, String> param, Model model) {
        MemberVO vo = new MemberVO();
        vo.setEmail(param.get("email"));
        vo.setNickname(param.get("nickname"));
        vo.setPhonenumber(param.get("phonenumber"));
        vo.setAddress(param.get("address"));

        memberService.web_modify(vo);
        MemberVO result = memberService.login((MemberVO)model.getAttribute("userinfo"));
        model.addAttribute("userinfo", result);

        return "success";
    }


    @ResponseBody
    @PostMapping(value = "/member/pwconfirm")
    public String pwconfirm(@RequestBody HashMap<String, String> param, HttpSession session) {

        MemberVO vo = (MemberVO) session.getAttribute("userinfo");
        if (param.get("password").equals(vo.getPassword().toString())) {
            return vo.getEmail();
        } else {
            return "failure";
        }
    }

    @GetMapping(value = "/member/join")
    public String join(MemberVO vo) {
        return "member/join";
    }

    @ResponseBody
    @PostMapping(value = "/member/createMember")
    public int createMember(@RequestBody MemberVO vo) {
        return memberService.join(vo);
    }

    @GetMapping(value = "/member/test")
    public String test() {
        return "member/test";
    }

    @GetMapping(value = "/member/myinfo")
    public String myinfo() {
        return "member/myinfo";
    }

    @GetMapping(value = "/member/myboard")
    public String myboard() {
        return "member/myboard";
    }

    @GetMapping(value = "/member/mydevice")
    public String mydevice() {
        return "member/mydevice";
    }

    @GetMapping(value = "/member/cscenter")
    public String cscenter() {
        return "member/cscenter";
    }

    @GetMapping(value = "/member/away")
    public String away() {
        return "member/away";
    }

    @ResponseBody
    @PostMapping(value = "/member/away/approval")
    public int away_approval(@RequestBody HashMap<String, String> email, SessionStatus sessionStatus) {
        MemberVO vo = new MemberVO();
        vo.setEmail(email.get("email"));
        sessionStatus.setComplete();

        return memberService.away(vo.getEmail());
    }

    @ResponseBody
    @PostMapping(value = "/member/email_search")
    public String email_search(@RequestBody MemberVO vo, Model model) {
        model.addAttribute("email", vo.getEmail());
        System.out.println(vo.getEmail());
        return memberService.email_search(vo.getEmail());
    }

    @GetMapping(value = "/admin/member")
    public String admin_member() {
        return "member/admin";
    }
}
