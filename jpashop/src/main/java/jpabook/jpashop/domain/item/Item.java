package jpabook.jpashop.domain.item;

import jpabook.jpashop.domain.Category;
import jpabook.jpashop.exception.NotEnoughStockException;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
/**상속들을 어떤 형식으로 할건지 */
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
/**구분 어떻게 할건지 Book:B 이런거 말하는거임 */
@DiscriminatorColumn(name = "dtype")
@Getter @Setter
public abstract class Item {
    @Id @GeneratedValue
    @Column(name = "item_id")
    private Long id;

    private String name;

    private int price;

    private  int stockQuantity;

    @ManyToMany(mappedBy = "items")
    private List<Category> categories = new ArrayList<>();

    //==비즈니스 로직==//
    /**
     * 보통은 service에서 setStockQuantity를 불러서 이 값을 저장했겠지만
     * 해당 변수가 있는 곳에 비스니스매서드가 있는게 좋아
     * 다시말해서 Entity안에 비즈니스로직을 넣는게 객체지향적임 즉, 응집도도 높고*/

    /**
     * stock 증가
     * */
    public void addStock(int quantity){
        this.stockQuantity+=quantity;
    }

    /**
     * stock 감소
     * */
    public void removeStock(int quantity){
        int restStock=this.stockQuantity-quantity;
        if (restStock<0){
            throw new NotEnoughStockException("need more stock");
        }
        this.stockQuantity=restStock;
    }
}
