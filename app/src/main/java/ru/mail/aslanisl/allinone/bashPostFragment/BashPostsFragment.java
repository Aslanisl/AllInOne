package ru.mail.aslanisl.allinone.bashPostFragment;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import ru.mail.aslanisl.allinone.App;
import ru.mail.aslanisl.allinone.R;

import static android.widget.Toast.makeText;

public class BashPostsFragment extends Fragment {

    public static final String API_RESOURCE_NAME = "bash";

    RecyclerView mPostsRecycleView;
    Toolbar mToolBar;
    List<BushPostModel> mPosts;
    Call<List<BushPostModel>> mCallPost;
    Call<List<BushPostModel>> mCallPostRandom;
    int mPostLoaded = 50;
    int mPostRandomLoaded = 50;

    public BashPostsFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_bash_posts, container, false);

        mPosts = new ArrayList<>();

        mToolBar = (Toolbar) rootView.findViewById(R.id.toolbar);

        mPostsRecycleView = (RecyclerView) rootView.findViewById(R.id.posts_recycleView);
        LinearLayoutManager layoutManager = new LinearLayoutManager(container.getContext());
        mPostsRecycleView.setLayoutManager(layoutManager);
        RecyclerView.ItemAnimator itemAnimator = new DefaultItemAnimator();
        mPostsRecycleView.setItemAnimator(itemAnimator);

        BushPostsAdapter adapter = new BushPostsAdapter(container.getContext(), mPosts);
        mPostsRecycleView.setAdapter(adapter);

        mCallPost = App.getApiBush().getData(API_RESOURCE_NAME, mPostLoaded);
        mCallPostRandom = App.getApiBush().getRandomData(mPostRandomLoaded);

        mCallPost.enqueue(new Callback<List<BushPostModel>>() {
            @Override
            public void onResponse(Call<List<BushPostModel>> call, Response<List<BushPostModel>> response) {
                if (response.isSuccessful()) {
                    mPosts.addAll(response.body());

                    mPostsRecycleView.getAdapter().notifyDataSetChanged();

                    mPostLoaded =+ 50;
                }
            }

            @Override
            public void onFailure(Call<List<BushPostModel>> call, Throwable t) {
                makeText(getActivity(), "An error occurred during networking", Toast.LENGTH_SHORT).show();
            }
        });

        mPostsRecycleView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                if (!recyclerView.canScrollVertically(1)) {
                    recyclerViewUpdateAdapter();
                }
            }
        });

        return rootView;
    }

    public void recyclerViewUpdateAdapter(){

        try {
            mCallPost.enqueue(new Callback<List<BushPostModel>>() {
                @Override
                public void onResponse(Call<List<BushPostModel>> call, Response<List<BushPostModel>> response) {
                    if (response.isSuccessful()) {
                        mPosts.addAll(response.body());

                        mPostsRecycleView.getAdapter().notifyDataSetChanged();

                        mPostLoaded += 50;
                    }
                }

                @Override
                public void onFailure(Call<List<BushPostModel>> call, Throwable t) {
                    makeText(getActivity(), "An error occurred during networking", Toast.LENGTH_SHORT).show();
                }
            });
        } catch (IllegalStateException e) {
            try {
                mCallPostRandom.enqueue(new Callback<List<BushPostModel>>() {
                    @Override
                    public void onResponse(Call<List<BushPostModel>> call, Response<List<BushPostModel>> response) {
                        if (response.isSuccessful()) {
                            mPosts.addAll(response.body());

                            mPostsRecycleView.getAdapter().notifyDataSetChanged();

                            mPostRandomLoaded += 50;
                        }
                    }

                    @Override
                    public void onFailure(Call<List<BushPostModel>> call, Throwable t) {
                        makeText(getActivity(), "An error occurred during networking", Toast.LENGTH_SHORT).show();
                    }
                });
            } catch (Exception e1) {
                Toast.makeText(getActivity(), "Новых данных нет", Toast.LENGTH_SHORT).show();
            }
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();

        if (mCallPost != null)
            mCallPost.cancel();
        if (mCallPostRandom != null)
            mCallPostRandom.cancel();
    }
}
