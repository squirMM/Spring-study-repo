package jpabook.jpashop.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
/**롬복 사용 -> 로그 띄울수 있음!*/
@Slf4j
public class HomeController {

    /** 첫번째 화면으로 잡히는 거*/
    @RequestMapping("/")
    public String home(){
        log.info("home controller");
        /** home.html 찾아가서 타임리프 띄움*/
        return "home";
    }
}

