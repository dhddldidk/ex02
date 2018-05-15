<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<style type="text/css">
	.pagination{
		width:100%;
	}
	.pagination li{
		list-style: none;
		float:left;
		padding:3px;
		border:1px solid orange;
		margin:3px;
	}
	.pagination li a{
		margin:3px;
		text-decoration: none;	
	}
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
		<button id="getListPageBtn">get List Page</button>
	</div>
	
	<hr>
	
	<!-- 게시글 리스트가 나오는 부분 -->
	<ul id="replies"></ul>
	
	<!-- 덧글 리스트가 나오는 부분 -->
	<ul class="pagination"></ul>
	
	
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
	var pageNumber=1; //화면에 보이는 페이지 번호
	
	
		$("#getListPageBtn").click(function(){
			//1번째 페이지가 나오도록
			var bnoVal = $("#bno").val();
			
			$.ajax({
				url:"replies/"+bnoVal+"/"+pageNumber,
				type:"get",
				dataType:"json",
				success:function(result){
					console.log(result);
					
					// 덧글 list
					displayList(result.list);
					
					
					// 덧글 pagination
					displayPaging(result);
				}
			
			})
		})
		
		function displayList(list){
			$("#replies").empty();
			
			
				
				for(var i =0; i<list.length; i++){
					var liObj = $("<li>");
					var btnObj1 = $('<button type="button" class="btn btn-danger update" data-target="#myModal" data-toggle="modal">').html("수정");
					var btnObj2 = $("<button class='delete'>").html("삭제");
					var span = $("<span class='rno'>").append(list[i].rno);
					var spanContext = $("<span class='context'>").append(list[i].replytext);
					
					liObj.append(span);
					liObj.append(", "+list[i].replyer+", ");
					liObj.append(spanContext);
					liObj.append(btnObj1).append(btnObj2);
					$("#replies").append(liObj);
				}
				
			
		}
		
		
		function displayPaging(result){
			//pagination
			var str = "";
			if(result.pageMaker.prev){
				str += "<li><a href='#'> << </a></li>";
			}
			
			for(var i = result.pageMaker.startPage; i<=result.pageMaker.endPage; i++){
				str += "<li><a href='#'> "+i+" </a></li>";
			}
			
			if(result.pageMaker.next){
				str += "<li><a href='#'> >> </a></li>";
			}
			$(".pagination").html(str);
		}
		
		
		//덧글 페이징에 a태그를 눌렀을 때
		$(document).on("click", ".pagination a", function(e){
			e.preventDefault();//a태그 링크 막기
			
			//해당 페이지 정보 얻기
			pageNumber = $(this).text();//해당 a태그의 값이 들어가면 됨
			
			//getListPage(ajax를 실행시켜야 함 ) - > 버튼이 클릭되도록 함getListPage
			$("#getListPageBtn").trigger("click"); // = $("#getListPage").click();
		})
		
		
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