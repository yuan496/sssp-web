package com.engineer.sssp.service.impl;

import java.util.Date;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.engineer.sssp.dao.UserRepository;
import com.engineer.sssp.entity.User;
import com.engineer.sssp.service.UserService;


/** {描述: 功能，使用对象，使用方法等}
 * @author fangzhibin
 * @since 版本号，从什么版本开始
 * @createDate 2015年10月6日 下午6:54:48
 */
@Service("userService")
@Transactional
public class UserServiceImpl implements UserService {
	
	private static final Logger log = LoggerFactory.getLogger(UserServiceImpl.class);
	
	@Autowired
	private UserRepository userRepository;
	
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
    public Page<User> getPage(int pageNo, int pageSize) {
    	PageRequest pageRequest = new PageRequest(pageNo - 1, pageSize);
	    return userRepository.findAll(pageRequest);
    }

	/**
     * {简述，保留点号}.
     * <p>
     * {详述}
     * <p>
     * <code>{样例代码， 小于号大于号转义&lt; &gt;}</code>
     * @author fangzhibin 2015年12月7日 下午7:26:13
     * @param user
     * @modify {上次修改原因} by fangzhibin 2015年12月7日 下午7:26:13
     */
    @Override
    public void save(User user) {
    	log.info("save the user is {}", user.toString());
		if(null != user && null == user.getId()) {
			user.setCreateTime(new Date());
		}
		userRepository.saveAndFlush(user);
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
    public User get(Long id) {
    	log.info("get the user by id is {}", id);
	    return userRepository.findOne(id);
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
    	log.info("delete the user by id is {}", id);
    	userRepository.delete(id);
    }
    
    /**
     * {简述，保留点号}.
     * <p>
     * {详述}
     * <p>
     * <code>{样例代码， 小于号大于号转义&lt; &gt;}</code>
     * @author fangzhibin 2015年12月18日 上午10:53:10
     * @param code
     * @return
     * @modify {上次修改原因} by fangzhibin 2015年12月18日 上午10:53:10
     */
    @Override
    public List<User> findByCode(String code) {
        return userRepository.findByCode(code);
    }
    
    /**
     * {简述，保留点号}.
     * <p>
     * {详述}
     * <p>
     * <code>{样例代码， 小于号大于号转义&lt; &gt;}</code>
     * @author fangzhibin 2016年1月24日 下午4:25:43
     * @param name
     * @param password
     * @return
     * @modify {上次修改原因} by fangzhibin 2016年1月24日 下午4:25:43
     */
    @Override
    public User findByUserNameAndPassword(String userName, String password) {
        return userRepository.findByUserNameAndPassword(userName, password);
    }
}
