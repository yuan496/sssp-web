<html>
<head>
	<title>SSSP-Employee ftl</title>
</head>
<body>

<h2>Employee Manager</h2>

<#assign url="${request.contextPath}/web/emp/emp" />
<#if employee.id??>
	<#assign url="${pageContext.request.contextPath }/web/emp/emp/{employee.id}" />
</#if>

<form method="post" action="${url}">

	<table>
	<input type="hidden" name="id" value="${employee.id}" />
	<#if employee.id??>
		<input type="hidden" name="_method" value="PUT" />
	</#if>
	<tr>
		<td><label path="lastName">lastName</td>
		<td><input name="lastName" type="text" value="${employee.lastName}"/></td> 
	</tr>
	<tr>
		<td><label path="email">label.email</td>
		<td><input name="email" type="text" value="${employee.email}"/></td> 
	</tr>
	<tr>
		<td><label path="birth">label.birth</td>
		<td><input name="birth" type="text" value="${employee.birth?string("yyyy-MM-dd")}"/></td> 
	</tr>
	<tr>
		<td><label path="department">department</td>
		<td>
			<select name="department.id">
				<#list departments as department>
				<option value="${department.id}" <#if employee.department.id = department.id>selected="true"</#if>>${department.departmentName}</option>
				</#list>
			</select>
		</td> 
	</tr>
	<tr>
		<td colspan="2">
			<input type="submit" value="submit"/>
		</td>
	</tr>
</table>	
</form>
</body>
</html>