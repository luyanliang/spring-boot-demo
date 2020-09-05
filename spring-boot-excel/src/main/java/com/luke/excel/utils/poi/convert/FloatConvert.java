package com.luke.excel.utils.poi.convert;

/**
 * float类型转换器
 */
public class FloatConvert implements IValueConvert<Float> {

	@Override
	public Float parse(String obj) {
		return Float.parseFloat(obj);
	}
}
