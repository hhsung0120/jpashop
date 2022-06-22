package jpabook.jpashop.controller;

import jpabook.jpashop.domain.Address;
import jpabook.jpashop.domain.Member;
import jpabook.jpashop.service.MemberService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;
import java.util.List;

@Controller
@Log4j2
@RequiredArgsConstructor
public class MemberController {

    private final MemberService memberService;

    @GetMapping("/members/new")
    public ModelAndView createForm() {
        return new ModelAndView("/members/createMemberForm")
                .addObject("memberForm", new MemberForm());
    }

    @PostMapping("/members/new")
    public String create(@Valid MemberForm form, BindingResult result) {
        log.info("form data : {}", form);

        if(result.hasErrors()){
            return "/members/createMemberForm";
        }

        Address address = new Address(form.getCity(), form.getStreet(), form.getZipcode());

        Member member = new Member();
        member.setName(form.getName());
        member.setAddress(address);

        memberService.join(member);

        return "redirect:/";
    }

    @GetMapping("members")
    public ModelAndView list(){
        List<Member> members = memberService.findMembers();
        return new ModelAndView("/members/memberList")
                .addObject("members", members);
    }

}
