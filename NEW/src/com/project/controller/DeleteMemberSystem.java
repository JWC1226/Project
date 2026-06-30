package com.project.controller;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

public class DeleteMemberSystem {

    // 💡 메인 메뉴에서 호출할 수 있도록 run 메서드를 만들고 스캐너를 매개변수로 받습니다.
    public void delete() {
    	Scanner sc = new Scanner(System.in);
        System.out.println("\n=== 회원 탈퇴 진행 ===");
        System.out.println("ID를 입력하세요: ");
        String inputId = sc.nextLine().trim();

        System.out.println("비밀번호를 입력하세요: ");
        String inputPw = sc.nextLine().trim();

        int i = 0;
        boolean idFound = false;

        // 💡 특정 줄을 삭제하기 위해 세 파일의 전체 데이터를 백업해둘 리스트 생성
        ArrayList<String> idList = new ArrayList<>();
        ArrayList<String> pwList = new ArrayList<>();
        ArrayList<String> nameList = new ArrayList<>();

        // 1. 모든 파일의 데이터를 한 줄씩 읽어 리스트에 모으면서 아이디/비밀번호 확인
        try (BufferedReader brId = new BufferedReader(new FileReader("id.txt"));
             BufferedReader brPw = new BufferedReader(new FileReader("pw.txt"));
             BufferedReader brName = new BufferedReader(new FileReader("name.txt"))) {

            String idLine;
            String pwLine;
            String nameLine;

            while ((idLine = brId.readLine()) != null && 
                   (pwLine = brPw.readLine()) != null && 
                   (nameLine = brName.readLine()) != null) {
                
                idLine = idLine.trim();
                pwLine = pwLine.trim();
                nameLine = nameLine.trim();

                // 모든 데이터를 삭제 가공을 위해 리스트에 순서대로 쌓아둡니다.
                idList.add(idLine);
                pwList.add(pwLine);
                nameList.add(nameLine);

                // (if) 만약 불러온 id.txt 데이터의 값 = 콘솔에 작성한 아이디가 같다면
                if (idLine.equals(inputId)) {
                    idFound = true;

                    // (if) 만약 불러온 pw.txt 데이터의 값 = 콘솔에 작성한 비밀번호가 같다면
                    if (pwLine.equals(inputPw)) {
                        // 💡 i번째 name.txt 데이터값 출력
                        System.out.println(nameLine + "님, 회원탈퇴 하시겠습니까?. (yes/no)");
                        String choice = sc.nextLine().trim().toLowerCase(); // 대소문자 구분 방지

                        // (if) 만약 "yes" 를 선택했다면
                        if (choice.equals("yes")) {
                            // 💡 리스트에서 일치하는 i번째 줄 데이터를 쏙 제거합니다.
                            idList.remove(i);
                            pwList.remove(i);
                            nameList.remove(i);
                            System.out.println("회원 데이터 삭제를 진행합니다...");
                        } else {
                            // (else) "no" 등을 고르면 메인메뉴로 이동 (함수 종료)
                            System.out.println("회원 탈퇴를 취소합니다. 메인 메뉴로 돌아갑니다.");
                            return; 
                        }
                    } else {
                        System.out.println("❌ 비밀번호가 다릅니다.");
                        return; // 비밀번호가 다르면 함수를 종료하고 메인 메뉴로 복귀
                    }
                }

                // (else) 아이디가 같지 않다면 다음 줄로 이동하기 위해 i 증가
                if (!idFound) {
                    i = i + 1;
                }
            }

            if (!idFound) {
                System.out.println("❌ 존재하지 않는 ID입니다.");
                return;
            }

        } catch (IOException e) {
            System.out.println("[오류] 회원 정보를 읽어오는 중 문제가 발생했습니다: " + e.getMessage());
            return;
        }

        // 2. 💡 항목이 삭제된 리스트들을 각 파일에 처음부터 끝까지 덮어쓰기(Overwrite)
        try {
            // id.txt 업데이트
            try (BufferedWriter bwId = new BufferedWriter(new FileWriter("id.txt"))) {
                for (String id : idList) {
                    bwId.write(id);
                    bwId.newLine();
                }
            }
            // pw.txt 업데이트
            try (BufferedWriter bwPw = new BufferedWriter(new FileWriter("pw.txt"))) {
                for (String pw : pwList) {
                    bwPw.write(pw);
                    bwPw.newLine();
                }
            }
            // name.txt 업데이트
            try (BufferedWriter bwName = new BufferedWriter(new FileWriter("name.txt"))) {
                for (String name : nameList) {
                    bwName.write(name);
                    bwName.newLine();
                }
            }
            System.out.println("✨ 회원 탈퇴 처리가 안전하게 완료되었습니다.");

        } catch (IOException e) {
            System.out.println("[오류] 파일을 업데이트하는 중 문제가 발생했습니다: " + e.getMessage());
        }
        
        // ⚠️ 메인 메뉴의 스캐너 오작동 방지를 위해 절대로 sc.close()를 하지 않습니다.
    }
}