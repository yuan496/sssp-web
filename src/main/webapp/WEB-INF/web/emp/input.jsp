<%@page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<html>
<head>
<title>SSSP-Employee jsp</title>
</head>
<body>

	<h2>Employee Manager</h2>
	
	<c:set value="${pageContext.request.contextPath }/emp/emp" var="url"></c:set>
	<c:if test="${employee.id != null }">
		<c:set value="${pageContext.request.contextPath }/emp/emp/{employee.id}" var="url"></c:set>
	</c:if>

	<form:form method="post"
		action="${url}"
		commandName="employee">

		<table>
			<form:hidden path="id" />
			<c:if test="${employee.id != null }">
				<input type="hidden" name="_method" value="PUT" />
			</c:if>
			<tr>
				<td><form:label path="lastName">lastName</form:label></td>
				<td><form:input path="lastName" /></td>
			</tr>
			<tr>
				<td><form:label path="email">email</form:label></td>
				<td><form:input path="email" /></td>
			</tr>
			<tr>
				<td><form:label path="birth">birth</form:label></td>
				<td><form:input path="birth" /></td>
			</tr>
			<tr>
				<td><form:label path="department">department</form:label></td>
				<td><form:select path="department.id" items="${departments }"
						itemLabel="departmentName" itemValue="id"></form:select></td>
			</tr>
			<tr>
				<td colspan="2"><input type="submit"
					value="submit" /></td>
			</tr>
		</table>
	</form:form>