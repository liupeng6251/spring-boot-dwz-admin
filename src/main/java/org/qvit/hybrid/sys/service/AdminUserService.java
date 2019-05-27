package org.qvit.hybrid.sys.service;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.qvit.hybrid.enums.InvalidStatus;
import org.qvit.hybrid.sys.dto.AdminUserDto;
import org.qvit.hybrid.sys.entity.AdminUser;
import org.qvit.hybrid.sys.entity.AdminUserRole;
import org.qvit.hybrid.sys.repository.AdminUserRepository;
import org.qvit.hybrid.sys.repository.AdminUserRoleRepository;
import org.qvit.hybrid.utils.BaseEntityUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import com.github.pagehelper.Page;

@Service
public class AdminUserService {

    @Autowired
    private AdminUserRepository adminUserRepository;

    @Autowired
    private AdminUserRoleRepository adminUserRoleRepository;

    public void save(AdminUserDto dto) {
        AdminUser emailUser = adminUserRepository.findByEmail(dto.getEmail());
        if (emailUser != null) {
            throw new RuntimeException("邮箱已存在！");

        }
        AdminUser mobileUser = adminUserRepository.findByMobile(dto.getMobile());
        if (mobileUser != null) {
            throw new RuntimeException("电话号码已存在！");

        }
        AdminUser user = convert(dto);
        BaseEntityUtil.bindNewBaseEntity(user);
        adminUserRepository.save(user);
    }

    public AdminUserDto findById(Long userId) {
        AdminUser user = adminUserRepository.findById(userId);
        if (user == null) {
            return null;
        }
        return convertDto(user);
    }

    public AdminUserDto findByIdEmail(String email) {
        AdminUser user = adminUserRepository.findByEmail(email);
        if (user == null) {
            return null;
        }
        return convertDto(user);
    }

    public AdminUserDto findByIdMobile(String mobile) {
        AdminUser user = adminUserRepository.findByMobile(mobile);
        if (user == null) {
            return null;
        }
        return convertDto(user);
    }

    public Page<AdminUserDto> findList(Integer pageNum,Integer pageSize, AdminUserDto dto) {
        AdminUser user = convert(dto);
        Page<AdminUser> orders = adminUserRepository.findAll(pageNum,pageSize,user);
        List<AdminUserDto> dtos = new ArrayList<>();
        for (AdminUser o : orders) {
            dtos.add(convertDto(o));
        }
        Page<AdminUserDto> retValue = new Page<>();
        retValue.addAll(dtos);
        retValue.setPageNum(orders.getPageNum());
        retValue.setTotal(orders.getTotal());
        retValue.setPageSize(orders.getPageSize());
        return retValue;
    }

    private AdminUserDto convertDto(AdminUser user) {
        AdminUserDto dto = new AdminUserDto();
        dto.setCreateDate(user.getCreateDate());
        dto.setDepartment(user.getDepartment());
        dto.setEmail(user.getEmail());
        dto.setEntryDate(user.getEntryDate());
        dto.setId(user.getId());
        dto.setJobNumber(user.getJobNumber());
        dto.setMobile(user.getMobile());
        dto.setOccupation(user.getOccupation());
        dto.setPassword(user.getPassword());
        dto.setStatus(InvalidStatus.getEnum(user.getStatus()));
        dto.setUpdateDate(user.getUpdateDate());
        dto.setUserName(user.getUserName());
        dto.setVersion(user.getVersion());
        return dto;
    }

    private AdminUser convert(AdminUserDto dto) {
        AdminUser user = new AdminUser();
        user.setCreateDate(dto.getCreateDate());
        user.setDepartment(dto.getDepartment());
        user.setEmail(dto.getEmail());
        user.setEntryDate(dto.getEntryDate());
        user.setId(dto.getId());
        user.setJobNumber(dto.getJobNumber());
        user.setMobile(dto.getMobile());
        user.setOccupation(dto.getOccupation());
        user.setPassword(dto.getPassword());
        if (dto.getStatus() != null) {
            user.setStatus(dto.getStatus().getValue());
        }
        user.setUpdateDate(dto.getUpdateDate());
        user.setUserName(dto.getUserName());
        user.setVersion(dto.getVersion());
        return user;
    }

    public void updatePassword(Long userId, String password) {
        AdminUser user = adminUserRepository.findById(userId);
        if (user == null) {
            throw new RuntimeException("修改失败,对应的用户不存在！");
        }
        AdminUser newUser = new AdminUser();
        newUser.setId(userId);
        newUser.setVersion(user.getVersion());
        BaseEntityUtil.bindUpdateBaseEntity(newUser);
        newUser.setPassword(password);
        adminUserRepository.update(newUser);
    }

    public void updateInfo(AdminUserDto dto) {
        AdminUser user = adminUserRepository.findById(dto.getId());
        if (user == null) {
            throw new RuntimeException("修改失败,对应的用户不存在！");
        }
        if (StringUtils.isNotEmpty(dto.getEmail())) {
            AdminUser emailUser = adminUserRepository.findByEmail(dto.getEmail());
            if (emailUser != null && !emailUser.getId().equals(user.getId())) {
                throw new RuntimeException("邮箱已存在！");

            }
        }
        if (StringUtils.isNotEmpty(dto.getMobile())) {
            AdminUser mobileUser = adminUserRepository.findByMobile(dto.getMobile());
            if (mobileUser != null && !mobileUser.getId().equals(user.getId())) {
                throw new RuntimeException("电话号码已存在！");

            }
        }
        AdminUser newUser = new AdminUser();
        newUser.setId(user.getId());
        newUser.setVersion(user.getVersion());
        BaseEntityUtil.bindUpdateBaseEntity(newUser);
        newUser.setDepartment(dto.getDepartment());
        newUser.setEmail(dto.getEmail());
        newUser.setEntryDate(dto.getEntryDate());
        newUser.setJobNumber(dto.getJobNumber());
        newUser.setMobile(dto.getMobile());
        newUser.setOccupation(dto.getOccupation());
        if (dto.getStatus() != null) {
            newUser.setStatus(dto.getStatus().getValue());
        }
        newUser.setUserName(dto.getUserName());
        adminUserRepository.update(newUser);

    }

    @Transactional
    public void changeRoleById(Long userId, List<String> asList) {
        adminUserRoleRepository.deleteByUserId(userId);
        if (CollectionUtils.isEmpty(asList)) {
            return;
        }
        List<AdminUserRole> roles = new ArrayList<>();
        for (String roleId : asList) {
            AdminUserRole role = new AdminUserRole();
            role.setUserId(userId);
            role.setRoleId(Long.parseLong(roleId));
            BaseEntityUtil.bindNewBaseEntity(role);
            roles.add(role);
        }
        adminUserRoleRepository.save(roles);
    }
}
