package com.nailseong.invitation.view;

import com.nailseong.invitation.authentication.presentation.AuthController;
import com.nailseong.invitation.authentication.presentation.dto.LoginRequest;
import com.nailseong.invitation.authentication.support.LoginSession;
import com.nailseong.invitation.authentication.support.LoginView;
import com.nailseong.invitation.channel.ChannelController;
import com.nailseong.invitation.channel.application.dto.ChannelListResponse;
import com.nailseong.invitation.member.MemberController;
import com.nailseong.invitation.member.dto.SignupRequest;
import java.util.List;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class ViewController {

    private final AuthController authController;
    private final MemberController memberController;
    private final ChannelController channelController;

    public ViewController(final AuthController authController,
                          final MemberController memberController,
                          final ChannelController channelController) {
        this.authController = authController;
        this.memberController = memberController;
        this.channelController = channelController;
    }

    @GetMapping("/")
    public ModelAndView getHome(final LoginRequest request, @LoginView final LoginSession session) {
        final ModelAndView view = new ModelAndView("index");
        view.addObject("request", request);
        view.addObject("isLogin", session != null);
        if (session != null) {
            view.addObject("username", session.username());
            final List<ChannelListResponse> channels = channelController.getList(session);
            view.addObject("channels", channels);
        }
        return view;
    }

    @PostMapping("/login")
    public String postLogin(@ModelAttribute("request") final LoginRequest request) {
        authController.login(request);
        return "redirect:/";
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
