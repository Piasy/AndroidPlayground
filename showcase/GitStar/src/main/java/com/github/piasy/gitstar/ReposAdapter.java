/*
 * The MIT License (MIT)
 *
 * Copyright (c) 2016 Piasy
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

package com.github.piasy.gitstar;

import android.content.res.Resources;
import android.support.annotation.StringRes;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import butterknife.BindView;
import butterknife.ButterKnife;
import java.util.List;
import me.gujun.android.taggroup.TagGroup;

/**
 * Created by Piasy{github.com/Piasy} on 22/09/2016.
 */

class ReposAdapter extends RecyclerView.Adapter<ReposAdapter.RepoHolder> {

    private final List<TaggedRepo> mRepos;

    ReposAdapter(List<TaggedRepo> repos) {
        mRepos = repos;
    }

    @Override
    public RepoHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new RepoHolder(LayoutInflater.from(parent.getContext())
                .inflate(R.layout.ui_repo_item, parent, false));
    }

    @Override
    public void onBindViewHolder(RepoHolder holder, int position) {
        TaggedRepo repo = mRepos.get(position);
        holder.mTvName.setText(
                holder.getString(R.string.repo_display_name_formatter, repo.owner(), repo.name()));
        holder.mTvStar.setText(
                holder.getString(R.string.repo_star_formatter, repo.stargazers_count()));
        holder.mTvDesc.setText(repo.description());
        if (TextUtils.isEmpty(repo.tag())) {
            holder.mTagGroup.setVisibility(View.INVISIBLE);
        } else {
            holder.mTagGroup.setVisibility(View.VISIBLE);
            holder.mTagGroup.setTags(repo.tag());
        }
    }

    @Override
    public int getItemCount() {
        return mRepos.size();
    }

    static class RepoHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.mTvName)
        TextView mTvName;
        @BindView(R.id.mTvStar)
        TextView mTvStar;
        @BindView(R.id.mTvDesc)
        TextView mTvDesc;
        @BindView(R.id.mTagGroup)
        TagGroup mTagGroup;

        private final Resources mResources;

        RepoHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            mResources = itemView.getResources();
        }

        String getString(@StringRes int formatter, Object... args) {
            return String.format(mResources.getString(formatter), args);
        }
    }
}
