package com.yuncommunity.item;

import com.yuncommunity.base.BaseItem;

/**
 * 小区
 * 
 * @author oldfeel
 * 
 */
public class CommunityItem extends BaseItem {
	private long communityid;
	private long userid;
	private double lat;
	private double lon;
	private String image;
	private String description;
	private String time;
	private String name;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public long getCommunityid() {
		return communityid;
	}

	public void setCommunityid(long communityid) {
		this.communityid = communityid;
	}

	public long getUserid() {
		return userid;
	}

	public void setUserid(long userid) {
		this.userid = userid;
	}

	public double getLat() {
		return lat;
	}

	public void setLat(double lat) {
		this.lat = lat;
	}

	public double getLon() {
		return lon;
	}

	public void setLon(double lon) {
		this.lon = lon;
	}

	public String getImage() {
		return image;
	}

	public void setImage(String image) {
		this.image = image;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getTime() {
		return time;
	}

	public void setTime(String time) {
		this.time = time;
	}

}
