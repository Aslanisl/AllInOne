package ru.mail.aslanisl.allinone.bashPostFragment;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import ru.mail.aslanisl.allinone.R;

public class BushPostsAdapter extends RecyclerView.Adapter<BushPostsAdapter.ViewHolder> {
    private Context context;
    private List<BushPostModel> posts;


    public BushPostsAdapter (Context context, List<BushPostModel> posts) {
        this.posts = posts;
        this.context = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_posts, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

        BushPostModel post = posts.get(position);

        holder.post.setText(Html.fromHtml(post.getElementPureHtml()));
        holder.site.setText(post.getSite());
    }

    @Override
    public int getItemCount() {
        if (posts == null)
            return 0;
        return posts.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder{
        TextView post;
        TextView site;

        public ViewHolder(View itemView) {
            super(itemView);
            post = (TextView) itemView.findViewById(R.id.post_item_post);
            site = (TextView) itemView.findViewById(R.id.post_item_site);
        }

    }
}

