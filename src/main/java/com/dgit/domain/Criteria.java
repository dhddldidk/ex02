package com.dgit.domain;

public class Criteria {
	
	//화면 상단의 게시글이 나오는 부분
	
	private int page;//현재 선택된 페이지 번호
	private int perPageNum;

	public Criteria() {
		this.page = 1;// 페이지
		this.perPageNum = 10;// 게시글 갯수
	}

	public int getPage() {
		return page;
	}

	public void setPage(int page) {
		this.page = page;
	}

	public int getPerPageNum() {
		return perPageNum;
	}

	public void setPerPageNum(int perPageNum) {
		this.perPageNum = perPageNum;
	}

	public int getPageStart(){
		return (this.page-1)*this.perPageNum;//this.perPageNum이 10임
	}
	@Override
	public String toString() {
		return "Criteria [page=" + page + ", perPageNum=" + perPageNum + "]";
	}

}
