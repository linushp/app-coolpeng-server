package com.coolpeng.framework.utils;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.Writer;
import java.nio.charset.Charset;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class StringUtils
{
	public static final String UTF8 = "UTF-8";
	public static final Charset CHARTSET_UTF8 = Charset.forName("UTF-8");
	public static final char UNDERLINE = '_';

	public static String maxSize(String str, int maxSize)
	{
		if (str == null) {
			return str;
		}
		if (str.length() > maxSize) {
			return new StringBuilder().append(str.substring(0, maxSize - 4)).append("...").toString();
		}
		return str;
	}

	public static String camelToUnderline(String param)
	{
		if ((param == null) || ("".equals(param.trim()))) {
			return "";
		}
		int len = param.length();
		StringBuilder sb = new StringBuilder(len);
		for (int i = 0; i < len; i++) {
			char c = param.charAt(i);
			if (Character.isUpperCase(c)) {
				sb.append('_');
				sb.append(Character.toLowerCase(c));
			} else {
				sb.append(c);
			}
		}
		return sb.toString();
	}

	public static String underlineToCamel(String param) {
		if ((param == null) || ("".equals(param.trim()))) {
			return "";
		}
		int len = param.length();
		StringBuilder sb = new StringBuilder(len);
		for (int i = 0; i < len; i++) {
			char c = param.charAt(i);
			if (c == '_') {
				i++; if (i < len)
					sb.append(Character.toUpperCase(param.charAt(i)));
			}
			else {
				sb.append(c);
			}
		}
		return sb.toString();
	}

	public static String underlineToCamel2(String param) {
		if ((param == null) || ("".equals(param.trim()))) {
			return "";
		}
		StringBuilder sb = new StringBuilder(param);
		Matcher mc = Pattern.compile("_").matcher(param);
		int i = 0;
		while (mc.find()) {
			int position = mc.end() - i++;

			sb.replace(position - 1, position + 1, sb.substring(position, position + 1).toUpperCase());
		}
		return sb.toString();
	}

	public static String htmlEncode(String message)
	{
		if (message == null) {
			return null;
		}
		char[] content = new char[message.length()];
		message.getChars(0, message.length(), content, 0);
		StringBuilder result = new StringBuilder(content.length + 50);
		for (int i = 0; i < content.length; i++) {
			switch (content[i]) {
				case '<':
					result.append("&lt;");
					break;
				case '>':
					result.append("&gt;");
					break;
				case '&':
					result.append("&amp;");
					break;
				case '"':
					result.append("&quot;");
					break;
				default:
					result.append(content[i]);
			}
		}
		return result.toString();
	}

	public static String getStackTrace(Throwable aThrowable)
	{
		Writer result = new StringWriter();
		PrintWriter printWriter = new PrintWriter(result);
		aThrowable.printStackTrace(printWriter);
		return result.toString();
	}

	public static boolean isNotBlank(String cs) {
		return !isBlank(cs);
	}

	public static boolean isBlank(CharSequence cs)
	{
		int strLen;
		if ((cs == null) || ((strLen = cs.length()) == 0))
			return true;

		for (int i = 0; i < strLen; i++) {
			if (!Character.isWhitespace(cs.charAt(i))) {
				return false;
			}
		}
		return true;
	}
}