package com.fhmou.utils.poi.convert;

/**
 * long类型转换器
 */
public class LongConvert implements IValueConvert<Long> {

	@Override
	public Long parse(String obj) {
		if (obj.contains(".")) {
			obj = obj.substring(0, obj.indexOf("."));
		}
		return Long.parseLong(obj);
	}
}