package org.qvit.hybrid.sys.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

public abstract class BaseEntity implements Serializable {

    private static final long serialVersionUID = -2860864887818225163L;

    @Id
    @GeneratedValue(generator = "JDBC")
    private Long id;

    @Column(name = "version")
    private Long version;

    @Column(name = "create_Date")
    private Date createDate;

    @Column(name = "update_Date")
    private Date updateDate;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getVersion() {
        return version;
    }

    public void setVersion(Long version) {
        this.version = version;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public Date getUpdateDate() {
        return updateDate;
    }

    public void setUpdateDate(Date updateDate) {
        this.updateDate = updateDate;
    }

}
