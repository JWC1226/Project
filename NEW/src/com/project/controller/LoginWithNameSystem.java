package com.project.controller;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Scanner;

public class LoginWithNameSystem {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        System.out.println("ID를 입력하세요");
        String inputId = sc.nextLine();
        System.out.println("비밀번호를 입력하세요");
        String inputPw = sc.nextLine();

        int i = 0; 
        boolean idFound = false; 

        // id.txt, pw.txt, name.txt 3개의 파일을 동시에 첫 줄부터 읽기 시작
        try (BufferedReader brId = new BufferedReader(new FileReader("id.txt"));
             BufferedReader brPw = new BufferedReader(new FileReader("pw.txt"));
             BufferedReader brName = new BufferedReader(new FileReader("name.txt"))) {
        	//txt 뒤에 true가 붙는 이유는 새롭게 저장할때 동일한 파일이 있는지 확인 여부 => 저장할 때만 사용
            String idLine;
            String pwLine;
            String nameLine;

            // 반복문(while): 세 파일 모두에서 동일하게 한 줄씩 데이터를 불러옴
            while ((idLine = brId.readLine()) != null && 
                   (pwLine = brPw.readLine()) != null && 
                   (nameLine = brName.readLine()) != null) {
                
                // (if) 만약 불러온 id.txt 데이터의 값 = 콘솔에 작성한 아이디가 같다면
                if (idLine.equals(inputId)) {
                    idFound = true; 

                    // (if) 만약 불러온 pw.txt 데이터의 값 = 콘솔에 작성한 비밀번호가 같다면
                    if (pwLine.equals(inputPw)) {
                        // name.txt에서 읽어온 이름(nameLine)을 함께 출력
                        System.out.println(nameLine + "님, 로그인이 되었습니다.");
                    } else {
                        System.out.println("비밀번호가 다릅니다.");
                    }
                    break; // 아이디를 찾았으므로 결과를 출력하고 반복문 탈출
                }
                i = i + 1;
            }
            // 파일 끝까지 다 읽었는데도 아이디를 발견하지 못한 경우
            if (!idFound) {
                System.out.println("존재하지 않는 ID입니다.");
            }
        } catch (IOException e) {
            System.out.println("파일을 읽는 중 오류가 발생했습니다: " + e.getMessage());
        }
    }
}