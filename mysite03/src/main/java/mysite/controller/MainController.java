package mysite.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import mysite.service.SiteService;
import mysite.vo.UserVo;

@Controller
public class MainController {
	private final SiteService siteService;
	
	public MainController(SiteService siteService) {
		this.siteService = siteService;
	}

	@RequestMapping({"/", "/main"})
	public String main(Model model) {
		model.addAttribute("siteVo", siteService.getSite());
		return "main/index";
	}
	
	@ResponseBody
	@RequestMapping("/msg01")
	public String message01() {
		return "Hello World";
	}
	
	@ResponseBody
	@RequestMapping("/msg02")
	public String message02() {
		return "안녕 세상";
	}

	@ResponseBody
	@RequestMapping("/msg03")
	public Object message03() {
		UserVo vo = new UserVo();
		vo.setId(10L);
		vo.setName("둘리");
		vo.setEmail("dooly@gmail.com");
		
		return vo;
	}
	
}