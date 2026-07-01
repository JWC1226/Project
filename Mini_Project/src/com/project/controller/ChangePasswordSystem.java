package com.project.controller;

import com.project.model.User;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

public class ChangePasswordSystem {
    
    public void password() {
        Scanner sc = new Scanner(System.in);
        System.out.println("\n=== 비밀번호 변경 진행 ===");
        System.out.println("ID를 입력하세요: ");
        String inputId = sc.nextLine();
        System.out.println("비밀번호를 입력하세요: ");
        String inputPw = sc.nextLine();

//      [Q1] Model User 객체를 담는 리스트 생성
        ArrayList<User> userList = new ArrayList<>();
        
        boolean idFound = false;
        boolean isChanged = false;		// 변경이 성공했는지 체크

//      TODO 1: 파일 데이터를 읽고, User 객체로 변경 및 txt 에 담기
        try (BufferedReader brId = new BufferedReader(new FileReader("id.txt"));
             BufferedReader brPw = new BufferedReader(new FileReader("pw.txt"))) {

            String idLine;
            String pwLine;

            while ((idLine = brId.readLine()) != null && (pwLine = brPw.readLine()) != null) {
                // name 파일이 있다면 여기서 읽어와야 하지만, 현재 없으므로 Unknown 으로 임시 처리합니다.
                String nameLine = "[오류] 알 수 없는 이름입니다"; 
                
//              [Q2] 읽어온 데이터를 합쳐 하나의 User 객체로 생성 후, 리스트에 추가  
                User user = new User(idLine, pwLine, nameLine);
                userList.add(user);
            }

        } catch (IOException e) {
            System.out.println("[오류] 파일을 읽을 수 없습니다: " + e.getMessage());
            return;
        }

//      TODO 2: User 파일 탐색 및 ID PW 확인 및 수정 (Engine)  
        for (User user : userList) {
            // 리스트에 담긴 객체의 ID와 입력한 ID가 같다면
            if (user.getId().equals(inputId)) {
                idFound = true;
                
                // 해당 객체의 비밀번호와 입력한 비밀번호가 같다면 (if)
                if (user.getPassword().equals(inputPw)) {
                    System.out.println("변경할 새 비밀번호를 입력하세요: ");
                    String newPw = sc.nextLine();

//                  [Q3] 인덱스 i 없이 객체의 setter 를 사용해 즉각 변경 (pw)
                    user.setPassword(newPw); 
                    isChanged = true;
                    System.out.println("비밀번호를 성공적으로 변경 중입니다...");
                } else {
                    System.out.println("비밀번호가 다릅니다.");
                    return; // 비밀번호 불일치 시 즉시 종료
                }
                break;
            }
        }

        if (!idFound) {
            System.out.println("존재하지 않는 ID입니다.");
            return;
        }

//      TODO 3: 변경 후 User 파일에서 pw.txt 덮어쓰기 (저장할때만)
        if (isChanged) {
            try (BufferedWriter bwPw = new BufferedWriter(new FileWriter("pw.txt"))) {
                for (User user : userList) {
                	
//                	[Q4] User 객체에서 getPassword()로 비번을 추출하여 파일에 덮음
                    bwPw.write(user.getPassword());
                    bwPw.newLine();
                }
                System.out.println("비밀번호 변경 파일 저장이 완료되었습니다!");
            } catch (IOException e) {
                System.out.println("[오류] 새 비밀번호를 저장하는 중 문제가 발생했습니다: " + e.getMessage());
            }
        }
    }
}