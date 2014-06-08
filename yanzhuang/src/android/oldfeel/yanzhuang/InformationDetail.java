package android.oldfeel.yanzhuang;

import java.util.List;

import org.json.JSONException;
import org.json.JSONObject;

import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.oldfeel.yanzhuang.app.Constant;
import android.oldfeel.yanzhuang.app.JsonApi;
import android.oldfeel.yanzhuang.app.PersonInfo;
import android.oldfeel.yanzhuang.base.BaseActivity;
import android.oldfeel.yanzhuang.fragment.InformationMedia;
import android.oldfeel.yanzhuang.fragment.list.CommentListFragment;
import android.oldfeel.yanzhuang.item.CommentItem;
import android.oldfeel.yanzhuang.item.InformationItem;
import android.oldfeel.yanzhuang.item.TagItem;
import android.oldfeel.yanzhuang.util.DialogUtil;
import android.oldfeel.yanzhuang.util.ETUtil;
import android.oldfeel.yanzhuang.util.JSONUtil;
import android.oldfeel.yanzhuang.util.LogUtil;
import android.oldfeel.yanzhuang.util.NetUtil;
import android.oldfeel.yanzhuang.util.NetUtil.RequestStringListener;
import android.oldfeel.yanzhuang.util.StringUtil;
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
		setTitle("详情");
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
		btnFollowing.setText(isFollowing ? "取消关注" : "关注");
		rbScore.setRating(Float.valueOf(scoreAvg));
		tvScoreCount.setText(scoreAvg + "分,共" + scoreCount + "人评价");
		btnEvaluation.setText((myComment == null) ? "评价" : "修改");
		invalidateOptionsMenu();
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
				"关注者(" + followerCount + ")");
		menu.findItem(R.id.action_product).setTitle("产品(" + productCount + ")");
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
		etContent.setHint("说点什么吧");
		new AlertDialog.Builder(InformationDetail.this).setTitle("举报")
				.setView(etContent)
				.setPositiveButton("确定", new DialogInterface.OnClickListener() {

					@Override
					public void onClick(DialogInterface dialog, int which) {
						report(getString(etContent));
					}

				}).setNegativeButton("取消", null).show();
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
		showSimpleDialog("非常感谢!");
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
		builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {

			@Override
			public void onClick(DialogInterface dialog, int which) {
				submitEvaluation(rbEvaluationScore, etEvaluationTag,
						etEvaluationContent);
				btnEvaluation.setText("修改");
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
			builder.setNegativeButton("删除评论",
					new DialogInterface.OnClickListener() {

						@Override
						public void onClick(DialogInterface dialog, int which) {
							deleteEvaluation();
							btnEvaluation.setText("评价");
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
					btnEvaluation.setText("评价");
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
		if (btnFollowing.getText().equals("关注")) {
			isFollowing = true;
			btnFollowing.setText("取消关注");
			followerCount++;
		} else {
			isFollowing = false;
			btnFollowing.setText("关注");
			followerCount--;
		}
		invalidateOptionsMenu();
		NetUtil netUtil = new NetUtil(InformationDetail.this,
				JsonApi.INFORMATION_FOLLOWING);
		netUtil.setParams("userid", getUserid());
		netUtil.setParams("informationid", item.getInformationid());
		netUtil.setParams("isfollowing", isFollowing);
		netUtil.postRequest("", new RequestStringListener() {

			@Override
			public void onComplete(String result) {
				if (JSONUtil.isSuccess(result)) {
					LogUtil.showLog("关注成功");
				} else {
					LogUtil.showLog(JSONUtil.getMessage(result));
				}
			}
		});
	}

	private void call() {
		DialogUtil.getInstance().showSimpleDialog(InformationDetail.this,
				"拨打电话联系?\n" + item.getPhone(),
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
