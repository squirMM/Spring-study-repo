package jpabook.jpashop.repository;

import jpabook.jpashop.domain.item.Item;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Repository
//@RequiredArgsConstructor
public class ItemRepository {
    @PersistenceContext
    private EntityManager em;

    public void save(Item item){

        /**
         *  item은 jpa에서 저장하기 전까지 id가 없음
         * 그 뜻은 완전히 새로 생성한 객체가 된다.
         * persist로 새로 등록한다.
         * 이미있다면 merge를 통해 db에서 가져온다고 생각하면 쉬움
         * */

        if(item.getId()==null){
            em.persist(item);
        }else {
            em.merge(item);
        }
    }

    public Item findOne(Long id){
        return em.find(Item.class,id);
    }

    public List<Item> findAll(){
        return em.createQuery("select i from Item i",Item.class)
                .getResultList();
    }
}
