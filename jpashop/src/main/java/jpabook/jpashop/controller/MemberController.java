package jpabook.jpashop.controller;

import jpabook.jpashop.domain.Address;
import jpabook.jpashop.domain.Member;
import jpabook.jpashop.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import javax.validation.Valid;
import java.util.List;

@Controller
@RequiredArgsConstructor
public class MemberController {
    private final MemberService memberService;

    @GetMapping("/members/new")
    public String createForm(Model model){
        model.addAttribute("memberForm",new MemberForm());
        return "members/createMemberForm";
    }

    /**
     * @Valid: @NotEmpty를(어노테이션을) validation으로 사용할 수 있어.
     * BindingResult: 오류 출력이 아닌 오류를 포함해서 화면을 띄워줌
     * */
    @PostMapping("/members/new")
    public String create(@Valid MemberForm form , BindingResult result){

        if (result.hasErrors()){
            return "members/createMemberForm";
        }

        Address address = new Address(form.getCity(), form.getStreet(), form.getZipcode());
        Member member = new Member();
        member.setName(form.getName());
        member.setAddress(address);

        /** 등록 */
        memberService.join(member);
        /**
         * 이런 행위를 한후 재 로딩 하면 안좋아서 리다이렉트 사용
         * 첫번째 페이지로 넘어가
         * */
        return "redirect:/";
    }

    @GetMapping("/members")
    public String list(Model model){
        /**
         * 만약 Member에 화면에 보여주지 않아도 될게 많다면
         * DTO로 변환해서 원하는데이터만 추출해서 전송하자!
         *
         * API를 만들때는 절때 엔티티를 외부로 반환하지 말것.
         *
         * */
        List<Member>members=memberService.findMembers();
        model.addAttribute("members",members);
        return "members/memberList";
    }

}
