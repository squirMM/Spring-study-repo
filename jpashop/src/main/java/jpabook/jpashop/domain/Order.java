package jpabook.jpashop.domain;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name="orders")
@Getter @Setter
/** 따로 order=new Order() 못하도록 막아주는것*/
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Order {

    @Id @GeneratedValue
    @Column(name="order_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    /** 매핑을 뭘로 할거냐? + 연관관계의 주인은 order이니까 */
    @JoinColumn(name="member_id")
    private Member member;

    /** FetchType.EAGER */
    //jpql : select o from o -> sql : select * from order
    // 그대로 번역이 됨... 그래서 n+1(order)문제 발생
    // member을 꼭 같이 조회 하겠다는 말이 됨
    // LAZY로 설정한 후 꼭 함께 조회하고 싶으면 fetch join을 사용해라

    @OneToMany(mappedBy = "order",cascade = CascadeType.ALL)
    private List<OrderItem> orderItems = new ArrayList<>();

    /**Cascade.All*/
    //원래 디비에 저장? 하려면 persist를 엔티티 마다 해줘야하는데
    //이걸쓰면 OrderItem도 알아서 persist가 되는거임 + 각각 해주지 않아도 된다.
    //이걸 어느 범위에서 써야하는가? order에서만 delivery와 orderItem을 사용해 즉 persist lifeCycle이 같으므로 사용해야하는거
    // 모르면 안쓰는게 나음

    @OneToOne(fetch = FetchType.LAZY,cascade = CascadeType.ALL)
    @JoinColumn(name="delivery_id")
    private Delivery delivery;

    /** 하이버네이트가 알아서 지원해줌 */
    private LocalDateTime oderDate;

    @Enumerated(EnumType.STRING)
    private OrderStatus status;

    //==연관관계 매서드==//
    /** 양방향 연관관계를 주입해 주는것임
     * 컨트롤 해주는 쪽이 들고 있는게 좋음
     * */
    public void setMember(Member member){
        this.member = member;
        member.getOrders().add(this);
    }

    public void addOrderItem(OrderItem orderItem){
        orderItems.add(orderItem);
        orderItem.setOrder(this);
    }

    public void setDelivery(Delivery delivery){
        this.delivery=delivery;
        delivery.setOrder(this);
    }

    //==생성 매서드==//
    public static Order CreateOrder(Member member, Delivery delivery, OrderItem...orderItems){
        Order order = new Order();
        order.setMember(member);
        order.setDelivery(delivery);
        for (OrderItem orderItem : orderItems ){
            order.addOrderItem(orderItem);
        }
        order.setStatus(OrderStatus.ORDER);
        order.setOderDate(LocalDateTime.now());
        return order;
    }

    //==비즈니스 로직==//
    /**
     * 주문 취소
     * */
    public void cancel(){
        if (delivery.getStatus()==DeliveryStatus.COMP){
            throw new IllegalStateException("이미 배송 완료된 상품은 취소가 불가능 합니다.");
        }
        /** jpa의 장점 굳이 디비에서 정보를 끌어와서 변경해주지 않아도
         * 이렇게 상태가 바뀌면 jpa가 알아서 변경탐색을 해서 db를 정보 변경해줌*/
        this.setStatus(OrderStatus.CANCEL);
        for (OrderItem orderItem: orderItems) {
            orderItem.cancel();
        }
    }

    //==조회 로직==//
    /**
     * 전체 주문 가격 조회
     * */
    public int getTotalPrice(){
        /**같은 코드임*/
//        int totalPrice=0;
//        for (OrderItem orderItem:orderItems
//        ) {
//            totalPrice+=orderItem.getTotalPrice();
//        }
//        return totalPrice;
        return orderItems.stream().mapToInt(OrderItem::getTotalPrice).sum();
    }


}
