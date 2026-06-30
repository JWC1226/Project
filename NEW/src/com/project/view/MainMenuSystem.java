package com.project.view;

import java.util.Scanner;

import com.project.controller.ChangePasswordSystem;
import com.project.controller.DeleteMemberSystem;
import com.project.controller.LoginWithNameSystem;
import com.project.controller.SignUpSeparateFiles;

public class MainMenuSystem {
    // 💡 모든 메서드에서 함께 사용할 수 있도록 스캐너를 클래스 멤버로 선언
    private static final Scanner sc = new Scanner(System.in);

    public static void main(String[] args) {
        MainMenuSystem system = new MainMenuSystem();
        system.mainMenu(); // 메인 메뉴 실행
        sc.close(); // 프로그램 전체 종료 시 스캐너 닫기
    }

    // 📱 메인 메뉴 창
    public void mainMenu() {
        while (true) {
            System.out.println("\n=== 환영합니다 ===");
            System.out.println("[1] 회원가입");
            System.out.println("[2] 로그인");
            System.out.println("[3] 비번 변경");
            System.out.println("[4] 회원 탈퇴");
            System.out.println("[9] 프로그램 종료");
            System.out.print("메뉴 선택: ");

            int menu = sc.nextInt();    // 메뉴값을 정수로 받아옴
            sc.nextLine();             // 버퍼 비우기 (엔터 키 제거)

            switch (menu) {
                case 1:
                	SignUpSeparateFiles signUpSystem = new SignUpSeparateFiles();
                	signUpSystem.main(null);
                    break;
                case 2:
                	LoginWithNameSystem loginSystem = new LoginWithNameSystem();
                	loginSystem.main(null);
                    break;
                case 3:
                	ChangePasswordSystem changePassword = new ChangePasswordSystem();
                	changePassword.password();
                	break;
                case 4:	
                	DeleteMemberSystem userDelete = new DeleteMemberSystem();
                	userDelete.delete();
                	break;
                case 9:
                    System.out.println("프로그램을 종료합니다.");
                    return; // 메인 메뉴 메서드를 빠져나가 프로그램 종료
                default:
                    System.out.println("[오류] 올바른 번호를 입력해주세요");
                    break;
            }
        }
    }
}