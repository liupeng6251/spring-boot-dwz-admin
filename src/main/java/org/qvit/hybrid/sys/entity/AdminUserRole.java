package org.qvit.hybrid.sys.entity;

import javax.persistence.Column;
import javax.persistence.Table;

@Table(name = "sys_user_role")
public class AdminUserRole extends BaseEntity {

    /**
     * 
     */
    private static final long serialVersionUID = -2561189414263281644L;

    
    @Column(name = "user_id")
    private Long userId;
    
    @Column(name = "role_id")
    private Long roleId;
    public Long getUserId() {
        return userId;
    }
    public void setUserId(Long userId) {
        this.userId = userId;
    }
    public Long getRoleId() {
        return roleId;
    }
    public void setRoleId(Long roleId) {
        this.roleId = roleId;
    }

}
