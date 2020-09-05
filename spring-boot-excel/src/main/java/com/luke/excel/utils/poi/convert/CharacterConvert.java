package com.luke.excel.utils.poi.convert;

import java.util.regex.Pattern;

/**
 * char类型转换器
 */
public class CharacterConvert implements IValueConvert<Character> {
	@Override
	public Character parse(String obj) {
		if (Pattern.matches("^\\d+$", obj)) {
			return (char) Integer.parseInt(obj);
		}
		return obj.length() == 1 ? obj.charAt(0) : 0;
	}
}