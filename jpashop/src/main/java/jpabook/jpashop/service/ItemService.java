package jpabook.jpashop.service;

import jpabook.jpashop.domain.item.Book;
import jpabook.jpashop.domain.item.Item;
import jpabook.jpashop.repository.ItemRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class ItemService {

    private final ItemRepository itemRepository;

    @Transactional
    public void saveItem(Item item){
        itemRepository.save(item);
    }

    /**
     * 이 함수 자체를 em.merge()로 해결할 수 있어
     * 그러나 실무에선 이걸 사용해
     * 트랜젝션 커밋 시점에 변경감지가 동작하여
     * 데이터베이스에 UPDATE 쿼리를 실행해 줌
     * */
    @Transactional
    public void updateItem(Long itemId, String name, int price, int stockQuantity){
        Item findItem=itemRepository.findOne(itemId);

        //set보단 이런 방식이 좋음
        //item.change(prict,name,stockQuantity)

//        findItem.setPrice(param.getPrice());
//        findItem.setName(param.getName());
//        findItem.setStockQuantity(param.getStockQuantity());
        //다른 함수는 필요 없어!

        findItem.setPrice(price);
        findItem.setName(name);
        findItem.setStockQuantity(stockQuantity);

    }

    public List<Item> findItems(){
        return itemRepository.findAll();
    }

    public Item findOne(Long itemId){
        return itemRepository.findOne(itemId);
    }

}
