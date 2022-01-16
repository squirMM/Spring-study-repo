package jpabook.jpashop.domain;

import javax.persistence.MappedSuperclass;

@MappedSuperclass
public class BaseEntity {
    private String lastModifiedBy;
    private String createDate;

    public String getLastModifiedBy() {
        return lastModifiedBy;
    }

    public void setLastModifiedBy(String lastModifiedBy) {
        this.lastModifiedBy = lastModifiedBy;
    }

    public String getCreateDate() {
        return createDate;
    }

    public void setCreateDate(String createDate) {
        this.createDate = createDate;
    }
}
