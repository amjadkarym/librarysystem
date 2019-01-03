package util;

import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class Util {


	public static boolean isExists(Boolean bool) {
		return bool != null;
	}

	public static boolean isExists(byte[] arr) {
		return arr != null && arr.length > 0;
	}

	public static boolean isExists(String str) {
		return str != null && !str.isEmpty();
	}
	public static boolean isExists(Integer input) {
		return input != null;
	}
	public static boolean isExists(Long input) {
		return input != null;
	}
	public static boolean isExists(Double input) {
		return input != null;
	}

	public static boolean isExists(List<?> list) {
		return list != null && !list.isEmpty();
	}

	public static boolean isExists(Set<?> set) {
		return set != null && !set.isEmpty();
	}

	public static boolean isExists(Map<?, ?> map) {
		return map != null && !map.isEmpty();
	}

	public static boolean isExists(StringBuilder sb) {
		return sb != null && sb.length() > 0;
	}

	public static Date currentDatePlusDays(int days) {
		Date dueDate = new Date();
		Calendar c = Calendar.getInstance(); 
		c.setTime(dueDate); 
		c.add(Calendar.DATE, days);
		dueDate = c.getTime();
		return dueDate;
	}

}
