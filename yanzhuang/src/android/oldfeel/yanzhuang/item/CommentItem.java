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
	private int score;
	private long opposition;
	private long apporval;

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

	public int getScore() {
		return score;
	}

	public void setScore(int score) {
		this.score = score;
	}

	public long getOpposition() {
		return opposition;
	}

	public void setOpposition(long opposition) {
		this.opposition = opposition;
	}

	public long getApporval() {
		return apporval;
	}

	public void setApporval(long apporval) {
		this.apporval = apporval;
	}

}
