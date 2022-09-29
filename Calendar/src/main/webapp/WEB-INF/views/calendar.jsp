<%@page import="java.util.List"%>
<%@page import="com.bin.cal.dto.CalDto"%>
<%@page import="com.bin.cal.utils.Util"%>
<%@page import="java.util.Calendar"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>일정보기</title>
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.6.0/jquery.min.js"></script>
<style type="text/css">
	#calendar{
		border-collapse: collapse; /*표의 테두리를 살선으로 표현한다*/
		border: 1px solid gray;
	}
	#calendar td{
		width: 80px;
		height: 80px;
		text-align: left;
		vertical-align: top;
	}
	a{
		text-decoration: none;
	}
	img{
		width: 17px;
		height: 17px;
	}
	
	#container{
		width: 600px;
		margin: 0 auto; /* 	0은 위아래 auto는 왼쪽오른쪽 */
	}

	#container td > p{
		margin-bottom:1px;
		font-size: 5px;
		background-color: orange;
		color: white;
	}
	
	
	td:hover{
	background-color: yellow;
	}
	
	.cPreview{
/* 		absolute는 주변화면이 영향받지않는다  */
		position :absolute; 
		left:-40px;
		top:-30px;
		background-color: pink;
		width: 40px;
		height: 40px;
		text-align: center;
		line-height: 40px;
		font-weight: bold;
		border-radius: 20px 20px 1px 20px;
	}
	
	td{
		position: relative;
	}
	
</style>
<script type="text/javascript">

	//값이 1자리이면 두자리로 만들어주는 기능
	function isTwo(str){
			return str.length<2?"0"+str:str;
		}


	//$(function(){}) --> window.onload = function(){}
	$(function() {
		$(".countView").hover(function(){
			var aObj=$(this);
			var year =$(".y").text().trim();//년
			var month=$(".m").text().trim();//월
			var date=$(this).text().trim(); //this는 hover가 선택 된 일자를 불러오는 것 
			var yyyyMMdd=year+isTwo(month)+isTwo(date);
// 			alert(yyyyMMdd);
// 			console.log(yyyyMMdd);
			
			//ajax스는 기본이 비동기식이로 진행 (실행순서가 순서대로가 아닐 수 있음)
			$.ajax({ 
				method:"post", //전송방식
				url:"calCountAjax.do", //요청URL
				data:{"yyyyMMdd":yyyyMMdd}, //전송 파라미터
				dataType:"text", //서버로 부터 받는 값의 타입
				async:false,  //메소드가 비동기식 실행이므로 false로 설정함 
				success:function(val){
// 					alert("서버통신성공!"+val);
					aObj.after("<div class='cPreview'>"+val+"</div>");
				},
				error: function(){
					alert("서버통신실패!");
				}
			});
		},function(){
			$(".cPreview").remove();//마우스가 나가면 해당 엘리먼트를 삭제한다. (jQery 메소드)
		});
	});

</script>
</head>
<%
	//달력의 날짜를 바꾸기 위해 전달된 year와 month 파라미터를 받는다. 
	String paramYear = request.getParameter("year");
	String paramMonth = request.getParameter("month");

	//java에서 제공하는 API: Calendar 객체를 사용
	//추상클래스이기 떄문에 new를 쓸 수 없다
	Calendar cal = Calendar.getInstance();// new (X)
	int year = cal.get(Calendar.YEAR); //현재 년도를 구함
	int month = cal.get(Calendar.MONTH)+1;// 현재 월을 구함
	
	if(paramYear!=null){
		year=Integer.parseInt(paramYear);
	}
	
	if(paramMonth!=null){
		month=Integer.parseInt(paramMonth);
	} //이렇게만 해주면 월이 13,14,15로 넘어가고 -2-3-4로 넘어가기 때문에 (문제2)
	
	//조건으로 잡아준다
	//월이 증가하다가 12보다 커지면 13,14,.. 현상을 처리
	if(month>12){
		month=1;//1월로 변경
		year++;//년도는 다음해로 넘어가니깐 년도 +1 증가시키기 
	}
	
	if(month<1){
		month=12;
		year--;
	}
	
	
	//현재 월의 1일에 대한 요일 구하기 : 1~7 --> 1(일요일),2(월요일),3(화요일),.......7(토요일)
	//set(y, month-1, 1)--> month-1 : calendar 기준(0~11), 우리기준(1~12)
	cal.set(year, month-1,1);//원하는 날짜로 넣어준다 
	int dayOfWeek = cal.get(Calendar.DAY_OF_WEEK);
	
	//현재 월의 마지막 날 구하기 
	int lastDay = cal.getActualMaximum(Calendar.DAY_OF_MONTH);
	
	//해당 달의 일정 받기
	List<CalDto> clist=(List<CalDto>)request.getAttribute("clist");
