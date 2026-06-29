package com.project.controller;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import com.project.model.Membership;
import com.project.model.User;

public class MembershipController extends BaseMembershipController implements FileManageable {
	public MembershipController() {
		loadFromFile();		//policy 라고 정책 기능을 로드함
	}
//	
	//오버라이딩을 통한 회원가입 시 파일 저장 기능 추가
	@Override
	public void registerUser(User newUser) {
		super.registerUser(newUser);
		saveUserToFile(newUser);
	}
//	
	@Override
	public void updateUser(User user) {
		saveUserToFile(user);
	}
//	
	//반환 타입을 integer 에서 boolean 으로 변경하여 성공 / 실패를 View 에 전송
	public boolean processPurchase(User user, int amount, int payMethod) { 
        try {
            user.setPaymentMethod(payMethod);

            //[안전장치1] 등급 정책을 못 찾을 경우를 대비한 예외 처리
            Membership currentMem = getMembershipPolicy(user.getCurrentGrade());
            if (currentMem == null) {
                System.out.println("[시스템 오류] 유저의 등급 정책을 찾을 수 없습니다");
                return false; 
            }

            //할인율 적용된 최종 금액
            int finalPrice = (int)(amount * (1.0 - currentMem.getDiscountRate()));

            //보유 금액 검사
            if (user.getMoney() < finalPrice) {
                System.out.println("\n[결제 실패] 잔액이 부족합니다");
                System.out.println("필요 금액: " + finalPrice + "원 / 보유 금액: " + user.getMoney() + "원");
                return false; 
            }

            //포인트 적립율 계산
            double pointRate = 0.0;
            switch(payMethod) {
                case 1: pointRate = 0.03; break;
                case 2: pointRate = 0.01; break;
                case 3: pointRate = 0.02; break;
                case 4: pointRate = 0.05; break;
            }
            int earnedPoints = (int)(finalPrice * pointRate);

            //결제 확정 및 데이터 변경 (보유 금액 차감)
            user.setMoney(user.getMoney() - finalPrice); 
            user.setTotalSpent(user.getTotalSpent() + finalPrice);
            user.setPoints(user.getPoints() + earnedPoints);
            user.setPurchaseCount(user.getPurchaseCount() + 1); //누적 구매 횟수 증가값(itemCount), 구매 횟수 증가(1)

            System.out.println("\n[결제 완료] " + finalPrice + "원이 결제되었습니다 (적립: " + earnedPoints + "점)");
            System.out.println("남은 보유 금액: " + user.getMoney() + "원");

            checkAndUpgrade(user);
            updateUser(user);
            return true;			//결제 성공

        } catch (NullPointerException e) {
            System.out.println("\n[결제 오류] 데이터 참조 오류가 발생했습니다");
            return false;
        } catch (Exception e) {
            System.out.println("\n[결제 오류] 알 수 없는 문제가 발생하여 결제에 실패했습니다");
            return false;
        }
    }
//	누적 구매 횟수 기반 5단계 검사
	private void checkAndUpgrade(User user) {
		
		String oldGrade = user.getCurrentGrade();
        int counts = user.getPurchaseCount(); // 누적 금액 대신 구매 횟수 호출

        // gradePolicy 배열의 4, 3, 2, 1, 0 순서대로 가장 높은 조건부터 만족하는지 확인
        if (counts >= gradePolicy[4].getUpgradeCondition()) user.setCurrentGrade(gradePolicy[4].getGradeName());
        else if (counts >= gradePolicy[3].getUpgradeCondition()) user.setCurrentGrade(gradePolicy[3].getGradeName());
        else if (counts >= gradePolicy[2].getUpgradeCondition()) user.setCurrentGrade(gradePolicy[2].getGradeName());
        else if (counts >= gradePolicy[1].getUpgradeCondition()) user.setCurrentGrade(gradePolicy[1].getGradeName());
// Q 윗줄) 해당 부분을 수정해야 하는가?
        
        if (!oldGrade.equals(user.getCurrentGrade())) {
            System.out.println("★ 멤버십 등급이 [" + user.getCurrentGrade() + "]로 상승했습니다! ★");
        }
    }
//	
    public int getRemainingAmount(User user) {
        int spent = user.getTotalSpent();
        for (int i = 0; i < gradePolicy.length; i++) {
            if (spent < gradePolicy[i].getUpgradeCondition()) {
                return gradePolicy[i].getUpgradeCondition() - spent;
            }
        }
        return 0; 
    }
//	남은 구매 횟수 계산 로직
    private Membership getMembershipPolicy(String gradeName) {
        for (int i = 0; i < gradePolicy.length; i++) {
            if (gradePolicy[i] != null && gradePolicy[i].getGradeName().equals(gradeName)) {
                return gradePolicy[i];
            }
        }
        return gradePolicy[0]; 
    }
//	5단계 초기값 및 배열 크기 조정
    @Override
    public void loadFromFile() {
        File file = new File("membership.txt");
        if (!file.exists()) {
            try (BufferedWriter bw = new BufferedWriter(new FileWriter(file))) {
                bw.write("WELCOME,0.0,0\nSILVER,0.05,50000\nGOLD,0.1,100000\n");
            } catch (IOException e) {}
        }
        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            String line; int index = 0;
            while ((line = br.readLine()) != null && index < 3) {
                String[] data = line.split(",");
                gradePolicy[index++] = new Membership(data[0], Double.parseDouble(data[1]), Integer.parseInt(data[2]));
            }
        } catch (IOException e) { }
    }
