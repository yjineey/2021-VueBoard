package kr.co.board.vo;

public class PageVo {
    private int totalListItemCount; 	// 전체 게시글 수
    private int currentPageIndex ;    //페이지번호
    
    private int listRowCount;    	//페이지당 게시글 출력 수
    private int pageLinkCount;		///페이지 링크 보여지는 수

    private int  pageCount;			//전체 페이지 수
    private int startPageIndex; 		// 페이징시작번호 (1, 11, 21...)
    private int endPageIndex;			// 페이징끝번호 (9,19,29...)
    
    private boolean prev=false;
    private boolean next=false;
	public int getTotalListItemCount() {
		return totalListItemCount;
	}
	public void setTotalListItemCount(int totalListItemCount) {
		this.totalListItemCount = totalListItemCount;
	}
	public int getCurrentPageIndex() {
		return currentPageIndex;
	}
	public void setCurrentPageIndex(int currentPageIndex) {
		this.currentPageIndex = currentPageIndex;
	}
	public int getListRowCount() {
		return listRowCount;
	}
	public void setListRowCount(int listRowCount) {
		this.listRowCount = listRowCount;
	}
	public int getPageLinkCount() {
		return pageLinkCount;
	}
	public void setPageLinkCount(int pageLinkCount) {
		this.pageLinkCount = pageLinkCount;
	}
	public int getPageCount() {
		return pageCount;
	}
	public void setPageCount(int pageCount) {
		this.pageCount = pageCount;
	}
	public int getStartPageIndex() {
		return startPageIndex;
	}
	public void setStartPageIndex(int startPageIndex) {
		this.startPageIndex = startPageIndex;
	}
	public int getEndPageIndex() {
		return endPageIndex;
	}
	public void setEndPageIndex(int endPageIndex) {
		this.endPageIndex = endPageIndex;
	}
	public boolean isPrev() {
		return prev;
	}
	public void setPrev(boolean prev) {
		this.prev = prev;
	}
	public boolean isNext() {
		return next;
	}
	public void setNext(boolean next) {
		this.next = next;
	}
    
}
