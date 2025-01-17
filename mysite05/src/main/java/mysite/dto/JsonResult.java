package mysite.dto;

public class JsonResult {
	private String result;	// "success"  or "fail"
	private Object data;	// if success, set
	private String message;	// if fail, set

	public static JsonResult success(Object data) {
		return new JsonResult(data);
	}

	public static JsonResult fail(String message) {
		return new JsonResult(message);
	}
	
	private JsonResult(Object data) {
		this.result = "success";
		this.data = data;
		this.message = null;
	}

	private JsonResult(String message) {
		this.result = "fail";
		this.data = null;
		this.message = message;
	}
	
	public String getResult() {
		return result;
	}
	public Object getData() {
		return data;
	}
	public String getMessage() {
		return message;
	}
}
