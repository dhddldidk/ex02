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
	<p>file : ${file }</p>
	
	<!-- 외부 경로에 저장된 파일은 이미지 정보를 달라고 한 방법이 
	/displayFile?filename 
	파일 주소를 받아서 고객이 요청한데 보여줌-->
	<img src="displayFile?filename=${file }">
</body>
</html>