package com.engineer.sssp.service.impl;

import java.util.Date;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.engineer.sssp.dao.OrderRepository;
import com.engineer.sssp.entity.Order;
import com.engineer.sssp.service.OrderService;


/** {描述: 功能，使用对象，使用方法等}
 * @author fangzhibin
 * @since 版本号，从什么版本开始
 * @createDate 2015年10月6日 下午6:54:48
 */
@Service("orderService")
@Transactional
public class OrderServiceImpl implements OrderService {
	
	private static final Logger log = LoggerFactory.getLogger(OrderServiceImpl.class);
	
	@Autowired
	private OrderRepository orderRepository;
	
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
    public Page<Order> getPage(int pageNo, int pageSize) {
    	PageRequest pageRequest = new PageRequest(pageNo - 1, pageSize);
	    return orderRepository.findAll(pageRequest);
    }

	/**
     * {简述，保留点号}.
     * <p>
     * {详述}
     * <p>
     * <code>{样例代码， 小于号大于号转义&lt; &gt;}</code>
     * @author fangzhibin 2015年12月7日 下午7:26:13
     * @param order
     * @modify {上次修改原因} by fangzhibin 2015年12月7日 下午7:26:13
     */
    @Override
    public void save(Order order) {
    	log.info("save the order is {}", order.toString());
		if(null != order && null == order.getId()) {
			order.setCreateTime(new Date());
		}
		orderRepository.saveAndFlush(order);
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
    public Order get(Long id) {
    	log.info("get the order by id is {}", id);
	    return orderRepository.findOne(id);
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
    	log.info("delete the order by id is {}", id);
    	orderRepository.delete(id);
    }
}
