package com.yuncommunity.item;

import com.oldfeel.base.BaseItem;

/**
 * 
 * @author oldfeel
 * 
 *         Create on: 2014年10月26日
 */
public class FuncItem extends BaseItem {
	private int imageResId;
	private Class<?> TheClass;
	private String name;

	public FuncItem(String name, int imageResId, Class<?> theClass) {
		super();
		this.name = name;
		this.imageResId = imageResId;
		TheClass = theClass;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
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
