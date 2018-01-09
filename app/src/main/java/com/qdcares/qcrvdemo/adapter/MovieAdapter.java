package com.qdcares.qcrvdemo.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.qdcares.qcrvdemo.listener.OnItemClickListener;
import com.qdcares.qcrvdemo.R;
import com.qdcares.qcrvdemo.enity.Movie;
import com.qdcares.qdcaresrecyclerview.swipemenu.SwipeItemLayout;

import java.util.List;

/**
 * Created by handaolin on 2017/4/25.
 */

public class MovieAdapter extends RecyclerView.Adapter<MovieAdapter.ThemesViewHolder> implements View.OnClickListener, View.OnLongClickListener {
    private List<Movie> list;
    private Context context;
    private LayoutInflater inflater;
    private OnItemClickListener mOnItemClickListener = null;

    public MovieAdapter(Context context, List<Movie> list) {
        this.context = context;
        this.list = list;
        inflater = LayoutInflater.from(context);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.mOnItemClickListener = listener;
    }

    @Override
    public ThemesViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.activity_recyclerview_item_left_right, parent, false);
        view.setOnClickListener(this);
        view.setOnLongClickListener(this);
        ThemesViewHolder viewHolder = new ThemesViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(final ThemesViewHolder holder, final int position) {
        final Movie movie = list.get(position);
        holder.name.setText(movie.getTitle());
        holder.itemView.setTag(position);
        holder.leftMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mOnItemClickListener.onItemLeftClick(v, position);
                holder.itemLayout.close();
            }
        });
        holder.rightMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mOnItemClickListener.onItemRightClick(v, position);
                holder.itemLayout.close();
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    @Override
    public void onClick(View v) {
        mOnItemClickListener.onItemClick(v, (int) v.getTag());
    }

    @Override
    public boolean onLongClick(View v) {
        mOnItemClickListener.onItemLongClick(v, (int) v.getTag());
        return true;
    }

    class ThemesViewHolder extends RecyclerView.ViewHolder {
        TextView name;
        View leftMenu;
        View rightMenu;
        SwipeItemLayout itemLayout;

        public ThemesViewHolder(View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.tv_name);
            leftMenu = itemView.findViewById(R.id.left_menu);
            rightMenu = itemView.findViewById(R.id.right_menu);
            itemLayout = itemView.findViewById(R.id.swipe_layout);
        }
    }
}
