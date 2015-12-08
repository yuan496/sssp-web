<html>
<head>
	<title>SSSP-Employee ftl</title>
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
<h3>Employee</h3>
<#if page?? && page.numberOfElements gt 0>
<table class="data">
	<tr>
		<td class="head">lastName</td>
		<td class="head">email</td>
		<td class="head">birth</td>
		<td class="head">createTime</td>
		<td class="head">department</td>
		<td class="head">&nbsp;</td>
		<td class="head">&nbsp;</td>
	</tr>
<#list page.content as emp>
	<tr>
		<td>${emp.lastName}</td>
		<td>${emp.email}</td>
		<td>${(emp.birth?string("yyyy-MM-dd"))!''}</td>
		<td>${(emp.createTime?string("yyyy-MM-dd HH:mm:ss"))!''}</td>
		<td>${emp.department.name}</td>
		<td><a href="${request.contextPath}/emp/emp/${emp.id}">edit</a></td>
		<td><a href="${request.contextPath}/emp/delete?id=${emp.id}">delete</a></td>
	</tr>
</#list>
	<tr>
		<td colspan="7">
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
</#if>
</body>
</html>
