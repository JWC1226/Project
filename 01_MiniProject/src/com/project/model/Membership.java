package com.project.model;

public class Membership {
	private String gradeName;
	private double discountRate;
	private int upgradeCondition;
	
	public Membership(String gradeName, double discountRate, int upgradeCondition) {
		this.gradeName = gradeName;
		this.discountRate = discountRate;
		this.upgradeCondition = upgradeCondition;
	}

	public String getGradeName() {
		return gradeName;
	}
	public double getDiscountRate() {
		return discountRate;
	}
	public int getUpgradeCondition() {
		return upgradeCondition;
	}
}