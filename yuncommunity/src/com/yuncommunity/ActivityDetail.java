package com.yuncommunity;

import java.util.List;

import org.json.JSONException;
import org.json.JSONObject;

import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.Spinner;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.yuncommunity.app.JsonApi;
import com.yuncommunity.app.LoginInfo;
import com.yuncommunity.base.BaseActivity;
import com.yuncommunity.fragment.InformationMedia;
import com.yuncommunity.item.CommentItem;
import com.yuncommunity.item.InformationItem;
import com.yuncommunity.item.TagItem;
import com.yuncommunity.list.CommentListFragment;
import com.yuncommunity.util.DialogUtil;
import com.yuncommunity.util.ETUtil;
import com.yuncommunity.util.JSONUtil;
import com.yuncommunity.util.LogUtil;
import com.yuncommunity.util.NetUtil;
import com.yuncommunity.util.NetUtil.RequestStringListener;
import com.yuncommunity.util.StringUtil;

/**
 * 获取详情
 * 
 * @author oldfeel
 * 
 *         Create on: 2014年7月27日
 */
public class ActivityDetail extends BaseActivity implements OnClickListener {
	private LinearLayout llTags;
	private Button btnFollowing, btnEvaluation;
	private ImageButton ibCall, ibMap;
	private TextView tvTitle, tvTime, tvDesc, tvScoreCount;
	private RatingBar rbScore;
	private InformationItem item;
	private CommentItem myComment;
	private long followerCount;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_detail);
		setTitle(getText(R.string.details));
		item = (InformationItem) getIntent().getSerializableExtra("item");
		getSupportFragmentManager()
				.beginTransaction()
				.replace(R.id.activity_detail_media,
						InformationMedia.newInstance(item)).commit();
		initView();
		initListener();
		putDataToView();
		getDetail();
		getCommentList();
	}

	private void initListener() {
		btnFollowing.setOnClickListener(this);
		btnEvaluation.setOnClickListener(this);
		ibCall.setOnClickListener(this);
		ibMap.setOnClickListener(this);
	}

	private void putDataToView() {
		tvTitle.setText(item.getTitle());
		tvTime.setText(item.getTime());
		tvDesc.setText(item.getDescription());
		if (StringUtil.isEmpty(item.getPhone())
				|| !StringUtil.isMobileNO(item.getPhone())) {
			ibCall.setVisibility(View.GONE);
		}
	}

	private void getCommentList() {
		CommentListFragment fragment = CommentListFragment
				.newInstance(getCommentListNetUtil());
		getSupportFragmentManager().beginTransaction()
				.replace(R.id.activity_detail_commentlist, fragment).commit();
	}

	private NetUtil getCommentListNetUtil() {
		NetUtil netUtil = new NetUtil(ActivityDetail.this,
				JsonApi.INFORMATION_COMMENTLIST);
		netUtil.setParams("informationid", item.getInformationid());
		netUtil.setParams("userid", getUserid());
		return netUtil;
	}

	private void getDetail() {
		NetUtil netUtil = new NetUtil(ActivityDetail.this,
				JsonApi.INFORMATION_DETAIL);
		netUtil.setParams("userid", getUserid());
		netUtil.setParams("informationid", item.getInformationid());
		netUtil.postRequest("", new RequestStringListener() {

			@Override
			public void onComplete(String result) {
				if (JSONUtil.isSuccess(result)) {
					try {
						parseDetail(result);
					} catch (JSONException e) {
						e.printStackTrace();
					}
				} else {
					showToast(JSONUtil.getMessage(result));
				}
			}
		});
	}

	protected void parseDetail(String result) throws JSONException {
		JSONObject data = JSONUtil.getData(result);
		String scoreAvg = data.getString("scoreavg");
		long scoreCount = data.getLong("scorecount");
		boolean isFollowing = data.getBoolean("isfollowing");
		myComment = new Gson().fromJson(data.getString("mycomment"),
				CommentItem.class);
		List<TagItem> list = new Gson().fromJson(data.getString("taglist"),
				new TypeToken<List<TagItem>>() {
				}.getType());
		for (int i = 0; i < list.size(); i++) {
			llTags.addView(getTagView(list.get(i)));
		}
		followerCount = data.getLong("followercount");
		btnFollowing.setText(isFollowing ? getText(R.string.activity_signup)
				: getText(R.string.activity_signup_cancel));
		rbScore.setRating(Float.valueOf(scoreAvg));
		tvScoreCount.setText(scoreAvg + getText(R.string.score_total)
				+ scoreCount + getText(R.string.people_feedback));
		btnEvaluation.setText((myComment == null) ? R.string.evaluation
				: R.string.modify);
		supportInvalidateOptionsMenu();
	}

	private View getTagView(TagItem tagItem) {
		Button button = new Button(getApplicationContext());
		button.setSingleLine(true);
		button.setText(tagItem.getName() + "(" + tagItem.getCount() + ")");
		return button;
	}

	private void initView() {
		llTags = getLinearLayout(R.id.activity_detail_tags);
		btnFollowing = getButton(R.id.activity_detail_following);
		btnEvaluation = getButton(R.id.activity_detail_evaluation);
		ibCall = getImageButton(R.id.activity_detail_call);
		ibMap = getImageButton(R.id.activity_detail_map);
		tvTitle = getTextView(R.id.activity_detail_title);
		tvTime = getTextView(R.id.activity_detail_time);
		tvDesc = getTextView(R.id.activity_detail_desc);
		tvScoreCount = getTextView(R.id.activity_detail_scorecount);
		rbScore = (RatingBar) findViewById(R.id.activity_detail_scoreavg);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.activity_detail, menu);
		if (item.getUserid() != LoginInfo.getInstance(getApplicationContext())
				.getUserid()) {
			menu.findItem(R.id.action_edit).setVisible(false);
		}
		return super.onCreateOptionsMenu(menu);
	}

	@Override
	public boolean onPrepareOptionsMenu(Menu menu) {
		menu.findItem(R.id.action_followers).setTitle(
				getString(R.string.activity_signup_people) + followerCount
						+ ")");
		return super.onPrepareOptionsMenu(menu);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case R.id.action_edit:
			edit();
			break;
		case R.id.action_followers:
			seeSignups();
			break;
		case R.id.action_author:
			seeAuthor();
			break;
		case R.id.action_report:
			isReport();
			break;
		default:
			break;
		}
		return super.onOptionsItemSelected(item);
	}

	private void edit() {
		Intent intent = new Intent(ActivityDetail.this,
				InformationReleaseActivity.class);
		intent.putExtra("item", item);
		startActivity(intent);
	}

	/**
	 * 查看报名者
	 */
	private void seeSignups() {
		Intent intent = new Intent();
		if (item.getUserid() == getUserid()) { // 如果当前用户是活动发起者,查看报名者的详细信息
			intent.setClass(ActivityDetail.this, ActivitySignUps.class);
		} else { // 否则只能看基本信息
			intent.setClass(ActivityDetail.this, UserList.class);
			intent.putExtra("api", JsonApi.INFORMATION_FOLLOWERS);
		}
		intent.putExtra("informationid", item.getInformationid());
		startActivity(intent);

	}

	private void seeAuthor() {
		Intent intent = new Intent(ActivityDetail.this,
				PersonHomeActivity.class);
		intent.putExtra("targetid", item.getUserid());
		startActivity(intent);
	}

	private void isReport() {
		final EditText etContent = new EditText(getApplicationContext());
		etContent.setHeight(72);
		etContent.setHint(R.string.say_something);
		new AlertDialog.Builder(ActivityDetail.this)
				.setTitle(R.string.report)
				.setView(etContent)
				.setPositiveButton(android.R.string.ok,
						new DialogInterface.OnClickListener() {

							@Override
							public void onClick(DialogInterface dialog,
									int which) {
								report(getString(etContent));
							}

						}).setNegativeButton(android.R.string.cancel, null)
				.show();
	}

	protected void report(String string) {
		NetUtil netUtil = new NetUtil(ActivityDetail.this, JsonApi.REPORT);
		netUtil.setParams("userid", getUserid());
		netUtil.setParams("informationid", item.getInformationid());
		netUtil.setParams("content", string);
		netUtil.postRequest("", new RequestStringListener() {

			@Override
			public void onComplete(String result) {
				LogUtil.showLog(result);
			}
		});
		showSimpleDialog(getString(R.string.thanks));
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.activity_detail_call:
			call();
			break;
		case R.id.activity_detail_following:
			singUpOrCancel();
			break;
		case R.id.activity_detail_evaluation:
			evaluation();
			break;
		case R.id.activity_detail_map:
			showMap();
			break;
		default:
			break;
		}
	}

	private void showMap() {
		Intent intent = new Intent(ActivityDetail.this, InformationMap.class);
		intent.putExtra("item", item);
		startActivity(intent);
	}

	/**
	 * 评价/修改评价
	 */
	private void evaluation() {
		View view = LayoutInflater.from(ActivityDetail.this).inflate(
				R.layout.evaluation_dialog, null);
		final RatingBar rbEvaluationScore = (RatingBar) view
				.findViewById(R.id.evaluation_score);
		final EditText etEvaluationContent = (EditText) view
				.findViewById(R.id.evaluation_content);
		final EditText etEvaluationTag = (EditText) view
				.findViewById(R.id.evaluation_tag);
		Builder builder = new AlertDialog.Builder(ActivityDetail.this);
		builder.setView(view);
		builder.setPositiveButton(android.R.string.ok,
				new DialogInterface.OnClickListener() {

					@Override
					public void onClick(DialogInterface dialog, int which) {
						submitEvaluation(rbEvaluationScore, etEvaluationTag,
								etEvaluationContent);
						btnEvaluation.setText(R.string.modify);
						if (myComment == null) {
							myComment = new CommentItem();
						}
						myComment.setTags(ETUtil.getString(etEvaluationTag));
						myComment.setContent(ETUtil
								.getString(etEvaluationContent));
						myComment.setScore((int) rbEvaluationScore.getRating());
					}

				});
		if (myComment != null) {
			rbEvaluationScore.setRating(myComment.getScore());
			etEvaluationTag.setText(myComment.getTags());
			etEvaluationContent.setText(myComment.getContent());
			builder.setNegativeButton(R.string.comment_delete,
					new DialogInterface.OnClickListener() {

						@Override
						public void onClick(DialogInterface dialog, int which) {
							deleteEvaluation();
							btnEvaluation.setText(R.string.evaluation);
						}
					});
		}
		builder.create();
		builder.show();
	}

	protected void submitEvaluation(RatingBar rbEvaluationScore,
			EditText etEvaluationTag, EditText etEvaluationContent) {
		NetUtil netUtil = new NetUtil(ActivityDetail.this,
				JsonApi.INFORMATION_COMMENT);
		netUtil.setParams("userid", getUserid());
		netUtil.setParams("informationid", item.getInformationid());
		netUtil.setParams("content", ETUtil.getString(etEvaluationContent));
		netUtil.setParams("tags", ETUtil.getString(etEvaluationTag));
		netUtil.setParams("score", rbEvaluationScore.getRating());
		netUtil.postRequest("", new RequestStringListener() {

			@Override
			public void onComplete(String result) {
				LogUtil.showLog(result);
				if (JSONUtil.isSuccess(result)) {
					myComment = new Gson().fromJson(JSONUtil.getData(result)
							.toString(), CommentItem.class);
				} else {
					showToast(JSONUtil.getMessage(result));
					btnEvaluation.setText(R.string.evaluation);
				}
			}
		});
	}

	protected void deleteEvaluation() {
		NetUtil netUtil = new NetUtil(ActivityDetail.this,
				JsonApi.INFORMATION_COMMENTDELETE);
		netUtil.setParams("userid", getUserid());
		netUtil.setParams("informationid", myComment.getInformationid());
		netUtil.postRequest("", new RequestStringListener() {

			@Override
			public void onComplete(String result) {
				LogUtil.showLog(JSONUtil.getMessage(result));
			}
		});
		myComment = null;
	}

	/**
	 * 报名或者取消报名
	 */
	private void singUpOrCancel() {
		if (btnFollowing.getText().equals(getText(R.string.activity_signup))) {
			signUp();
		} else {
			cancelSingUp();
		}
	}

	/**
	 * 取消报名
	 */
	private void cancelSingUp() {
		NetUtil netUtil = new NetUtil(ActivityDetail.this,
				JsonApi.ACTIVITY_SIGN_UP_CANCEL);
		netUtil.setParams("userid", getUserid());
		netUtil.setParams("informationid", item.getInformationid());
		netUtil.postRequest(R.string.activity_signup_canceling,
				new RequestStringListener() {

					@Override
					public void onComplete(String result) {
						if (JSONUtil.isSuccess(result)) {
							btnFollowing.setText(R.string.activity_signup);
							followerCount--;
							supportInvalidateOptionsMenu();
						} else {
							showToast(getString(R.string.activity_signup_cancel_fail)
									+ JSONUtil.getMessage(result));
						}
					}
				});
	}

	/**
	 * 报名
	 */
	private void signUp() {
		View view = LayoutInflater.from(ActivityDetail.this).inflate(
				R.layout.activity_sign_up, null);
		final EditText etName = (EditText) view
				.findViewById(R.id.activity_sign_up_name);
		final EditText etPhone = (EditText) view
				.findViewById(R.id.activity_sign_up_phone);
		final EditText etEmail = (EditText) view
				.findViewById(R.id.activity_sign_up_email);
		final EditText etRemark = (EditText) view
				.findViewById(R.id.activity_sign_up_remark);
		final Spinner spAdult = (Spinner) view
				.findViewById(R.id.activity_sign_up_adult);
		final Spinner spChild = (Spinner) view
				.findViewById(R.id.activity_sign_up_child);
		LoginInfo loginInfo = LoginInfo.getInstance(getApplicationContext());
		etName.setText(loginInfo.getName());
		etPhone.setText(loginInfo.getPhone());
		etEmail.setText(loginInfo.getEmail());
		new AlertDialog.Builder(ActivityDetail.this)
				.setTitle(R.string.activity_signup)
				.setView(view)
				.setPositiveButton(android.R.string.ok,
						new DialogInterface.OnClickListener() {

							@Override
							public void onClick(DialogInterface dialog,
									int which) {
								String name = getString(etName);
								String phone = getString(etPhone);
								String email = getString(etEmail);
								String remark = getString(etRemark);
								int adult = getInt(spAdult);
								int child = getInt(spChild);
								signUp(name, phone, email, remark, adult, child);
							}

						}).setNegativeButton(android.R.string.cancel, null)
				.show();
	}

	protected void signUp(String name, String phone, String email,
			String remark, int adult, int child) {
		NetUtil netUtil = new NetUtil(ActivityDetail.this,
				JsonApi.ACTIVITY_SIGN_UP);
		netUtil.setParams("userid", getUserid());
		netUtil.setParams("informationid", item.getInformationid());
		netUtil.setParams("name", name);
		netUtil.setParams("phone", phone);
		netUtil.setParams("email", email);
		netUtil.setParams("remark", remark);
		netUtil.setParams("adult", adult);
		netUtil.setParams("child", child);
		netUtil.postRequest(R.string.activity_signuping,
				new RequestStringListener() {

					@Override
					public void onComplete(String result) {
						if (JSONUtil.isSuccess(result)) {
							btnFollowing
									.setText(R.string.activity_signup_cancel);
							followerCount++;
							supportInvalidateOptionsMenu();
						} else {
							showToast(getString(R.string.activity_signup_fail)
									+ JSONUtil.getMessage(result));
						}
					}
				});
	}

	private void call() {
		DialogUtil.getInstance().showSimpleDialog(ActivityDetail.this,
				getString(R.string.activity_call) + item.getPhone(),
				new DialogInterface.OnClickListener() {

					@Override
					public void onClick(DialogInterface dialog, int which) {
						Intent intent = new Intent(
								"android.intent.action.CALL", Uri.parse("tel:"
										+ item.getPhone()));
						startActivity(intent);
					}
				});
	}
}
