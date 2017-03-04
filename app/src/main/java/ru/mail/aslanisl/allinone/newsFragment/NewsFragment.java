package ru.mail.aslanisl.allinone.newsFragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
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
import ru.mail.aslanisl.allinone.newsFragment.newsModel.News;
import ru.mail.aslanisl.allinone.newsFragment.newsModel.Source;
import ru.mail.aslanisl.allinone.newsFragment.newsModel.UrlsToLogos;

import static android.widget.Toast.makeText;


public class NewsFragment extends Fragment {

    public static final String API_NEWS = "4d66a218288d466294bf0b675183908c";

    RecyclerView mNewsRecycleView;
    Call<News> mCallNewsSource;
    List<Source> mListNewsSource;


    public static NewsFragment newInstance(int sectionNumber) {
        NewsFragment fragment = new NewsFragment();
        Bundle args = new Bundle();
        args.putInt("News", sectionNumber);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater,ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_news, container, false);

        mListNewsSource = new ArrayList<>();

        mNewsRecycleView = (RecyclerView) rootView.findViewById(R.id.news_recycleView);
        LinearLayoutManager layoutManager = new LinearLayoutManager(container.getContext());
        mNewsRecycleView.setLayoutManager(layoutManager);
        RecyclerView.ItemAnimator itemAnimator = new DefaultItemAnimator();
        mNewsRecycleView.setItemAnimator(itemAnimator);

        NewsAdapter adapter = new NewsAdapter(container.getContext(), mListNewsSource);
        mNewsRecycleView.setAdapter(adapter);

        mCallNewsSource = App.getNewsApi().getSource(null, "en", null, API_NEWS);

        mCallNewsSource.enqueue(new Callback<News>() {
            @Override
            public void onResponse(Call<News> call, Response<News> response) {

                mListNewsSource.addAll(response.body().getSources());

                mNewsRecycleView.getAdapter().notifyDataSetChanged();
            }

            @Override
            public void onFailure(Call<News> call, Throwable t) {
                makeText(getActivity(), "An error occurred during networking", Toast.LENGTH_SHORT).show();
            }
        });

        return rootView;
    }
}
