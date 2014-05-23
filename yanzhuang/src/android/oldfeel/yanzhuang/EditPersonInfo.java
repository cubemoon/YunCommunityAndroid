package android.oldfeel.yanzhuang;

import android.oldfeel.yanzhuang.app.PersonInfo;
import android.oldfeel.yanzhuang.base.BaseActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.Spinner;

/**
 * 编辑个人资料
 * 
 * @author oldfeel
 * 
 */
public class EditPersonInfo extends BaseActivity {
	private EditText etName, etIntro, etPhone, etEmail, etHouseNumber,
			etBirthday;
	private Spinner spPermission;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.edit_person_info);
		initView();
		putDataToView();
	}

	private void initView() {
		etName = getEditText(R.id.edit_person_info_name);
		etIntro = getEditText(R.id.edit_person_info_intro);
		etPhone = getEditText(R.id.edit_person_info_phone);
		etEmail = getEditText(R.id.edit_person_info_email);
		etHouseNumber = getEditText(R.id.edit_person_info_housenumber);
		etBirthday = getEditText(R.id.edit_person_info_birthday);
		spPermission = getSpinner(R.id.edit_person_info_permission);
	}

	private void putDataToView() {
		PersonInfo personInfo = PersonInfo.getInstance(getApplicationContext());
		etName.setText(personInfo.getName());
		etIntro.setText(personInfo.getIntroduction());
		etPhone.setText(personInfo.getPhone());
		etEmail.setText(personInfo.getEmail());
		etHouseNumber.setText(personInfo.getHouseNumber());
		etBirthday.setText(personInfo.getBirthday());
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		return super.onCreateOptionsMenu(menu);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		return super.onOptionsItemSelected(item);
	}
}
