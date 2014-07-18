package com.khunglong.xanh.json;

public class Paging {
	private String previous;
	private String next;
	private FbPageCursor cursors;
	public Paging() {
	    super();
	    cursors = new FbPageCursor(null, null);
    }
	public String getPrevious() {
		return previous;
	}
	public void setPrevious(String previous) {
		this.previous = previous;
	}
	public String getNext() {
		return next;
	}
	public void setNext(String next) {
		this.next = next;
	}
	public Paging(String previous, String next) {
	    super();
	    this.previous = previous;
	    this.next = next;
    }
	public FbPageCursor getCursors() {
		return cursors;
	}
	public void setCursors(FbPageCursor cursors) {
		this.cursors = cursors;
	}
}
