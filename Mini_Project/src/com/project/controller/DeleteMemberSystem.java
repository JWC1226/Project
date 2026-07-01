package com.project.controller;

import com.project.model.User;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

public class DeleteMemberSystem {

    public void delete() {
        Scanner sc = new Scanner(System.in);
        System.out.println("\n=== 회원 탈퇴 진행 ===");
        System.out.println("ID를 입력하세요: ");
        String inputId = sc.nextLine();
        System.out.println("비밀번호를 입력하세요: ");
        String inputPw = sc.nextLine();

//      [Q1] model 패킷을 위해 리스트 대신 User 객체를 담는 1개의 리스트를 생성
        ArrayList<User> userList = new ArrayList<>();
        
        boolean idFound = false;
        User targetUser = null;
//      [Q2] Delete User 객체를 임시 저장하는 용도

//      TODO 1: 모든 파일의 데이터를 한 줄씩 읽어 User 객체로 만들고 읽어오기 위한 처리 과정
        try (BufferedReader brId = new BufferedReader(new FileReader("id.txt"));
             BufferedReader brPw = new BufferedReader(new FileReader("pw.txt"));
             BufferedReader brName = new BufferedReader(new FileReader("name.txt"))) {

            String idLine;
            String pwLine;
            String nameLine;

            while ((idLine = brId.readLine()) != null && 
                   (pwLine = brPw.readLine()) != null && 
                   (nameLine = brName.readLine()) != null) {
                
                User user = new User(idLine, pwLine, nameLine);
                userList.add(user);
            }

        } catch (IOException e) {
            System.out.println("[오류] 회원 정보를 읽어오는 중 문제가 발생했습니다: " + e.getMessage());
            return;
        }
        
//		TODO 2: User 리스트를 탐색하며 일치하는 사용자 탐색 (과정)
        for (User user : userList) {
            if (user.getId().equals(inputId)) {
                idFound = true;

                if (user.getPassword().equals(inputPw)) {
                    System.out.println(user.getName() + "님, 회원탈퇴 하시겠습니까? (yes/no)");
                    String choice = sc.nextLine().toLowerCase();

                    if (choice.equals("yes")) {
                        // 향상된 for문 안에서 리스트 요소를 즉시 삭제하면 
                    	// 오류(ConcurrentModificationException)가 발생할 수 있으므로
                    	//삭제할 타겟 객체만 지정해두고 반복문을 빠져나옵니다.
                        targetUser = user;
                        System.out.println("회원 데이터 삭제를 진행합니다...");
                    } else {
                        System.out.println("회원 탈퇴를 취소합니다. 메인 메뉴로 돌아갑니다.");
                        return; 
                    }
                } else {
                    System.out.println("비밀번호가 다릅니다.");
                    return; 
                }
                break;
            }
        }

        if (!idFound) {
            System.out.println("존재하지 않는 ID입니다.");
            return;
        }

        // 반복문 종료 후, 타겟 객체가 존재한다면 리스트에서 객체 통째로 삭제
        if (targetUser != null) {
            userList.remove(targetUser);
//          [Q3] 인덱스가 아닌 객체를 리스트에서 직접 제거하는 방식으로 변경
        }

//      TODO 3: 삭제 처리된 유저값을 3개의 파일로 나누어 저장하기
//      [Q4] try with resource 하나로 3개의 BufferedWriter 를 통시에 연결
        if (targetUser != null) {
            try (BufferedWriter bwId = new BufferedWriter(new FileWriter("id.txt"));
                 BufferedWriter bwPw = new BufferedWriter(new FileWriter("pw.txt"));
                 BufferedWriter bwName = new BufferedWriter(new FileWriter("name.txt"))) {

                for (User user : userList) {
                    bwId.write(user.getId());
                    bwId.newLine();
                    
                    bwPw.write(user.getPassword());
                    bwPw.newLine();
                    
                    bwName.write(user.getName());
                    bwName.newLine();
                }
                System.out.println("회원 탈퇴 처리가 안전하게 완료되었습니다.");

            } catch (IOException e) {
                System.out.println("[오류] 파일을 업데이트하는 중 문제가 발생했습니다: " + e.getMessage());
            }
        }
    }
}