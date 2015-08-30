package bpig.drawerdog.adapter;

import android.graphics.drawable.Drawable;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import bpig.drawerdog.Item;
import bpig.drawerdog.R;

/**
 * Created: shuai.li(286287737@qq.com)
 * Date: 2015-08-29
 * Time: 19:20
 */
public class ItemAdapter extends RecyclerView.Adapter<ItemAdapter.ViewHolder> {
    private Item[] items;
    private Listener listener;

    public void setListener(Listener listener) {
        this.listener = listener;
    }

    public ItemAdapter(Item[] items) {
        this.items = items;

    }

    @Override
    public int getItemCount() {
        return items.length;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        CardView cardView = (CardView) LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_card, parent, false);
        return new ViewHolder(cardView);
    }

    @Override
    public void onViewRecycled(ViewHolder holder) {
        super.onViewRecycled(holder);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        CardView cardView = holder.cardView;
        ImageView imageView = (ImageView) cardView.findViewById(R.id.item_image);
        Drawable drawable = cardView.getResources().getDrawable(items[position].getImageId());
        imageView.setImageDrawable(drawable);
        TextView textView = (TextView) cardView.findViewById(R.id.item_time);
        textView.setText(items[position].getTimestamp());
        cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (listener != null) {
                    listener.onClick(position);
                }
            }
        });
    }

    public interface Listener {
        void onClick(int position);
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private CardView cardView;

        public ViewHolder(CardView cardView) {
            super(cardView);
            this.cardView = cardView;
        }
    }

}
