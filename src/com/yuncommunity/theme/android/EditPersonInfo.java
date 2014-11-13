package com.yuncommunity.theme.android;

import java.util.Calendar;
import java.util.Locale;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.oldfeel.base.BaseActivity;
import com.oldfeel.utils.JsonUtil;
import com.oldfeel.utils.NetUtil.RequestStringListener;
import com.yuncommunity.R;
import com.yuncommunity.conf.LoginInfo;
import com.yuncommunity.utils.UpdateUtils;

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
		LoginInfo loginInfo = LoginInfo.getInstance(getApplicationContext());
		etName.setText(loginInfo.getUserInfo().getName());
		etIntro.setText(loginInfo.getUserInfo().getIntroduction());
		etPhone.setText(loginInfo.getUserInfo().getPhone());
		etEmail.setText(loginInfo.getUserInfo().getEmail());
		etHouseNumber.setText(loginInfo.getUserInfo().getHousenumber());
		tvBirthday.setText(loginInfo.getUserInfo().getBirthday());
		spPermission.setSelection(loginInfo.getUserInfo().getPermission());
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
		LoginInfo loginInfo = LoginInfo.getInstance(getApplicationContext());
		loginInfo.getUserInfo().setName(getString(etName));
		loginInfo.getUserInfo().setIntroduction(getString(etIntro));
		loginInfo.getUserInfo().setPhone(getString(etPhone));
		loginInfo.getUserInfo().setHousenumber(getString(etHouseNumber));
		loginInfo.getUserInfo().setBirthday(super.getString(tvBirthday));
		loginInfo.getUserInfo().setPermission(
				spPermission.getSelectedItemPosition());
		UpdateUtils.update(EditPersonInfo.this,
				String.valueOf(getText(R.string.updating_personal_details)),
				new RequestStringListener() {

					@Override
					public void onComplete(String result) {
						if (JsonUtil.isSuccess(result)) {
							showToast(String
									.valueOf(getText(R.string.updated_personal_details)));
							setResult(PersonHomeActivity.EDIT_PERSON_INFO);
							finish();
						} else {
							showToast(getText(R.string.failed_update_personal_details)
									+ "," + JsonUtil.getData(result));
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
