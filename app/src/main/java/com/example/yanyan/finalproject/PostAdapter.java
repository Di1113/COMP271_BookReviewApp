package com.example.yanyan.finalproject;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import java.lang.reflect.Array;
import java.util.ArrayList;

/**
 * Created by yanyan on 4/8/18.
 */

public class PostAdapter extends BaseAdapter {
    private Context mContext;
    private ArrayList<Post> mPostList;
    private LayoutInflater mInflater;
    // for filter
    private ArrayList<Post> filterpostlist;

    // constructor
    public PostAdapter(Context mContext, ArrayList<Post> mPostList) {
        //initialize instances variables
        this.mContext = mContext;
        this.mPostList = mPostList;
//        this.filterpostlist =mPostList;
        mInflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }


    @Override
    public int getCount() {return mPostList.size();}

    @Override
    public Object getItem(int i) {
        return mPostList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;

        if (convertView == null) {
            //inflate
            convertView = mInflater.inflate(R.layout.post_list_item, parent, false);
            //add the views to the holder
            holder = new ViewHolder();
            holder.BooktitleTextView = convertView.findViewById(R.id.post_title);
            holder.reviewTextView = convertView.findViewById(R.id.post_review);
            holder.hashtagTextView = convertView.findViewById(R.id.post_hashtag);
            holder.datetimeTextView = convertView.findViewById(R.id.post_datetime);
            holder.rateTextView = convertView.findViewById(R.id.post_rate);
            //add the holder to the view
            convertView.setTag(holder);
        } else {
            //get the view holder from converview
            holder = (ViewHolder) convertView.getTag();
        }

        TextView BooktitleTextView = holder.BooktitleTextView;
        TextView reviewTextView = holder.reviewTextView;
        TextView hashtagTextView = holder.hashtagTextView;
        TextView datetimeTextView = holder.datetimeTextView;
        TextView rateTextView = holder.rateTextView;

        Post post = (Post) getItem(position);


        //BooktitleTextView
        BooktitleTextView.setText(post.gettitle());//title
        //reviewTextView
        reviewTextView.setText(post.getReviews());//review
        //hashtagTextView
        hashtagTextView.setText(post.gethashtag());//rate
        //datetimeTextView
        datetimeTextView.setText(post.getDatetime());//dt
        //rateTextView
        rateTextView.setText(post.getrate());//quote

        return convertView;
    }



    private static class ViewHolder {
        public TextView BooktitleTextView;
        public TextView reviewTextView;
        public TextView hashtagTextView;
        public TextView datetimeTextView;
        public TextView rateTextView;
    }

    public void setFilter(ArrayList<Post> newList){
        filterpostlist = new ArrayList<>();
        filterpostlist.addAll(newList);
        notifyDataSetChanged();

    }

    public void addListitemtoAdapter(ArrayList<Post> postlist){
        mPostList.addAll(postlist);
        this.notifyDataSetChanged();
    }

//    @Override
//    public Filter getFilter() {
//        return new Filter() {
//            @Override
//            protected FilterResults performFiltering(CharSequence constraint) {
//                FilterResults results = new FilterResults();
//
//                if (constraint == null || constraint.length() == 0) {
//                    //no constraint given, just return all the data. (no search)
//                    results.count = filterpostlist.size();
//                    results.values = filterpostlist;
//                } else {//do the search
//                    ArrayList<Post> resultsData = new ArrayList<>();
//                    String searchStr = constraint.toString().toLowerCase();
//                    for (Post p : filterpostlist) {
//                        if (p.booktitle.toLowerCase().contains(searchStr)) {
//                            resultsData.add(p);
//                            results.count = resultsData.size();
//                            results.values = resultsData;
//                        }
//                    }
//                }
//
//                return results;
//            }
//            @Override
//            protected void publishResults(CharSequence constraint, FilterResults results) {
//                filterpostlist = (ArrayList<Post>) results.values;
//                notifyDataSetChanged();
//            }
//        };
//    }


}
