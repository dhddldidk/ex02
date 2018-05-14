<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<style type="text/css">

</style>
<link rel="stylesheet"
	href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css">
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>
<script
	src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>
</head>
<body>
	<h1>Ajax Test Page</h1>
	<div>
		<div>
			bno <input type="text" name="bno" id="bno">
		</div>
		<div>
			replyer <input type="text" name="replyer" id="replyer">
		</div>
		<div>
			replytext <input type="text" name="replytext" id="replytext">
		</div>
		<button id="addReplyBtn">add Reply</button>
		<button id="getListBtn">get List All</button>
	</div>
	
	<hr>
	<ul id="replies">
	
	</ul>
	
	<!-- Modal -->
  <div class="modal fade" id="myModal" role="dialog">
    <div class="modal-dialog">
    
      <!-- Modal content-->
      <div class="modal-content">
        <div class="modal-header">
        	<!-- data-dismiss="modal" 닫게 해줌 -->
          <button type="button" class="close" data-dismiss="modal">&times;</button>
          <h4 class="modal-title">수정하기</h4>
        </div>
        <div class="modal-body">
          
			<div class="form-group">
				<label for="rno">번호</label>
				<input type="text" class="form-control" id="rno" readonly="readonly">
			</div>
			<div class="form-group">
				<label for="content">덧글내용</label>
				<input type="text" class="form-control" id="content">
			</div>
			<div class="form-group">
				<button type="submit" class="btn btn-primary updateComplete" data-dismiss="modal">수정하기</button>
			</div>
		
        </div>
        <div class="modal-footer">
          <button type="button" class="btn btn-primary" data-dismiss="modal">닫기</button>
        </div>
      </div>
      
    </div>
  </div>
	<script type="text/javascript">
	
		//추가
		$("#addReplyBtn").click(function(){
			var bnoVal=$("#bno").val();
			var replyerVal = $("#replyer").val();
			var replytextVal = $("#replytext").val();
			var sendData = {bno:bnoVal, replyer:replyerVal, replytext:replytextVal};//키 : 값
			
			//Spring Controller에 
			//@requestBody를 쓸 경우 jsp 파일에 JSON.stringify, headers에 Content-type을 써줘야 함
			$.ajax({
				type:"post",
				url:"replies", //방법2 : "/ex02/replies" == 방법3 : "${pageContext.request.contextPath}/replies"
				data:JSON.stringify(sendData), //(보내는 타입) JSON string 으로 바꿔서 보냄
				dataType:"text",//xml, text, json형태가 들어갈 수 있음(받는 타입)
				headers:{"Content-Type":"application/json"},
				success:function(result){
					console.log(result);
				}
			})
		})
		
		//리스트
		$("#getListBtn").click(function(){
			var bnoVal = $("#bno").val();
			
			$.ajax({
				type:"get",
				url:"${pageContext.request.contextPath}/replies/all/"+bnoVal,
				dataType:"json",
				success:function(result){
					console.log(result);

					//empty()를 안쓰면 getListAll버튼을 클릭할때마다 계속 추가되기 때문에
					$("#replies").empty();
					
					//console에 배열로 들어온 경우 each를 쓴다
					$(result).each(function(i,obj){
						var liObj = $("<li>");
						var btnObj1 = $('<button type="button" class="btn btn-danger update" data-target="#myModal" data-toggle="modal">').html("수정");
						var btnObj2 = $("<button class='delete'>").html("삭제");
						var span = $("<span class='rno'>").append(obj.rno);
						var spanContext = $("<span class='context'>").append(obj.replytext);
						
						liObj.append(span);
						liObj.append(", "+obj.replyer+", ");
						liObj.append(spanContext);
						liObj.append(btnObj1).append(btnObj2);
						$("#replies").append(liObj);
					})
				}
			})
			
		})
		
		//삭제
		$(document).on("click", ".delete", function(){
			var rnoVal = $(this).parent().find(".rno").html();
			$.ajax({
				type:"delete",
				url:"${pageContext.request.contextPath}/replies/"+rnoVal,
				dataType:"json",
				success:function(result){
					console.log(result);
				}
			})
			$(this).parent().remove();
		})
		
		//수정버튼 누르면 모달창 띄우기
		$(document).on("click", ".update", function(){
			var rnoVal = $(this).parent().find(".rno").html();
			var contentVal = $(this).parent().find(".context").html();
			var bnoVal=$("#bno").val();
			
			
			$("#rno").val(rnoVal);
			$("#content").val(contentVal);
			
			
		})
		
		//덧글 내용수정 후 수정하기 버튼을 눌렀을 때
		$(document).on("click",".updateComplete", function(){
			var rnoVal=$("#rno").val();
			var replytextVal = $("#content").val();
			var sendData = {replytext:replytextVal};//키 : 값
			
			$.ajax({
			type:"put",
			url:"${pageContext.request.contextPath}/replies/"+rnoVal,
			data:JSON.stringify(sendData), //(보내는 타입) JSON string 으로 바꿔서 보냄
			dataType:"text",//xml, text, json형태가 들어갈 수 있음(받는 타입)
			headers:{"Content-Type":"application/json"},
			success:function(result){
				console.log(result);
				if(result == "success"){
					alert("수정되었습니다.");
				}
				$("#getListBtn").trigger("click");
			}
		})
		})
		
	</script>
</body>
</html>