package hello.core;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**직접 생성해줘야할 코드를 lombok이 알아서 생성해줌
 * 진짜 많이 씀
 * */
@Getter
@Setter
@ToString
public class HelloLombok {
    private String name;
    private int age;

    public static void main(String[] agrs){
        HelloLombok helloLombok = new HelloLombok();
        helloLombok.setName("adeafe");

        String name= helloLombok.getName();
        System.out.println("name = " + name);
    }
}
