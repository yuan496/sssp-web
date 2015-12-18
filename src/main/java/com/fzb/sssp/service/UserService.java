package com.fzb.sssp.service;

import java.util.List;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.MediaType;
import org.springframework.data.domain.Page;
import com.fzb.sssp.entity.User;


/** {描述: 功能，使用对象，使用方法等}
 * @author fangzhibin
 * @since 版本号，从什么版本开始
 * @createDate 2015年10月6日 下午12:30:50
 */
@Path("/userService")
public interface UserService {
	
	@GET
	@Path("/getPage/{pageNo}/{pageSize}")
	public Page<User> getPage(@PathParam("pageNo") int pageNo, @PathParam("pageSize") int pageSize);
	
	@PUT
	@Path("/save")
	@Consumes(MediaType.APPLICATION_JSON)
	public void save(User user);
	
	@GET
	@Path("/get/{id}")
	public User get(@PathParam("id") Long id);
	
	@DELETE
	@Path("/delete/{id}")
	public void delete(@PathParam("id") Long id);

	/**
     * {简述，保留点号}.
     * <p>
     * {详述}
     * <p>
     * <code>{样例代码， 小于号大于号转义&lt; &gt;}</code>
     * @author fangzhibin 2015年12月18日 上午11:05:00
     * @param adminName
     * @return
     * @modify {上次修改原因} by fangzhibin 2015年12月18日 上午11:05:00
     */
	@GET
	@Path("/get/{code}")
    public List<User> findByCode(String code);
}
