package org.qvit.hybrid.base.vo;

import java.util.List;

import org.qvit.hybrid.enums.InvalidStatus;

public class AdminMenuVo {
    private String menuName;
    private String menuUrl;
    private InvalidStatus status;
    private Long id;
    private Integer level;
    private Long parentId;
    
    private List<AdminMenuVo> childrens;

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

    public InvalidStatus getStatus() {
        return status;
    }

    public void setStatus(InvalidStatus status) {
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

    public List<AdminMenuVo> getChildrens() {
        return childrens;
    }

    public void setChildrens(List<AdminMenuVo> childrens) {
        this.childrens = childrens;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    
}
