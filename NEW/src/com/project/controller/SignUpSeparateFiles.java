package com.project.controller;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

public class SignUpSeparateFiles {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int i = 0;

        while (true) {
            System.out.println("사용 할 ID를 입력하세요");
            String inputId = sc.nextLine();
            
            boolean isDuplicate = false;
            boolean fileIsEmpty = true;

//             1. id.txt 파일을 읽으며 중복 및 공백(끝) 체크
            try (BufferedReader br = new BufferedReader(new FileReader("id.txt"))) {
                String fileLine;
                i = 0; // 파일 탐색 시작할 때 줄 번호 초기화

                while ((fileLine = br.readLine()) != null) {
                    fileIsEmpty = false;
                    
                    // 만약 불러온 데이터의 값 = 콘솔에 작성한 아이디가 같다면
                    if (fileLine.trim().equals(inputId)) {
                        System.out.println("중복 된 ID 입니다.");
                        isDuplicate = true;
                        i = 0; // i를 0으로 바꾼 뒤
                        break; // 파일 읽기 탈출 (새 아이디 입력으로)
                    }
                    i++; // 한 줄 읽을 때마다 줄 번호 증가
                }
            } catch (IOException e) {
                // 파일이 존재하지 않는 경우 (첫 가입자일 때)
                fileIsEmpty = true;
            }
            // 중복이 발생했다면 while문의 처음(아이디 입력)으로 돌아감
            if (isDuplicate) {
                continue;
            }

            // 파일의 끝(공백/null)에 도달했거나 파일이 완전히 비어있는 경우 -> 사용할 수 있는 ID
            System.out.println("사용할 수 있는 ID입니다.");
            
            System.out.println("사용 할 비밀번호를 입력하세요");
            String inputPw = sc.nextLine();
            System.out.println("사용자의 이름을 입력하세요");
            String inputName = sc.nextLine();

//             2. 각 파일의 i번째 줄에 데이터 저장 (이어쓰기 모드 필수)
            try {
                // id.txt에 ID 추가
                try (BufferedWriter bwId = new BufferedWriter(new FileWriter("id.txt", true))) {
                    bwId.write(inputId);
                    bwId.newLine();
                }
                // pw.txt에 비밀번호 추가
                try (BufferedWriter bwPw = new BufferedWriter(new FileWriter("pw.txt", true))) {
                    bwPw.write(inputPw);
                    bwPw.newLine();
                }
                // name.txt에 이름 추가
                try (BufferedWriter bwName = new BufferedWriter(new FileWriter("name.txt", true))) {
                    bwName.write(inputName);
                    bwName.newLine();
                }
                System.out.println("회원가입이 완료되었습니다!");
                break; // 회원가입 성공했으므로 전체 프로그램 종료

            } catch (IOException e) {
                System.out.println("파일 저장 중 오류가 발생했습니다: " + e.getMessage());
                break;
            }
        }
    }
}