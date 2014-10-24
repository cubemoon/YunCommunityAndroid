package com.yuncommunity.theme.android.item;

import com.google.gson.Gson;
import com.oldfeel.base.BaseItem;
import com.yuncommunity.theme.android.app.Constant;

/**
 * 好友动态
 * 
 * @author oldfeel
 * 
 */
public class FriendDynamicListItem extends BaseItem {
	private static final long serialVersionUID = 1L;
	private long Userid;
	private long Targetid;
	/** 1为关注活动,2为关注商家服务,3为关注个人服务,4为关注用户,5为评论,6为赞同评论,7为反对评论 */
	private int TynamicType;
	private String Time;
	private Object Content;
	private String Avatar;
	private String Name;

	public void setName(String name) {
		this.Name = name;
	}

	public String getName() {
		return Name;
	}

	public String getAvatar() {
		return Avatar;
	}

	public void setAvatar(String avatar) {
		this.Avatar = avatar;
	}

	public long getUserid() {
		return Userid;
	}

	public void setUserid(long userid) {
		this.Userid = userid;
	}

	public long getTargetid() {
		return Targetid;
	}

	public void setTargetid(long targetid) {
		this.Targetid = targetid;
	}

	public int getDynamicType() {
		return TynamicType;
	}

	public void setDynamicType(int dynamicType) {
		this.TynamicType = dynamicType;
	}

	public String getTime() {
		return Time;
	}

	public void setTime(String time) {
		this.Time = time;
	}

	public void setContent(Object content) {
		this.Content = content;
	}

	public Object getContent() {
		if (Content == null) {
			return "";
		}
		return Content;
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
		switch (TynamicType) {
		case Constant.TYPE_ACTIVITY:
		case Constant.TYPE_BUSINESS:
		case Constant.TYPE_TASK:
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
		switch (TynamicType) {
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
		case Constant.TYPE_TASK:
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
		case Constant.TYPE_TASK:
			return "个人服务";
		default:
			break;
		}
		return null;
	}

}
