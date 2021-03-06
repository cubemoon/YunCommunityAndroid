package com.yuncommunity.item;

import com.oldfeel.base.BaseItem;

/**
 * 信息
 * 
 * @author oldfeel
 * 
 */
public class InformationItem extends BaseItem {

	/** informationid. */
	private long Informationid;

	/** userid. */
	private long Userid;

	/** title. */
	private String Title;

	/** description. */
	private String Description;

	/** address. */
	private String Address;

	/** lon. */
	private Double Lon;

	/** lat. */
	private Double Lat;

	/** phone. */
	private String Phone;

	/** image. */
	private String Image;

	/** voice. */
	private String Voice;

	/** video. */
	private String Video;

	/** time. */
	private String Time;

	/** infotype. 1为活动,2为商家,3为个人 */
	private Integer Infotype;

	private double Evaluation;

	private float Score;

	private UserItem UserInfo;

	private long CommentCount;

	public long getCommentCount() {
		return CommentCount;
	}

	public void setCommentCount(long commentCount) {
		CommentCount = commentCount;
	}

	public UserItem getUserInfo() {
		return UserInfo;
	}

	public void setUserInfo(UserItem UserInfo) {
		this.UserInfo = UserInfo;
	}

	public double getEvaluation() {
		return Evaluation;
	}

	public void setEvaluation(double evaluation) {
		this.Evaluation = evaluation;
	}

	/**
	 * Constructor.
	 */
	public InformationItem() {
	}

	/**
	 * Set the informationid.
	 * 
	 * @param informationid
	 *            informationid
	 */
	public void setInformationid(long informationid) {
		this.Informationid = informationid;
	}

	/**
	 * Get the informationid.
	 * 
	 * @return informationid
	 */
	public long getInformationid() {
		return this.Informationid;
	}

	/**
	 * Set the userid.
	 * 
	 * @param userid
	 *            userid
	 */
	public void setUserid(long userid) {
		this.Userid = userid;
	}

	/**
	 * Get the userid.
	 * 
	 * @return userid
	 */
	public long getUserid() {
		return this.Userid;
	}

	/**
	 * Set the title.
	 * 
	 * @param title
	 *            title
	 */
	public void setTitle(String title) {
		this.Title = title;
	}

	/**
	 * Get the title.
	 * 
	 * @return title
	 */
	public String getTitle() {
		return this.Title;
	}

	/**
	 * Set the description.
	 * 
	 * @param description
	 *            description
	 */
	public void setDescription(String description) {
		this.Description = description;
	}

	/**
	 * Get the description.
	 * 
	 * @return description
	 */
	public String getDescription() {
		return this.Description;
	}

	/**
	 * Set the address.
	 * 
	 * @param address
	 *            address
	 */
	public void setAddress(String address) {
		this.Address = address;
	}

	/**
	 * Get the address.
	 * 
	 * @return address
	 */
	public String getAddress() {
		return this.Address;
	}

	/**
	 * Set the lon.
	 * 
	 * @param lon
	 *            lon
	 */
	public void setLon(Double lon) {
		this.Lon = lon;
	}

	/**
	 * Get the lon.
	 * 
	 * @return lon
	 */
	public Double getLon() {
		return this.Lon;
	}

	/**
	 * Set the lat.
	 * 
	 * @param lat
	 *            lat
	 */
	public void setLat(Double lat) {
		this.Lat = lat;
	}

	/**
	 * Get the lat.
	 * 
	 * @return lat
	 */
	public Double getLat() {
		return this.Lat;
	}

	/**
	 * Set the phone.
	 * 
	 * @param phone
	 *            phone
	 */
	public void setPhone(String phone) {
		this.Phone = phone;
	}

	/**
	 * Get the phone.
	 * 
	 * @return phone
	 */
	public String getPhone() {
		return this.Phone;
	}

	/**
	 * Set the image.
	 * 
	 * @param image
	 *            image
	 */
	public void setImage(String image) {
		this.Image = image;
	}

	/**
	 * Get the image.
	 * 
	 * @return image
	 */
	public String getImage() {
		return this.Image;
	}

	/**
	 * Set the voice.
	 * 
	 * @param voice
	 *            voice
	 */
	public void setVoice(String voice) {
		this.Voice = voice;
	}

	/**
	 * Get the voice.
	 * 
	 * @return voice
	 */
	public String getVoice() {
		return this.Voice;
	}

	/**
	 * Set the video.
	 * 
	 * @param video
	 *            video
	 */
	public void setVideo(String video) {
		this.Video = video;
	}

	/**
	 * Get the video.
	 * 
	 * @return video
	 */
	public String getVideo() {
		return this.Video;
	}

	/**
	 * Set the time.
	 * 
	 * @param time
	 *            time
	 */
	public void setTime(String time) {
		this.Time = time;
	}

	/**
	 * Get the time.
	 * 
	 * @return time
	 */
	public String getTime() {
		// return this.Time;
		return super.getTime(Time);
	}

	/**
	 * Set the infotype.
	 * 
	 * @param infotype
	 *            infotype
	 */
	public void setInfotype(Integer infotype) {
		this.Infotype = infotype;
	}

	/**
	 * Get the infotype.
	 * 
	 * @return infotype
	 */
	public Integer getInfotype() {
		return this.Infotype;
	}

	public void setScore(float score) {
		this.Score = score;
	}

	public float getScore() {
		return Score;
	}
}
