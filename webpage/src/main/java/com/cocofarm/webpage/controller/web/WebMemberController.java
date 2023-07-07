package com.cocofarm.webpage.controller.web;

import java.util.ArrayList;
import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.servlet.ModelAndView;

import com.cocofarm.webpage.common.PreviousPageHandler;
import com.cocofarm.webpage.domain.CriteriaDTO;
import com.cocofarm.webpage.domain.MemberVO;
import com.cocofarm.webpage.domain.PageDTO;
import com.cocofarm.webpage.service.MemberService;

@Controller
@SessionAttributes({ "userinfo", "prevPage", "email" })
public class WebMemberController {

    private final PreviousPageHandler previousPageHandler;

    public WebMemberController(PreviousPageHandler previousPageHandler) {
        this.previousPageHandler = previousPageHandler;
    }

    @Autowired
    MemberService memberService;

    // 로그인 ( Login ) --------------------------------------------------------------------------------------------
    // 로그인 화면
    @GetMapping(value = "/member/login")
    public ModelAndView loginGET(HttpServletRequest request) {
        ModelAndView mav = new ModelAndView();
        mav.addObject("prevPage", previousPageHandler.getPreviousPage(request));
        mav.setViewName("member/login");
        return mav;
    }
    // 로그인 처리
    @ResponseBody
    @PostMapping(value = "/member/login")
    public String loginPOST(@RequestBody MemberVO emailAndPassword, Model model) {
        MemberVO userinfo = memberService.login(emailAndPassword);
        if (userinfo != null) {
            model.addAttribute("userinfo", userinfo);
           if (userinfo.getIsactivated().toString().equals("Y")) {
                return model.getAttribute("prevPage") + "";
           } else { // 차단회원 로그인 시
                model.addAttribute("userinfo", null);
                return "/member/refused";
           }
        } else {
            return "false";
        }
    }
    // 비밀번호 찾기
    @GetMapping(value = "/member/findpw")
    public String findpw() {
        return "member/findpw";
    }
    // 비밀번호 찾기 > 비밀번호 변경
    @GetMapping(value = "/member/modifypw")
    public ModelAndView modifypwGET(Model model) {
        ModelAndView mav = new ModelAndView();
        String email = model.getAttribute("email") + "";
        mav.addObject("email", email);
        mav.setViewName("member/modifypw");
        return mav;
    }
    // 차단회원 안내페이지
    @GetMapping(value = "/member/refused")
    public String refused() {
        return "member/refused";
    }
    // 로그아웃
    @ResponseBody
    @PostMapping(value = "/member/logout")
    public String logout(SessionStatus sessionStatus, Model model) {
        sessionStatus.setComplete();
        return "/";
    }
    //------------------------------------------------------------------------------------------------로그인 ( Login ) end





    // 마이페이지 -------------------------------------------------------------------------------------------------------
    // 내 정보
    @GetMapping(value = "/member/myinfo")
    public String myinfo() {
        return "member/myinfo";
    }
    // 정보 수정 화면
    @GetMapping(value = "/member/modifyinfo")
    public String modifyinfo_GET() {
        return "member/modifyinfo";
    }
    // 정보 수정 처리
    @PostMapping(value = "/member/modifyinfo")
    public String modifyinfo_POST(  MemberVO vo, Model model) {
        memberService.web_modify(vo);
        MemberVO result = memberService.login((MemberVO) model.getAttribute("userinfo"));
        model.addAttribute("userinfo", result);
        return "redirect:/member/myinfo";
    }
    // 비밀번호 확인
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
    // 내가 쓴 글
    @GetMapping(value = "/member/myboard")
    public String myboard() {
        return "member/myboard";
    }
    // 내 기기
    @GetMapping(value = "/member/mydevice")
    public String mydevice() {
        return "member/mydevice";
    }
    // 회원탈퇴
    @GetMapping(value = "/member/away")
    public String away_GET() {
        return "member/away";
    }
    // 회원탈퇴 처리
    @ResponseBody
    @PostMapping(value = "/member/away/approval")
    public int away_POST(@RequestBody HashMap<String, String> email, SessionStatus sessionStatus) {
        MemberVO vo = new MemberVO();
        vo.setEmail(email.get("email"));
        sessionStatus.setComplete();
        return memberService.away(vo.getEmail());
    }
    // 비밀번호 변경 화면
    @GetMapping(value = "/member/modifypw_mp")
    public ModelAndView modifypw_GET(Model model) {
        ModelAndView mav = new ModelAndView();
        String email = model.getAttribute("email") + "";
        mav.addObject("email", email);
        mav.setViewName("member/modifypw_mp");
        return mav;
    }
    // 비밀번호 변경 처리
    @ResponseBody
    @PostMapping(value = "/member/modifypw")
    public String modifypw_POST(@RequestBody HashMap<String, String> param, Model model) {
        String email = model.getAttribute("email") + "";
        String password = param.get("password");
        memberService.pw_modify(email, password);
        return "success";
    }
    // 비밀번호 확인
    @GetMapping(value = "/member/pwok")
    public String pwok() {
        return "member/pwok";
    }
    // ------------------------------------------------------------------------------------------------------ 마이페이지 end






