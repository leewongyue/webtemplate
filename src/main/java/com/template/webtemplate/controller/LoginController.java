package com.template.webtemplate.controller;

import com.template.webtemplate.Session.SessionManager;
import com.template.webtemplate.dto.MemberDTO;
import com.template.webtemplate.entity.Member;
import com.template.webtemplate.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@Controller
@RequestMapping("/")
@Log4j2
//final이 붙거나 @NotNull 이 붙은 필드의 생성자를 자동 생성(@AutoWired 생략)
@RequiredArgsConstructor

public class LoginController {
    @Autowired
    private MemberRepository repository;
    //GetMapping과 PostMapping 의 차이는 Post 방식이냐 Get 방식이냐의 차이

    @GetMapping("/member/main")
    public String mainpage(){return "main";}

    @GetMapping("/member/login")
    public String loginpage(){return "login";}
    @GetMapping("/member/register")
    public String register(Model model){
        model.addAttribute("member",new Member());
        return "register";
    }

    @PostMapping("/member/register.do")
    public String registerProcess(Member member) throws Exception
    {
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        String encodedPw = encoder.encode(member.getPwd());
        member.setPwd(encodedPw);
        repository.save(member);
        return "main";
    }
    @PostMapping("/member/login.do")
    public ModelAndView loginProcess(HttpServletRequest request)
    {
        //로그인 sql 인젝션 관련한 대처방안
        //keyword , html 자체 길이 제한
//        if(request.getParameter("email").matches(""))
        ModelAndView mnv = new ModelAndView();
        Member member =repository.findByEmail(request.getParameter("email"));
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        HttpSession session = request.getSession(false);
        if(encoder.matches(request.getParameter("pw"),member.getPwd()) || session == null)
        {
            session.setAttribute(SessionManager.LOGIN_MEMBER,member.getMid());
            mnv.addObject("로그인 성공");
            mnv.setViewName("/member/main");
            return mnv;
        }
        else
        {

            mnv.addObject("로그인에 실패했습니다.");
            mnv.setViewName("/member/login");
            return mnv;
        }
    }
    @PostMapping("/member/logout.do")
    public ModelAndView logoutProcess(HttpServletRequest request)
    {
        ModelAndView mnv = new ModelAndView();
        //세션 부여되어있는지 확인하기위한 용도
        HttpSession session=request.getSession(false);
        if(session == null)
        {
            mnv.addObject("로그인 세션 정보가 없습니다.");
            mnv.setViewName("/member/main");
            return mnv;
        }
        else
        {
            request.getSession().invalidate();
            mnv.addObject("로그아웃 성공.");
            mnv.setViewName("/member/main");
            return mnv;
        }

    }
    @GetMapping("/member/checksession")
    public String checkSession(HttpServletRequest request)
    {
        HttpSession session = request.getSession(false);
        if(session == null)
        {
            return "/member/main";
        }
        else
        {
            System.out.println((String)session.getAttribute("userId"));
            return "/member/main";
        }
    }

}