%>
<body>
<div id="container">
<h1>일정달력보기</h1>
<table border="1" id="calendar">
	<caption>
		<a href="calendar.do?year=<%=year-1%>&month=<%=month%>">◁</a>
		<a href="calendar.do?year=<%=year%>&month=<%=month-1%>">◀</a>
		<b><span class="y"><%=year%></span>년<span class="m"><%=month%></span>월</b>
		<a href="calendar.do?year=<%=year%>&month=<%=month+1%>">▶</a>
		<a href="calendar.do?year=<%=year+1%>&month=<%=month%>">▷</a>
		
	</caption>
	<tr>
		
		<th>일</th>
		<th>월</th>
		<th>화</th>
		<th>수</th>
		<th>목</th>
		<th>금</th>
		<th>토</th>
	</tr>
	<tr>
		<%	//달력에 시작하는 공백을 출력한다.
			//공백출력하는 for문
			for(int i=0; i<dayOfWeek-1; i++){
				out.print("<td>&nbsp;</td>");
			}
			//날짜 출력하는 for문
			for(int i=1; i<=lastDay;i++){
				%>
				<td>
					<b><a class="countView" style="color: <%=Util.fontColor(dayOfWeek, i) %>;" href="calBoardList.do?year=<%=year%>&month=<%=month%>&date=<%=i%>"><%=i%></a></b>
					
					<a href="insertCalForm.do?year=<%=year%>&month=<%=month%>&date=<%=i%>">
						<img src="img/pen.png" alt="일정추가"/>
					</a>
					<%=getCalViewList(i, clist) %>
				</td>
				<%
				//행을 바꿔주기 ---> 현재일(i)이 토요일인지 확인 : (공백수+ 현재 날짜)한 값이 7로 나눠떨어지면 7배수
				if((dayOfWeek-1+i)%7==0){
					out.print("</tr><tr>");
					
				}
			}
			
			//나머지 공백 출력하는 for문(문제해결 1)
			int countNbsp = (7-(dayOfWeek-1+lastDay)%7)%7;
			for(int i=0; i<countNbsp;i++){
				out.print("<td>&nbsp;</td>");
			}
		%>
	</tr>
</table>
</div>
<%!
	public String getCalViewList(int i,List<CalDto> clist){
		String d = Util.isTwo(i+""); //mdate --> "05"
		String cList = ""; //달력에 출력해줄 일정 제목을 저장할 변수
		for(CalDto calDto:clist){
			if(calDto.getMdate().substring(6, 8).equals(d)){
				cList+="<p>"
						+(calDto.getTitle().length()>7?calDto.getTitle().substring(0,7)+"..." //삼항연산자를 활용하여 글자가 넘어가게 되면 ...으로 출력 
								:calDto.getTitle())
						+"</p>";
			}
		}
	return cList;//결과 : "<p>title</p><p>title</p>"
}

// 	public String fontColor(int dayOfWeek, int i){
// 		String color="";
// 		if((dayOfWeek-1+i)%7==0){//토요일인 경우
// 			color="blue";
// 		}else if((dayOfWeek-1+i)%7==1){	//일요일인 경우
// 			color="red";
// 		}else{
// 			color="black";
// 		}
// 		return color;
// }
%>

</body>
</html>