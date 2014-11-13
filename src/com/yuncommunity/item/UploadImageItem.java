package com.yuncommunity.item;

import java.io.File;

import com.oldfeel.base.BaseItem;

/**
 * 
 * @author oldfeel
 * 
 *         Create on: 2014年11月13日
 */
public class UploadImageItem extends BaseItem {
	private File file;
	private String key;

	public File getFile() {
		return file;
	}

	public void setFile(File file) {
		this.file = file;
	}

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

}
