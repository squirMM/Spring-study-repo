package hellojpa;

import javax.persistence.*;
import java.util.List;

public class JpaMain {
    public static void main(String[] args){
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("hello");
        EntityManager em = emf.createEntityManager();

        /**
         * 스프링이 알아서 해줘서 실상은 이 코드 필요 없어...
         * */

        /** jpa의 모든 단위는 트렌젝션으로 부터...실행돼
         * 대충 이거 없으면 안된다는 말
         * */
        EntityTransaction tx = em.getTransaction();
        tx.begin();
        try{
            Team team=new Team();
            team.setName("TeamA");
            em.persist(team);

            em.flush();
            em.clear();

            Member member= new Member();
            member.setName("member1");
            /**이 연관관계 메서드는 하나로 결정해서 만들어!
             * 장애가 날 수 있음!!!!!!!!!
             * */
            member.changeTeam(team);
            em.persist(member);

            em.flush();
            em.clear();

            Member findMember = em.find(Member.class, member.getId());
            List<Member>members=findMember.getTeam().getMembers();

            for (Member m :members){
                System.out.println("m = " + m.getName());
            }


            tx.commit();
        }catch (Exception e){
            tx.rollback();
        }
        finally {
            /**
             * 중요! 꼭 닫아라!
             * 엔티티 매니저가 기본적으로 데이테베이스커넥션을 물고 동작하기때문
             * */
            em.close();
        }
        /**
         * was가 내려갈때 반환
         * 그래야 커넥션 풀링 같은 리소스가 릴리즈댐
         * */
        emf.close();
    }
}
