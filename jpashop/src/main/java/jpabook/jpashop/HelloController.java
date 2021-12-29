package jpabook.jpashop;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HelloController {
    /**hello라는 url이 오면 해당 컨트롤러가 호출됨 */
    @GetMapping("hello")
    public String hello(Model model){
        model.addAttribute("data","hello!!!");
        /**화면이름 다시말해서 templates의 이름 -> 스프링 부트의 타임리프가 html을 찾아주는것*/
        return "hello";
    }
}
