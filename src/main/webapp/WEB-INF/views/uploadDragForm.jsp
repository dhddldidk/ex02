<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<style>
	#dropBox{
		width:400px;
		height: 300px;
		border:3px dotted orange;
	}
	#dropBox img{
		width:100px;
	}
</style>
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>
</head>
<body>
	<!-- 드래그 한 데이터를 인풋 테그에 바로 넣을 수 없음
	그래서 ajax로 넣어줘야함 (submit이 안됨) -->
	<form id="f1" action="dragUpload" method="post" enctype="multipart/form-data">
		<input type="text" name="writer" placeholder="작성자 이름">
		<input type="submit">
	</form>
	
	<div id="dropBox">
	</div>
	
	<!-- 드래그 되도록 처리 -->
	<script type="text/javascript">
	
	var formData = new FormData();
	//ajax로 파일 데이터를 보낼 때 주로 사용함. Form객체로 보내줌, form안의 data를 보내주는 형식
	
	
	//
	$("#dropBox").on("dragenter dragover", function (e){
		e.preventDefault();
	})
	//event안에 드래그 앤 드랍된 패일 정보가 들어있음
		$("#dropBox").on("drop", function(event){
		event.preventDefault();
		var files = event.originalEvent.dataTransfer.files;
		var file = files[0];
		console.log(file);
		
		var reader = new FileReader();//이미지 읽을 객체 생성
		reader.addEventListener("load", function(){//로드가 완료되야지 이벤트 발생
			var imgObj = $("<img>").attr("src", reader.result);
			$("#dropBox").append(imgObj);
		})
		if(file){//파일이 있을때만 로드래
			reader.readAsDataURL(file);//로드실행			
		}
		
		formData.append("files", file);//여러개 추가를 위해 key이름은 files로 함
		//키가 있기 때문에 여러개 들어갈 수 있음
	})
	
	
	//제출버튼을 누르면 ajax가 실행되도록 함
	$("#f1").submit(function(e){
		//submit이 되면 안되고 ajax하기 위해 링크 막음
		e.preventDefault();
		
		var writer = $("input[name='writer']").val();
		formData.append("writer", writer);
		
		$.ajax({
			url:"dragUpload",
			type:"post",
			data:formData,
			processData:false,//processData,contentType : file을 ajax로 업로드 할 때 처리필요
			contentType:false,
			success:function(data){
				console.log(data);
				
				//박스안에 사진 지우고 받은 파일넣기
				//map.put("result", "success");
				//map.put("listFile", list);
				if(data.result=="success"){
					$("#dropBox").empty();
					$(data.listFile).each(function(i, obj){
						var imgObj = $("<img>").attr("src", "displayFile?filename="+obj);
						$("#dropBox").append(imgObj);
					}) 
				}
			}
		})
	});
	</script>
</body>
</html>