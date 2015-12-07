<!DOCTYPE html>
<%@page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<html>
<head>
<title>SSSP-User jsp</title>
</head>
<body>

	<h2>User Manager</h2>
	
	<c:set value="${pageContext.request.contextPath }/user/user" var="url"></c:set>
	<c:if test="${user.id != null }">
		<c:set value="${pageContext.request.contextPath }/user/user/{user.id}" var="url"></c:set>
	</c:if>

	<form:form method="post"
		action="${url}"
		commandName="user">

		<table>
			<form:hidden path="id" />
			<c:if test="${user.id != null }">
				<input type="hidden" name="_method" value="PUT" />
			</c:if>
			<tr>
				<td><form:label path="code">code</form:label></td>
				<td><form:input path="code" /></td>
			</tr>
			<tr>
				<td><form:label path="userName">userName</form:label></td>
				<td><form:input path="userName" /></td>
			</tr>
			<tr>
				<td><form:label path="name">name</form:label></td>
				<td><form:input path="name" /></td>
			</tr>
			<tr>
				<td><form:label path="mobile">mobile</form:label></td>
				<td><form:input path="mobile" /></td>
			</tr>
			<tr>
				<td><form:label path="address">address</form:label></td>
				<td><form:input path="address" /></td>
			</tr>
			<tr>
				<td><form:label path="birth">birth</form:label></td>
				<td><form:input path="birth" /></td>
			</tr>
			<tr>
				<td colspan="2"><input type="submit"
					value="submit" /></td>
			</tr>
		</table>
	</form:form>