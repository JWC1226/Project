package com.project.controller;

import com.project.model.User;

public interface MembershipService {
	public void registerUser (User newUser);
	User searchUser (String id);
	public void updateUser(User user);
	public void deleteUser(String id);
}