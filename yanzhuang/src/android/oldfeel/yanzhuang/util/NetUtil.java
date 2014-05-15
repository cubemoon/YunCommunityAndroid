package android.oldfeel.yanzhuang.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.SocketTimeoutException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.os.Handler;
import android.os.Looper;
import android.provider.Settings;
import android.widget.Toast;

/**
 * 网络接口,post请求string或者get请求json,里面只包含一个线程,只能同时发送一个网络请求
 * 
 * @author oldfeel
 * 
 */
public class NetUtil extends Handler {
	/** 打开网络连接 */
	public static final int OPEN_NETWORK = -1;
	/** 超时时间限制 */
	public static final int TIME_OUT = 30 * 1000;
	private Activity activity;
	private Map<String, String> params = new HashMap<String, String>();
	private String path = "";
	private ProgressDialog pd;
	private Thread requestThread;
	private AlertDialog dialog;

	/**
	 * 构造一个netapi对象
	 * 
	 * @param Activity
	 * 
	 * @param path
	 *            这次请求需要调用的url
	 */
	public NetUtil(Activity Activity, String path) {
		this.activity = Activity;
		this.path = path;
	}

	/**
	 * 添加参数,用url编码格式
	 * 
	 * @param key
	 * @param value
	 */
	public void addParams(String key, Object value) {
		if (!isEmpty(key) && !isEmpty(value)) {
			params.put(key.trim(), value.toString().trim());// *.trim(),取消首尾空格
		}
	}

	/**
	 * 补充路径,比如添加 /信息类别id/新闻id
	 * 
	 * @param objects
	 */
	public void addPath(Object... objects) {
		for (Object object : objects) {
			path = path + "/" + getUrlEncode(object);
		}
	}

	/**
	 * 检查该参数是否有内容,没有的话就不用添加了
	 * 
	 * @param str
	 * @return true为没有内容,false为有内容
	 */
	public boolean isEmpty(Object str) {
		if (str == null || str.toString().length() == 0)
			return true;
		else
			return false;
	}

