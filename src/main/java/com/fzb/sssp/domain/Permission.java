/**
 * 
 */
package com.fzb.sssp.domain;

import java.io.Serializable;

/**
 * 权限类
 * @author zhuyuyin
 */
public class Permission implements Serializable{

	private static final long serialVersionUID = 6872521982969850456L;
	
	private Long id;
	private Role role;
	private String operateCodes;
	private String menuCodes;
}
