package jpabook.jpashop.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter@Setter
public class Delivery {
    @Id @GeneratedValue
    @Column(name = "delivery_id")
    private Long id;

    @OneToOne(mappedBy = "delivery",fetch = FetchType.LAZY)
    private Order order;

    @Embedded
    private Address address;

    /** EnumType.ORIGINAL = 숫자로 들어가 + 예외케이스 나오면 오류 */
    /** EnumType.STRING = 문자로 들어가 + 예외케이스 나와도 상관 없어 */
    @Enumerated(EnumType.STRING)
    private DeliveryStatus status;
}
