package com.project.controller;

import com.project.model.User;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

public class SignUpSeparateFiles {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        while (true) {
            System.out.println("사용 할 ID를 입력하세요");
            String inputId = sc.nextLine().trim();
            boolean isDuplicate = false;

//          TODO 1: Id.txt 파일을 읽어(중복 체크) 파일이 없을 경우 catch 문구로 넘어가서 자동 패스 
            try (BufferedReader br = new BufferedReader(new FileReader("id.txt"))) {
                String fileLine;

                while ((fileLine = br.readLine()) != null) {
                    // 불러온 데이터와 입력한 아이디가 같다면 (if)
                    if (fileLine.trim().equals(inputId)) {
                        System.out.println("중복 된 ID 입니다.");
                        isDuplicate = true;
                        break;
                    }
                }
            } catch (IOException e) {
    	}
            
            // 중복이 발생했다면 while 문의 처음(아이디 입력)으로 돌아감
            if (isDuplicate) {
                continue;
            }

            System.out.println("사용할 수 있는 ID입니다.");
            
            System.out.println("사용 할 비밀번호를 입력하세요");
            String inputPw = sc.nextLine().trim();
            System.out.println("사용자의 이름을 입력하세요");
            String inputName = sc.nextLine().trim();

//          [Q1] 입력받은 흩어진 데이터들을 하나의 User 객체로 포함 (CRUD - C)
            User newUser = new User(inputId, inputPw, inputName);

//          TODO 2: User 객체에서 데이터를 꺼내 각 파일에 저장 / true = 재실행했을 때, 파일이 사라지지 않도록 유지 (누적)
//          [Q2] try with resource 하나로 3개의 파일을 한 번에 실행
            try (BufferedWriter bwId = new BufferedWriter(new FileWriter("id.txt", true));
                 BufferedWriter bwPw = new BufferedWriter(new FileWriter("pw.txt", true));
                 BufferedWriter bwName = new BufferedWriter(new FileWriter("name.txt", true))) {
                
                // 객체의 Getter 를 활용해 안전하게 데이터를 파일에 씁니다.
                bwId.write(newUser.getId());
                bwId.newLine();
                
                bwPw.write(newUser.getPassword());
                bwPw.newLine();
                
                bwName.write(newUser.getName());
                bwName.newLine();

                System.out.println("회원가입이 완료되었습니다!");
                break;

            } catch (IOException e) {
                System.out.println("파일 저장 중 오류가 발생했습니다: " + e.getMessage());
                break;
            }
        }
    }
}