	/**
	 * 获取url编码的字符串
	 * 
	 * @param value
	 * @return
	 */
	private String getUrlEncode(Object value) {
		try {
			return URLEncoder.encode(value.toString().trim(), "utf-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * get请求,拼接url路径,并对参数进行urlencode
	 */
	public String getPath() {
		if (params.size() == 0) {
			return path;
		}
		StringBuilder sb = new StringBuilder();
		boolean first = path.indexOf("?") == -1; // 不包含?(first=true)说明是第一次添加参数,包含?说明url中带有参数
		for (String key : params.keySet()) {
			if (first) {
				first = false;
				sb.append("?");
			} else {
				sb.append("&");
			}
			sb.append(key + "=" + getUrlEncode(params.get(key)));
		}
		return path + sb.toString();
	}

	/**
	 * post请求,传入的参数
	 * 
	 * @return
	 */
	private String postParams() {
		JSONObject json = new JSONObject();
		try {
			for (String string : params.keySet()) {
				json.put(string, params.get(string));
			}
			JSONObject aesJson = new JSONObject();
			AESCrypt crypt = new AESCrypt();
			aesJson.put("data", crypt.encrypt(json.toString()));
			return aesJson.toString();
		} catch (JSONException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return json.toString();
	}

	/**
	 * 显示进度条提示
	 * 
	 * @param message
	 */
	private void showPd(String message) {
		showLog("start connect");
		if (message == null || message.length() == 0) {
			return;
		}
		if (pd != null) {
			pd.cancel();
			pd = null;
		}
		pd = new ProgressDialog(activity);
		pd.setMessage(message);
		pd.setCanceledOnTouchOutside(false);
		pd.setOnCancelListener(new DialogInterface.OnCancelListener() {

			@Override
			public void onCancel(DialogInterface dialog) {
				if (failListener != null) {
					failListener.cancel();
				}
				if (requestThread != null) {
					requestThread.interrupt();
				}
			}
		});
		pd.setCancelable(true);
		pd.show();
	}

	/**
	 * 发起一个post请求,返回string对象
	 * 
	 * @param stringListener
	 * @param isLoadCache
	 *            true 为加载缓存，false为不加载缓存
	 */
	public void postRequest(final String text,
			final RequestStringListener stringListener) {
		if (!isNetworkConnect()) {
			whetherOpenNet();
			return;
		}
		showPd(text);
		Runnable task = new Runnable() {

			@Override
			public void run() {
				try {
					final String result = postStringResult();
					if (requestThread.isInterrupted()) {
						showLog("is interrupted");
						return;
					}
					if (result == null) {
						netError();
						return;
					}
					post(new Complete() {

						@Override
						public void run() {
							super.run();
							stringListener.onComplete(result);
						}
					});
				} catch (SocketTimeoutException e) {
					timeOut(text, stringListener);
					e.printStackTrace();
				} catch (Exception e) {
					netError();
					e.printStackTrace();
				}

			}
		};
		requestThread = new Thread(task);
		requestThread.start();
	}

	/**
	 * 发送一个post请求,返回string对象
	 * 
	 * @param text
	 * @param requestStringListener
	 */
	public void getRequest(final String text,
			final RequestStringListener stringListener) {
		if (!isNetworkConnect()) {
			whetherOpenNet();
			return;
		}
		showPd(text);
		Runnable task = new Runnable() {

			@Override
			public void run() {
				try {
					final String result = getStringResult();
					if (requestThread.isInterrupted()) {
						showLog("is interrupted");
						return;
					}
					if (result == null) {
						netError();
						return;
					}
					post(new Complete() {

						@Override
						public void run() {
							super.run();
							stringListener.onComplete(result);
						}
					});
				} catch (SocketTimeoutException e) {
					timeOut(text, stringListener);
					e.printStackTrace();
				} catch (Exception e) {
					netError();
					e.printStackTrace();
				}

			}
		};
		requestThread = new Thread(task);
		requestThread.start();
	}

	/**
	 * 网络连接超时
	 * 
	 * @param text
	 * @param stringListener
	 */
	protected void timeOut(final String text,
			final RequestStringListener stringListener) {
		Looper.prepare();
		post(new Complete() {

			@Override
			public void run() {
				super.run();
				if (failListener != null) {
					failListener.onTimeOut();
				}
				new AlertDialog.Builder(activity)
						.setTitle("网络连接超时")
						.setNegativeButton("取消", null)
						.setPositiveButton("重连",
								new DialogInterface.OnClickListener() {

									@Override
									public void onClick(DialogInterface dialog,
											int which) {
										showLog("重连");
										postRequest(text, stringListener);
									}
								}).show();
			}
		});

	}

	/**
	 * 网络连接错误或返回数据为空
	 * 
	 */
	protected void netError() {
		Looper.prepare();
		post(new Complete() {

			@Override
			public void run() {
				super.run();
				if (failListener != null) {
					failListener.onError();
				}
				if (!isNetworkConnect()) {
					whetherOpenNet();
				} else {
					Toast.makeText(activity, "网络连接失败,请稍后重试", Toast.LENGTH_LONG)
							.show();
				}
			}
		});
	}

	/**
	 * 发送post上传文件,获取字符串结果
	 * 
	 * @param isLoadCache
	 *            true为加载缓存，false为不加载缓存
	 * @throws Exception
	 */
	public String postStringResult() throws SocketTimeoutException {
		showLog("path is " + path);
		AESCrypt crypt = null;
		try {
			crypt = new AESCrypt();
			JSONObject jsonObject = new JSONObject(postParams());
			showLog("post is " + crypt.decrypt(jsonObject.getString("data")));
		} catch (Exception e1) {
			e1.printStackTrace();
		}
		HttpPost post = new HttpPost(path);
		try {
			StringEntity entity = new StringEntity(postParams(), HTTP.UTF_8);
			entity.setContentType("application/json");
			post.setEntity(entity);
			HttpResponse httpResponse = new DefaultHttpClient().execute(post);
			if (httpResponse.getStatusLine().getStatusCode() == 200) {
				String result = EntityUtils.toString(httpResponse.getEntity());
				result = crypt.decrypt(result);
				return result;
			}
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 发送get请求,获取string对象
	 * 
	 * @return
	 * @throws IOException
	 */
	protected String getStringResult() throws IOException {
		String path = getPath();
		LogUtil.showLog("path is " + path);
		URL url = new URL(path);
		HttpURLConnection conn = (HttpURLConnection) url.openConnection();
		conn.setRequestMethod("GET");
		conn.setConnectTimeout(30 * 1000);
		conn.setReadTimeout(30 * 1000);
		if (conn.getResponseCode() == HttpURLConnection.HTTP_OK) {
			StringBuffer out = new StringBuffer();
			BufferedReader input = new BufferedReader(new InputStreamReader(
					conn.getInputStream()));
			String line = null;
			while ((line = input.readLine()) != null) {
				out.append(line);
			}
			input.close();
			String result = out.toString();
			try {
				AESCrypt crypt = new AESCrypt();
				result = crypt.decrypt(result);
				return result;
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return null;
	}

	class Complete implements Runnable {
		@Override
		public void run() {
			if (pd != null) {
				pd.cancel();
			}
			requestThread.interrupt();
		}
	}

	/**
	 * 判断网络连接
	 */
	private boolean isNetworkConnect() {
		ConnectivityManager cm = (ConnectivityManager) activity
				.getSystemService(Context.CONNECTIVITY_SERVICE);
		cm.getActiveNetworkInfo();
		if (cm.getActiveNetworkInfo() != null) {
			return cm.getActiveNetworkInfo().isAvailable();
		}
		return false;
	}

	/**
	 * 打开网络对话框
	 */
	private void whetherOpenNet() {
		if (dialog != null) {
			dialog.cancel();
			dialog = null;
		}
		AlertDialog.Builder builder = new AlertDialog.Builder(activity);
		builder.setTitle("网络木有连接");
		builder.setMessage("是否打开网络连接");
		builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {

			@Override
			public void onClick(DialogInterface dialog, int which) {
				activity.startActivity(new Intent(
						Settings.ACTION_WIRELESS_SETTINGS));
			}
		});
		builder.setNegativeButton("取消", null);
		dialog = builder.create();
		dialog.show();
	}

	/**
	 * 打印日志
	 * 
	 * @param log
	 */
	private void showLog(String log) {
		LogUtil.showLog(log);
	}

	/**
	 * 获取当前NetApi绑定的activity
	 * 
	 * @return
	 */
	public Activity getActivity() {
		return this.activity;
	}

	/**
	 * 请求string的监听
	 */
	public interface RequestStringListener {
		/** 返回字符串 */
		public void onComplete(String result);
	}

	/**
	 * 判断网络连接
	 * */
	public static boolean isNetworkConnect(Context context) {
		ConnectivityManager cm = (ConnectivityManager) context
				.getSystemService(Context.CONNECTIVITY_SERVICE);
		cm.getActiveNetworkInfo();
		if (cm.getActiveNetworkInfo() != null) {
			return cm.getActiveNetworkInfo().isAvailable();
		}
		return false;
	}

	/**
	 * 打开网络对话框
	 */
	public static void whetherOpenNet(final Context context) {
		new AlertDialog.Builder(context)
				.setTitle("网络木有连接")
				.setMessage("是否打开网络连接")
				.setPositiveButton(android.R.string.ok,
						new DialogInterface.OnClickListener() {
							public void onClick(DialogInterface dialog,
									int whichButton) {
								context.startActivity(new Intent(
										Settings.ACTION_WIRELESS_SETTINGS));
							}
						}).setNeutralButton(android.R.string.cancel, null)
				.show();
	}

	public void setPage(int page) {
		params.put("index", String.valueOf(page));
	}

	public void setPageSize(int pageSize) {
		params.put("perPage", String.valueOf(pageSize));
	}

	/**
	 * 网络连接失败,包括取消请求/网络错误/网络延时
	 * 
	 * @author oldfeel
	 * 
	 *         Create on: 2014年4月18日
	 */
	public interface OnNetFailListener {
		public void cancel();

		public void onError();

		public void onTimeOut();
	}

	private OnNetFailListener failListener;

	/**
	 * 取消网络请求监听。。。
	 * 
	 * @param cancelListener
	 */
	public void setOnNetFailListener(OnNetFailListener cancelListener) {
		this.failListener = cancelListener;
	}
}