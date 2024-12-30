package mysite.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import mysite.service.GuestbookService;
import mysite.vo.GuestbookVo;

@Controller
@RequestMapping("/guestbook")
public class GuestbookController {
	private GuestbookService guestbookService;
	
	public GuestbookController(GuestbookService guestbookService) {
		this.guestbookService = guestbookService;
	}
	
	@RequestMapping("")
	public String index(Model model) {
		model.addAttribute("list", guestbookService.getContentsList());
		return "guestbook/index";
	}
	
	@RequestMapping("/add")
	public String add(GuestbookVo guestbookVo) {
		guestbookService.addContents(guestbookVo);
		return "redirect:/guestbook";
	}
	
	@RequestMapping(value="/delete/{id}", method=RequestMethod.GET)
	public String delete(@PathVariable("id") Long id) {
		return "/guestbook/delete";
	}
	
	@RequestMapping(value="/delete/{id}", method=RequestMethod.POST)
	public String delete(
		@PathVariable("id") Long id,
		@RequestParam(value="password", required=true, defaultValue="") String password) {
		guestbookService.deleteContents(id, password);
		return "redirect:/guestbook";
	}
}