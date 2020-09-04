package com.fhmou.utils.poi.convert;

/**
 * boolean类型转换器
 */
public class BooleanConvert implements IValueConvert<Boolean> {

	@Override
	public Boolean parse(String obj) {
		return Boolean.parseBoolean(obj);
	}
}
