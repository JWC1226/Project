package com.project.view;

import java.util.Scanner;

public class PurchaseView extends BaseView {
//	구매 및 결제 수단 관리 화면
	public void purchase() {
		Scanner sc = new Scanner(System.in);
		int totalPrice = 0;		//가격 총 합산
		int totalCount = 0;		//구매한 메뉴 개수
		
		while (true) {
			System.out.println("\n=== [메뉴 카테고리] ===");
			System.out.println("현재 담은 금액: " + totalPrice + "원 (총 " + totalCount + "개)");
            System.out.println("[1] 커피");
            System.out.println("[2] 논커피 & 블랜디드");
            System.out.println("[3] 브레드 & 디저트");
            System.out.println("[0] 결제 진행하기");
            System.out.println("[9] 주문 취소");
            System.out.print("카테고리 선택: ");
            int category = sc.nextInt();
            
        if (category == 0) {
        	if (totalCount == 0) {
        		System.out.println("담은 메뉴가 없습니다. 메인으로 돌아갑니다.");
        		return;
        	}
        	break;		//루프를 빠져나가 결제 단계로 이동
        } else if (category == 9) {
        	System.out.println("주문을 취소합니다");
        	return;
        }

        // 단순화를 위해 switch 문으로 메뉴 리스트업
        switch (category) {
            case 1:
                System.out.println("\n--- [커피] ---");
                System.out.println("1. 아메리카노 (3,200원)");
                System.out.println("2. 카페 모카 (4,500원)");
                System.out.println("3. 카푸치노 (4,200원)");
                System.out.println("4. 아포가토 (4,700원)");
                System.out.print("메뉴 번호 선택: ");
                	int coffeeSel = sc.nextInt();
            	if (coffeeSel == 1) { totalPrice += 3200; totalCount++; }
                else if (coffeeSel == 2) { totalPrice += 4500; totalCount++; }
                else if (coffeeSel == 3) { totalPrice += 4200; totalCount++; }
                else if (coffeeSel == 4) { totalPrice += 4700; totalCount++; }
                break;
            case 2:
                System.out.println("\n--- [논커피 & 블랜디드] ---");
                System.out.println("1. 우베 코코넛 (4,500원)");
                System.out.println("2. 말차 초코 라떼 (4,700원)");
                System.out.println("3. 달고나 라떼 (4,200원)");
                System.out.println("4. 말차 초코 쉐이크 (5,400원)");
                System.out.println("5. 초콜릿칩 플랫치노 (4,700원)");
                System.out.print("메뉴 번호 선택: ");
                    int nonCoffeeSel = sc.nextInt();
                if (nonCoffeeSel == 1) { totalPrice += 4500; totalCount++; }
                else if (nonCoffeeSel == 2) { totalPrice += 4700; totalCount++; }
                else if (nonCoffeeSel == 3) { totalPrice += 4200; totalCount++; }
                else if (nonCoffeeSel == 4) { totalPrice += 5400; totalCount++; }
                else if (nonCoffeeSel == 5) { totalPrice += 4700; totalCount++; }
                break;
            case 3:
                System.out.println("\n--- [브레드 & 디저트] ---");
                System.out.println("1. 허니 카라멜 브레드 (5,600원)");
                System.out.println("2. 크로크무슈 (4,800원)");
                System.out.println("3. 바스크 치즈케이크 (5,600원)");
                System.out.println("4. 티라미슈 케이크 (5,200원)");
                System.out.println("5. 수플레 치즈 조각케이크 (5,000원)");
                System.out.print("메뉴 번호 선택: ");
                    int dessertSel = sc.nextInt();
                if (dessertSel == 1) { totalPrice += 5600; totalCount++; }
                else if (dessertSel == 2) { totalPrice += 4800; totalCount++; }
                else if (dessertSel == 3) { totalPrice += 5600; totalCount++; }
                else if (dessertSel == 4) { totalPrice += 5200; totalCount++; }
                else if (dessertSel == 5) { totalPrice += 5000; totalCount++; }
                break;
            default:
                System.out.println("잘못된 입력입니다.");
        	}	//switch 문 종료
        }		//while  문 종료
		
	
				System.out.println("\n=== [주문/결제창] ===");
				System.out.println("총 결제 금액(원): " + totalPrice);
				System.out.println("\n--- [결제 수단 변경] ---");
		        System.out.println("1. (포인트 3% 적립) 현금");
		        System.out.println("2. (포인트 1% 적립) 신용카드");
		        System.out.println("3. (포인트 2% 적립) 모바일 페이");
		        System.out.println("4. (포인트 5% 적립) 포인트 충전 결제");
				System.out.print("번호 선택: ");
					int payMethod = sc.nextInt();
					sc.nextLine(); // 버퍼 비우기
    
			currentUser.setPaymentMethod(payMethod);
			// 컨트롤러로 결제 처리 위임 (User 객체와 총 금액만 넘김)
			boolean isSuccess = controller.processPurchase(currentUser, totalPrice, payMethod);
		        if (!isSuccess) {
		            System.out.println("\n[안내] 결제가 정상 처리되지 않아 메인 메뉴로 이동합니다.");
		            System.out.println("금액 부족 충전이나 등급 확인은 [3. 회원 정보 조회]를 이용해 주세요.");
		            // 프로그램이 강제 종료되지 않고 안전하게 메서드가 끝납니다.
		        }
			}	//purchase 문 종료
		
//		결제 수단 선택 및 진행
		public void changePayment() {
	        System.out.println("\n--- [결제 수단 변경] ---");
	        System.out.println("1. 현금 (포인트 3% 적립)");
	        System.out.println("2. 신용 카드 (포인트 1% 적립)");
	        System.out.println("3. 체크 카드 (포인트 2% 적립)");
	        System.out.println("4. 간편 결제 (포인트 5% 적립)");
	        System.out.print("선택: ");
	        
	        int method = sc.nextInt();
	        sc.nextLine(); // 버퍼 비우기

        if (method >= 1 && method <= 4) {
            currentUser.setPaymentMethod(method);
            controller.updateUser(currentUser); 
            System.out.println("결제 수단이 변경되었습니다.\n");
        } else {
            System.out.println("잘못된 번호입니다.\n");
        }
    }
}