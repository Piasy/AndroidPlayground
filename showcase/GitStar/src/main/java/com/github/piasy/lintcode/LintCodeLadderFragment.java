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

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import butterknife.BindView;
import butterknife.ButterKnife;
import com.github.piasy.octostars.R;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.yatatsu.autobundle.AutoBundle;
import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class LintCodeLadderFragment extends Fragment implements ProblemAdapter.Action {

    @BindView(R.id.mRvStars)
    RecyclerView mRvProblems;

    public LintCodeLadderFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        AutoBundle.bind(this);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_stars, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ButterKnife.bind(this, view);

        mRvProblems.setLayoutManager(new LinearLayoutManager(getContext()));
        Gson gson = new GsonBuilder()
                .registerTypeAdapterFactory(AutoValueGsonAdapterFactory.create())
                .create();
        List<LintCodeLadderLevel> levels = MockLintCodeProblemProvider.provideLevels(gson);
        List<LintCodeProblem> problems = new ArrayList<>();
        for (LintCodeLadderLevel level : levels) {
            for (List<LintCodeProblem> parts : level.problems().values()) {
                problems.addAll(parts);
            }
        }
        mRvProblems.setAdapter(new ProblemAdapter(problems, this));
    }

    @Override
    public void seeProblem(LintCodeProblem problem) {
        startActivity(LintCodeProblemActivityAutoBundle
                .createIntentBuilder(problem)
                .build(getContext()));
    }
}
