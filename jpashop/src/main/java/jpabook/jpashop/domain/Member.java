package jpabook.jpashop.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter @Setter
public class Member {
    @Id @GeneratedValue

    /** 칼럼명 지정 빈 칸으로 놔두면 id가 칼럼명이 됨*/
    @Column(name = "member_id")
    private Long id;

    private String name;

    /**내장 됨*/
    @Embedded
    private Address address;

    /** order안에 있는 member에의해 매핑 된거야! */
    @OneToMany(mappedBy = "member")
    private List<Order> orders =new ArrayList<>();


}
