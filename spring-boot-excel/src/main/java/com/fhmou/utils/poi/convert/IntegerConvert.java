package com.fhmou.utils.poi.convert;

/**
 * int类型转换器
 */
public class IntegerConvert implements IValueConvert<Integer> {

	@Override
	public Integer parse(String obj) {
		if (obj.contains(".")) {
			obj = obj.substring(0, obj.indexOf("."));
		}
		return Integer.parseInt(obj);
	}
}