package will_dejong.gifsearch;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.Target;

import java.util.ArrayList;

/**
 * Created by William on 2/24/2018.
 */

public class GifListAdapter extends RecyclerView.Adapter<GifListAdapter.ViewHolder> {
    private ArrayList<Emogi> mEmogis;
    private Context mContext;

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public ImageView mImageView;
        public ViewHolder(View itemLayoutView) {
            super(itemLayoutView);
            mImageView = itemLayoutView.findViewById(R.id.gifIV);
        }
    }

    public GifListAdapter(ArrayList<Emogi> emogis, Context context) {
        mEmogis = emogis;
        mContext = context;
    }

    @Override
    public GifListAdapter.ViewHolder onCreateViewHolder(ViewGroup parent,
                                                   int viewType) {
        View itemLayoutView =  LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_view, null);

        return new ViewHolder(itemLayoutView);

    }

    // Replace the Gif of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        GlideApp.with(mContext)
                .asGif()
                .load(mEmogis.get(position).getThumbUrl())
                .apply(new RequestOptions()
                        .override(Target.SIZE_ORIGINAL, Target.SIZE_ORIGINAL))
                .into(holder.mImageView);

        Log.d("URL", mEmogis.get(position).getThumbUrl());

    }

    @Override
    public int getItemCount() {return mEmogis.size();
    }
}
