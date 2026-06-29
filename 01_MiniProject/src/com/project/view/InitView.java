package com.project.view;

import com.project.model.User;

public class InitView extends BaseView {
	public void start() {
		System.out.println("=== 멤버십 조회 ===");
		System.out.print("사용하실 ID를 입력해주세요: ");
			String id = sc.nextLine();
		System.out.print("사용하실 닉네임을 입력해주세요: ");
			String nickname = sc.nextLine();
		System.out.print("나이를 입력해주세요: ");
			int age = sc.nextInt();
			sc.nextLine();		//버퍼 비우기
		System.out.print("성별을 입력해주세요 (남/여): ");
			String gender = sc.nextLine();
		
		currentUser = new User(id, nickname, age, gender);
		controller.registerUser(currentUser);
		System.out.printf("[%s]님의 가입을 환영합니다\n", nickname);
		
//		메인 메뉴 이동
		new MainMenuView().showMenu();
	}
}