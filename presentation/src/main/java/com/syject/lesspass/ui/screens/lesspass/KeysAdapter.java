package com.syject.lesspass.ui.screens.lesspass;

import android.support.v7.util.SortedList;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import com.syject.data.entities.Options;
import com.syject.lesspass.R;

import java.util.Comparator;
import java.util.List;

public class KeysAdapter extends RecyclerView.Adapter<KeysAdapter.ViewHolder> {

    private KeysCallBack callBack;
    private final Comparator<Options> comparator;
    private final SortedList<Options> sortedList = new SortedList<>(Options.class, new SortedList.Callback<Options>() {
        @Override
        public int compare(Options a, Options b) {
            return comparator.compare(a, b);
        }

        @Override
        public void onInserted(int position, int count) {
            notifyItemRangeInserted(position, count);
        }

        @Override
        public void onRemoved(int position, int count) {
            notifyItemRangeRemoved(position, count);
        }

        @Override
        public void onMoved(int fromPosition, int toPosition) {
            notifyItemMoved(fromPosition, toPosition);
        }

        @Override
        public void onChanged(int position, int count) {
            notifyItemRangeChanged(position, count);
        }

        @Override
        public boolean areContentsTheSame(Options oldItem, Options newItem) {
            return oldItem.equals(newItem);
        }

        @Override
        public boolean areItemsTheSame(Options item1, Options item2) {
            return item1 == item2;
        }
    });

    class ViewHolder extends RecyclerView.ViewHolder {

        private Options options;

        public ImageButton removeButton;
        public TextView siteTextView;
        public TextView loginTextView;

        public ViewHolder(View v) {
            super(v);
            siteTextView = (TextView) v.findViewById(R.id.list_item_site_text_view);
            loginTextView = (TextView) v.findViewById(R.id.list_item_login_text_view);

            removeButton = (ImageButton) v.findViewById(R.id.list_item_remove_image_button);
            removeButton.setOnClickListener(view -> callBack.onRemoveButtonClick(options));
        }

        private void bind(Options options) {
            this.options = options;
            siteTextView.setText(options.getSite());
            loginTextView.setText(options.getLogin());
        }
    }

    public KeysAdapter(List<Options> optionsList, Comparator<Options> comparator) {
        this.comparator = comparator;
        add(optionsList);
    }

    @Override
    public KeysAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_keys, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.bind(sortedList.get(position));
    }

    @Override
    public int getItemCount() {
        return sortedList.size();
    }

    public void add(Options model) {
        sortedList.add(model);
    }

    public void remove(Options model) {
        sortedList.remove(model);
    }

    public void add(List<Options> models) {
        sortedList.addAll(models);
    }

    public void remove(List<Options> models) {
        sortedList.beginBatchedUpdates();
        for (Options model : models) {
            sortedList.remove(model);
        }
        sortedList.endBatchedUpdates();
    }

    public void replaceAll(List<Options> models) {
        sortedList.beginBatchedUpdates();
        for (int i = sortedList.size() - 1; i >= 0; i--) {
            final Options model = sortedList.get(i);
            if (!models.contains(model)) {
                sortedList.remove(model);
            }
        }
        sortedList.addAll(models);
        sortedList.endBatchedUpdates();
    }

    public interface KeysCallBack {
        void onRemoveButtonClick(Options options);
    }

    public void setCallBack(KeysCallBack callBack) {
        this.callBack = callBack;
    }
}
