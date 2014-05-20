package android.oldfeel.yanzhuang.adapter;

import android.content.Context;
import android.oldfeel.yanzhuang.R;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

/**
 * 侧滑菜单适配器
 * 
 * @author oldfeel
 * 
 */
public class DrawerListAdapter extends BaseAdapter {
	private Context context;
	private String[] menus;
	private int selected;

	public DrawerListAdapter(Context context) {
		this.context = context;
		menus = context.getResources().getStringArray(R.array.drawer_menus);
	}

	@Override
	public int getCount() {
		return menus.length;
	}

	@Override
	public String getItem(int position) {
		return menus[position];
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		convertView = LayoutInflater.from(context).inflate(
				R.layout.single_text_light, null);
		TextView textView = (TextView) convertView.findViewById(R.id.text);
		textView.setText(menus[position]);
		if (selected == position) {
			textView.setBackgroundResource(R.color.green);
		} else {
			textView.setBackgroundResource(R.color.white);
		}
		return convertView;
	}

	public void setSelected(int selected) {
		this.selected = selected;
		notifyDataSetChanged();
	}

}
