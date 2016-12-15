package com.test.device;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.regex.Pattern;

/**
 * 瀛楃涓叉搷浣滃伐鍏峰寘
 * 
 * @author liux (http://my.oschina.net/liux)
 * @version 1.0
 * @created 2012-3-21
 */
public class StringUtils {
	private final static Pattern emailer = Pattern
			.compile("\\w+([-+.]\\w+)*@\\w+([-.]\\w+)*\\.\\w+([-.]\\w+)*");
	// private final static SimpleDateFormat dateFormater = new
	// SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	// private final static SimpleDateFormat dateFormater2 = new
	// SimpleDateFormat("yyyy-MM-dd");

	private final static ThreadLocal<SimpleDateFormat> dateFormater = new ThreadLocal<SimpleDateFormat>() {
		@Override
		protected SimpleDateFormat initialValue() {
			return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		}
	};

	private final static ThreadLocal<SimpleDateFormat> dateFormater2 = new ThreadLocal<SimpleDateFormat>() {
		@Override
		protected SimpleDateFormat initialValue() {
			return new SimpleDateFormat("yyyy-MM-dd");
		}
	};

	/**
	 * 灏嗗瓧绗︿覆杞綅鏃ユ湡绫诲瀷
	 * 
	 * @param sdate
	 * @return
	 */
	public static Date toDate(String sdate) {
		try {
			return dateFormater.get().parse(sdate);
		} catch (ParseException e) {
			return null;
		}
	}

	/**
	 * 浠ュ弸濂界殑鏂瑰紡鏄剧ず鏃堕棿
	 * 
	 * @param sdate
	 * @return
	 */
	public static String friendly_time(String sdate) {
		Date time = toDate(sdate);
		if (time == null) {
			return "Unknown";
		}
		String ftime = "";
		Calendar cal = Calendar.getInstance();

		// 鍒ゆ柇鏄惁鏄悓涓�澶�
		String curDate = dateFormater2.get().format(cal.getTime());
		String paramDate = dateFormater2.get().format(time);
		if (curDate.equals(paramDate)) {
			int hour = (int) ((cal.getTimeInMillis() - time.getTime()) / 3600000);
			if (hour == 0)
				ftime = Math.max(
						(cal.getTimeInMillis() - time.getTime()) / 60000, 1)
						+ "鍒嗛挓鍓�";
			else
				ftime = hour + "灏忔椂鍓�";
			return ftime;
		}

		long lt = time.getTime() / 86400000;
		long ct = cal.getTimeInMillis() / 86400000;
		int days = (int) (ct - lt);
		if (days == 0) {
			int hour = (int) ((cal.getTimeInMillis() - time.getTime()) / 3600000);
			if (hour == 0)
				ftime = Math.max(
						(cal.getTimeInMillis() - time.getTime()) / 60000, 1)
						+ "鍒嗛挓鍓�";
			else
				ftime = hour + "灏忔椂鍓�";
		} else if (days == 1) {
			ftime = "鏄ㄥぉ";
		} else if (days == 2) {
			ftime = "鍓嶅ぉ";
		} else if (days > 2 && days <= 10) {
			ftime = days + "澶╁墠";
		} else if (days > 10) {
			ftime = dateFormater2.get().format(time);
		}
		return ftime;
	}
	
	/**
	 * 褰撳墠鏃堕棿绮剧‘鍒版绉掓暟
	 * 
	 * @param sdate
	 * @return boolean
	 */
	public static String longdate(){
		
		SimpleDateFormat formatter = new SimpleDateFormat("dd-MMM-yyyy HH:mm:ss:SSS");
		return formatter.format(new Date());
	}
	
	/**
	 * 褰撳墠鏃堕棿绮剧‘鍒版绉掓暟骞惰浆鍖栦负date
	 * 
	 * @param sdate
	 * @return boolean
	 */
	public static Date longdate2(){
		Date time =null;
		SimpleDateFormat formatter = new SimpleDateFormat("dd-MMM-yyyy HH:mm:ss:SSS");
		try {
			   time= formatter.parse(formatter.format(new Date()));
			} catch (ParseException e) {
		}
		return time;
	}
	/**
	 * 鍒ゆ柇缁欏畾瀛楃涓叉椂闂存槸鍚︿负浠婃棩
	 * 
	 * @param sdate
	 * @return boolean
	 */
	public static boolean isToday(String sdate) {
		boolean b = false;
		Date time = toDate(sdate);
		Date today = new Date();
		if (time != null) {
			String nowDate = dateFormater2.get().format(today);
			String timeDate = dateFormater2.get().format(time);
			if (nowDate.equals(timeDate)) {
				b = true;
			}
		}
		return b;
	}
	/**
	 * 鍒ゆ柇String鏁扮粍鏄惁涓虹┖
	 * 
	 * @param sdate
	 * @return boolean
	 */
	public static boolean isNull(String [] v){
		   for(int i = 0;i<5;i++)
		     if (v[i] != null)
		       return false;
		   return true;
		}
	
