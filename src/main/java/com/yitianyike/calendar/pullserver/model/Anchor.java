package com.yitianyike.calendar.pullserver.model;

public class Anchor {
	private int id;
	private int anchor_id;
	private int card_style;
	private String column_name;
	private String quote_name;
	private int little_type;
	private long exp_date_time;
	private int type;
	private int frequence;
	private int skip_action;
	private String season;

	public String getSeason() {
		return season;
	}

	public void setSeason(String season) {
		this.season = season;
	}

	public int getSkip_action() {
		return skip_action;
	}

	public void setSkip_action(int skip_action) {
		this.skip_action = skip_action;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public long getExp_date_time() {
		return exp_date_time;
	}

	public void setExp_date_time(long exp_date_time) {
		this.exp_date_time = exp_date_time;
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	public int getFrequence() {
		return frequence;
	}

	public void setFrequence(int frequence) {
		this.frequence = frequence;
	}

	public int getAnchor_id() {
		return anchor_id;
	}

	public void setAnchor_id(int anchor_id) {
		this.anchor_id = anchor_id;
	}

	public int getCard_style() {
		return card_style;
	}

	public void setCard_style(int card_style) {
		this.card_style = card_style;
	}

	public String getColumn_name() {
		return column_name;
	}

	public void setColumn_name(String column_name) {
		this.column_name = column_name;
	}

	public String getQuote_name() {
		return quote_name;
	}

	public void setQuote_name(String quote_name) {
		this.quote_name = quote_name;
	}

	public int getLittle_type() {
		return little_type;
	}

	public void setLittle_type(int little_type) {
		this.little_type = little_type;
	}

}
