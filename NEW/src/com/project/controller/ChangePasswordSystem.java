package com.project.controller;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

public class ChangePasswordSystem {
    
    // 메인 메뉴에서 호출할 수 있도록 run 메서드를 생성하고 스캐너를 매개변수로 받습니다.
    public void password() {
    	Scanner sc = new Scanner(System.in);
        System.out.println("\n=== 비밀번호 변경 진행 ===");
        System.out.println("ID를 입력하세요: ");
        String inputId = sc.nextLine();

        System.out.println("비밀번호를 입력하세요: ");
        String inputPw = sc.nextLine();

        int i = 0;
        boolean idFound = false;
        
        // 💡 비밀번호 파일의 전체 내용을 임시로 담아둘 리스트 생성
        ArrayList<String> pwList = new ArrayList<>();

        // 1. id.txt와 pw.txt를 동시에 읽으며 정보 조회 및 pw 데이터 백업
        try (BufferedReader brId = new BufferedReader(new FileReader("id.txt"));
             BufferedReader brPw = new BufferedReader(new FileReader("pw.txt"))) {

            String idLine;
            String pwLine;

            while ((idLine = brId.readLine()) != null && (pwLine = brPw.readLine()) != null) {

            	// 💡 나중에 파일을 새로 쓰기 위해 기존 비밀번호들을 리스트에 차곡차곡 모아둡니다.
                pwList.add(pwLine);

                // (if) 만약 불러온 id.txt 데이터의 값 = 콘솔에 작성한 아이디가 같다면
                if (idLine.equals(inputId)) {
                    idFound = true;

                    // (if) 만약 불러온 pw.txt 데이터의 값 = 콘솔에 작성한 비밀번호가 같다면
                    if (pwLine.equals(inputPw)) {
                        System.out.println("변경할 비밀번호를 입력하세요: ");
                        String newPw = sc.nextLine();

                        // 💡 리스트의 i번째 위치에 있는 기존 비밀번호를 새 비밀번호로 변경합니다.
                        pwList.set(i, newPw);
                        System.out.println("비밀번호를 성공적으로 변경 중입니다...");
                    } else {
                        System.out.println("❌ 비밀번호가 다릅니다.");
                        return; // 비밀번호가 다르면 함수를 즉시 종료하고 메인 메뉴로 복귀
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
            System.out.println("[오류] 파일을 읽을 수 없습니다: " + e.getMessage());
            return;
        }

        // 2. 💡 수정한 비밀번호 리스트를 pw.txt 파일에 처음부터 끝까지 통째로 덮어쓰기(Overwrite)
        // FileWriter 뒤에 true를 붙이지 않으면 기존 내용을 지우고 새로 작성합니다.
        try (BufferedWriter bwPw = new BufferedWriter(new FileWriter("pw.txt"))) {
            for (String pw : pwList) {
                bwPw.write(pw);
                bwPw.newLine();
            }
            System.out.println("✨ 비밀번호 변경 파일 저장이 완료되었습니다!");
        } catch (IOException e) {
            System.out.println("[오류] 새 비밀번호를 저장하는 중 문제가 발생했습니다: " + e.getMessage());
        }
        
        // ⚠️ 메인 메뉴의 스캐너가 닫히지 않도록 여기서는 절대 sc.close()를 하지 않습니다.
    }
}