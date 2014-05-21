package android.oldfeel.yanzhuang;

import java.util.List;

import org.json.JSONException;
import org.json.JSONObject;

import android.oldfeel.yanzhuang.app.JsonApi;
import android.oldfeel.yanzhuang.app.PersonInfo;
import android.oldfeel.yanzhuang.base.BaseActivity;
import android.oldfeel.yanzhuang.item.CommentItem;
import android.oldfeel.yanzhuang.item.InformationItem;
import android.oldfeel.yanzhuang.item.TagItem;
import android.oldfeel.yanzhuang.util.JSONUtil;
import android.oldfeel.yanzhuang.util.NetUtil;
import android.oldfeel.yanzhuang.util.NetUtil.RequestStringListener;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ListView;
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
	private Button btnFollowing, btnMyComment;
	private ImageButton ibTagMore, ibCall;
	private TextView tvTitle, tvTime, tvDesc, tvScoreCount;
	private RatingBar rbScore;
	private ListView lvComments;
	private InformationItem item;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.information_detail);
		item = (InformationItem) getIntent().getSerializableExtra("item");
		initView();
		initListener();
		putDataToView();
		getDetail();
		getCommentList();
	}

	private void initListener() {
		btnFollowing.setOnClickListener(this);
		btnMyComment.setOnClickListener(this);
		ibCall.setOnClickListener(this);
		ibTagMore.setOnClickListener(this);
	}

	private void putDataToView() {
		tvTitle.setText(item.getTitle());
		tvTime.setText(item.getTime());
		tvDesc.setText(item.getDescription());
	}

	private void getCommentList() {
//		NetUtil netUtil = new NetUtil(InformationDetail.this,
//				JsonApi.INFORMATION_COMMENTLIST);
//		netUtil.setParams("informationid", item.getInformationid());
//		netUtil.setParams("page", 0);
	}

	private void getDetail() {
		NetUtil netUtil = new NetUtil(InformationDetail.this,
				JsonApi.INFORMATION_DETAIL);
		netUtil.setParams("userid",
				PersonInfo.getInstance(getApplicationContext()).getUserid());
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
		CommentItem myComment = new Gson().fromJson(
				data.getString("mycomment"), CommentItem.class);
		List<TagItem> list = new Gson().fromJson(data.getString("taglist"),
				new TypeToken<List<TagItem>>() {
				}.getType());
		for (int i = 0; i < list.size(); i++) {
			if (i >= 3) {
				break;
			}
			llTags.addView(getTagView(list.get(i)));
		}
		btnFollowing.setText(isFollowing ? "取消关注" : "关注");
		rbScore.setRating(Float.valueOf(scoreAvg));
		tvScoreCount.setText(scoreAvg + "分,共" + scoreCount + "人评价");
		btnMyComment.setText((myComment == null) ? "评价" : "修改");
	}

	private View getTagView(TagItem tagItem) {
		Button button = new Button(getApplicationContext());
		button.setText(tagItem.getName() + "(" + tagItem.getCount() + ")");
		return button;
	}

	private void initView() {
		llTags = getLinearLayout(R.id.information_detail_tags);
		btnFollowing = getButton(R.id.information_detail_following);
		btnMyComment = getButton(R.id.information_detail_mycomment);
		ibTagMore = getImageButton(R.id.information_detail_tagmore);
		ibCall = getImageButton(R.id.information_detail_call);
		tvTitle = getTextView(R.id.information_detail_title);
		tvTime = getTextView(R.id.information_detail_time);
		tvDesc = getTextView(R.id.information_detail_desc);
		tvScoreCount = getTextView(R.id.information_detail_scorecount);
		rbScore = (RatingBar) findViewById(R.id.information_detail_scoreavg);
		lvComments = getListView(R.id.information_detail_comments);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.information_detail, menu);
		return super.onCreateOptionsMenu(menu);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case R.id.action_edit:

			break;
		case R.id.action_followers:

			break;
		default:
			break;
		}
		return super.onOptionsItemSelected(item);
	}

	@Override
	public void onClick(View v) {
	}
}
