package android.oldfeel.yanzhuang;

import java.util.Calendar;
import java.util.Locale;

import android.app.DatePickerDialog;
import android.oldfeel.yanzhuang.app.PersonInfo;
import android.oldfeel.yanzhuang.base.BaseActivity;
import android.oldfeel.yanzhuang.util.JSONUtil;
import android.oldfeel.yanzhuang.util.NetUtil.RequestStringListener;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

/**
 * 编辑个人资料
 * 
 * @author oldfeel
 * 
 */
public class EditPersonInfo extends BaseActivity implements OnClickListener {
	private EditText etName, etIntro, etPhone, etEmail, etHouseNumber;
	private TextView tvBirthday;
	private Spinner spPermission;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.edit_person_info);
		initView();
		initListener();
		putDataToView();
	}

	private void initListener() {
		tvBirthday.setOnClickListener(this);
	}

	private void initView() {
		etName = getEditText(R.id.edit_person_info_name);
		etIntro = getEditText(R.id.edit_person_info_intro);
		etPhone = getEditText(R.id.edit_person_info_phone);
		etEmail = getEditText(R.id.edit_person_info_email);
		etHouseNumber = getEditText(R.id.edit_person_info_housenumber);
		tvBirthday = getTextView(R.id.edit_person_info_birthday);
		spPermission = getSpinner(R.id.edit_person_info_permission);
	}

	private void putDataToView() {
		PersonInfo personInfo = PersonInfo.getInstance(getApplicationContext());
		etName.setText(personInfo.getName());
		etIntro.setText(personInfo.getIntroduction());
		etPhone.setText(personInfo.getPhone());
		etEmail.setText(personInfo.getEmail());
		etHouseNumber.setText(personInfo.getHouseNumber());
		tvBirthday.setText(personInfo.getBirthday());
		spPermission.setSelection(personInfo.getPermission());
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.edit_person_info, menu);
		return super.onCreateOptionsMenu(menu);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case R.id.action_complete:
			complete();
			break;

		default:
			break;
		}
		return super.onOptionsItemSelected(item);
	}

	private void complete() {
		PersonInfo personInfo = PersonInfo.getInstance(getApplicationContext());
		personInfo.setName(getString(etName));
		personInfo.setIntroduction(getString(etIntro));
		personInfo.setPhone(getString(etPhone));
		personInfo.setHouseNumber(getString(etHouseNumber));
		personInfo.setBirthday(super.getString(tvBirthday));
		personInfo.setPermission(spPermission.getSelectedItemPosition());
		PersonInfo.update(EditPersonInfo.this, "正在更新个人资料",
				new RequestStringListener() {

					@Override
					public void onComplete(String result) {
						if (JSONUtil.isSuccess(result)) {
							showToast("更新用户信息成功");
							setResult(PersonHomeActivity.EDIT_PERSON_INFO);
							finish();
						} else {
							showToast("更新用户信息失败," + JSONUtil.getMessage(result));
						}
					}
				});
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.edit_person_info_birthday:
			editBirthday();
			break;

		default:
			break;
		}
	}

	/**
	 * 编辑生日
	 */
	private void editBirthday() {
		String birthday = getString(tvBirthday);
		String[] array = birthday.split("-");
		Calendar calendar = Calendar.getInstance(Locale.getDefault());
		Integer currentYear = calendar.get(Calendar.YEAR);
		Integer currentMonth = calendar.get(Calendar.MONTH);
		Integer currentDay = calendar.get(Calendar.DAY_OF_MONTH);
		if (array.length == 3) {
			currentYear = Integer.valueOf(array[0]);
			currentMonth = Integer.valueOf(array[1]);
			currentDay = Integer.valueOf(array[2]);
		}
		DatePickerDialog.OnDateSetListener dateSetListener = new DatePickerDialog.OnDateSetListener() {

			@Override
			public void onDateSet(DatePicker view, int year, int monthOfYear,
					int dayOfMonth) {
				tvBirthday.setText(year + "-" + monthOfYear + "-" + dayOfMonth);
			}
		};
		new DatePickerDialog(EditPersonInfo.this, dateSetListener, currentYear,
				currentMonth, currentDay).show();
	}
}
