package com.project.view;

public class UserDetailView extends BaseView {
	public void viewInfo() {
        while (true) {
            System.out.println("\n=== [회원 정보 조회] ===");
            System.out.println("아이디: " + currentUser.getId());
            System.out.println("닉네임: " + currentUser.getNickname());
            System.out.println("현재 등급: " + currentUser.getCurrentGrade());
            System.out.println("보유 포인트: " + currentUser.getPoints() + "점");
            System.out.println("현재 보유 금액: " + currentUser.getMoney() + "원"); // 추가
            System.out.println("======================");
            System.out.println("[1] 금액 충전하기");
            System.out.println("[0] 메인 메뉴로 돌아가기");
            System.out.print("선택: ");
            
            int choice = sc.nextInt();
            sc.nextLine(); // 버퍼 비우기

            if (choice == 1) {
                System.out.print("충전할 금액을 입력하세요(원): ");
                int charge = sc.nextInt();
                sc.nextLine();

                if (charge > 0) {
                    // 기존 금액에 더하기(+)
                    currentUser.setMoney(currentUser.getMoney() + charge);
                    controller.updateUser(currentUser); // 변경 사항 저장
                    System.out.println("▶ " + charge + "원이 정상 충전되었습니다!");
                } else {
                    System.out.println("올바른 금액을 입력해주세요");
                }
            } else if (choice == 0) {
                break; // 메인 메뉴로 복귀
            } else {
                System.out.println("잘못된 번호입니다");
            }
        }
    }
}