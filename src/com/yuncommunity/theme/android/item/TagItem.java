package com.yuncommunity.theme.android.item;

import com.oldfeel.base.BaseItem;

/**
 * 标签
 * 
 * @author oldfeel
 * 
 */
public class TagItem extends BaseItem {
	private long Userid;
	private long Informationid;
	private long Tagid;
	private String Name;
	private long Count;

	public long getUserid() {
		return Userid;
	}

	public void setUserid(long userid) {
		this.Userid = userid;
	}

	public long getInformationid() {
		return Informationid;
	}

	public void setInformationid(long informationid) {
		this.Informationid = informationid;
	}

	public long getTagid() {
		return Tagid;
	}

	public void setTagid(long tagid) {
		this.Tagid = tagid;
	}

	public String getName() {
		return Name;
	}

	public void setName(String name) {
		this.Name = name;
	}

	public long getCount() {
		return Count;
	}

	public void setCount(long count) {
		this.Count = count;
	}

}
