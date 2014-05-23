package android.oldfeel.yanzhuang.item;

import android.oldfeel.yanzhuang.base.BaseItem;

/**
 * 评论
 * 
 * @author oldfeel
 * 
 */
public class CommentItem extends BaseItem {
	private long commentid;
	private long userid;
	private long informationid;
	private String content;
	private float score;
	private long opposition;
	private long approval;
	private String avatar;
	private String name;
	private String time;
	private String tags;
	private boolean isOpposition;
	private boolean isApproval;

	public boolean isOpposition() {
		return isOpposition;
	}

	public void setOpposition(boolean isOpposition) {
		this.isOpposition = isOpposition;
	}

	public boolean isApproval() {
		return isApproval;
	}

	public void setApproval(boolean isApproval) {
		this.isApproval = isApproval;
	}

	public String getTime() {
		return time;
	}

	public void setTime(String time) {
		this.time = time;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getAvatar() {
		return avatar;
	}

	public void setAvatar(String avatar) {
		this.avatar = avatar;
	}

	public long getCommentid() {
		return commentid;
	}

	public void setCommentid(long commentid) {
		this.commentid = commentid;
	}

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

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public float getScore() {
		return score;
	}

	public void setScore(int score) {
		this.score = score;
	}

	public long getOppositionCount() {
		return opposition;
	}

	public void setOppositionCount(long opposition) {
		this.opposition = opposition;
	}

	public long getApprovalCount() {
		return approval;
	}

	public void setApprovalCount(long approval) {
		this.approval = approval;
	}

	public void setTags(String tags) {
		this.tags = tags;
	}

	public String getTags() {
		return tags;
	}

}
