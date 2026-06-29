package com.project.view;

public class MainMenuView extends BaseView {
	public void showMenu() {
		while (true) {
//			회원 탈퇴 등으로 현재 유저가 없어지면 종료
			if (currentUser == null || controller.searchUser(currentUser.getId()) == null) {
				System.out.println("시스템을 종료합니다");
				break;
			}
			System.out.println("=========================");
			System.out.println("[1] 메뉴 선택 (주문 및 결제)");
			System.out.println("[2] 결제 수단 변경");
			System.out.println("[3] 회원 정보 조회");
			System.out.println("[4] 회원 탈퇴");
			System.out.println("[9] 시스템 종료");
			System.out.println("=========================");
			System.out.print("번호 선택: ");
			
			int choice = sc.nextInt();
			sc.nextLine();

//			기능별 뷰 객체를 생성하여 위임
			switch (choice) {
			case 1:
				new PurchaseView().purchase();
				break;
			case 2:
				new PurchaseView().changePayment();
				break;
			case 3:
				new UserDetailView().viewInfo();
				break;
			case 4:
				System.out.println("\n--- [ 회원 탈퇴 ] ---");
			    System.out.print("정말로 탈퇴하시겠습니까? 탈퇴 시 모든 포인트와 잔액이 소멸됩니다. (Y/N): ");
			    String confirm = sc.next();
			    sc.nextLine(); // 버퍼 비우기

			    if (confirm.equalsIgnoreCase("Y")) {
			        // 1. 컨트롤러를 호출하여 파일 및 시스템에서 유저 데이터 삭제
			        controller.deleteAccount(currentUser);
			        
			        // 2. 현재 로그인된 유저 세션을 null 로 초기화 (로그아웃 효과)
			        currentUser = null; 
			        
			        System.out.println("회원 탈퇴가 정상적으로 완료되었습니다. 초기 화면으로 이동합니다.");
			        return; // ★ 중요: 현재 메인 메뉴 메서드를 완전히 종료(return)하여 첫 화면으로 튕겨냅니다.
			    } else {
			        System.out.println("회원 탈퇴를 취소하고 메인 메뉴로 돌아갑니다.");
			    }
			    break;
			}
		}
	}
}