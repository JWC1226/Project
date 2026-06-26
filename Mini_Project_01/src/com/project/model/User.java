package com.project.model;

public class User {
	private String id;
	private String nickname;
	private int totalSpent;
	private String currentGrade;
	private int points;
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getNickname() {
		return nickname;
	}
	public void setNickname(String nickname) {
		this.nickname = nickname;
	}
	public int getTotalSpent() {
		return totalSpent;
	}
	public void setTotalSpent(int totalSpent) {
		this.totalSpent = totalSpent;
	}
	public String getCurrentGrade() {
		return currentGrade;
	}
	public void setCurrentGrade(String currentGrade) {
		this.currentGrade = currentGrade;
	}
	public int getPoints() {
		return points;
	}
	public void setPoints(int points) {
		this.points = points;
	}
}