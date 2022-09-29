<%@page import="java.sql.Timestamp"%>
<%@page import="java.text.SimpleDateFormat"%>
<%@page import="com.bin.cal.dto.CalDto"%>
<%@page import="java.util.List"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<!-- <script type="text/javascript" src="http://code.jquery.com/jquery-lotest.js"></script> -->
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.6.0/jquery.min.js"></script>
<script type="text/javascript">
	//core, BOM, DOM 3가지 영역으로 나눔 
	function allSel(val) {
		//val --> input객체 --> Element 객체 안에 구현 여러 속성들이 있음 그중 tagName을 사용해봄 
// 		alert(val.tagName);
// 		alert(val);
		//getElementById(), getElementsByTagName(), ...className...등
		var chks = document.getElementsByName("seq");//[chk,chk,chk,...]
		for (var i = 0; i < chks.length; i++) {
			chks[i].checked = val;
		}
// 		val.parentNode.style.backgroundColor="red";
	}
	
	$(function() { //window.onload=function(){ }  //page가 로딩되는 작업을 이벤트로 인식
		//form태그에서 submit이벤트가 발생하면 함수 실행
		$("form").submit(function() {
			var bool = true;
			var count = $(this).find("input[name=seq]:checked").length;
			if(count==0){
				alert("최소 하나 이상 체크해주세요 ");
				bool = false;
			}else if(confirm("정말 삭제 하시겠습니까?")){
				bool = true;
			}else{
				bool = false;
			}
			return bool;
		});
	
	
		//체크박스 처리 : 전체선택 체크박스 체크/해제를 자동으로 하는 기능 
		var chks = document.getElementsByName("seq");//[chk,chk,chk,...]
		for (var i = 0; i < chks.length; i++) {
			chks[i].onclick=function(){ //체크박스에서 클릭 이벤트가 발생하면 함수를 실행하라
				var checkedObjs= document.querySelectorAll("input[name=seq]:checked");
				if(checkedObjs.length==chks.length){
					document.getElementsByName("all")[0].checked=true;//체크해줌
				}else{
					document.getElementsByName("all")[0].checked=false;//체크해줌
				}
			}
		}
	})

</script>

</head>
<%
	List<CalDto> list = (List<CalDto>)request.getAttribute("list");
%>
<body>
<h1>일정목록보기</h1>
<form action="calMuldel.do" method="post">
<input type="hidden" name="year" value="${Param.year}">
<input type="hidden" name="month" value="${Param.month}">
<input type="hidden" name="date" value="${Param.date}">
<table border="1">
	<col width="50px">
	<col width="50px">
	<col width="300px">
	<col width="250px">
	<col width="250px">
	<tr>
		<th><input type="checkbox" name="all" onclick="allSel(this.checked)"/></th>
		<th>번호</th>
		<th>제목</th>
		<th>일정</th>
		<th>작성일</th>
	</tr>
	<%
		if(list==null||list.size()==0){
			out.print("<tr><td colspan='5'>-----작성 된 일정이 없습니다.-----</td></tr>");
		}else{
			for(CalDto dto : list){
				%>
				<tr>
					<td><input type="checkbox" name="seq" value="<%=dto.getSeq()%>"/></td>
					<td><%=dto.getSeq() %></td>
					<td><a href="calDetail.do?seq=<%=dto.getSeq()%>"><%=dto.getTitle() %></a></td>
					<td><%=toDates(dto.getMdate())%></td>
					<td><fmt:formatDate pattern="yyyy-MM-dd" value="<%=dto.getRegdate() %>"/></td>
				</tr>
				<%
			}
		}
	%>
	<tr>
		<td colspan="5">
			<input type="submit" value="삭제"/>
			<a href="calendar.do?year=${sessionScope.ymd.year}&month=${sessionScope.ymd.month}">달력보기</a>
		</td>
	</tr>
</table>
</form>
<%!
	public String toDates(String mdate){
	
		//문자열 ---> date타입으로 변환-->  문자열을 데이트패턴으로 수정 --> 데이트타입으로 변환
		//날짜형식 : yyyy-MM-dd hh:mm:ss
		
		String m = mdate.substring(0,4)+"-"
				+mdate.substring(4,6)+"-"
				+mdate.substring(6,8)+" "
				+mdate.substring(8,10)+":"
				+mdate.substring(10)+":00";
		
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy년MM월dd일 HH시mm분");
		Timestamp tm = Timestamp.valueOf(m); //문자열을 Date타입으로 변환
		
		return sdf.format(tm);
	}
%>
</body>
</html>