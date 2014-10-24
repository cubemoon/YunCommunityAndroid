package com.yuncommunity.theme.android.item;

import com.oldfeel.base.BaseItem;

/**
 * 小区
 * 
 * @author oldfeel
 * 
 */
public class CommunityItem extends BaseItem {
	private long Communityid;
	private long Userid;
	private double Lat;
	private double Lon;
	private String Image;
	private String Description;
	private String Time;
	private String Name;
	private double Distance;
	private int PeopleCount;

	public int getPeopleCount() {
		return PeopleCount;
	}

	public void setPeopleCount(int peopleCount) {
		PeopleCount = peopleCount;
	}

	public String getDistance() {
		return super.getDistance(Distance);
	}

	public void setDistance(double distance) {
		this.Distance = distance;
	}

	public String getName() {
		return Name;
	}

	public void setName(String name) {
		this.Name = name;
	}

	public long getCommunityid() {
		return Communityid;
	}

	public void setCommunityid(long communityid) {
		this.Communityid = communityid;
	}

	public long getUserid() {
		return Userid;
	}

	public void setUserid(long userid) {
		this.Userid = userid;
	}

	public double getLat() {
		return Lat;
	}

	public void setLat(double lat) {
		this.Lat = lat;
	}

	public double getLon() {
		return Lon;
	}

	public void setLon(double lon) {
		this.Lon = lon;
	}

	public String getImage() {
		return Image;
	}

	public void setImage(String image) {
		this.Image = image;
	}

	public String getDescription() {
		return Description;
	}

	public void setDescription(String description) {
		this.Description = description;
	}

	public String getTime() {
		return Time;
	}

	public void setTime(String time) {
		this.Time = time;
	}

}
