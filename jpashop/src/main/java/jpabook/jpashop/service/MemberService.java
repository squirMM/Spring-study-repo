package jpabook.jpashop.service;

import jpabook.jpashop.domain.Member;
import jpabook.jpashop.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
/**jpa 의 모든 데이터 변경같은건 트렌젝션 안에서 생성 되야해
 * 종류가 두가지가 있는데 이게 써먹을게 많아! 이거 써!!
 * 조회가 읽기 전용으로 남기면 최적화 됨! + 근데 DB마다 다르니까 알아보긴해야해
 * */
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;

    /**생성자 주입이 좋아
     * RequiredArgsConstructor: final변수로 생성자 만들어 줌
     * 생성자 하나면 Autowired안써도 알아서 됨*/
//    @Autowired
//    public MemberService(MemberRepository memberRepository) {
//        this.memberRepository = memberRepository;
//    }

    //회원 가입
    /**이렇게 따로 애노테이션을 넣어주면 이게 우선권을 가져! 그럼 데이터 추가가 가능해 */
    @Transactional
    public Long join(Member member){
        validateDuplicateMember(member);
        /** 영속성 컨텍스트가 보장되기 때문에 getid가능 */
        memberRepository.save(member);
        return member.getId();
    }
    private void validateDuplicateMember(Member member) {
        /**동시에 memberA가 가입한다고 했을때 save()가 동시 호출 가능함
         * 그러므로 최후의 방어로 이름을 유니크키로 잡으면 중복 방지가능해 *
         * */
        List<Member> findMembers = memberRepository.findByName(member.getName());
        /* 수가 0이아니라면 이라는 조건을 넣으면 좀 더 최적화 시킬 수 있음 */
        if(!findMembers.isEmpty()){
            throw new IllegalStateException("이미 존재하는 회원입니다.");
        }
    }

    //회원 조회
    public List<Member> findMembers(){
        return memberRepository.findAll();
    }

    public Member findOne(Long memberId){
        return memberRepository.findOne(memberId);
    }
}
