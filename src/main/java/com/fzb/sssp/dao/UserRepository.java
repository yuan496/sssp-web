package com.fzb.sssp.dao;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import com.fzb.sssp.entity.User;

/**
 * {描述: 功能，使用对象，使用方法等}
 * @author fangzhibin
 * @since 版本号，从什么版本开始
 * @createDate 2015年10月6日 下午12:28:43
 */
public interface UserRepository extends JpaRepository<User, Long>, JpaSpecificationExecutor<User> {
	
	List<User> findByCode(String code);
}
