<!DOCTYPE html>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<html>
<head>
<title>SSSP-Department jsp</title>
<script type="text/javascript" src="${pageContext.request.contextPath }/static/js/jquery-1.11.3.min.js"></script>
<style type="text/css">
body {
	font-family: sans-serif;
}

.data, .data td {
	border-collapse: collapse;
	border: 1px solid #aaa;
	margin: 2px;
	padding: 2px;
}

.data .head {
	font-weight: bold;
	background-color: #5C2CAA;
	color: white;
}
</style>
</head>
<body>
	<h3>Department</h3>
	<c:if test="${page != null && page.numberOfElements > 0}">
		<table class="data">
			<tr>
				<td class="head"><spring:message code="menu.code"></spring:message></td>
				<td class="head"><spring:message code="menu.name"></spring:message></td>
				<td class="head"><spring:message code="dept.createTime"></spring:message></td>
				<td class="head"><spring:message code="base.edit"></spring:message></td>
				<td class="head"><spring:message code="base.delete"></spring:message></td>
			</tr>
			<c:forEach items="${page.content}" var="dept">
				<tr>
					<td>${dept.code}</td>
					<td>${dept.name}</td>
					<td><fmt:formatDate value="${dept.createTime}" pattern="yyyy-MM-dd HH:mm:ss"/></td>
					<td><a href="${pageContext.request.contextPath }/web/dept/dept/${dept.id}">edit</a></td>
					<td><a href="${pageContext.request.contextPath }/web/dept/delete?id=${dept.id}">delete</a></td>
				</tr>
			</c:forEach>
			<tr>
				<td colspan="8">
					<div style="float:right">
						共${page.totalElements } 条记录
						共${page.totalPages } 页
						当前第${page.number + 1 } 页
						<a href="?pageNo=${page.number}">上一页</a>
						<a href="?pageNo=${page.number + 2}">下一页</a>
					</div>
				</td>
			</tr>
		</table>
	</c:if>


</body>
</html>
