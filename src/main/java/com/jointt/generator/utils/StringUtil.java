package com.jointt.generator.utils;

import org.apache.commons.lang3.StringUtils;

public class StringUtil extends StringUtils {

	public static boolean isNullOrEmpty(Object obj) {
		return obj == null || "".equals(obj.toString());
	}

	/**
	 * 功能 将英文字符串首字母转为大写
	 * 
	 * @param str
	 *            要转换的字符串
	 * @return String 型值
	 */
	public static String toUpCaseFirst(String str) {
		if (str == null || "".equals(str)) {
			return str;
		} else {
			char[] temp = str.toCharArray();
			temp[0] = str.toUpperCase().toCharArray()[0];
			str = String.valueOf(temp);
		}

		return str;
	}

	/**
	 * 功能 将英文字符串首字母转为小写
	 * 
	 * @param str
	 *            要转换的字符串
	 * @return String 型值
	 */
	public static String toLowCaseFirst(String str) {
		if (str == null || "".equals(str)) {
			return str;
		} else {
			char[] temp = str.toCharArray();
			temp[0] = str.toLowerCase().toCharArray()[0];
			str = String.valueOf(temp);
		}
		return str;
	}

	/**
	 * 批量将英文字符串首字母转为大写
	 * 
	 * @param str
	 *            要转换的字符串数组
	 * @return 字符数组
	 */
	public static String[] toUpCaseFirst(String[] str) {
		if (str == null || str.length == 0) {
			return str;
		} else {
			String[] result = new String[str.length];
			for (int i = 0; i < result.length; ++i) {
				result[i] = StringUtil.toUpCaseFirst(str[i]);
			}

			return result;
		}
	}

	/**
	 * 批量将英文字符串首字母转为小写
	 * 
	 * @param str
	 *            要转换的字符串数组
	 * @return 字符数组
	 */
	public static String[] toLowCaseFirst(String[] str) {
		if (str == null || str.length == 0) {
			return str;
		} else {
			String[] result = new String[str.length];
			for (int i = 0; i < result.length; ++i) {
				result[i] = StringUtil.toLowCaseFirst(str[i]);
			}

			return result;
		}
	}

	/**
	 * 首字母大写，其他的小写
	 * 
	 * @param sqlName
	 * @return
	 */
	public static String makeAllWordFirstLetterUpperCase(String sqlName) {
		String[] strs = sqlName.toLowerCase().split("_");
		String result = "";
		String preStr = "";
		for (int i = 0; i < strs.length; i++) {
			if (preStr.length() == 1) {
				result += strs[i];
			} else {
				result += capitalize(strs[i]);
			}
			preStr = strs[i];
		}
		return result;
	}

	/**
	 * 替换
	 * 
	 * @param inString
	 * @param oldPattern
	 * @param newPattern
	 * @return
	 */
	public static String replace(String inString, String oldPattern, String newPattern) {
		if (inString == null) {
			return null;
		}
		if (oldPattern == null || newPattern == null) {
			return inString;
		}

		StringBuffer sbuf = new StringBuffer();
		int pos = 0;
		int index = inString.indexOf(oldPattern);
		int patLen = oldPattern.length();
		while (index >= 0) {
			sbuf.append(inString.substring(pos, index));
			sbuf.append(newPattern);
			pos = index + patLen;
			index = inString.indexOf(oldPattern, pos);
		}
		sbuf.append(inString.substring(pos));

		return sbuf.toString();
	}

	public static String capitalize(String str) {
		return changeFirstCharacterCase(str, true);
	}

	public static String uncapitalize(String str) {
		return changeFirstCharacterCase(str, false);
	}

	/** copy from spring */
	private static String changeFirstCharacterCase(String str, boolean capitalize) {
		if (str == null || str.length() == 0) {
			return str;
		}
		StringBuffer buf = new StringBuffer(str.length());
		if (capitalize) {
			buf.append(Character.toUpperCase(str.charAt(0)));
		} else {
			buf.append(Character.toLowerCase(str.charAt(0)));
		}
		buf.append(str.substring(1));
		return buf.toString();
	}
}
