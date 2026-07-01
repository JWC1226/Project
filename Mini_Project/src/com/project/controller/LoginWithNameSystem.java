package com.project.controller;

import com.project.model.User;

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

        boolean idFound = false; 

        // id.txt, pw.txt, name.txt 3개의 파일을 동시에 첫 줄부터 읽기 시작
        try (BufferedReader brId = new BufferedReader(new FileReader("id.txt"));
             BufferedReader brPw = new BufferedReader(new FileReader("pw.txt"));
             BufferedReader brName = new BufferedReader(new FileReader("name.txt"))) {
            
            String idLine;
            String pwLine;
            String nameLine;

            // 반복문(while): 세 파일 모두에서 동일하게 한 줄씩 데이터를 불러옴
            while ((idLine = brId.readLine()) != null && 
                   (pwLine = brPw.readLine()) != null && 
                   (nameLine = brName.readLine()) != null) {
                
//            	[Q1] 불러온 파일의 ID 와 입력한 ID 가 일치할 때만 객체를 생성
                if (idLine.equals(inputId)) {
                    idFound = true; 

//                  [Q2] 일치하는 데이터를 찾았으므로 User 객체로 조립 (읽어오기)
                    User loginUser = new User(idLine, pwLine, nameLine);

//                  [Q3] 조립된 User 객체의 정보를 활용하여 pw 검증 (과정)
                    if (loginUser.getPassword().equals(inputPw)) {
                        System.out.println(loginUser.getName() + "님, 로그인이 되었습니다.");
                    } else {
                        System.out.println("비밀번호가 다릅니다.");
                    }
                    break;
                }
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