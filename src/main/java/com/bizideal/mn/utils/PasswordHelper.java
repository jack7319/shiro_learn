package com.bizideal.mn.utils;

import org.apache.shiro.crypto.RandomNumberGenerator;
import org.apache.shiro.crypto.SecureRandomNumberGenerator;
import org.apache.shiro.crypto.hash.Md5Hash;

public class PasswordHelper {

	public static RandomNumberGenerator randomNumberGenerator = new SecureRandomNumberGenerator();
	public static String algorithmName = "md5";
	public static int hashIterations = 3;
	public static String salt = "liulq";

	public static String encryptPassword(String pwd) {

		Md5Hash md5Hash = new Md5Hash("user", null, 3);// 不设置盐值，只设置加密两次

		String hashPwd = md5Hash.toString();// 经过加密以后的密码

		return hashPwd;

	}

	public static void main(String[] args) {
		Md5Hash md5Hash = new Md5Hash("liulq", null, 3);// 不设置盐值，只设置加密两次

		String hashPwd = md5Hash.toString();// 经过加密以后的密码

		System.out.println(hashPwd);
	}

}
