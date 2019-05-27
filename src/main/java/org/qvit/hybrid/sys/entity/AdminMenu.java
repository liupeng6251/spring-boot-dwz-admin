package org.qvit.hybrid.sys.entity;

import javax.persistence.Column;
import javax.persistence.Table;

@Table(name = "sys_admin_menu")
public class AdminMenu extends BaseEntity {

    /**
     * 
     */
    private static final long serialVersionUID = -6705765756550913693L;

    @Column(name = "menu_Name")
    private String menuName;
    @Column(name = "menu_Url")
    private String menuUrl;
    private Integer status;
    private Integer level;
    @Column(name = "parent_Id")
    private Long parentId;

    public String getMenuName() {
        return menuName;
    }

    public void setMenuName(String menuName) {
        this.menuName = menuName;
    }

    public String getMenuUrl() {
        return menuUrl;
    }

    public void setMenuUrl(String menuUrl) {
        this.menuUrl = menuUrl;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Integer getLevel() {
        return level;
    }

    public void setLevel(Integer level) {
        this.level = level;
    }

    public Long getParentId() {
        return parentId;
    }

    public void setParentId(Long parentId) {
        this.parentId = parentId;
    }

}
