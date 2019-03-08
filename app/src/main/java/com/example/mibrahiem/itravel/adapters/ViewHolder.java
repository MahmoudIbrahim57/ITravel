package com.example.mibrahiem.itravel.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.example.mibrahiem.itravel.R;

/**
 * Created by Mibrahiem on 6/18/2018.
 */

public class ViewHolder extends RecyclerView.ViewHolder {
 View mView;
    public ViewHolder(View itemView) {
        super(itemView);

        mView=itemView;
    }

public void setDetails (Context  ctx, String author, String content){

    TextView PostDetails = itemView.findViewById(R.id.tv_post);
    TextView   postWriter = itemView.findViewById(R.id.tv_nameOfPostwriter);

    PostDetails.setText(content);
    postWriter.setText(author);

}

}
