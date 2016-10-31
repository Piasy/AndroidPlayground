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

package com.github.piasy.lintcode;

import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import butterknife.BindView;
import butterknife.ButterKnife;
import com.github.piasy.gitstar.R;
import java.util.List;

/**
 * Created by Piasy{github.com/Piasy} on 22/09/2016.
 */

class ProblemAdapter extends RecyclerView.Adapter<ProblemAdapter.RepoHolder> {

    private final List<LintCodeProblem> mProblems;
    private final Action mAction;

    ProblemAdapter(List<LintCodeProblem> problems, Action action) {
        mProblems = problems;
        mAction = action;
    }

    @Override
    public RepoHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new RepoHolder(LayoutInflater.from(parent.getContext())
                .inflate(R.layout.ui_problem_item, parent, false));
    }

    @Override
    public void onBindViewHolder(RepoHolder holder, int position) {
        final LintCodeProblem problem = mProblems.get(position);
        holder.mProblemCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mAction.seeProblem(problem);
            }
        });
        holder.mTvName.setText(problem.name());
        holder.mTvDifficulty.setText(problem.difficulty());
    }

    @Override
    public int getItemCount() {
        return mProblems.size();
    }

    interface Action {
        void seeProblem(LintCodeProblem problem);
    }

    static class RepoHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.mProblemCard)
        CardView mProblemCard;
        @BindView(R.id.mTvName)
        TextView mTvName;
        @BindView(R.id.mTvDifficulty)
        TextView mTvDifficulty;

        RepoHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
