package hellojpa;

import javax.persistence.*;
import java.util.Date;

@Entity
/**
 * 만약 테이블이름이  Member이 아닐 경우 매핑
@Table(name="USER")
*/
public class Member {
    @Id @GeneratedValue
    @Column(name = "MEMBER_ID")
    /*PK*/
    private Long id;

    @Column(name = "NAME")
    private String name;

    @ManyToOne
    /** fk와 테이블을 매핑시켜줌*/
    @JoinColumn(name = "TEAM_ID")
    private Team team;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Team getTeam() {
        return team;
    }

    public void setTeam(Team team) {
        this.team = team;
    }

    public void changeTeam(Team team) {
        this.team = team;
        team.getMembers().add(this);
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    /**
     * jpa는 동적으로 생성해야하기때문에 기본생성자 필수
     * */
    public Member(){
    }

}
