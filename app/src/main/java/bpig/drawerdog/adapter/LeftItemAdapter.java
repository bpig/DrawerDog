package bpig.drawerdog.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import bpig.drawerdog.R;
import bpig.drawerdog.dao.LeftMenuItem;

public class LeftItemAdapter extends BaseAdapter {
    private Context mContext;
    private List<LeftMenuItem> mItemList;

    public LeftItemAdapter(Context context, List<LeftMenuItem> itemList) {
        super();
        this.mContext = context;
        this.mItemList = itemList;
    }

    @Override
    public int getCount() {
        if (this.mItemList != null) {
            return mItemList.size();
        }
        return 0;
    }

    @Override
    public Object getItem(int position) {
        if (mItemList != null) {
            return mItemList.get(position);
        }
        return null;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup paret) {
        ItemViewHold hold;
        if (convertView == null) {
            hold = new ItemViewHold();
            convertView = LayoutInflater.from(mContext).inflate(R.layout.item_left_menu, null);
            convertView.setTag(hold);
        } else {
            hold = (ItemViewHold) convertView.getTag();
        }
        hold.mImageView = (ImageView) convertView.findViewById(R.id.left_item_icon);
        hold.mTextView = (TextView) convertView.findViewById(R.id.left_item_name);

        hold.mImageView.setImageResource(mItemList.get(position).getItemImage());
        hold.mTextView.setText(mItemList.get(position).getItemName());

        return convertView;
    }

    private static class ItemViewHold {
        public ImageView mImageView;
        public TextView mTextView;
    }
}
