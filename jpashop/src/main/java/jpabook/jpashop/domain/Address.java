package jpabook.jpashop.domain;

import lombok.Getter;

import javax.persistence.Embeddable;

/**jpa의 내장타입 즉 내장 될 수 있다.*/
@Embeddable
@Getter
public class Address {
    private String city;
    private String street;
    private String zipcode;

    /** new로 생성을 방지하기 위해 */
    protected Address(){
    }

    /** setter을 사용하지 않도록 하기 위해서
     * 값 타입은 immutable 하게 만들기 위해
     * */
    public Address(String city, String street, String zipcode) {
        this.city = city;
        this.street = street;
        this.zipcode = zipcode;
    }
}
