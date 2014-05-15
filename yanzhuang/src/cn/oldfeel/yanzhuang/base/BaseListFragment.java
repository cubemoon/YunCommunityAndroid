package cn.oldfeel.yanzhuang.base;

import uk.co.senab.actionbarpulltorefresh.library.ActionBarPullToRefresh;
import uk.co.senab.actionbarpulltorefresh.library.PullToRefreshLayout;
import uk.co.senab.actionbarpulltorefresh.library.listeners.OnRefreshListener;
import android.app.Fragment;
import android.app.ListFragment;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AbsListView.OnScrollListener;
import android.widget.ListView;
import android.widget.ProgressBar;
import cn.oldfeel.yanzhuang.app.Constant;
import cn.oldfeel.yanzhuang.util.DialogUtil;
import cn.oldfeel.yanzhuang.util.JSONUtil;
import cn.oldfeel.yanzhuang.util.NetUtil;
import cn.oldfeel.yanzhuang.util.NetUtil.OnNetFailListener;
import cn.oldfeel.yanzhuang.util.NetUtil.RequestStringListener;

/**
 * 下拉刷新,上拉加载更多的fragment
 * 
 * @author oldfeel
 * 
 *         Created on: 2014年3月2日
 */
public abstract class BaseListFragment extends ListFragment implements
		OnRefreshListener, OnScrollListener {

	protected NetUtil netUtil;
	protected BaseBaseAdapter<?> adapter;
	private PullToRefreshLayout mPullToRefreshLayout;
	private int lastVisibleIndex;
	private int page;
	private ProgressBar progressBar;

	@Override
	public void onViewCreated(View view, Bundle savedInstanceState) {
		super.onViewCreated(view, savedInstanceState);
		ViewGroup viewGroup = (ViewGroup) view;
		// As we're using a ListFragment we create a PullToRefreshLayout
		// manually
		mPullToRefreshLayout = new PullToRefreshLayout(viewGroup.getContext());
		// We can now setup the PullToRefreshLayout
		ActionBarPullToRefresh
				.from((ActionBarActivity) getActivity())
				// We need to insert the PullToRefreshLayout into the Fragment's
				// ViewGroup
				.insertLayoutInto(viewGroup)
				// Here we mark just the ListView and it's Empty View as
				// pullable
				.theseChildrenArePullable(android.R.id.list, android.R.id.empty)
				.listener(this).setup(mPullToRefreshLayout);
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		progressBar = new ProgressBar(getActivity());
		getListView().addFooterView(progressBar);
		initAdapter();
		setListAdapter(adapter);
		setListShownNoAnimation(true);
		getListView().setOnScrollListener(this);
		getListView().setOnCreateContextMenuListener(this);
		mPullToRefreshLayout.setRefreshing(true);
		if (netUtil != null) {
			getData(0);
		}
	}

	public void setNetUtil(NetUtil netUtil) {
		this.netUtil = netUtil;
		getData(0);
	}

	public void getData(final int page) {
		this.page = page;
		if (adapter.isAddOver()) {
			return;
		}
		netUtil.setPage(page * (int) Constant.PAGE_SIZE);
		netUtil.setOnNetFailListener(new OnNetFailListener() {

			@Override
			public void onTimeOut() {
				DialogUtil.getInstance().showToast(getActivity(), "网络链接超时");
				refreshComplete();
			}

			@Override
			public void onError() {
				DialogUtil.getInstance().showToast(getActivity(), "网络连接错误");
				refreshComplete();
			}

			@Override
			public void cancel() {
				refreshComplete();
			}
		});
		netUtil.postRequest("", new RequestStringListener() {

			@Override
			public void onComplete(String result) {
				if (JSONUtil.isSuccess(result)) {
					adapter.addResult(page, result);
				} else {
					showToast("加载失败," + JSONUtil.getMessage(result));
				}
				refreshComplete();
			}
		});
	}

	protected void refreshComplete() {
		if (adapter.getCount() < Constant.PAGE_SIZE) {
			if (isVisible() && getListView() != null && progressBar != null) {
				getListView().removeFooterView(progressBar);
			}
		}
		mPullToRefreshLayout.setRefreshComplete();
	}

	@Override
	public void onRefreshStarted(View view) {
		getData(0);
	}

	@Override
	public void onScrollStateChanged(AbsListView view, int scrollState) {
		if (scrollState == OnScrollListener.SCROLL_STATE_IDLE
				&& lastVisibleIndex == getListAdapter().getCount()) {
			if (!adapter.isAddOver()) {
				getData(++page);
			} else { // 加载完成后移除底部进度条
				getListView().removeFooterView(progressBar);
			}
		}
	}

	@Override
	public void onScroll(AbsListView view, int firstVisibleItem,
			int visibleItemCount, int totalItemCount) {
		lastVisibleIndex = firstVisibleItem + visibleItemCount - 1;
		if (listener != null) {
			listener.scrollTo(firstVisibleItem);
		}
	}

	@Override
	public void onListItemClick(ListView l, View v, int position, long id) {
		super.onListItemClick(l, v, position, id);
		onItemClick(position);
	}

	public void showToast(String text) {
		DialogUtil.getInstance().showToast(getActivity(), text);
	}

	public abstract void onItemClick(int position);

	public abstract void initAdapter();

	/**
	 * 滑动监听
	 * 
	 * @author oldfeel
	 * 
	 *         Create on: 2014年3月11日
	 */
	public interface ScrollListener {
		public void scrollTo(int position);
	}

	private ScrollListener listener;

	public void setScrollListener(ScrollListener listener) {
		this.listener = listener;
	}

	public interface onCreatedListener {
		public void created(Fragment fragment);
	}

	protected onCreatedListener createdListener;

	public void setOnCreatedListener(onCreatedListener createdListener) {
		this.createdListener = createdListener;
	}
}
