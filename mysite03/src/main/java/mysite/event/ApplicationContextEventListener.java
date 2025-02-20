package mysite.event;

import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;

public class ApplicationContextEventListener {

	@EventListener({ContextRefreshedEvent.class})
	public void handlerContextRefreshEvent() {
		System.out.println("-- Context Refreshed Event Received --");
	}
}
