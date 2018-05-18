<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<style type="text/css">
	#previewBox{
		width:400px;
		height: 300px;
		border:3px dotted orange;
	}
	#previewBox img{
		width:100px;
	}
</style>
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>
</head>
<body>
	<form action="previewUpload" method="post" enctype="multipart/form-data">
		<input type="text" name="writer" placeholder="작성자 이름">
		<input type="file" name="file" id="file">
		<input type="submit">
	</form>
	<div id="previewBox">
	</div>
	
	
	<!-- 파일 선택할 때 프리뷰 하는 방법 -->
	<script type="text/javascript">
		$("#file").change(function(){//누군가 이미지를 다른걸 선택했다는 말
			$("#previewBox").empty();
		
			var reader = new FileReader();//e.target.result=reader.result
			reader.onload = function(e){
				var imgObj = $("<img>").attr("src", e.target.result);
				$("#previewBox").html(imgObj);
			}
			reader.readAsDataURL($(this)[0].files[0]);
			//$(this) input 파일 객체 제이쿼리 객체
			//$(this)[0] 자바스크립트 객체로 반환해줌 == var file = document.getElementById("file");
			//$(this)[0].files[0] input에서 multiple="multiple"할경우를 대비해서 
			//지금은 하나만 선택해서 [0]번째
			//만약 여러개이면 files[i] for문 돌려서 가져오면 됨
		})
	</script>
</body>
</html>