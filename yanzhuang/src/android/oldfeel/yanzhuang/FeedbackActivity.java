package android.oldfeel.yanzhuang;

import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.oldfeel.yanzhuang.app.Api;
import android.oldfeel.yanzhuang.base.BaseActivity;
import android.oldfeel.yanzhuang.util.DialogUtil;
import android.oldfeel.yanzhuang.util.JSONUtil;
import android.oldfeel.yanzhuang.util.NetUtil;
import android.oldfeel.yanzhuang.util.NetUtil.RequestStringListener;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.EditText;
import android.widget.TextView;

/**
 * 建议反馈
 * 
 * @author oldfeel
 * 
 */
public class FeedbackActivity extends BaseActivity {
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
			etContent.setError("必填");
			return;
		}
		NetUtil netUtil = new NetUtil(FeedbackActivity.this, Api.FEEDBACK);
		netUtil.setParams("userid", getUserid());
		netUtil.setParams("content", getString(etContent));
		netUtil.setParams("anonymous", cbIsAnonymous.isChecked());
		netUtil.postRequest("正在提交反馈", new RequestStringListener() {

			@Override
			public void onComplete(String result) {
				if (JSONUtil.isSuccess(result)) {
					DialogUtil.getInstance().showSimpleDialog(
							FeedbackActivity.this, "提交成功,感谢您的反馈",
							new OnClickListener() {

								@Override
								public void onClick(DialogInterface dialog,
										int which) {
									FeedbackActivity.this.finish();
								}
							});
				} else {
					showToast("提交失败," + JSONUtil.getMessage(result));
				}
			}
		});
	}
}
