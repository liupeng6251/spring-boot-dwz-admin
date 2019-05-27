package org.qvit.hybrid.sys.dto;

public class AdminRoleDto extends BaseDto {

    /**
     * 
     */
    private static final long serialVersionUID = 873835434100665373L;

    private String roleName;
    
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
