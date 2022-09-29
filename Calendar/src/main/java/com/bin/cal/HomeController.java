package com.bin.cal;

import java.text.DateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.bin.cal.dto.CalDto;
import com.bin.cal.service.ICalService;
import com.bin.cal.utils.Util;


@Controller
public class HomeController {
	
	private static final Logger logger = LoggerFactory.getLogger(HomeController.class);
	
	@Autowired
	private ICalService calService;
	

	@RequestMapping(value = "/home.do", method = RequestMethod.GET)
	public String home(Locale locale, Model model) {
		logger.info("Welcome home! The client locale is {}.", locale);
		
		Date date = new Date();
		DateFormat dateFormat = DateFormat.getDateTimeInstance(DateFormat.LONG, DateFormat.LONG, locale);
		
		String formattedDate = dateFormat.format(date);
		
		model.addAttribute("serverTime", formattedDate );
		
		return "index";
	}
	
	@RequestMapping(value = "/calendar.do", method = RequestMethod.GET)
	public String calendar(Locale locale, Model model,String year,String month) {
		logger.info("달력보기 {}.", locale);

		String id ="hk";//회원관리 기능을 추가하면 세션에서 로그인 된 아이디를 구해서 저장
		
		//calendar를 요철할때 년,월의 값을 전달하지 않으면 현재 달을 보여준다.
		if(year==null||month==null) {
			Calendar cal = Calendar.getInstance();
			year = cal.get(Calendar.YEAR)+"";
			month=cal.get(Calendar.MONTH)+1+"";
		}else {
			//크기를 비교하기 위해 전수형으로 변환 : month>12 , month<1
			int yearInt = Integer.parseInt(year);
			int monthInt = Integer.parseInt(month);
			
			//월이 증가하다가 12보다 커지면 13,14,.. 현상을 처리
			if(monthInt>12){
				monthInt=1;//1월로 변경
				yearInt++;//년도는 다음해로 넘어가니깐 년도 +1 증가시키기 
			}
			
			if(monthInt<1){
				monthInt=12;
				yearInt--;
			}
			
			year=yearInt+""; //"" 컨켄트네이션 문자열을 만나면 모두 문자열이 됨 3+2+""-> 5가 되고 ,""+3+2-> 32가 된다
			month=String.valueOf(monthInt);
		}
		
		//월별 일정에 대해 하루마다 일정 3개씩 표시하기 기능 구현
		String yyyyMM = year+Util.isTwo(month);
		List<CalDto> clist = calService.calViewList(id, yyyyMM);
		model.addAttribute("clist",clist);
		
		return "calendar";
	}
	
	
	//@RequestParam Map<String, String> ymd-> String year,String month,String date 포함
	@RequestMapping(value = "/calBoardList.do", method = RequestMethod.GET)
	public String calBoardList(HttpServletRequest request, Locale locale, Model model,@RequestParam Map<String, String> ymd) {
		
		logger.info("일정목록보기 {}.", locale);
		
		
		//session에 정보를 담는다. 이렇게 하면 바로 session. 으로 해서 꺼내 쓸 수 있다.
		HttpSession session = request.getSession();
		if(ymd==null||ymd.get("year")==null) { //일정목록을 페이지로 들어온 상태이기 때문에 ymd 필요없음 session에 정보가 있으니 
			ymd=(Map<String, String>)session.getAttribute("ymd");
		}else {
			session.setAttribute("ymd", ymd); //새로운 일자로 들어가면 또 다시 새로은 session이 필요하기 때문에 다시 session 저장
		}
		
		
		//Utils 사용전 방법
//		String yyyyMMdd = ymd.get("year")
//				+(ymd.get("month").length()<2?"0"+ymd.get("month"):ymd.get("month"))
//				+(ymd.get("date").length()<2?"0"+ymd.get("date"):ymd.get("date"));
		//년월일을 8자리로 만들기 위해 1자리 값은 2자리로 만들어서 8자리로 만든다.
		String yyyyMMdd = ymd.get("year")
						+(Util.isTwo(ymd.get("month")))
						+(Util.isTwo(ymd.get("date")));
		
		//아이디는 로그인한 아이디를 전달한다.
//		HttpSession session = request.getSession();
//		String id=(String)session.getAttribute("id");
		String id="hk";
//		
		List<CalDto> list = calService.calBoardList(id, yyyyMMdd);
		model.addAttribute("list",list);
		
		return "calBoardList";
	}
	
	
	
