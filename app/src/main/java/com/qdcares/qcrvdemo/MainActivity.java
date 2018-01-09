package com.qdcares.qcrvdemo;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.qdcares.qcrvdemo.adapter.MovieAdapter;
import com.qdcares.qcrvdemo.enity.Movie;
import com.qdcares.qcrvdemo.enity.MovieEnity;
import com.qdcares.qcrvdemo.http.HttpMethod;
import com.qdcares.qcrvdemo.listener.OnItemClickListener;
import com.qdcares.qdcaresrecyclerview.QdCaresRecyclerView;
import com.qdcares.qdcaresrecyclerview.callback.CustomItemTouchHelperCallback;
import com.qdcares.qdcaresrecyclerview.listener.LoadMoreListener;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import rx.Subscriber;

public class MainActivity extends AppCompatActivity {

    @Bind(R.id.rv_main)
    QdCaresRecyclerView rvMain;

    private List<Movie> movieList = new ArrayList<>();
    private int start = 0;
    private CustomItemTouchHelperCallback itemTouchHelper;
    private MovieAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        setRv();
        setAdapter();
        setTouchHelper();
        setListener();
    }

    private void setRv() {
        LinearLayoutManager manager = new LinearLayoutManager(MainActivity.this);
        manager.setOrientation(LinearLayoutManager.VERTICAL);
        rvMain.getRecyclerView().setLayoutManager(manager);
        rvMain.getRecyclerView().addItemDecoration(new DividerItemDecoration(MainActivity.this, 1));
    }

    private void setAdapter() {
        adapter = new MovieAdapter(MainActivity.this, movieList);
        adapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                showToast(movieList.get(position).getTitle() + "：点击");
            }

            @Override
            public void onItemLongClick(View view, int position) {
                showToast(movieList.get(position).getTitle() + "：长按");
            }

            @Override
            public void onItemLeftClick(View view, int position) {
                showToast(movieList.get(position).getTitle() + "：左菜单点击");
            }

            @Override
            public void onItemRightClick(View view, int position) {
                showToast(movieList.get(position).getTitle() + "：右菜单点击");

            }

        });
        rvMain.setAdapter(adapter);
    }

    private void setTouchHelper() {
        itemTouchHelper = new CustomItemTouchHelperCallback(new CustomItemTouchHelperCallback.OnItemTouchCallbackListener() {
            @Override
            public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder,
                                  RecyclerView.ViewHolder target) {
                if (movieList == null) {
                    return false;
                }
                //最后一个
                if (target.getAdapterPosition() >= movieList.size()) {
                    return false;
                }
                //处理拖动排序
                //使用Collection对数组进行重排序，目的是把我们拖动的Item换到下一个目标Item的位置
                Collections.swap(movieList, viewHolder.getAdapterPosition(), target.getAdapterPosition());
                //通知Adapter它的Item发生了移动
                adapter.notifyItemMoved(viewHolder.getAdapterPosition(), target.getAdapterPosition());
                return true;
            }

            @Override
            public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {
                if (movieList == null) {
                    return;
                }
                //处理滑动删除
                //直接从数据中删除该Item的数据
                movieList.remove(viewHolder.getAdapterPosition());
                //通知Adapter有Item被移除了
                adapter.notifyItemRemoved(viewHolder.getAdapterPosition());
            }
        });
        //禁止滑动删除
        itemTouchHelper.setCanSwipe(false);
        //禁止拖动排序
        itemTouchHelper.setCanDrag(false);
        //并绑定RecyclerView
        ItemTouchHelper touchHelper = new ItemTouchHelper(itemTouchHelper);
        touchHelper.attachToRecyclerView(rvMain.getRecyclerView());
    }

    private void setListener() {
        rvMain.setOnLoadListener(new LoadMoreListener() {
            @Override
            public void onRefresh() {
                rvMain.setLoadMoreEnable(false);
                start = 0;
                getDate(start);
            }

            @Override
            public void onLoadMore() {
                rvMain.setLoadMoreEnable(false);
                start = start + 5;
                getDate(start);
            }
        });
        getDate(start);
    }

    private void getDate(int start) {
        Subscriber<MovieEnity> subscriber = new Subscriber<MovieEnity>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {
                showToast(e.getMessage());
            }

            @Override
            public void onNext(MovieEnity movieEnity) {
                //刷新
                if (rvMain.isRefreshing()) {
                    movieList.clear();
                }

                rvMain.stopLoadingMore();
                rvMain.setRefreshing(false);

                movieList.addAll(movieEnity.getSubjects());
                if (movieList.size() > 20) {
                    rvMain.setLoadingMore(true);
                    rvMain.onNoMore("-- the end --");
                } else {
                }
                adapter.notifyDataSetChanged();
                rvMain.setLoadMoreEnable(true);
            }
        };
        HttpMethod.getInstance().getlastest(subscriber, 5, start);
    }

    private void showToast(String message) {
        Toast.makeText(MainActivity.this, message, Toast.LENGTH_SHORT).show();
    }
}
