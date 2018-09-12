package com.peeru.labs.task.ui.adapter;


import android.databinding.DataBindingUtil;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.peeru.labs.task.R;
import com.peeru.labs.task.databinding.DetailListItemTypeTitleBinding;

import java.util.ArrayList;


public class HorizontalRecyclerAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private ArrayList<String> songList = new ArrayList<String>();

    public HorizontalRecyclerAdapter() {
    }

    public void updateList(ArrayList<String> list) {
        this.songList = list;
    }

    private class CellViewHolder extends RecyclerView.ViewHolder  {
        private DetailListItemTypeTitleBinding binding;

        public CellViewHolder(DetailListItemTypeTitleBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(final ViewGroup viewGroup, int viewType) {
        DetailListItemTypeTitleBinding  binding = DataBindingUtil.inflate(LayoutInflater.from(viewGroup.getContext()), R.layout.detail_list_item_type_title, viewGroup, false);
                return new CellViewHolder(binding);

    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder viewHolder, int position) {
                CellViewHolder cellViewHolder = (CellViewHolder) viewHolder;
                cellViewHolder.binding.setValue(songList.get(position));
     }

    @Override
    public int getItemCount() {
        if (songList == null)
            return 0;
        return songList.size();
    }


}