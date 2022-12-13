package com.nailseong.invitation.view;

import com.nailseong.invitation.member.MemberController;
import com.nailseong.invitation.member.dto.SignupRequest;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class ViewController {

    private final MemberController memberController;

    public ViewController(final MemberController memberController) {
        this.memberController = memberController;
    }

    @GetMapping("/")
    public ModelAndView getHome() {
        return new ModelAndView("index");
    }

    @GetMapping("/signup")
    public ModelAndView getSignup(final SignupRequest request) {
        final ModelAndView view = new ModelAndView("signup");
        view.addObject("request", request);
        return view;
    }

    @PostMapping("/signup")
    public String postSignup(@ModelAttribute("request") final SignupRequest request) {
        memberController.signup(request);
        return "redirect:/";
    }
}
