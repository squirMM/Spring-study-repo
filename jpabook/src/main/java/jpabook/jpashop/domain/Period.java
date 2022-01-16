package jpabook.jpashop.domain;

import javax.persistence.Embeddable;
import java.time.LocalDateTime;
import java.util.Date;

@Embeddable
public class Period {
    private LocalDateTime startDate;
    private LocalDateTime endDate;
}
