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
import com.fzb.sssp.entity.Menu;


/** {描述: 功能，使用对象，使用方法等}
 * @author fangzhibin
 * @since 版本号，从什么版本开始
 * @createDate 2015年10月6日 下午12:30:50
 */
@Path("/menuService")
public interface MenuService {
	
	@GET
	@Path("/getAll")
	public List<Menu> getAll();
	
	@GET
	@Path("/getPage/{pageNo}/{pageSize}")
	public Page<Menu> getPage(@PathParam("pageNo") int pageNo, @PathParam("pageSize") int pageSize);
	
	@PUT
	@Path("/save")
	@Consumes(MediaType.APPLICATION_JSON)
	public void save(Menu menu);
	
	@GET
	@Path("/get/{id}")
	public Menu get(@PathParam("id") Long id);
	
	@DELETE
	@Path("/delete/{id}")
	public void delete(@PathParam("id") Long id);
}
