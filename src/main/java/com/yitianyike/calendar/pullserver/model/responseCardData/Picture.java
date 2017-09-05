package com.yitianyike.calendar.pullserver.model.responseCardData;

public class Picture {

	private String title;
	private String snapshot;
	private String skip_url;
	private String start;
	private String end;

	public String getStart() {
		return start;
	}

	public void setStart(String start) {
		this.start = start;
	}

	public String getEnd() {
		return end;
	}

	public void setEnd(String end) {
		this.end = end;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getSnapshot() {
		return snapshot;
	}

	public void setSnapshot(String snapshot) {
		this.snapshot = snapshot;
	}

	public String getSkip_url() {
		return skip_url;
	}

	public void setSkip_url(String skip_url) {
		this.skip_url = skip_url;
	}

}
