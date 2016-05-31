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

package com.github.piasy.recyclerviewscrollbug;

import android.content.Context;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Space;
import com.facebook.drawee.view.SimpleDraweeView;

import static butterknife.ButterKnife.findById;

public final class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.NormalWatcherVH> {

    private final Context mContext;

    private final String[] mUrls = new String[] {
            "http://yolo-debug.oss-cn-beijing.aliyuncs" +
                    ".com/headimgs/13027b8a-f17f-11e5-868c-00163e000555.jpeg",
            "http://yolo-debug.oss-cn-beijing.aliyuncs" +
                    ".com/headimgs/673ad840-f590-11e5-af95-00163e000555.jpeg",
            "http://yolo-debug.oss-cn-beijing.aliyuncs" +
                    ".com/headimgs/yolo-debug55a7b416168833-73144273.jpeg",
            "http://yolo-debug.oss-cn-beijing.aliyuncs" +
                    ".com/headimgs/ab111288-f09e-11e5-b426-00163e000555.jpeg",
            "http://yolo-debug.oss-cn-beijing.aliyuncs" +
                    ".com/headimgs/yolo-debug55a7b41c44b987-27105293.jpeg",
            "http://yolo-image.oss-cn-beijing.aliyuncs.com/default/avatar.png",
            "http://yolo-debug.oss-cn-beijing.aliyuncs" +
                    ".com/headimgs/c002d484-e53f-11e5-b70a-00163e000555.jpeg",
            "http://yolo-debug.oss-cn-beijing.aliyuncs" +
                    ".com/headimgs/f378c3d6-8dd4-11e5-a8ae-00163e000555.jpeg",
            "http://yolo-debug.oss-cn-beijing.aliyuncs" +
                    ".com/headimgs/d571f098-9f17-11e5-bda9-00163e000555.jpeg",
            "http://yolo-debug.oss-cn-beijing.aliyuncs" +
                    ".com/headimgs/aefdfb8a-2e8a-11e5-94ab-00163e000555.jpeg",
            "http://yolo-debug.oss-cn-beijing.aliyuncs" +
                    ".com/headimgs/63317b3a-f407-11e5-b7ed-00163e000555.jpeg",
            "http://yolo-debug.oss-cn-beijing.aliyuncs" +
                    ".com/headimgs/50d9eb2e-3068-11e5-ba97-00163e000555.jpeg",
            "http://yolo-debug.oss-cn-beijing.aliyuncs" +
                    ".com/headimgs/20c8435a-5d12-11e5-8a0b-00163e000555.jpeg",
            "http://yolo-debug.oss-cn-beijing.aliyuncs" +
                    ".com/headimgs/185ffc0c-32a2-11e5-a626-00163e000555.jpeg",
            "https://static-api-yoloyolo-tv.alikunlun.com/default/avatar.png",
            "http://yolo-debug.oss-cn-beijing.aliyuncs" +
                    ".com/headimgs/6c22263a-ef53-11e5-b748-00163e000555.jpeg",
            "http://yolo-debug.oss-cn-beijing.aliyuncs" +
                    ".com/headimgs/d89c7bb4-ef56-11e5-a742-00163e000555.jpeg",
            "http://yolo-debug.oss-cn-beijing.aliyuncs" +
                    ".com/headimgs/0b7c5f1c-ef67-11e5-add1-00163e000555.jpeg",
            "http://yolo-debug.oss-cn-beijing.aliyuncs" +
                    ".com/headimgs/d371e7de-f314-11e5-b034-00163e000555.jpeg",
            "http://yolo-debug.oss-cn-beijing.aliyuncs" +
                    ".com/headimgs/5352320e-7de2-11e5-b56e-00163e000555.jpeg",
            "http://yolo-debug.oss-cn-beijing.aliyuncs" +
                    ".com/headimgs/yolo-debug559a02082179a5-39359934.jpeg",
            "http://yolo-debug.oss-cn-beijing.aliyuncs" +
                    ".com/headimgs/7d83ba98-f25e-11e5-a056-00163e000555.jpeg",
            "http://yolo-debug.oss-cn-beijing.aliyuncs" +
                    ".com/headimgs/yolo-debug55a9cb8eec4f90-86075957.jpeg"
    };

    public RecyclerAdapter(Context context) {
        mContext = context;
    }

    static class NormalWatcherVH extends RecyclerView.ViewHolder {

        private final SimpleDraweeView mIvAvatar;

        private final Space mSpaceRight;

        public NormalWatcherVH(View view) {
            super(view);
            mIvAvatar = findById(view, R.id.mAvatar);
            mSpaceRight = findById(view, R.id.mSpaceRight);
        }
    }

    private int mItemCount = 3;

    public void add() {
        //mItemCount++;
        //notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return mUrls.length;
    }

    @Override
    public NormalWatcherVH onCreateViewHolder(ViewGroup parent, int viewType) {
        return new NormalWatcherVH(LayoutInflater.from(parent.getContext())
                .inflate(R.layout.ui_list_item, parent, false));
    }

    private Uri res2Uri(int resId) {
        return Uri.parse("res://" + mContext.getPackageName() + "/" + resId);
    }

    @Override
    public void onBindViewHolder(NormalWatcherVH holder, int position) {
        bind4NormalWatcher(holder, /*res2Uri(R.drawable.sample_avatar)*/Uri.parse(mUrls[position]),
                position < getItemCount() - 1);
    }

    private void bind4NormalWatcher(NormalWatcherVH holder, final Uri uri, boolean showRightSpace) {
        holder.mIvAvatar.setImageURI(uri);
        if (showRightSpace) {
            holder.mSpaceRight.setVisibility(View.VISIBLE);
        } else {
            holder.mSpaceRight.setVisibility(View.GONE);
        }
    }
}
