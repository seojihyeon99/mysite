package mysite.vo;

public class BoardVo {
	private Long id;
	private String title;
	private String contents;
	private String regDate;
	private Integer hit;
	private Integer gNo;
	private Integer oNo;
	private Integer depth;
	private Long userId;
	private String name;
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getContents() {
		return contents;
	}
	public void setContents(String contents) {
		this.contents = contents;
	}
	public String getRegDate() {
		return regDate;
	}
	public void setRegDate(String regDate) {
		this.regDate = regDate;
	}
	public Integer getHit() {
		return hit;
	}
	public void setHit(Integer hit) {
		this.hit = hit;
	}
	public Integer getgNo() {
		return gNo;
	}
	public void setgNo(Integer gNo) {
		this.gNo = gNo;
	}
	public Integer getoNo() {
		return oNo;
	}
	public void setoNo(Integer oNo) {
		this.oNo = oNo;
	}
	public Integer getDepth() {
		return depth;
	}
	public void setDepth(Integer depth) {
		this.depth = depth;
	}
	public Long getUserId() {
		return userId;
	}
	public void setUserId(Long userId) {
		this.userId = userId;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
	
	@Override
	public String toString() {
		return "BoardVo [id=" + id + ", title=" + title + ", contents=" + contents + ", regDate=" + regDate + ", hit="
				+ hit + ", gNo=" + gNo + ", oNo=" + oNo + ", depth=" + depth + ", userId=" + userId + ", name=" + name
				+ "]";
	}
	
}
