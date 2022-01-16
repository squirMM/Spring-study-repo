package jpabook.jpashop.domain;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

import static javax.persistence.FetchType.LAZY;

@Entity
public class Category extends BaseEntity{
    @Id @GeneratedValue
    private Long id;

    private String name;

    /**
     * 카테고리 보면 구분? 보여주는거 ㅇㅇㅇㅇㅇㅇ
     * */
    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "PARENT_ID")
    private Category parent;

    @OneToMany(mappedBy = "parent")
    private List<Category> child=new ArrayList<>();

    /**
     * 중간테이블 만들어 준ㄴ거
     * BaseEntity도 안들어가.... 이런게 안되는거임
     * */
    @ManyToMany
    @JoinTable(name="CATEGORY_ITEM",
            joinColumns = @JoinColumn(name = "CATEGORY_ID"),
            inverseJoinColumns = @JoinColumn(name="ITEM_ID")
    )
    private List<Item> items=new ArrayList<>();
}

