package com.peeru.labs.task.ui.adapter;


import android.content.Context;
import android.databinding.DataBindingUtil;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.peeru.labs.task.R;
import com.peeru.labs.task.databinding.DetailListItemVerticalBinding;

import java.util.ArrayList;
import java.util.HashMap;

public class VerticalRecyclerAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private HashMap<String, ArrayList<String>> songData;
    private Context mContext;
    private ArrayList<String> filterType;
    private Integer songCount;

    public VerticalRecyclerAdapter(HashMap<String, ArrayList<String>> songData,Integer songCount) {
        this.songData = songData;
        this.songCount = songCount;
        filterType = new ArrayList<String>(songData.keySet());
    }

    private class CellViewHolder extends RecyclerView.ViewHolder {
        private HorizontalRecyclerAdapter adapter;
        private DetailListItemVerticalBinding binding;

        public CellViewHolder(DetailListItemVerticalBinding detailListItemVerticalBinding) {
            super(detailListItemVerticalBinding.getRoot());
            binding = detailListItemVerticalBinding;
            binding.verticalRecyclerView.setHasFixedSize(true);
            adapter = new HorizontalRecyclerAdapter();
            binding.verticalRecyclerView.setAdapter(adapter);
            binding.verticalRecyclerView.setNestedScrollingEnabled(false);
        }

        public void setData(ArrayList<String> songList) {
            int count;
            if(songCount>songList.size()){
                count=songList.size();
            }else{
                count = songCount;
            }
            GridLayoutManager gridLayoutManager = new GridLayoutManager(mContext,count, GridLayoutManager.HORIZONTAL, false);
            binding.verticalRecyclerView.setLayoutManager(gridLayoutManager);
            adapter.updateList(songList);
        }
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(final ViewGroup viewGroup, int viewType) {
        mContext = viewGroup.getContext();
        DetailListItemVerticalBinding binding = DataBindingUtil.inflate(LayoutInflater.from(viewGroup.getContext()), R.layout.detail_list_item_vertical, viewGroup, false);
        CellViewHolder viewHolder = new CellViewHolder(binding);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder viewHolder, final int position) {
        CellViewHolder cellViewHolder = (CellViewHolder) viewHolder;
        cellViewHolder.binding.setFilterType(filterType.get(position));
        cellViewHolder.setData(songData.get(filterType.get(position)));

    }

/*
    @Override
    public void onViewRecycled(RecyclerView.ViewHolder viewHolder) {
        final int position = viewHolder.getAdapterPosition();
      //  CellViewHolder cellViewHolder = (CellViewHolder) viewHolder;
//        int firstVisiblePosition = cellViewHolder.layoutManager.findFirstVisibleItemPosition();
  //      listPosition.put(position, firstVisiblePosition);

        super.onViewRecycled(viewHolder);
    }
*/


    @Override
    public int getItemCount() {
        if (filterType == null)
            return 0;
        return filterType.size();
    }


}