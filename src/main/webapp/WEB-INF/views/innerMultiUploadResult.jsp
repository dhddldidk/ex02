<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
	<p>writer : ${writer }</p>
	
	<c:forEach items="${listPath }" var="item">
		<p>file : ${item }</p>
	</c:forEach>
	<c:forEach items="${listPath }" var="item">
		<img src="resources/upload/${item }">
	</c:forEach>
</body>
</html>