//
    @Override
    public void saveUserToFile(User user) {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter("user_log.txt", true))) {
            bw.write("ID: " + user.getId() + " | 누적금액: " + user.getTotalSpent() + " | 등급: " + user.getCurrentGrade());
            bw.newLine();
        } catch (IOException e) {}
    }
//    
    public void deleteAccount(User user) {
    	String filePath = "users.txt"; 
        File inputFile = new File(filePath);
        File tempFile = new File("users_temp.txt"); // 삭제 처리를 위한 임시 파일
        // try-with-resources 문법으로 스트림이 자동으로 닫히도록 안전하게 처리
        try (BufferedReader reader = new BufferedReader(new FileReader(inputFile));
             BufferedWriter writer = new BufferedWriter(new FileWriter(tempFile))) {
            String currentLine;
            // 파일을 한 줄씩 읽어옴
            while ((currentLine = reader.readLine()) != null) {
                // 회원 정보가 저장된 텍스트의 구분자(쉼표 또는 공백 등)에 맞게 자릅니다.
                String[] userData = currentLine.split(","); 
                // 데이터가 존재하고, 첫 번째 항목(보통 ID)이 탈퇴하려는 유저의 ID와 같다면
                if (userData.length > 0 && userData[0].trim().equals(user.getId())) {
                    continue; // ★ 임시 파일에 쓰지 않고 패스 (즉, 데이터 삭제 효과)
                }
                // 탈퇴 유저가 아니라면 임시 파일에 그대로 기록
                writer.write(currentLine + System.lineSeparator());
            }
        } catch (IOException e) {
            System.out.println("[오류] 회원 탈퇴 처리 중 시스템 에러가 발생했습니다: " + e.getMessage());
            return;
        }
        // 3. 파일 교체 작업 (기존 파일 삭제 -> 임시 파일을 원본 파일명으로 변경)
        if (inputFile.delete()) {
            if (!tempFile.renameTo(inputFile)) {
                System.out.println("[시스템 오류] 임시 파일명을 변경할 수 없습니다.");
            } else {
                System.out.println("[시스템] 데이터베이스에서 회원 정보가 정상 삭제되었습니다.");
            }
        } else {
            System.out.println("[시스템 오류] 기존 회원 파일을 갱신할 수 없습니다. (파일 잠금 상태 확인 필요)");
        }
    }
}