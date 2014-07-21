package com.yuncommunity.item;

import com.google.gson.Gson;
import com.yuncommunity.app.Constant;
import com.yuncommunity.base.BaseItem;

/**
 * 好友动态
 * 
 * @author oldfeel
 * 
 */
public class FriendDynamicListItem extends BaseItem {
	private static final long serialVersionUID = 1L;
	private long userid;
	private long targetid;
	/** 1为关注活动,2为关注商家服务,3为关注个人服务,4为关注用户,5为评论,6为赞同评论,7为反对评论 */
	private int dynamicType;
	private String time;
	private Object content;
	private String avatar;
	private String name;

	public void setName(String name) {
		this.name = name;
	}

	public String getName() {
		return name;
	}

	public String getAvatar() {
		return avatar;
	}

	public void setAvatar(String avatar) {
		this.avatar = avatar;
	}

	public long getUserid() {
		return userid;
	}

	public void setUserid(long userid) {
		this.userid = userid;
	}

	public long getTargetid() {
		return targetid;
	}

	public void setTargetid(long targetid) {
		this.targetid = targetid;
	}

	public int getDynamicType() {
		return dynamicType;
	}

	public void setDynamicType(int dynamicType) {
		this.dynamicType = dynamicType;
	}

	public String getTime() {
		return time;
	}

	public void setTime(String time) {
		this.time = time;
	}

	public void setContent(Object content) {
		this.content = content;
	}

	public Object getContent() {
		if (content == null) {
			return "";
		}
		return content;
	}

	public CharSequence getChild() {
		CommentItem commentItem = new Gson().fromJson(getContent().toString(),
				CommentItem.class);
		if (commentItem == null) {
			return "";
		}
		return commentItem.getContent();
	}

	public CharSequence getDesc() {
		switch (dynamicType) {
		case Constant.TYPE_ACTIVITY:
		case Constant.TYPE_BUSINESS:
		case Constant.TYPE_PERSONAL:
		case Constant.TYPE_COMMENT:
		case Constant.TYPE_APPROVAL:
		case Constant.TYPE_OPPOSITION:
			InformationItem informationItem = new Gson().fromJson(getContent()
					.toString(), InformationItem.class);
			if (informationItem == null) {
				return "";
			}
			return informationItem.getTitle();
		case Constant.TYPE_FOLLOWING_USER:
			UserItem userItem = new Gson().fromJson(getContent().toString(),
					UserItem.class);
			if (userItem == null) {
				return "";
			}
			return userItem.getName();
		default:
			break;
		}
		return "";
	}

	public CharSequence getExplanation() {
		switch (dynamicType) {
		case Constant.TYPE_ACTIVITY:
			return "关注了该活动";
		case Constant.TYPE_APPROVAL:
			return "称赞了该评论";
		case Constant.TYPE_BUSINESS:
			return "关注了该商家";
		case Constant.TYPE_COMMENT:
			return "评论了该" + getInfoType();
		case Constant.TYPE_FOLLOWING_USER:
			return "关注了该用户";
		case Constant.TYPE_OPPOSITION:
			return "反对了该评论";
		case Constant.TYPE_PERSONAL:
			return "关注了该个人服务";
		default:
			break;
		}
		return null;
	}

	private String getInfoType() {
		CommentItem commentItem = new Gson().fromJson(getContent().toString(),
				CommentItem.class);
		if (commentItem == null) {
			return null;
		}
		InformationItem informationItem = new Gson().fromJson(commentItem
				.getInformationItem().toString(), InformationItem.class);
		switch (informationItem.getInfotype()) {
		case Constant.TYPE_ACTIVITY:
			return "活动";
		case Constant.TYPE_BUSINESS:
			return "商家";
		case Constant.TYPE_PERSONAL:
			return "个人服务";
		default:
			break;
		}
		return null;
	}

}
