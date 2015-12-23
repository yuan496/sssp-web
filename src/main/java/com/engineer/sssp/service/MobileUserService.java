package com.engineer.sssp.service;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.MediaType;
import org.springframework.data.domain.Page;
import com.engineer.sssp.entity.MobileUser;


/** {描述: 功能，使用对象，使用方法等}
 * @author fangzhibin
 * @since 版本号，从什么版本开始
 * @createDate 2015年10月6日 下午12:30:50
 */
@Path("/mobileUserService")
public interface MobileUserService {
	
	@GET
	@Path("/getPage/{pageNo}/{pageSize}")
	public Page<MobileUser> getPage(@PathParam("pageNo") int pageNo, @PathParam("pageSize") int pageSize);
	
	@PUT
	@Path("/save")
	@Consumes(MediaType.APPLICATION_JSON)
	public void save(MobileUser mobileUser);
	
	@GET
	@Path("/get/{id}")
	public MobileUser get(@PathParam("id") Long id);
	
	@DELETE
	@Path("/delete/{id}")
	public void delete(@PathParam("id") Long id);
}