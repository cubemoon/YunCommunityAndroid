package com.yuncommunity.dialog;

import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.ListView;

import com.oldfeel.base.BaseDialogFragment;
import com.oldfeel.interfaces.FragmentListener;
import com.oldfeel.utils.ETUtil;
import com.oldfeel.utils.JsonUtil;
import com.oldfeel.utils.NetUtil;
import com.oldfeel.utils.NetUtil.RequestStringListener;
import com.yuncommunity.R;
import com.yuncommunity.adapter.TagHistoryAdapter;
import com.yuncommunity.adapter.TagRecommendAdapter;
import com.yuncommunity.conf.JsonApi;
import com.yuncommunity.conf.LoginInfo;
import com.yuncommunity.item.TagItem;

/**
 * 
 * @author oldfeel
 * 
 *         Create on: 2014年11月15日
 */
public class TagEdit extends BaseDialogFragment {
	private ImageButton ibCancel, ibComplete;
	private EditText etContent;
	private ListView lvHistory;
	private TagHistoryAdapter historyAdapter;
	private int infoType;
	private String tags;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setStyle(DialogFragment.STYLE_NO_TITLE,
				R.style.Theme_AppCompat_Light_DialogWhenLarge);
	}

	public static TagEdit newInstance(int infoType, String tags,
			FragmentListener fragmentListener) {
		TagEdit dialog = new TagEdit();
		dialog.fragmentListener = fragmentListener;
		dialog.infoType = infoType;
		dialog.tags = tags;
		return dialog;
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.tag_edit, null);
		ibCancel = getImageButton(view, R.id.tag_edit_cancel);
		ibComplete = getImageButton(view, R.id.tag_edit_complete);
		etContent = getEditText(view, R.id.tag_edit_content);
		lvHistory = getListView(view, R.id.tag_edit_history);
		return view;
	}

	@Override
	public void onActivityCreated(Bundle bundle) {
		super.onActivityCreated(bundle);
		etContent.setText(tags);
		ibCancel.setOnClickListener(clickListener);
		ibComplete.setOnClickListener(clickListener);
		lvHistory.addHeaderView(getHeaderView());
		historyAdapter = new TagHistoryAdapter(getActivity());
		lvHistory.setAdapter(historyAdapter);
		lvHistory.setOnItemClickListener(itemClickListener);
		getHistory();
	}

	/**
	 * 获取我发布过的商品标签历史
	 */
	private void getHistory() {
		NetUtil netUtil = new NetUtil(getActivity(), JsonApi.TAG_HISTORY);
		netUtil.setParams("userid", LoginInfo.getInstance(getActivity())
				.getUserId());
		netUtil.setParams("infotype", infoType);
		netUtil.postRequest("", new RequestStringListener() {

			@Override
			public void onComplete(String result) {
				if (JsonUtil.isSuccess(result)) {
					historyAdapter.addResult(0, result);
				}
			}
		});
	}

	private View getHeaderView() {
		View view = LayoutInflater.from(getActivity()).inflate(
				R.layout.tag_history_header, null);
		GridView gvRecommend = (GridView) view
				.findViewById(R.id.tag_history_header);
		TagRecommendAdapter adapter = new TagRecommendAdapter(getActivity());
		gvRecommend.setAdapter(adapter);
		gvRecommend.setOnItemClickListener(itemClickListener);
		return view;
	}

	private OnItemClickListener itemClickListener = new OnItemClickListener() {

		@Override
		public void onItemClick(AdapterView<?> parent, View view, int position,
				long id) {
			if (parent instanceof ListView) {
				position = position - ((ListView) parent).getHeaderViewsCount();
			}
			TagItem item = (TagItem) parent.getAdapter().getItem(position);
			if (ETUtil.isEmpty(etContent)) {
				etContent.append(item.getName());
			} else {
				etContent.append("," + item.getName());
			}
			ETUtil.setEnd(etContent);
		}
	};

	private OnClickListener clickListener = new OnClickListener() {

		@Override
		public void onClick(View v) {
			switch (v.getId()) {
			case R.id.tag_edit_cancel:
				dismiss();
				break;
			case R.id.tag_edit_complete:
				fragmentListener.onComplete(getString(etContent));
				dismiss();
				break;
			default:
				break;
			}
		}
	};
}
