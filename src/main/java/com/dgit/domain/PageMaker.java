package com.dgit.domain;

public class PageMaker {
	
	//화면 하단의 페이징 부분
	
	private int totalCount;// 전체 게시물 갯수
	private int startPage;// 페이지 번호의 시작번호
	private int endPage;//
	private boolean prev;
	private boolean next;

	private int displayPageNum = 10; // 보여지는 페이지 번호의 갯수

	private Criteria cri; // 현재 페이지 번호를 알아야 페이지 번호의 시작을 알 수 있음

	
	//페이징 숫자 넘어가는거 계산하는 함수
	private void calcData(){
		//현재 페이지의 끝 번호를 구한다.
		//ex)15/10 => Math.ceil(1.5) -> 2*10 ->20
		endPage = (int)(Math.ceil(cri.getPage()/(double) displayPageNum)*displayPageNum);
		
		
		//현재 페이지의 시작 번호를 구한다. ex)20-10+1=11
		startPage = (endPage - displayPageNum)+1;
		
		
		//전체 게시물이 151이고 현재 페이지가 15일 때, 마지막 end는 16으로 나타나야 한다.
		//Math.ceil(151/10) = 16
		int tempEndPage = (int)Math.ceil(totalCount/(double) cri.getPerPageNum());
				
		if(endPage>tempEndPage){
			endPage=tempEndPage;
		}
		
		//이전 페이지 생성 여부
		prev = (startPage ==1)? false:true;//prev가 1번이면 안나오게 false 아니면 true해서 나오게
		
		
		//다음 페이지 생성 여부
		next = (endPage*cri.getPerPageNum()>=totalCount)? false:true;
	}
	
	
	public int getTotalCount() {
		return totalCount;
	}

	public void setTotalCount(int totalCount) {
		this.totalCount = totalCount;
		calcData();
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

	public int getDisplayPageNum() {
		return displayPageNum;
	}

	public void setDisplayPageNum(int displayPageNum) {
		this.displayPageNum = displayPageNum;
	}

	public Criteria getCri() {
		return cri;
	}

	public void setCri(Criteria cri) {
		this.cri = cri;
	}

	@Override
	public String toString() {
		return "PageMaker [totalCount=" + totalCount + ", startPage=" + startPage + ", endPage=" + endPage + ", prev="
				+ prev + ", next=" + next + ", displayPageNum=" + displayPageNum + ", cri=" + cri + "]";
	}

}
