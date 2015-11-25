package com.fzb.sssp.dao;

import java.util.List;
import javax.persistence.QueryHint;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.QueryHints;
import com.fzb.sssp.domain.Department;

/**
 * {描述: 功能，使用对象，使用方法等}
 * @author fangzhibin
 * @since 版本号，从什么版本开始
 * @createDate 2015年10月6日 下午12:28:43
 */
public interface DepartmentRepository extends JpaRepository<Department, Integer>, JpaSpecificationExecutor<Department> {
	
	@QueryHints({@QueryHint(name=org.hibernate.jpa.QueryHints.HINT_CACHEABLE,value="true")})
	@Query("FROM Department d")
	List<Department> getAll();
}
