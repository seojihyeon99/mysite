package mysite.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import mysite.security.Auth;
import mysite.service.FileUploadService;
import mysite.service.SiteService;
import mysite.vo.SiteVo;

@Auth(role="ADMIN")
@Controller
@RequestMapping("/admin")
public class AdminController {
	private final FileUploadService fileUploadService;
	private final SiteService siteService;
	
	public AdminController(FileUploadService fileUploadService, SiteService siteService) {
		this.fileUploadService = fileUploadService;
		this.siteService = siteService;
	}
	
	@RequestMapping({"", "/main"})
	public String main(Model model) {
		model.addAttribute("siteVo", siteService.getSite());
		return "admin/main";
	}
	
	@RequestMapping("/main/update")
	public String update(SiteVo siteVo, MultipartFile multipartFile) {
		String profile = fileUploadService.restore(multipartFile);
		if(profile != null) {
			siteVo.setProfile(profile);
		}
		
		siteService.updateSite(siteVo);
		return "redirect:/admin";
	}

	@RequestMapping("/guestbook")
	public String guestbook() {
		return "admin/guestbook";
	}

	@RequestMapping("/board")
	public String board() {
		return "admin/board";
	}


	@RequestMapping("/user")
	public String user() {
		return "admin/user";
	}
}