	@RequestMapping(value = "/insertCalForm.do", method = RequestMethod.GET)
	public String insertCalForm(Locale locale, Model model) {
		logger.info("일정추가폼이동 {}.", locale);
		
		
		
		return "insertCalForm";
	}
	
	@RequestMapping(value = "/insertCalBoard.do", method = RequestMethod.POST)
	public String insertCalBoard(CalDto dto, Locale locale, Model model) {
		logger.info("일정추가하기 {}.", locale);
		
		//mdate는 12자리로 만들어서 DB에 저장해야 함
		String mdate = dto.getYear()
					+Util.isTwo(dto.getMonth())
					+Util.isTwo(dto.getDate())
					+Util.isTwo(dto.getHour())
					+Util.isTwo(dto.getMin());
		
		boolean isS = calService.insertCal(
					new CalDto(22, dto.getId(), dto.getTitle(), dto.getContent(), mdate, null));
		
		if(isS) {
			return "redirect:calendar.do?year="+dto.getYear()+"&month="+dto.getMonth();
		}else {
			model.addAttribute("msg","일정등록실패!!");
			return "error";
		}
	}
	
	@RequestMapping(value = "/calMuldel.do", method = {RequestMethod.GET,RequestMethod.POST})
	public String calMuldel(String[] seq,CalDto dto, Locale locale, Model model) {
		logger.info("일정삭제하기{}.", locale);
		
//		String source="year="+dto.getYear()+"&month="+dto.getMonth()
//						+"&date="+dto.getDate();
		
		boolean isS = calService.calMuldel(seq);
		if(isS) {
//			return "redirect:calBoardList.do?"+source;
			return "redirect:calBoardList.do";
		}else {
			model.addAttribute("msg","일정삭제실패");
		}
		
		return "error";
	}
	
	
	@RequestMapping(value = "/calDetail.do", method = RequestMethod.GET)
	public String calDetail(int seq ,Locale locale, Model model) {
		logger.info("일정상세내용보기 {}.", locale);
		CalDto dto = calService.calDetatil(seq);
		model.addAttribute("dto",dto);
		return "calDetail";
	}
	
	@RequestMapping(value = "/calUpdateForm.do", method = RequestMethod.GET)
	public String insertCalForm(int seq ,Locale locale, Model model) {
		logger.info("일정수정폼이동 {}.", locale);
		CalDto dto = calService.calDetatil(seq);
		model.addAttribute("dto",dto);
		return "calUpdateForm";
	}
	
	@RequestMapping(value = "/calUpdate.do", method = RequestMethod.POST)
	public String calUpdate(CalDto dto, Locale locale, Model model) {
		logger.info("일정수정하기 {}.", locale);
		
		String mdate = dto.getYear()
				+Util.isTwo(dto.getMonth())
				+Util.isTwo(dto.getDate())
				+Util.isTwo(dto.getHour())
				+Util.isTwo(dto.getMin());
		
		boolean isS = calService.calUpdate(
				new CalDto(dto.getSeq(),null,dto.getTitle(),dto.getContent(),mdate,null));
				if(isS) {
//					return "calDetail";(x)
					return "redirect:calDetail.do?seq="+dto.getSeq();
				}else {
					model.addAttribute("msg","일정수정실패");
					return "error";
				}
		}
	
	//text 타입의 결과 값을 클라이언트로 응답할 때
	//PrintWriter 객체 와 같은 것이라고 생각하면 된다   //json은 jacascript객체이가 //페이지가 넘어가는 것이 아니라 val로 넘어가게 해준다 
	@ResponseBody 
	@RequestMapping(value = "/calCountAjax.do", method = RequestMethod.POST)
	public String calCountAjax(@RequestParam("yyyyMMdd") String ymd, Locale locale, Model model) {//String yyyyMMdd이렇게 똑같이 사용해도 가능 
		logger.info("일정개수보여주기 {}.", locale);
		String id="hk";
		int count=calService.calCount(id, ymd);
		
		return count+"";
	}
	
	

	
}
