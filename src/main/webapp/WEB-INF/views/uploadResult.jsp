<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
	<p>writer : ${writer }</p>
	<p>filePath : ${fileName }</p>
	<img src="displayFile?filename=${fileName }">
</body>
</html>