package com.project.view;

import java.util.Scanner;
import com.project.controller.UserController;
import com.project.model.User;

public class MainMenuSystem {
    private static final Scanner sc = new Scanner(System.in);
    private UserController userController = new UserController();		//통합된 컨트롤러 객체 1개 생성

    public static void main(String[] args) {
        MainMenuSystem system = new MainMenuSystem();
        system.mainMenu(); 
        sc.close(); 
    }

    //메인 메뉴 창
    public void mainMenu() {
        while (true) {
            System.out.println("\n=== 환영합니다 ===");
            System.out.println("[1] 회원가입");
            System.out.println("[2] 로그인");
            System.out.println("[3] 비번 변경");
            System.out.println("[4] 회원 탈퇴");
            System.out.println("[9] 프로그램 종료");
            System.out.print("메뉴 선택: ");
            int menu = sc.nextInt();    
            sc.nextLine();             

            switch (menu) {
                case 1:
                    System.out.println("\n=== 회원가입 진행 ===");
                    String inputId;
                    while (true) {
                        System.out.println("사용 할 ID를 입력하세요");
                        inputId = sc.nextLine();
                        
                        // 컨트롤러를 통해 중복 검사 실행
                        if (userController.isDuplicateId(inputId)) {
                            System.out.println("중복 된 ID 입니다");
                        } else {
                            System.out.println("사용할 수 있는 ID입니다");
                            break;
                        }
                    }
                    System.out.println("사용 할 비밀번호를 입력하세요");
                    String inputPw = sc.nextLine();
                    System.out.println("사용자의 이름을 입력하세요");
                    String inputName = sc.nextLine();
                    
                    // 입력받은 데이터를 컨트롤러로 넘겨 가입 처리
                    userController.signUp(inputId, inputPw, inputName);
                    break;

                case 2:
                    System.out.println("\n=== 로그인 진행 ===");
                    System.out.println("ID를 입력하세요");
                    String loginId = sc.nextLine();
                    System.out.println("비밀번호를 입력하세요");
                    String loginPw = sc.nextLine();

                    // 컨트롤러를 통해 로그인 검증 진행 (성공 시 User 객체 반환)
                    User loginUser = userController.authenticateUser(loginId, loginPw);
                    if (loginUser != null) {
                        System.out.println(loginUser.getName() + "님, 로그인이 되었습니다");
                    }
                    break;

                case 3:
                    System.out.println("\n=== 비밀번호 변경 진행 ===");
                    System.out.println("ID를 입력하세요: ");
                    String changeId = sc.nextLine();
                    System.out.println("비밀번호를 입력하세요: ");
                    String changePw = sc.nextLine();

                    // 본인 확인 진행
                    User targetToChange = userController.authenticateUser(changeId, changePw);
                    if (targetToChange != null) {
                        System.out.println("변경할 새 비밀번호를 입력하세요: ");
                        String newPw = sc.nextLine();
                        // 확인된 유저 객체와 새 비밀번호를 넘겨서 비번 변경 처리
                        userController.changePassword(targetToChange, newPw);
                    }
                    break;

                case 4:	
                    System.out.println("\n=== 회원 탈퇴 진행 ===");
                    System.out.println("ID를 입력하세요: ");
                    String delId = sc.nextLine();
                    System.out.println("비밀번호를 입력하세요: ");
                    String delPw = sc.nextLine();

                    // 본인 확인 진행
                    User targetToDelete = userController.authenticateUser(delId, delPw);
                    if (targetToDelete != null) {
                        System.out.println(targetToDelete.getName() + "님, 회원탈퇴 하시겠습니까? (yes/no)");
                        String choice = sc.nextLine().toLowerCase();

                        if (choice.equals("yes")) {
                            userController.deleteUser(targetToDelete);
                        } else {
                            System.out.println("회원 탈퇴를 취소합니다. 메인 메뉴로 돌아갑니다");
                        }
                    }
                    break;

                case 9:
                    System.out.println("프로그램을 종료합니다");
                    return; 
                default:
                    System.out.println("[오류] 올바른 번호를 입력해주세요");
                    break;
            }
        }
    }
}