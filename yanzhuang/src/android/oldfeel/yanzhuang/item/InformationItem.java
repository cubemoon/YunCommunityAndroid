package android.oldfeel.yanzhuang.item;

import android.oldfeel.yanzhuang.base.BaseItem;

/**
 * 信息
 * 
 * @author oldfeel
 * 
 */
public class InformationItem extends BaseItem {

	/** serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/** informationid. */
	private Long informationid;

	/** userid. */
	private Long userid;

	/** title. */
	private String title;

	/** description. */
	private String description;

	/** address. */
	private String address;

	/** lon. */
	private Double lon;

	/** lat. */
	private Double lat;

	/** phone. */
	private String phone;

	/** image. */
	private String image;

	/** voice. */
	private String voice;

	/** video. */
	private String video;

	/** time. */
	private String time;

	/** infotype. */
	private Integer infotype;

	private double evaluation;

	private float score;

	public double getEvaluation() {
		return evaluation;
	}

	public void setEvaluation(double evaluation) {
		this.evaluation = evaluation;
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
	public void setInformationid(Long informationid) {
		this.informationid = informationid;
	}

	/**
	 * Get the informationid.
	 * 
	 * @return informationid
	 */
	public Long getInformationid() {
		return this.informationid;
	}

	/**
	 * Set the userid.
	 * 
	 * @param userid
	 *            userid
	 */
	public void setUserid(Long userid) {
		this.userid = userid;
	}

	/**
	 * Get the userid.
	 * 
	 * @return userid
	 */
	public Long getUserid() {
		return this.userid;
	}

	/**
	 * Set the title.
	 * 
	 * @param title
	 *            title
	 */
	public void setTitle(String title) {
		this.title = title;
	}

	/**
	 * Get the title.
	 * 
	 * @return title
	 */
	public String getTitle() {
		return this.title;
	}

	/**
	 * Set the description.
	 * 
	 * @param description
	 *            description
	 */
	public void setDescription(String description) {
		this.description = description;
	}

	/**
	 * Get the description.
	 * 
	 * @return description
	 */
	public String getDescription() {
		return this.description;
	}

	/**
	 * Set the address.
	 * 
	 * @param address
	 *            address
	 */
	public void setAddress(String address) {
		this.address = address;
	}

	/**
	 * Get the address.
	 * 
	 * @return address
	 */
	public String getAddress() {
		return this.address;
	}

	/**
	 * Set the lon.
	 * 
	 * @param lon
	 *            lon
	 */
	public void setLon(Double lon) {
		this.lon = lon;
	}

	/**
	 * Get the lon.
	 * 
	 * @return lon
	 */
	public Double getLon() {
		return this.lon;
	}

	/**
	 * Set the lat.
	 * 
	 * @param lat
	 *            lat
	 */
	public void setLat(Double lat) {
		this.lat = lat;
	}

	/**
	 * Get the lat.
	 * 
	 * @return lat
	 */
	public Double getLat() {
		return this.lat;
	}

	/**
	 * Set the phone.
	 * 
	 * @param phone
	 *            phone
	 */
	public void setPhone(String phone) {
		this.phone = phone;
	}

	/**
	 * Get the phone.
	 * 
	 * @return phone
	 */
	public String getPhone() {
		return this.phone;
	}

	/**
	 * Set the image.
	 * 
	 * @param image
	 *            image
	 */
	public void setImage(String image) {
		this.image = image;
	}

	/**
	 * Get the image.
	 * 
	 * @return image
	 */
	public String getImage() {
		return this.image;
	}

	/**
	 * Set the voice.
	 * 
	 * @param voice
	 *            voice
	 */
	public void setVoice(String voice) {
		this.voice = voice;
	}

	/**
	 * Get the voice.
	 * 
	 * @return voice
	 */
	public String getVoice() {
		return this.voice;
	}

	/**
	 * Set the video.
	 * 
	 * @param video
	 *            video
	 */
	public void setVideo(String video) {
		this.video = video;
	}

	/**
	 * Get the video.
	 * 
	 * @return video
	 */
	public String getVideo() {
		return this.video;
	}

	/**
	 * Set the time.
	 * 
	 * @param time
	 *            time
	 */
	public void setTime(String time) {
		this.time = time;
	}

	/**
	 * Get the time.
	 * 
	 * @return time
	 */
	public String getTime() {
		return this.time;
	}

	/**
	 * Set the infotype.
	 * 
	 * @param infotype
	 *            infotype
	 */
	public void setInfotype(Integer infotype) {
		this.infotype = infotype;
	}

	/**
	 * Get the infotype.
	 * 
	 * @return infotype
	 */
	public Integer getInfotype() {
		return this.infotype;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((informationid == null) ? 0 : informationid.hashCode());
		return result;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (getClass() != obj.getClass()) {
			return false;
		}
		InformationItem other = (InformationItem) obj;
		if (informationid == null) {
			if (other.informationid != null) {
				return false;
			}
		} else if (!informationid.equals(other.informationid)) {
			return false;
		}
		return true;
	}

	public void setScore(float score) {
		this.score = score;
	}

	public float getScore() {
		return score;
	}
}
