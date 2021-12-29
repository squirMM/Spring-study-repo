package jpabook.jpashop.controller;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;

@Getter @Setter
public class MemberForm {

    /**
     * Member과 비슷한 구성이지만
     * 도메인과 컨트롤러가 원하는 validation이 다를 수 가 있어!
     * 같이 묶어 놓으면 복잡해져 그러니까 그냥 다 따로 해~!!
     * */

    @NotEmpty(message = "회원 이름은 필수 입니다.")
    private String name;
    /**있으면 좋고 아님말고 변수들...*/
    private String city;
    private String street;
    private String zipcode;
}
