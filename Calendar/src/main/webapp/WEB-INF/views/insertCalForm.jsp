<%@page import="java.util.Calendar"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<%
	int year = Integer.parseInt(request.getParameter("year"));
	int month = Integer.parseInt(request.getParameter("month"));
	int date = Integer.parseInt(request.getParameter("date"));
	
	//현재시간 구하기
	Calendar cal = Calendar.getInstance();
	int hour = cal.get(Calendar.HOUR_OF_DAY);
	int min = cal.get(Calendar.MINUTE);
%>
<script type="text/javascript">

	function calendar() {
		location.href="calendar.do?year=<%=year%>&month=<%=month%>&date=<%=date%>";
	}
</script>

</head>

<body>
<h1>일정추가</h1>
<form action="insertCalBoard.do" method="post">
	<table border="1">
		<tr>
			<th>ID</th>
			<td><input type="text" name="id" value="hk" readonly="readonly"/></td>
		</tr>
		<tr>
			<th>일정</th>
			<td>
				<select name="year">
					<%
						 for(int i=year-5; i<year+5; i++){
							 %>
							 <option <%=year==i?"selected":"" %> value="<%=i%>"><%=i%></option>
							 <%
						 }
					%>
				</select>년
				<select name="month">
					<%
						 for(int i=1; i<=12; i++){
							 %>
							 <option <%=month==i?"selected":"" %> value="<%=i%>"><%=i%></option>
							 <%
						 }
					%>
				</select>월
				<select name="date">
					<%
						 for(int i=1; i<=31; i++){
							 %>
							 <option <%=date==i?"selected":"" %> value="<%=i%>"><%=i%></option>
							 <%
						 }
					%>
				</select>일
				<select name="hour">
					<%
						 for(int i=0; i<=24; i++){
							 %>
							 <option <%=hour==i?"selected":"" %> value="<%=i%>"><%=i%></option>
							 <%
						 }
					%>
				</select>시
				<select name="min">
					<%
						 for(int i=0; i<=60; i++){
							 %>
							 <option <%=min==i?"selected":"" %> value="<%=i%>"><%=i%></option>
							 <%
						 }
					%>
				</select>분
			</td>
		</tr>
		<tr>
			<th>제목</th>
			<td><input type="text" name="title" /></td>
		</tr>
		<tr>
			<th>내용</th>
			<td><textarea rows="10" cols="60" name="content"></textarea></td>
		</tr>
		<tr>
			<td colspan="2">
				<input type="submit" value="일정작성"/>
				<input type="button" value="돌아가기" onclick="calendar()"/>
			</td>
		</tr>
	</table>
</form>
</body>
</html>