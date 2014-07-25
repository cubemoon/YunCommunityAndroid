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
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.yuncommunity.app.JsonApi;
import com.yuncommunity.app.Constant;
import com.yuncommunity.app.PersonInfo;
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
 * 活动/商家服务/个人服务详情
 * 
 * @author oldfeel
 * 
 */
public class InformationDetail extends BaseActivity implements OnClickListener {
	private LinearLayout llTags;
	private Button btnFollowing, btnEvaluation;
	private ImageButton ibCall, ibMap;
	private TextView tvTitle, tvTime, tvDesc, tvScoreCount;
	private RatingBar rbScore;
	private InformationItem item;
	private CommentItem myComment;
	private long followerCount;
	private long productCount;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.information_detail);
		setTitle(getText(R.string.details));
		item = (InformationItem) getIntent().getSerializableExtra("item");
		getSupportFragmentManager()
				.beginTransaction()
				.replace(R.id.information_detail_media,
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
				.replace(R.id.information_detail_commentlist, fragment)
				.commit();
	}

	private NetUtil getCommentListNetUtil() {
		NetUtil netUtil = new NetUtil(InformationDetail.this,
				JsonApi.INFORMATION_COMMENTLIST);
		netUtil.setParams("informationid", item.getInformationid());
		netUtil.setParams("userid", getUserid());
		return netUtil;
	}

	private void getDetail() {
		NetUtil netUtil = new NetUtil(InformationDetail.this,
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
		productCount = data.getLong("productcount");
		btnFollowing.setText(isFollowing ? getText(R.string.follow_cancel) : getText(R.string.follow));
		rbScore.setRating(Float.valueOf(scoreAvg));
		tvScoreCount.setText(scoreAvg + getText(R.string.score_total) + scoreCount + getText(R.string.people_feedback));
		btnEvaluation.setText((myComment == null) ? getText(R.string.feedback) : getText(R.string.modify));
		supportInvalidateOptionsMenu();
	}

	private View getTagView(TagItem tagItem) {
		Button button = new Button(getApplicationContext());
		button.setSingleLine(true);
		button.setText(tagItem.getName() + "(" + tagItem.getCount() + ")");
		return button;
	}

	private void initView() {
		llTags = getLinearLayout(R.id.information_detail_tags);
		btnFollowing = getButton(R.id.information_detail_following);
		btnEvaluation = getButton(R.id.information_detail_evaluation);
		ibCall = getImageButton(R.id.information_detail_call);
		ibMap = getImageButton(R.id.information_detail_map);
		tvTitle = getTextView(R.id.information_detail_title);
		tvTime = getTextView(R.id.information_detail_time);
		tvDesc = getTextView(R.id.information_detail_desc);
		tvScoreCount = getTextView(R.id.information_detail_scorecount);
		rbScore = (RatingBar) findViewById(R.id.information_detail_scoreavg);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.information_detail, menu);
		if (item.getUserid() != PersonInfo.getInstance(getApplicationContext())
				.getUserid()) {
			menu.findItem(R.id.action_edit).setVisible(false);
		}
		if (item.getInfotype() != Constant.TYPE_BUSINESS) {
			menu.findItem(R.id.action_product).setVisible(false);
		}
		return super.onCreateOptionsMenu(menu);
	}

	@Override
	public boolean onPrepareOptionsMenu(Menu menu) {
		menu.findItem(R.id.action_followers).setTitle(
				getText(R.string.follower)+"(" + followerCount + ")");
		menu.findItem(R.id.action_product).setTitle(getText(R.string.product)+"(" + productCount + ")");
		return super.onPrepareOptionsMenu(menu);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case R.id.action_edit:
			edit();
			break;
		case R.id.action_followers:
			seeFollowers();
			break;
		case R.id.action_author:
			seeAuthor();
			break;
		case R.id.action_report:
			isReport();
			break;
		case R.id.action_product:
			productList();
			break;
		default:
			break;
		}
		return super.onOptionsItemSelected(item);
	}

	/**
	 * 产品列表
	 */
	private void productList() {
		Intent intent = new Intent(InformationDetail.this, ProductList.class);
		intent.putExtra("item", item);
		startActivity(intent);
	}

	private void edit() {
		Intent intent = new Intent(InformationDetail.this,
				InformationReleaseActivity.class);
		intent.putExtra("item", item);
		startActivity(intent);
	}

	private void seeFollowers() {
		Intent intent = new Intent(InformationDetail.this, UserList.class);
		intent.putExtra("api", JsonApi.INFORMATION_FOLLOWERS);
		intent.putExtra("informationid", item.getInformationid());
		startActivity(intent);
	}

	private void seeAuthor() {
		Intent intent = new Intent(InformationDetail.this,
				PersonHomeActivity.class);
		intent.putExtra("targetid", item.getUserid());
		startActivity(intent);
	}

	private void isReport() {
		final EditText etContent = new EditText(getApplicationContext());
		etContent.setHeight(72);
		etContent.setHint(getText(R.string.say_something));
		new AlertDialog.Builder(InformationDetail.this).setTitle(getText(R.string.report))
				.setView(etContent)
				.setPositiveButton(getText(R.string.confirm), new DialogInterface.OnClickListener() {

					@Override
					public void onClick(DialogInterface dialog, int which) {
						report(getString(etContent));
					}

				}).setNegativeButton(getText(R.string.cancel), null).show();
	}

	protected void report(String string) {
		NetUtil netUtil = new NetUtil(InformationDetail.this, JsonApi.REPORT);
		netUtil.setParams("userid", getUserid());
		netUtil.setParams("informationid", item.getInformationid());
		netUtil.setParams("content", string);
		netUtil.postRequest("", new RequestStringListener() {

			@Override
			public void onComplete(String result) {
				LogUtil.showLog(result);
			}
		});
		showSimpleDialog(String.valueOf(getText(R.string.thanks)));
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.information_detail_call:
			call();
			break;
		case R.id.information_detail_following:
			following();
			break;
		case R.id.information_detail_evaluation:
			evaluation();
			break;
		case R.id.information_detail_map:
			showMap();
			break;
		default:
			break;
		}
	}

	private void showMap() {
		Intent intent = new Intent(InformationDetail.this, InformationMap.class);
		intent.putExtra("item", item);
		startActivity(intent);
	}

	/**
	 * 评价/修改评价
	 */
	private void evaluation() {
		View view = LayoutInflater.from(InformationDetail.this).inflate(
				R.layout.evaluation_dialog, null);
		final RatingBar rbEvaluationScore = (RatingBar) view
				.findViewById(R.id.evaluation_score);
		final EditText etEvaluationContent = (EditText) view
				.findViewById(R.id.evaluation_content);
		final EditText etEvaluationTag = (EditText) view
				.findViewById(R.id.evaluation_tag);
		Builder builder = new AlertDialog.Builder(InformationDetail.this);
		builder.setView(view);
		builder.setPositiveButton(getText(R.string.confirm), new DialogInterface.OnClickListener() {

			@Override
			public void onClick(DialogInterface dialog, int which) {
				submitEvaluation(rbEvaluationScore, etEvaluationTag,
						etEvaluationContent);
				btnEvaluation.setText(getText(R.string.modify));
				if (myComment == null) {
					myComment = new CommentItem();
				}
				myComment.setTags(ETUtil.getString(etEvaluationTag));
				myComment.setContent(ETUtil.getString(etEvaluationContent));
				myComment.setScore((int) rbEvaluationScore.getRating());
			}

		});
		if (myComment != null) {
			rbEvaluationScore.setRating(myComment.getScore());
			etEvaluationTag.setText(myComment.getTags());
			etEvaluationContent.setText(myComment.getContent());
			builder.setNegativeButton(getText(R.string.delete_comment),
					new DialogInterface.OnClickListener() {

						@Override
						public void onClick(DialogInterface dialog, int which) {
							deleteEvaluation();
							btnEvaluation.setText(getText(R.string.comment));
						}
					});
		}
		builder.create();
		builder.show();
	}

	protected void submitEvaluation(RatingBar rbEvaluationScore,
			EditText etEvaluationTag, EditText etEvaluationContent) {
		NetUtil netUtil = new NetUtil(InformationDetail.this,
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
					btnEvaluation.setText(getText(R.string.comment));
				}
			}
		});
	}

	protected void deleteEvaluation() {
		NetUtil netUtil = new NetUtil(InformationDetail.this,
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

	private void following() {
		boolean isFollowing;
		if (btnFollowing.getText().equals(getText(R.string.follow))) {
			isFollowing = true;
			btnFollowing.setText(getText(R.string.follow_cancel));
			followerCount++;
		} else {
			isFollowing = false;
			btnFollowing.setText(getText(R.string.follow));
			followerCount--;
		}
		supportInvalidateOptionsMenu();
		NetUtil netUtil = new NetUtil(InformationDetail.this,
				JsonApi.INFORMATION_FOLLOWING);
		netUtil.setParams("userid", getUserid());
		netUtil.setParams("informationid", item.getInformationid());
		netUtil.setParams("isfollowing", isFollowing);
		netUtil.postRequest("", new RequestStringListener() {

			@Override
			public void onComplete(String result) {
				if (JSONUtil.isSuccess(result)) {
					LogUtil.showLog(String.valueOf(getText(R.string.followed)));
				} else {
					LogUtil.showLog(JSONUtil.getMessage(result));
				}
			}
		});
	}

	private void call() {
		DialogUtil.getInstance().showSimpleDialog(InformationDetail.this,
				getText(R.string.call)+"?\n" + item.getPhone(),
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
