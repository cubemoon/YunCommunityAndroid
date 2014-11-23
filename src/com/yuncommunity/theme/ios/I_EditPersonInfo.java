package com.yuncommunity.theme.ios;

import java.util.Calendar;
import java.util.Locale;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.google.gson.Gson;
import com.oldfeel.utils.JsonUtil;
import com.oldfeel.utils.NetUtil;
import com.oldfeel.utils.NetUtil.RequestStringListener;
import com.yuncommunity.R;
import com.yuncommunity.conf.JsonApi;
import com.yuncommunity.conf.LoginInfo;
import com.yuncommunity.theme.android.A_PersonHomeActivity;
import com.yuncommunity.theme.ios.base.I_BaseActivity;

/**
 * 
 * @author oldfeel
 * 
 *         Create on: 2014年11月15日
 */
public class I_EditPersonInfo extends I_BaseActivity implements OnClickListener {
	private EditText etName, etIntro, etPhone, etEmail, etHouseNumber;
	private TextView tvBirthday;
	private Spinner spPermission;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentLayout(R.layout.i_edit_personinfo);
		initTop();
		initView();
		initListener();
		putDataToView();
	}

	private void initTop() {
		btnRight.setText("完成");
		btnRight.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				complete();
			}
		});
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

	private void complete() {
		LoginInfo loginInfo = LoginInfo.getInstance(getApplicationContext());
		loginInfo.getUserInfo().setName(getString(etName));
		loginInfo.getUserInfo().setIntroduction(getString(etIntro));
		loginInfo.getUserInfo().setPhone(getString(etPhone));
		loginInfo.getUserInfo().setHousenumber(getString(etHouseNumber));
		loginInfo.getUserInfo().setBirthday(super.getString(tvBirthday));
		loginInfo.getUserInfo().setPermission(
				spPermission.getSelectedItemPosition());
		NetUtil netUtil = new NetUtil(I_EditPersonInfo.this,
				JsonApi.EDIT_PERSON_INFO);
		netUtil.setParams("userinfo",
				new Gson().toJson(loginInfo.getUserInfo()));
		netUtil.postRequest(getString(R.string.updating_personal_details),
				new RequestStringListener() {

					@Override
					public void onComplete(String result) {
						if (JsonUtil.isSuccess(result)) {
							showToast(String
									.valueOf(getText(R.string.updated_personal_details)));
							setResult(A_PersonHomeActivity.EDIT_PERSON_INFO);
							onBackPressed();
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
		new DatePickerDialog(I_EditPersonInfo.this, dateSetListener,
				currentYear, currentMonth, currentDay).show();
	}
}
