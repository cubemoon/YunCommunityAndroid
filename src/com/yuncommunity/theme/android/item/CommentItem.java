package com.yuncommunity.theme.android.item;

import com.oldfeel.base.BaseItem;

/**
 * 评论
 * 
 * @author oldfeel
 * 
 */
public class CommentItem extends BaseItem {
	private long Commentid;
	private long Userid;
	private long Informationid;
	private String Content;
	private float Score;
	private long Opposition;
	private long Approval;
	private String Avatar;
	private String Name;
	private String Time;
	private String Tags;
	private boolean IsOpposition;
	private boolean IsApproval;
	private Object InformationItem;

	public Object getInformationItem() {
		if (InformationItem == null) {
			return "";
		}
		return InformationItem;
	}

	public void setInformationItem(Object informationItem) {
		this.InformationItem = informationItem;
	}

	public boolean isOpposition() {
		return IsOpposition;
	}

	public void setOpposition(boolean isOpposition) {
		this.IsOpposition = isOpposition;
	}

	public boolean isApproval() {
		return IsApproval;
	}

	public void setApproval(boolean isApproval) {
		this.IsApproval = isApproval;
	}

	public String getTime() {
		return Time;
	}

	public void setTime(String time) {
		this.Time = time;
	}

	public String getName() {
		return Name;
	}

	public void setName(String name) {
		this.Name = name;
	}

	public String getAvatar() {
		return Avatar;
	}

	public void setAvatar(String avatar) {
		this.Avatar = avatar;
	}

	public long getCommentid() {
		return Commentid;
	}

	public void setCommentid(long commentid) {
		this.Commentid = commentid;
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

	public String getContent() {
		return Content;
	}

	public void setContent(String content) {
		this.Content = content;
	}

	public float getScore() {
		return Score;
	}

	public void setScore(float score) {
		this.Score = score;
	}

	public long getOppositionCount() {
		return Opposition;
	}

	public void setOppositionCount(long opposition) {
		this.Opposition = opposition;
	}

	public long getApprovalCount() {
		return Approval;
	}

	public void setApprovalCount(long approval) {
		this.Approval = approval;
	}

	public void setTags(String tags) {
		this.Tags = tags;
	}

	public String getTags() {
		return Tags;
	}

}
