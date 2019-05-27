package org.qvit.hybrid.sys.entity;

import javax.persistence.Column;
import javax.persistence.Table;

@Table(name = "sys_admin_role")
public class AdminRole extends BaseEntity {

    /**
     * 
     */
    private static final long serialVersionUID = 873835434100665373L;

    @Column(name = "role_Name")
    private String roleName;
    
    @Column(name = "role_Desc")
    private String roleDesc;

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }

    public String getRoleDesc() {
        return roleDesc;
    }

    public void setRoleDesc(String roleDesc) {
        this.roleDesc = roleDesc;
    }

}
