package cn.oldfeel.yanzhuang.base;

import java.io.Serializable;

/**
 * item 基类
 * 
 * @author oldfeel
 * 
 *         Created on: 2014-1-14
 */
public abstract class BaseItem implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * 获取图片路径
	 * 
	 * @param url
	 * @return
	 */
	public String getImageUrl(String url) {
		return "http://meow8.u.qiniudn.com/" + url;
	}

	/**
	 * 获取视频的ts路径
	 * 
	 * @param filepath
	 * @return
	 */
	public String getVideoTs(String filepath) {
		return "http://bameow-ts.qiniudn.com/" + filepath;
	}

}
