package mysite.controller;

import java.util.Map;

import jakarta.servlet.annotation.WebServlet;
import mysite.controller.action.board.DeleteAction;
import mysite.controller.action.board.ListAction;
import mysite.controller.action.board.ModifyAction;
import mysite.controller.action.board.ModifyFormAction;
import mysite.controller.action.board.ViewAction;
import mysite.controller.action.board.WriteAction;
import mysite.controller.action.board.WriteFormAction;


@WebServlet("/board")
public class BoardServlet extends ActionServlet {

	private Map<String, Action> mapAction = Map.of(
		"writeform", new WriteFormAction(),
		"write", new WriteAction(),
		"view", new ViewAction(),
		"modifyform", new ModifyFormAction(),
		"modify", new ModifyAction(),
		"delete", new DeleteAction()
	);
	
	@Override
	protected Action getAction(String actionName) {
		return mapAction.getOrDefault(actionName, new ListAction());
	}	
}
