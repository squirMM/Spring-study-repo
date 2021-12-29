package jpabook.jpashop.repository;

import jpabook.jpashop.domain.Member;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

/**스프링이 빈으로 등록해 줌*/
@Repository
//@RequiredArgsConstructor
public class MemberRepository {

    /**jpa 엔티티매니저를 스프링 생성한 엔티티매니저에 주입해줌?!
     * 스프링 데이터 jpa에서 Autowired로 PersistenceContext 해줌*/
    @PersistenceContext
    private EntityManager em;

    public void save(Member member){
        /**
         * 영속성 컨텍스트에 member객체를 올림
         * pk가 항상 생성되어있는게 보장이 됨 + 여기서는 id임
         * 아직 DB에 들어가있는 시점이 아니더라도 그렇게 해줌
         * */
        em.persist(member);
    }

    public Member findOne(Long id){
        return em.find(Member.class, id);
    }

    public List<Member> findAll(){
        /**
         * sql은 테이블 대상으로 쿼리를 실행
         * 결국 jpql은 (sql으로 번역 되긴 하지만) 엔티티를 대상으로 쿼리를 진행
         * */
        return em.createQuery("select m from Member m",Member.class)
                .getResultList();
    }

    public List<Member> findByName(String name){
        return em.createQuery("select m from Member m where m.name=:name",Member.class)
                .setParameter("name",name)
                .getResultList();
    }

}
