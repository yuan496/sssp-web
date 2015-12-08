<!DOCTYPE html>
<%@page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<html>
<head>
<meta charset="utf-8" />
<meta http-equiv="X-UA-Compatible" content="IE=8" />
<title>系统异常</title>
<link href="${pageContext.request.contextPath }/static/css/error.css" rel="stylesheet" />
</head>

<body>
	<div class="wrapper">
		<div class="title">
			<h1>opps!</h1>
		</div>
		<div class="content">
			<div class="message">
				<h3>您访问的页面后台异常！</h3>
				<p>
					服务器产生内部错误或者系统发生异常！请联系管理员！<br>错误信息:${sessionScope.errMsg.msg}
				</p>
			</div>
		</div>
	</div>
</body>
</html>
