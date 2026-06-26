package com.project.model;

public class Membership {
	private String gradeName;
	private double discountRate;
	private int upgradeCondition;
	
	public String getGradeName() {
		return gradeName;
	}
	public void setGradeName(String gradeName) {
		this.gradeName = gradeName;
	}
	public double getDiscountRate() {
		return discountRate;
	}
	public void setDiscountRate(double discountRate) {
		this.discountRate = discountRate;
	}
	public int getUpgradeCondition() {
		return upgradeCondition;
	}
	public void setUpgradeCondition(int upgradeCondition) {
		this.upgradeCondition = upgradeCondition;
	}
}