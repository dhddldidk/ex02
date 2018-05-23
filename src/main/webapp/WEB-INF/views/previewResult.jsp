<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<style type="text/css">
	.wrap{
		position: relative;
		width:100px;
		height: 100px;
		border:1px solid gray;
		text-align: center;
	}
	.wrap button{
		position: absolute;
		bottom:0px;
		right: 0px;
	}
</style>
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>
</head>
<body>
	<p>writer : ${writer }</p>
	<p>file : ${file }</p>
	
	<!-- 외부 경로에 저장된 파일은 이미지 정보를 달라고 한 방법이 
	/displayFile?filename 
	파일 주소를 받아서 고객이 요청한데 보여줌-->
	<div class="wrap">
		<img src="displayFile?filename=${file }">
		<button data-file="${file }">X</button>
	</div>
	
	<!-- 위에 X를 클릭하면 파일 삭제 -->
	<form action="deleteFile" method="get" id="f1">
		<input type="text" name="fileName" id="delFile">	
	</form>
	
	
	<script type="text/javascript">
		$(".wrap button").click(function(){
			//버튼을 클릭을 했을 때 버튼이 파일 경로의 값을 들고 있을 수 있도록
			//버튼에 속성을 줄 수 있음 ex)<button data-file="${file }">X</button>
			
			var path = $(this).attr("data-file");
			$("#delFile").val(path);
			
			$("#f1").submit();
		})
	</script>
</body>
</html>