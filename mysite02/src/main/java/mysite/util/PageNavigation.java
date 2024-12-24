package mysite.util;

public class PageNavigation {
	
	private boolean startRange; // 현재 페이지가 이전이 눌려지지 않는 범위의 페이지 체크
	private boolean endRange; 	// 현재 페이지가 다음이 눌려지지 않는 범위의 페이지 체크
	private int totalCount; 	// 총 게시글 갯수
	private int totalPageCount; // 총 페이지 갯수
	private int currentPage; 	// 현재 페이지 번호
	private int naviSize; 		// 네비게이션 사이즈
	private int startPage;
	private int endPage;
	
	public boolean isStartRange() {
		return startRange;
	}
	public void setStartRange(boolean startRange) {
		this.startRange = startRange;
	}
	public boolean isEndRange() {
		return endRange;
	}
	public void setEndRange(boolean endRange) {
		this.endRange = endRange;
	}
	public int getTotalCount() {
		return totalCount;
	}
	public void setTotalCount(int totalCount) {
		this.totalCount = totalCount;
	}
	public int getTotalPageCount() {
		return totalPageCount;
	}
	public void setTotalPageCount(int totalPageCount) {
		this.totalPageCount = totalPageCount;
	}
	public int getCurrentPage() {
		return currentPage;
	}
	public void setCurrentPage(int currentPage) {
		this.currentPage = currentPage;
	}
	public int getNaviSize() {
		return naviSize;
	}
	public void setNaviSize(int naviSize) {
		this.naviSize = naviSize;
	}
	public int getStartPage() {
		return startPage;
	}
	public void setStartPage(int startPage) {
		this.startPage = startPage;
	}
	public int getEndPage() {
		return endPage;
	}
	public void setEndPage(int endPage) {
		this.endPage = endPage;
	}
	
	
	@Override
	public String toString() {
		return "PageNavigation [startRange=" + startRange + ", endRange=" + endRange + ", totalCount=" + totalCount
				+ ", totalPageCount=" + totalPageCount + ", currentPage=" + currentPage + ", naviSize=" + naviSize
				+ ", startPage=" + startPage + ", endPage=" + endPage + "]";
	}
	
}
