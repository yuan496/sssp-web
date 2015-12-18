package com.fzb.sssp.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import com.fzb.sssp.entity.Operate;

/**
 * {描述: 功能，使用对象，使用方法等}
 * @author fangzhibin
 * @since 版本号，从什么版本开始
 * @createDate 2015年10月6日 下午12:28:43
 */
public interface OperateRepository extends JpaRepository<Operate, Long>, JpaSpecificationExecutor<Operate> {
}