	/**
	 * 鍒ゆ柇缁欏畾瀛楃涓叉槸鍚︾┖鐧戒覆銆� 绌虹櫧涓叉槸鎸囩敱绌烘牸銆佸埗琛ㄧ銆佸洖杞︾銆佹崲琛岀缁勬垚鐨勫瓧绗︿覆 鑻ヨ緭鍏ュ瓧绗︿覆涓簄ull鎴栫┖瀛楃涓诧紝杩斿洖true
	 * 
	 * @param input
	 * @return boolean
	 */
	public static boolean isEmpty(String input) {
		if (input == null || "".equals(input))
			return true;

		for (int i = 0; i < input.length(); i++) {
			char c = input.charAt(i);
			if (c != ' ' && c != '\t' && c != '\r' && c != '\n') {
				return false;
			}
		}
		return true;
	}

	/**
	 * 鍒ゆ柇鏄笉鏄竴涓悎娉曠殑鐢靛瓙閭欢鍦板潃
	 * 
	 * @param email
	 * @return
	 */
	public static boolean isEmail(String email) {
		if (email == null || email.trim().length() == 0)
			return false;
		return emailer.matcher(email).matches();
	}

	/**
	 * 瀛楃涓茶浆鏁存暟
	 * 
	 * @param str
	 * @param defValue
	 * @return
	 */
	public static int toInt(String str, int defValue) {
		try {
			return Integer.parseInt(str);
		} catch (Exception e) {
		}
		return defValue;
	}

	/**
	 * 瀵硅薄杞暣鏁�
	 * 
	 * @param obj
	 * @return 杞崲寮傚父杩斿洖 0
	 */
	public static int toInt(Object obj) {
		if (obj == null)
			return 0;
		return toInt(obj.toString(), 0);
	}

	/**
	 * 瀵硅薄杞暣鏁�
	 * 
	 * @param obj
	 * @return 杞崲寮傚父杩斿洖 0
	 */
	public static long toLong(String obj) {
		try {
			return Long.parseLong(obj);
		} catch (Exception e) {
		}
		return 0;
	}

	/**
	 * 瀛楃涓茶浆甯冨皵鍊�
	 * 
	 * @param b
	 * @return 杞崲寮傚父杩斿洖 false
	 */
	public static boolean toBool(String b) {
		try {
			return Boolean.parseBoolean(b);
		} catch (Exception e) {
		}
		return false;
	}

	/**
	 * 灏嗗瓧绗︿覆涓棿鐢ㄦ槦鍙烽殣钘�
	 * 
	 * @param start
	 *            浠庡摢涓�浣嶅紑濮嬮殣钘�
	 * @param end
	 *            鏈�鍚庡墿鍑犱綅
	 * 
	 */
	public static String hideString(String str, int start, int end) {
		if(start<0 || end<0){
			return str;
		}else if ((start + end) > str.length()) {
			return str;
		} else {
			int length = str.length();
			String strStart = str.substring(0, start);
			String strMid = str.substring(start, length - end);
			String strEnd = str.substring(start + strMid.length(), length);
			StringBuffer s = new StringBuffer();
			for (int i = 0; i < strMid.length(); i++) {
				s.append("*");
			}
			return strStart + s.toString() + strEnd;
		}
	}
	/**
	 * 鍒ゆ柇璋冪敤toString鏂规硶鐨勫璞℃槸涓嶆槸null锛岄伩鍏嶉�犳垚绌烘寚閽堝紓甯�
	 * @param 浼犻�掔殑鍙傛暟
	 * @return 濡傛灉浼犻�掔殑鍙傛暟鏄痭ull锛岃繑鍥炵┖涓诧紝鍚﹀垯杩斿洖obj.toString()
	 * 
	 * null鍙互鐞嗚В涓哄師濮嬬被鍨嬭嚦浜庡彲浠ユ妸null浣滀负鍙傛暟鍙槸鐗规畩瑙勫畾
	 * 
	 */
	public static String object2String(Object obj){
		String str = obj == null ? "" : obj.toString();
		return str ;
	}
}
