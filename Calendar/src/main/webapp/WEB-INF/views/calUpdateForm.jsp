<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
<h1>일정수정하기</h1>
<form action="calUpdate.do" method="post">
<input type="hidden" name="seq" value="${dto.seq}"/>
<table border="1">
	<tr>
		<th>ID</th>
		<td>${requestScope.dto.id}</td>
	</tr>
	<tr>
		<th>일정</th>
		<td>
				<select name="year">
					<c:set var="year" value="${fn:substring(dto.mdate,0,4)}"/>
					<c:forEach var="i" begin="${year-5}" end="${year+5}" step="1">
							<option ${year==i?"selected":""} value="${i}">${i}</option>				
					</c:forEach>
				</select>년
				<select name="month">
					<c:set var="month" value="${fn:substring(dto.mdate,4,6)}"/>
					<c:forEach var="i" begin="1" end="12" step="1">
							<option ${month==i?"selected":""} value="${i}">${i}</option>				
					</c:forEach>
				</select>월
				<select name="date">
					<c:set var="date" value="${fn:substring(dto.mdate,6,8)}"/>
					<c:forEach var="i" begin="1" end="31" step="1">
							<option ${date==i?"selected":""} value="${i}">${i}</option>				
					</c:forEach>
				</select>일
				<select name="hour">
					<c:set var="hour" value="${fn:substring(dto.mdate,8,10)}"/>
					<c:forEach var="i" begin="0" end="23" step="1">
							<option ${hour==i?"selected":""} value="${i}">${i}</option>				
					</c:forEach>
				</select>시
				<select name="min">
					<c:set var="min" value="${fn:substring(dto.mdate,10,12)}"/>
					<c:forEach var="i" begin="0" end="59" step="1">
							<option ${min==i?"selected":""} value="${i}">${i}</option>				
					</c:forEach>
				</select>분
		</td>
	</tr>
	<tr>
		<th>제목</th>
		<td><input type="text" name="title" value="${dto.title}"/></td>
	</tr>
	<tr>
		<th>내용</th>
		<td><textarea rows="10" cols="60" name="content">${dto.content}</textarea></td>
	</tr>
	<tr>
		<td colspan="2">
			<input type="submit" value="수정완료"/>
			<input type="button" value="상세보기" onclick="location.href='calDetail.do?seq=${dto.seq}'"/>
			<input type="button" value="목록" onclick="location.href='calBoardList.do'"/>
			<input type="button" value="달력" onclick="location.href='calendar.do?year=${sessionScope.ymd.year}&month=${sessionScope.ymd.month}'"/>
		</td>
	</tr>
</table>
</form>
</body>
</html>