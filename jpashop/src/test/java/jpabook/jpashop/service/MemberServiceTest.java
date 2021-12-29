package jpabook.jpashop.service;

import jpabook.jpashop.domain.Member;
import jpabook.jpashop.repository.MemberRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;

import static org.junit.Assert.*;


/**그래야 완전히 스프링과 인티그레이션해서 스프링 부트를 올려서 테스트 할 수 있음*/
@RunWith(SpringRunner.class)
@SpringBootTest
/** 기본 설정이 RollBack임 그러므로 jpa입장에서는 insert쿼리를 넣을 필요가 없음 + 영속성컨텍스트를 플러쉬해서...디비에 인서트?*/
@Transactional
public class MemberServiceTest {

    @Autowired MemberService memberService;
    @Autowired MemberRepository memberRepository;
    @Autowired EntityManager em;

    @Test
//    @Rollback(value = false)
    public void 회원가입() throws Exception{
        //given
        Member member=new Member();
        member.setName("kim");

        //when
        Long saveId = memberService.join(member);

        //then
        /** 이게 있어야 테스트 코드에서 디비에 넣어줌 */
        em.flush();
        /** jpa에서 영속성컨텍스트로 인해 pk가 같은걸 하나로 관리해 주기 때문*/
        assertEquals(member,memberRepository.findOne(saveId));
    }

    @Test(expected = IllegalStateException.class)
    public void 중복_회원_예외() throws Exception{
        //given
        Member member1 = new Member();
        member1.setName("kim");

        Member member2 = new Member();
        member2.setName("kim");

        //when
        memberService.join(member1);
        memberService.join(member2);
        /**  @Test(expected = IllegalStateException.class) 이문장으로 밑에거 안써도 됨*/
//        try{
//            memberService.join(member2);
//        }catch (IllegalStateException e){
//            return;
//        }

        //then
        /** 여기 오면 안되는거임 위에서 제어가 끝났어야...*/
        fail("예외가 발생해야 한다.");

    }
}