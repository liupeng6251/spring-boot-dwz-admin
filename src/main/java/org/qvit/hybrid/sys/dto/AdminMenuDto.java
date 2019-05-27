package org.qvit.hybrid.sys.dto;

import org.qvit.hybrid.enums.InvalidStatus;

public class AdminMenuDto extends BaseDto {

    /**
     * 
     */
    private static final long serialVersionUID = -6705765756550913693L;

    private String menuName;
    private String menuUrl;
    private InvalidStatus status;
    
    private Integer level;
    
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

}
