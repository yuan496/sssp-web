package com.engineer.sssp.dao;

import java.util.List;
import javax.persistence.QueryHint;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.QueryHints;
import com.engineer.sssp.entity.Menu;

/**
 * {描述: 功能，使用对象，使用方法等}
 * @author fangzhibin
 * @since 版本号，从什么版本开始
 * @createDate 2015年10月6日 下午12:28:43
 */
public interface MenuRepository extends JpaRepository<Menu, Long>, JpaSpecificationExecutor<Menu> {
	
	@QueryHints({@QueryHint(name=org.hibernate.jpa.QueryHints.HINT_CACHEABLE,value="true")})
	@Query("FROM Menu d")
	List<Menu> getAll();
}
