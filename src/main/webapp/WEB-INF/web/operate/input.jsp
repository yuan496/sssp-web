<!DOCTYPE html>
<%@page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<html>
<head>
<title>SSSP-Operate jsp</title>
</head>
<body>

	<h2>Operate Manager</h2>
	
	<c:set value="${pageContext.request.contextPath }/operate/operate" var="url"></c:set>
	<c:if test="${operate.id != null }">
		<c:set value="${pageContext.request.contextPath }/operate/operate/{operate.id}" var="url"></c:set>
	</c:if>

	<form:form method="post"
		action="${url}"
		commandName="operate">

		<table>
			<form:hidden path="id" />
			<c:if test="${operate.id != null }">
				<input type="hidden" name="_method" value="PUT" />
			</c:if>
			<tr>
				<td><form:label path="code">code</form:label></td>
				<td><form:input path="code" /></td>
			</tr>
			<tr>
				<td><form:label path="name">name</form:label></td>
				<td><form:input path="name" /></td>
			</tr>
			<tr>
				<td><form:label path="url">url</form:label></td>
				<td><form:input path="url" /></td>
			</tr>
			<tr>
				<td><form:label path="menu">menu</form:label></td>
				<td><form:select path="menu.id" items="${menus }"
						itemLabel="name" itemValue="id"></form:select></td>
			</tr>
			<tr>
				<td colspan="2"><input type="submit"
					value="submit" /></td>
			</tr>
		</table>
	</form:form>