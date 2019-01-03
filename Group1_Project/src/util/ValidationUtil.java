package util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ValidationUtil {
	public static boolean isValidEmailAddress(String emailAddress) {
		String expression = "^[\\w\\-]([\\.\\w])+[\\w]+@([\\w\\-]+\\.)+[A-Z]{2,4}$";
		CharSequence inputStr = emailAddress;
		Pattern pattern = Pattern.compile(expression, Pattern.CASE_INSENSITIVE);
		Matcher matcher = pattern.matcher(inputStr);
		return matcher.matches();

	}

	public static boolean isPhoneNumberValid(String phoneNumber) {
		boolean isValid = false;
		String expression = "^\\(?(\\d{3})\\)?[- ]?(\\d{3})[- ]?(\\d{4})$";
		CharSequence inputStr = phoneNumber;
		Pattern pattern = Pattern.compile(expression);
		Matcher matcher = pattern.matcher(inputStr);
		if (matcher.matches()) {
			isValid = true;
		}
		return isValid;
	}

	public static boolean isSSNValid(String ssn) {
		boolean isValid = false;

		// Initialize reg ex for SSN.
		String expression = "^\\d{3}[- ]?\\d{2}[- ]?\\d{4}$";
		CharSequence inputStr = ssn;
		Pattern pattern = Pattern.compile(expression);
		Matcher matcher = pattern.matcher(inputStr);
		if (matcher.matches()) {
			isValid = true;
		}
		return isValid;
	}

	public static boolean isNumeric(String number) {
		boolean isValid = false;
		// Initialize reg ex for numeric data.
		String expression = "^[-+]?[0-9]*\\.?[0-9]+$";
		CharSequence inputStr = number;
		Pattern pattern = Pattern.compile(expression);
		Matcher matcher = pattern.matcher(inputStr);
		if (matcher.matches()) {
			isValid = true;
		}
		return isValid;
	}

	public static boolean isValidName(String name) {
		if (name.matches("[a-zA-Z][a-zA-Z-_]*?[a-zA-Z]+$")) {
			return true;
		} else {
			return false;
		}
	}

	public static boolean isPositiveNumber(String number) {
		boolean isValid = false;
		// Initialize reg ex for numeric data.
		String expression = "^[+]?[0-9]*\\.?[0-9]+$";
		CharSequence inputStr = number;
		Pattern pattern = Pattern.compile(expression);
		Matcher matcher = pattern.matcher(inputStr);
		if (matcher.matches()) {
			isValid = true;
		}
		return isValid;
	}

	public static boolean isValidPassword(String password){
		boolean isValid = false;

		boolean digitFound =false;
		boolean letterFound =false;
		int index =0;
		while (!isValid && index < password.length()){
			Character c = password.charAt(index);
			if(Character.isDigit(c)){
				digitFound = true;
			}
			if(Character.isLetter(c)){
				letterFound = true;
			}

			isValid = letterFound && digitFound;

			index ++;
		}

		return isValid;
	}

	public static void main(String[] args){
		System.out.println(isValidPassword("ASDF"));
		System.out.println(isValidPassword("ASDFSDF"));
		System.out.println(isValidPassword("234234"));
		System.out.println(isValidPassword("WINDOW98"));
		System.out.println(isValidPassword("ASDDF9898"));
		System.out.println(isValidPassword("fffffff)(*)(*)(&^%$#$%^&88877"));
		System.out.println(isValidPassword("FFFFfff)(*)(*)(&^%$#$%^&88877"));
	}
}
