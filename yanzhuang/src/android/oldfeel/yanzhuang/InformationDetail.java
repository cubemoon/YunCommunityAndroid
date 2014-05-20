package android.oldfeel.yanzhuang;

import android.oldfeel.yanzhuang.base.BaseActivity;
import android.oldfeel.yanzhuang.item.InformationListItem;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RatingBar;
import android.widget.TextView;

/**
 * 活动/商家服务/个人服务详情
 * 
 * @author oldfeel
 * 
 */
public class InformationDetail extends BaseActivity {
	private LinearLayout llTags;
	private ImageButton ibTagMore, ibCall;
	private TextView tvTitle, tvTime, tvDesc, tvEvaluationCount;
	private RatingBar rbEvaluation;
	private ListView lvComments;
	private InformationListItem item;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.information_detail);
		initView();
		getData();
	}

	private void getData() {
	}

	private void initView() {
		llTags = getLinearLayout(R.id.information_detail_tags);
		ibTagMore = getImageButton(R.id.information_detail_tagmore);
		ibCall = getImageButton(R.id.information_detail_call);
		tvTitle = getTextView(R.id.information_detail_title);
		tvTime = getTextView(R.id.information_detail_time);
		tvDesc = getTextView(R.id.information_detail_desc);
		tvEvaluationCount = getTextView(R.id.information_detail_evaluationcount);
		rbEvaluation = (RatingBar) findViewById(R.id.information_detail_evaluation);
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
}
