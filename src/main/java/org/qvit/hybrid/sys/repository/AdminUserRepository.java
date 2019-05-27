package org.qvit.hybrid.sys.repository;

import java.util.List;

import org.qvit.hybrid.sys.entity.AdminUser;
import org.qvit.hybrid.sys.mapper.AdminUserMapper;
import org.qvit.hybrid.utils.PageHelperUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.util.CollectionUtils;

import com.github.pagehelper.Page;

import tk.mybatis.mapper.entity.Example;

@Repository
public class AdminUserRepository {

	@Autowired
	private AdminUserMapper adminUserMapper;

	public void save(AdminUser user) {
		adminUserMapper.insert(user);
	}

	public AdminUser findById(Long userId) {
		return adminUserMapper.selectByPrimaryKey(userId);
	}

	public AdminUser findByEmail(String email) {
		Example example = new Example(AdminUser.class);
		example.createCriteria().andEqualTo("email", email);
		List<AdminUser> list = adminUserMapper.selectByExample(example);
		if (CollectionUtils.isEmpty(list)) {
			return null;
		}
		return list.get(0);
	}

	public AdminUser findByMobile(String mobile) {
		Example example = new Example(AdminUser.class);
		example.createCriteria().andEqualTo("mobile", mobile);
		List<AdminUser> list = adminUserMapper.selectByExample(example);
		if (CollectionUtils.isEmpty(list)) {
			return null;
		}
		return list.get(0);
	}

	public Page<AdminUser> findAll(Integer pageNum, Integer pageSize, AdminUser user) {
		try {
			PageHelperUtils.startPage(pageNum, pageSize);
			return (Page<AdminUser>) adminUserMapper.findPage(user);
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException(e.getMessage(), e);
		}

	}

	public void update(AdminUser newUser) {
		Example example = new Example(AdminUser.class);
		example.createCriteria().andEqualTo("id", newUser.getId()).andEqualTo("version", newUser.getVersion() - 1);
		int effRow = adminUserMapper.updateByExampleSelective(newUser, example);
		if (effRow == 0) {
			throw new RuntimeException("修改数据失败，loanOrderId:" + newUser.getId());
		}

	}
}
