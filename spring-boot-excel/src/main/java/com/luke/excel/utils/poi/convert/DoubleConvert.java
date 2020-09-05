package com.luke.excel.utils.poi.convert;

/**
 * double类型转换器
 */
public class DoubleConvert implements IValueConvert<Double> {

	@Override
	public Double parse(String obj) {
		return Double.parseDouble(obj);
	}
}