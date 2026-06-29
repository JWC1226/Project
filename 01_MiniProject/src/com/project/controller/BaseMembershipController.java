package com.project.controller;

import com.project.model.Membership;
import com.project.model.User;

public abstract class BaseMembershipController implements MembershipService {
//	추상 클래스: 배열 관리 기본 기능 구현
	protected User[] userList = new User[10];					//10: 사용자 최대 할당값
	protected int userCount = 0;
	protected Membership[] gradePolicy = new Membership[5];		//멤버십 등급 5개
	
//	CRUD 인터페이스 구현
	@Override
	public void registerUser(User newUser) {			//[C] create
		if (userCount < userList.length) {
			userList[userCount] = newUser;
			userCount++;
		}
	}
	@Override
	public User searchUser(String id) {					//[R] read
		for(int i = 0; i < userCount; i++) {
			if (userList[i].getId().equals(id)) {
				return userList[i];
			}
		}
		return null;
	}
	@Override
	public void deleteUser(String id) {					//[D] delete
		for (int i = 0; i < userCount; i++) {
			if (userList[i].getId().equals(id)) {		
				for (int j = i; j < userCount-1; j++) {	//삭제할 유저 뒤의 데이터를 앞으로 당김
					userList[j] = userList[j+1];
				}
				userList[userCount-1] = null;
				userCount--;
				return;
			}
		}
	}
}