    // 회원가입 --------------------------------------------------------------------------------------------------------------------
    // 회원가입 화면
    @GetMapping(value = "/member/join")
    public String join_GET(MemberVO vo) {
        return "member/join";
    }
    // 회원가입 처리
    @ResponseBody
    @PostMapping(value = "/member/createMember")
    public int join_POST(@RequestBody MemberVO vo) {
        return memberService.join(vo);
    }
    // SNS 회원가입 화면
    @GetMapping(value = "/member/snsjoin")
    public String snsjoin_GET(MemberVO vo) {
        return "member/snsjoin";
    }
    // SNS 회원가입 처리
    @PostMapping(value = "/member/createMembersns")
    public String snsjoin_POST(MemberVO vo, HttpSession session) {
        memberService.join(vo);
        MemberVO userinfo = memberService.sns_login(vo);
        userinfo.setSns("KAKAO");
        session.setAttribute("userinfo", userinfo);
        return "redirect:/";
    }
    // 이메일 가입여부 체크
    @ResponseBody
    @PostMapping(value = "/member/email_search")
    public String email_search(@RequestBody MemberVO vo, Model model) {
        model.addAttribute("email", vo.getEmail());
        System.out.println(vo.getEmail());
        return memberService.email_search(vo.getEmail());
    }
    // ----------------------------------------------------------------------------------------------------------- 회원가입 end



    

    // 관리자 기능 ( Admin ) ---------------------------------------------------------------------------------------------
    // 전체 회원 리스트
    @GetMapping(value = "/admin/member")
    public ModelAndView admin_member(CriteriaDTO cri) {
        cri.setBoardPerPage(7);
        ModelAndView mav = new ModelAndView();
        ArrayList<MemberVO> vo = memberService.memberListAll(cri);
        mav.addObject("vo", vo);
        mav.addObject("countAll", memberService.countAll(cri));
        mav.addObject("pager", new PageDTO(cri, memberService.countAll(cri)));
        mav.setViewName("/member/admin/memberlist");
        return mav;
    }
    // 차단 회원 리스트
    @GetMapping(value = "/admin/memberbanned")
    public ModelAndView admin_memberBanned(CriteriaDTO cri) {
        cri.setBoardPerPage(7);
        ModelAndView mav = new ModelAndView();
        ArrayList<MemberVO> vo = memberService.memberListBanned(cri);
        mav.addObject("vo", vo);
        mav.addObject("countBanned", memberService.countBanned(cri));
        mav.addObject("pager", new PageDTO(cri, memberService.countBanned(cri)));
        mav.setViewName("/member/admin/bannedlist");
        return mav;
    }
    // 회원 IsActivated 처리
    @ResponseBody
    @PostMapping(value = "/admin/member")
    public String memberlist(@RequestBody HashMap<String, String> param) {
        String email = param.get("email");
        String isactivated = param.get("isactivated");
        memberService.banned(email, isactivated);
        return "success";
    }
    // -------------------------------------------------------------------------------------------- 관리자 기능 ( Admin ) end
}
