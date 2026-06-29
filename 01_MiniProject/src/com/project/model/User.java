package com.project.model;

public class User {
	private String id;
	private String nickname;
    private int age;
    private String gender;
    
    private int totalSpent;
    private String currentGrade;
    private int points;
    private int paymentMethod;		//결제 수단 번호
    private int purchaseCount;		//누적 구매 횟수
    private int money;
    
    public User(String id, String nickname, int age, String gender) {
        this.id = id;
        this.nickname = nickname;
        this.age = age;
        this.gender = gender;
        this.totalSpent = 0;
        this.currentGrade = "WELCOME";	//초기 등급 (이디야 멤버십에서 영감을 받음)
        this.points = 0;
        this.paymentMethod = 1;			//기본 결제 수단: 1번
        this.money = 0;
    }
//    getter
	public String getId() {
		return id;
	}
	public String getNickname() {
		return nickname;
	}
	public int getAge() {
		return age;
	}
	public String getGender() {
		return gender;
	}
//	getter＆setter
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
	public int getPaymentMethod() {
		return paymentMethod;
	}
	public void setPaymentMethod(int paymentMethod) {
		this.paymentMethod = paymentMethod;
	}
	public int getPurchaseCount() {
		return purchaseCount;
	}
	public void setPurchaseCount(int purchaseCount) {
		this.purchaseCount = purchaseCount;
	}
	public int getMoney() {
		return money;
	}
	public void setMoney(int money) {
		this.money = money;
	}
}