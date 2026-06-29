package com.project.controller;

import com.project.model.User;

public interface FileManageable {
//	파일 입출력
	public void loadFromFile();
	public void saveUserToFile(User user);
}