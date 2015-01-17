package com.yuncommunity.theme.android;

import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.EditText;
import android.widget.TextView;

import com.oldfeel.utils.DialogUtil;
import com.oldfeel.utils.JsonUtil;
import com.oldfeel.utils.NetUtil;
import com.oldfeel.utils.NetUtil.RequestStringListener;
import com.yuncommunity.R;
import com.yuncommunity.conf.JsonApi;
import com.yuncommunity.conf.LoginInfo;
import com.yuncommunity.theme.android.base.A_BaseActivity;

/**
 * 建议反馈
 * 
 * @author oldfeel
 * 
 */
public class A_FeedbackActivity extends A_BaseActivity {
	private EditText etContent;
	private TextView tvAnonymous;
	private CheckBox cbIsAnonymous;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.feedback);
		etContent = getEditText(R.id.feedback_content);
		tvAnonymous = getTextView(R.id.feedback_anonymous);
		cbIsAnonymous = super.getCheckBox(R.id.feedback_isanonymous);
		cbIsAnonymous.setOnCheckedChangeListener(new OnCheckedChangeListener() {

			@Override
			public void onCheckedChanged(CompoundButton buttonView,
					boolean isChecked) {
				if (isChecked) {
					tvAnonymous.setText(R.string.feedback_anonymous);
				} else {
					tvAnonymous.setText(R.string.feedback_not_anonymous);
				}
			}
		});
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.feedback, menu);
		return super.onCreateOptionsMenu(menu);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case R.id.action_complete:
			submitFeedback();
			break;

		default:
			break;
		}
		return super.onOptionsItemSelected(item);
	}

	/**
	 * 提交反馈
	 */
	private void submitFeedback() {
		if (isEmpty(etContent)) {
			etContent.setError(getText(R.string.not_optional));
			return;
		}
		NetUtil netUtil = new NetUtil(A_FeedbackActivity.this, JsonApi.FEEDBACK);
		netUtil.setParams("userid",
				LoginInfo.getInstance(getApplicationContext()).getUserId());
		netUtil.setParams("content", getString(etContent));
		netUtil.setParams("anonymous", cbIsAnonymous.isChecked());
		netUtil.postRequest(
				String.valueOf(getText(R.string.submiting_feedback)),
				new RequestStringListener() {

					@Override
					public void onComplete(String result) {
						if (JsonUtil.isSuccess(result)) {
							DialogUtil
									.getInstance()
									.showSimpleDialog(
											A_FeedbackActivity.this,
											String.valueOf(getText(R.string.submitted_feedback)),
											new OnClickListener() {

												@Override
												public void onClick(
														DialogInterface dialog,
														int which) {
													A_FeedbackActivity.this
															.finish();
												}
											});
						} else {
							showToast(getText(R.string.failed_submit_feedback)
									+ "," + JsonUtil.getData(result));
						}
					}
				});
	}
}
