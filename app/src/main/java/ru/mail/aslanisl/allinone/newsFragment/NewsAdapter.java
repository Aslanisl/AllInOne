package ru.mail.aslanisl.allinone.newsFragment;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;

import ru.mail.aslanisl.allinone.MainActivity;
import ru.mail.aslanisl.allinone.R;
import ru.mail.aslanisl.allinone.newsFragment.newsModel.Source;

public class NewsAdapter extends RecyclerView.Adapter<NewsAdapter.ViewHolder> {

    private Context context;
    private List<Source> mListNewsSource;

    public NewsAdapter (Context context, List<Source> sources){
        this.context = context;
        mListNewsSource = sources;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_news, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {

        final Source newsSource = mListNewsSource.get(position);

        holder.descriptionTextView.setText(newsSource.getDescription());

        final Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setData(Uri.parse(newsSource.getUrl()));

        holder.categoryTextView.setText(newsSource.getCategory());

        Picasso.with(context).load(newsSource.getUrlsToLogos().getLarge()).into(holder.logImageView);

        holder.logImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        if (mListNewsSource == null)
            return 0;
        return mListNewsSource.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder{

        TextView descriptionTextView;
        ImageView logImageView;
        TextView categoryTextView;

        public ViewHolder(View itemView) {
            super(itemView);
            descriptionTextView = (TextView) itemView.findViewById(R.id.description_textView);
            logImageView = (ImageView) itemView.findViewById(R.id.log_imageView);
            categoryTextView = (TextView) itemView.findViewById(R.id.category_textView);
        }
    }
}
