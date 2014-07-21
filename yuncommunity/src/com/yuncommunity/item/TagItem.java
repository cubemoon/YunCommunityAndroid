package com.yuncommunity.item;

import com.yuncommunity.base.BaseItem;

/**
 * 标签
 * 
 * @author oldfeel
 * 
 */
public class TagItem extends BaseItem {
	private long userid;
	private long informationid;
	private long tagid;
	private String name;
	private long count;

	public long getUserid() {
		return userid;
	}

	public void setUserid(long userid) {
		this.userid = userid;
	}

	public long getInformationid() {
		return informationid;
	}

	public void setInformationid(long informationid) {
		this.informationid = informationid;
	}

	public long getTagid() {
		return tagid;
	}

	public void setTagid(long tagid) {
		this.tagid = tagid;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public long getCount() {
		return count;
	}

	public void setCount(long count) {
		this.count = count;
	}

}
