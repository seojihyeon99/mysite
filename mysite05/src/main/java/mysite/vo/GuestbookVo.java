package mysite.vo;

public class GuestbookVo {
	private Long id;
	private String name;
	private String password;
	private String regDate;
	private String contents;
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getRegDate() {
		return regDate;
	}
	public void setRegDate(String regDate) {
		this.regDate = regDate;
	}
	public String getContents() {
		return contents;
	}
	public void setContents(String contents) {
		this.contents = contents;
	}
	@Override
	public String toString() {
		return "GuestbookVo [id=" + id + ", name=" + name + ", password=" + password + ", regDate=" + regDate
				+ ", contents=" + contents + "]";
	}
}
