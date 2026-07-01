package com.project.controller;

import com.project.model.User;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

public class UserController {
    //필드부: 파일에서 읽어온 User 객체들을 담아두는 리스트
    private ArrayList<User> userList = new ArrayList<>();

    //컨트롤러 생성 시 자동으로 파일에서 기존 유저 정보를 읽어옵니다.
    public UserController() {
        loadUsers();
    }
//
    //[userList] 파일 3개에 각각의 값을 저장, 하나의 배열로 연결하여, 등록된 유저의 경우 id 를 소실하였어도 비밀번호가 맞으면 로그인이 허용됨
    private void loadUsers() {
        userList.clear();
        try (BufferedReader br = new BufferedReader(new FileReader("users.txt"))) {
            String line;

            // 파일에서 한 줄씩 읽어옵니다. (예: "a,b123,cCc")
            while ((line = br.readLine()) != null) {

                // 쉼표(,)를 기준으로 문자열을 잘라서 배열에 넣습니다.
                // data[0] = "a", data[1] = "b123", data[2] = "cCc"
                String[] inputData = line.split(",");

                // 데이터가 정상적으로 3개(id, password, name) 존재하는 경우에만 객체 생성
                if (inputData.length == 3) {
                    String inputId = inputData[0];
                    String inputPw = inputData[1];
                    String inputName = inputData[2];

                    userList.add(new User(inputId, inputPw, inputName));
                }
            }
        } catch (IOException e) {
            // 최초 실행 시 파일이 없을 수 있으므로 예외 무시 (가입 시 파일 생성)
        }
    }

    //[내부 메서드] 리스트에 있는 모든 유저 정보를 파일에 덮어쓰기 (비번 변경, 탈퇴 시 사용)
    private void saveAllUsers() {
    	// 덮어쓰기
        try (BufferedWriter bw = new BufferedWriter(new FileWriter("users.txt"))) {

            for (User user : userList) {
                // 메모리(userList)에 있는 각 유저의 정보를 쉼표(,)로 연결하여 한 줄로 만듭니다.
                String lineToSave = user.getId() + "," + user.getPassword() + "," + user.getName();
                
                // 파일에 기록하고 줄바꿈을 합니다.
                bw.write(lineToSave);
                bw.newLine();
            }
        } catch (IOException e) {
            System.out.println("[오류] 파일을 업데이트하는 중 문제가 발생했습니다: " + e.getMessage());
        }
    }

//    [1-1] 회원가입: ID 중복 검사
    public boolean isDuplicateId(String inputId) {
        for (User user : userList) {
            if (user.getId().equals(inputId)) {
                return true; // 중복됨
            }
        }
        return false; // 중복 안됨 (사용 가능)
    }
//  [1-2] 회원가입: 새로운 유저 데이터 리스트 및 파일에 추가 (C)
    public void signUp(String id, String pw, String name) {
        User newUser = new User(id, pw, name);
        userList.add(newUser); // 메모리 리스트에 추가

        // 파일에는 단일 파일(users.txt)에 누적 저장 (true)
        try (BufferedWriter bw = new BufferedWriter(new FileWriter("users.txt", true))) {

            // ID, 비밀번호, 이름을 쉼표(,)로 연결하여 하나의 문자열로 만듭니다.
            String lineToSave = newUser.getId() + "," + newUser.getPassword() + "," + newUser.getName();

            bw.write(lineToSave);  // 쉼표로 연결된 한 줄의 데이터를 파일에 씁니다.
            bw.newLine();          // 다음 사람의 데이터를 위해 줄바꿈을 추가합니다.

            System.out.println("회원가입이 완료되었습니다!");
        } catch (IOException e) {
            System.out.println("파일 저장 중 오류가 발생했습니다: " + e.getMessage());
        }
    }
    
//  [2~4] 공통: 유저 아이디와 비밀번호 검증 (R)
    public User authenticateUser(String id, String pw) {
        for (User user : userList) {
            if (user.getId().equals(id)) {
                if (user.getPassword().equals(pw)) {
                    return user; 							// 로그인 검증 성공 시 해당 User 객체 반환
                } else {
                    System.out.println("비밀번호가 다릅니다.");
                    return null;
                }
            }
        }
        System.out.println("존재하지 않는 ID입니다.");
        return null;
    }
    
//  [3] 비밀번호 변경 (U)
    public void changePassword(User targetUser, String newPw) {
        targetUser.setPassword(newPw); // 객체 비번 수정
        System.out.println("비밀번호를 성공적으로 변경 중입니다...");
        saveAllUsers(); // 수정된 리스트 전체를 파일에 덮어쓰기
        System.out.println("비밀번호 변경 파일 저장이 완료되었습니다!");
    }
    
//  [4] 회원 탈퇴 (D)
    public void deleteUser(User targetUser) {
        System.out.println("회원 데이터 삭제를 진행합니다...");
        userList.remove(targetUser); // 리스트에서 제거
        saveAllUsers(); // 남은 회원 목록 전체를 파일에 덮어쓰기
        System.out.println("회원 탈퇴 처리가 안전하게 완료되었습니다.");
    }
}