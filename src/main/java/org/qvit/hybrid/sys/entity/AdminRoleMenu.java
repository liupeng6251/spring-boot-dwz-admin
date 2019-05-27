package org.qvit.hybrid.sys.entity;

import javax.persistence.Column;
import javax.persistence.Table;

@Table(name = "sys_role_menu")
public class AdminRoleMenu extends BaseEntity {

    /**
     * 
     */
    private static final long serialVersionUID = 7699134448675158239L;

    @Column(name = "role_id")
    private Long roleId;
    
    @Column(name = "menu_id")
    private Long menuId;

    public Long getRoleId() {
        return roleId;
    }

    public void setRoleId(Long roleId) {
        this.roleId = roleId;
    }

    public Long getMenuId() {
        return menuId;
    }

    public void setMenuId(Long menuId) {
        this.menuId = menuId;
    }

}
