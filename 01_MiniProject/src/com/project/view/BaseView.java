package com.project.view;

import java.util.Scanner;
import com.project.controller.MembershipController;
import com.project.model.User;

public class BaseView {
	//	클래스들이 공유하는 공통 객체들
	protected Scanner sc = new Scanner(System.in);
	protected static MembershipController controller = new MembershipController();
	protected static User currentUser;
}