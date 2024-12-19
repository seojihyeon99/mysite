package mysite.controller;

import java.util.Map;

import jakarta.servlet.annotation.WebServlet;
import mysite.controller.action.guestbook.AddAction;
import mysite.controller.action.guestbook.DeleteAction;
import mysite.controller.action.guestbook.DeleteFormAction;
import mysite.controller.action.guestbook.ListAction;

@WebServlet("/guestbook")
public class GuestbookServlet extends ActionServlet {
	private static final long serialVersionUID = 1L;
	
	private Map<String, Action> mapAction = Map.of(
		"add", new AddAction(),
		"deleteform", new DeleteFormAction(),
		"delete", new DeleteAction()
	);
	
	@Override
	protected Action getAction(String actionName) {
		return mapAction.getOrDefault(actionName, new ListAction());
	}
}

