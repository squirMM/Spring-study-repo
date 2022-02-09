package jpql;

import javax.persistence.*;
import java.util.Collection;
import java.util.List;

class JpaMain {
    public static void main(String[] args){
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("hello");
        EntityManager em = emf.createEntityManager();

        EntityTransaction tx = em.getTransaction();
        tx.begin();
        try{

            Team teamA =new Team();
            teamA.setName("teamA");
            em.persist(teamA);

            Team teamB=new Team();
            teamB.setName("teamB");
            em.persist(teamB);

            Member member1 = new Member();
            member1.setUsername("member1");
            member1.setAge(10);
            member1.setType(MemberType.ADMIN);
            member1.setTeam(teamA);
            em.persist(member1);

            Member member2 = new Member();
            member2.setUsername("member2");
            member2.setAge(10);
            member2.setType(MemberType.ADMIN);
            member2.setTeam(teamA);
            em.persist(member2);

            Member member3 = new Member();
            member3.setUsername("member3");
            member3.setAge(10);
            member3.setType(MemberType.ADMIN);
            member3.setTeam(teamB);
            em.persist(member3);

            em.flush();
            em.clear();


            String query="select distinct t From Team t join fetch t.members ";
            List<Team> resultList = em.createQuery(query, Team.class)
                    .getResultList();

            for (Team o : resultList) {
                System.out.println("team = "+o.getName()+"|members= "+o.getMembers().size() );
                //회원1, 팀A(SQL)
                //회원2, 팀A(1차캐쉬)
                //회원3, 팀B(SQL)

                //회원 100명 = 1(회원데이터)+N(첫번째 쿼리로 받아온 값으로 다시 날리는거?)
            }

            tx.commit();

        }catch (Exception e){
            tx.rollback();
        }
        finally {
            em.close();
        }
        emf.close();
    }
}
