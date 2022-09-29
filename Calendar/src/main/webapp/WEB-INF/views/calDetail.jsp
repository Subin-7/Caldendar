<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
<jsp:useBean id="util" class="com.bin.cal.utils.Util"/>
<h1>일정상세보기</h1>
<table border="1">
	<tr>
		<th>ID</th>
<!-- 		requestScope를 사용하면 어디서 꺼내오는 객체인지 확인하기 좋아 더 정확함 request에서 꺼내오는 것  -->
		<td>${requestScope.dto.id}</td>
	</tr>
	<tr>
		<th>일정</th>
		<td>
			<jsp:setProperty value="${dto.mdate}" property="toDates" name="util"/>
			<jsp:getProperty property="toDates" name="util"/>
		</td>
	</tr>
	<tr>
		<th>제목</th>
		<td><input type="text" name="title" value="${dto.content}"/></td>
	</tr>
	<tr>
		<th>내용</th>
		<td><textarea rows="10" cols="60" readonly="readonly">${dto.content}</textarea></td>
	</tr>
	<tr>
		<td colspan="2">
			<input type="button" value="수정" onclick="location.href='calUpdateForm.do?seq=${dto.seq}'"/>
			<input type="button" value="삭제" onclick="location.href='calMuldel.do?seq=${dto.seq}'"/>
			<input type="button" value="목록" onclick="location.href='calBoardList.do'"/>
			<input type="button" value="달력" onclick="location.href='calendar.do?year=${sessionScope.ymd.year}&month=${sessionScope.ymd.month}'"/>
		</td>
	</tr>
</table>
</body>
</html>