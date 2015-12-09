package com.fzb.sssp.service.impl;

import java.util.Date;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.fzb.sssp.dao.OperateRepository;
import com.fzb.sssp.domain.Operate;
import com.fzb.sssp.service.OperateService;


/** {描述: 功能，使用对象，使用方法等}
 * @author fangzhibin
 * @since 版本号，从什么版本开始
 * @createDate 2015年10月6日 下午6:54:48
 */
@Service("operateService")
@Transactional
public class OperateServiceImpl implements OperateService {
	
	private static final Logger log = LoggerFactory.getLogger(OperateServiceImpl.class);
	
	@Autowired
	private OperateRepository operateRepository;
	
	/**
     * {简述，保留点号}.
     * <p>
     * {详述}
     * <p>
     * <code>{样例代码， 小于号大于号转义&lt; &gt;}</code>
     * @author fangzhibin 2015年12月7日 下午7:26:13
     * @param pageNo
     * @param pageSize
     * @return
     * @modify {上次修改原因} by fangzhibin 2015年12月7日 下午7:26:13
     */
    @Override
    public Page<Operate> getPage(int pageNo, int pageSize) {
    	PageRequest pageRequest = new PageRequest(pageNo - 1, pageSize);
	    return operateRepository.findAll(pageRequest);
    }

	/**
     * {简述，保留点号}.
     * <p>
     * {详述}
     * <p>
     * <code>{样例代码， 小于号大于号转义&lt; &gt;}</code>
     * @author fangzhibin 2015年12月7日 下午7:26:13
     * @param operate
     * @modify {上次修改原因} by fangzhibin 2015年12月7日 下午7:26:13
     */
    @Override
    public void save(Operate operate) {
    	log.info("save the operate is {}", operate.toString());
		if(null != operate && null == operate.getId()) {
			operate.setCreateTime(new Date());
		}
		operateRepository.saveAndFlush(operate);
    }

	/**
     * {简述，保留点号}.
     * <p>
     * {详述}
     * <p>
     * <code>{样例代码， 小于号大于号转义&lt; &gt;}</code>
     * @author fangzhibin 2015年12月7日 下午7:26:13
     * @param id
     * @return
     * @modify {上次修改原因} by fangzhibin 2015年12月7日 下午7:26:13
     */
    @Override
    public Operate get(Long id) {
    	log.info("get the operate by id is {}", id);
	    return operateRepository.findOne(id);
    }

	/**
     * {简述，保留点号}.
     * <p>
     * {详述}
     * <p>
     * <code>{样例代码， 小于号大于号转义&lt; &gt;}</code>
     * @author fangzhibin 2015年12月7日 下午7:26:13
     * @param id
     * @modify {上次修改原因} by fangzhibin 2015年12月7日 下午7:26:13
     */
    @Override
    public void delete(Long id) {
    	log.info("delete the operate by id is {}", id);
    	operateRepository.delete(id);
    }
}
