package com.yuncommunity.item;

import com.oldfeel.base.BaseItem;

/**
 * 
 * @author oldfeel
 * 
 *         Create on: 2014年10月26日
 */
public class FuncItem extends BaseItem {
	private int nameResId;
	private int imageResId;
	private Class<?> TheClass;

	public int getNameResId() {
		return nameResId;
	}

	public void setNameResId(int nameResId) {
		this.nameResId = nameResId;
	}

	public int getImageResId() {
		return imageResId;
	}

	public void setImageResId(int imageResId) {
		this.imageResId = imageResId;
	}

	public Class<?> getTheClass() {
		return TheClass;
	}

	public void setTheClass(Class<?> theClass) {
		TheClass = theClass;
	}

}
