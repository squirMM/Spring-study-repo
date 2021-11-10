package hello.core.member;

import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
public class MemoryMemberRepository implements MemberRepository {
    /**HashMap은 동시성 문제가 있을수 있다. -> ConcurentMap? 을 씀*/
    private static Map<Long,Member>store= new HashMap<>();
    @Override
    public void save(Member member) {
        store.put(member.getId(),member);
    }
    @Override
    public Member findById(Long memberId) {
        return store.get(memberId);
    }
}
