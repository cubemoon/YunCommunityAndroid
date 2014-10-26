package com.yuncommunity.item;

import com.oldfeel.base.BaseItem;

/**
 * 标签
 * 
 * @author oldfeel
 * 
 */
public class TagItem extends BaseItem {
	public static final long TAGID_PROPERTY = 5; // 物业中心 的标签id
	public static final long TAGID_REPAIR = 6; // 维修的标签id
	public static final long TAGID_EXPRESS = 7; // 快递的标签id
	public static final long TAGID_WASHING = 8; // 洗衣的标签id
	public static final long TAGID_TAKEOUT = 9; // 外卖
	public static final long TAGID_LIFE_DISTRIBUTION = 10; // 生活配送
	public static final long TAGID_FRUITS = 11; // 水果
	public static final long TAGID_HOUSEKEEPING_SERVICE = 12; // 家政服务
	private long Userid;
	private long Informationid;
	private long Tagid;
	private String Name;
	private long Count;
	private String Icon;
	private int IconResId;

	public TagItem(String Name, int IconResId, long Tagid) {
		this.Name = Name;
		this.IconResId = IconResId;
		this.Tagid = Tagid;
	}

	public int getIconResId() {
		return IconResId;
	}

	public void setIconResId(int iconResId) {
		IconResId = iconResId;
	}

	public String getIcon() {
		return Icon;
	}

	public void setIcon(String icon) {
		Icon = icon;
	}

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
