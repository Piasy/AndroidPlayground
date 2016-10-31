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

import android.support.v4.util.ArrayMap;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by Piasy{github.com/Piasy} on 22/09/2016.
 */

class MockRepoProvider {
    private static final Map<String, List<TaggedRepo>> REPOS;

    static List<TaggedRepo> getRepo(String tag) {
        return REPOS.get(tag);
    }

    static {
        REPOS = new ArrayMap<>();
        String allTag = "All";
        List<TaggedRepo> allType = new ArrayList<>();
        String oneTag = "Untagged";
        List<TaggedRepo> oneType = new ArrayList<>();
        TaggedRepo oneRepo = TaggedRepo.create("OnsenUI",
                "Mobile app development framework and SDK using HTML5 and JavaScript. Create "
                        + "beautiful and performant cross-platform mobile apps. Based on Web "
                        + "Components, and provides bindings for Angular 1, 2, React and Vue.js.",
                "OnsenUI", "https://github.com/a-voyager/BluetoothHelper", 3463, "");
        oneType.add(oneRepo);
        allType.add(oneRepo);

        oneRepo = TaggedRepo.create("anyproxy",
                "A fully configurable http/https proxy in NodeJS",
                "alibaba", "http://www.lintcode.com/en/problem/subsets-ii", 31574, "");
        oneType.add(oneRepo);
        allType.add(oneRepo);

        oneRepo = TaggedRepo.create("TapTargetView",
                "An implementation of tap targets from the Material Design guidelines for feature"
                        + " discovery",
                "KeepSafe", "http://www.jiuzhang.com/solutions/subsets-ii", 1427, "");
        oneType.add(oneRepo);
        allType.add(oneRepo);

        REPOS.put(oneTag, oneType);

        oneTag = "Android-Text";
        oneType = new ArrayList<>();
        oneRepo = TaggedRepo.create("SimplifySpan",
                "A easy-to-use and powerful Spannable library",
                "iwgang", "", 231, oneTag);
        oneType.add(oneRepo);
        allType.add(oneRepo);

        oneRepo = TaggedRepo.create("TextDrawable",
                "This light-weight library provides images with letter/text like the Gmail app. "
                        + "It extends the Drawable class thus can be used with "
                        + "existing/custom/network ImageView classes. Also included is a fluent "
                        + "interface for creating drawables and a customizable ColorGenerator.",
                "amulyakhare", "", 1901, oneTag);
        oneType.add(oneRepo);
        allType.add(oneRepo);

        oneRepo = TaggedRepo.create("Shimmer-android",
                "An Android TextView with a shimmering effect",
                "RomainPiel", "", 1499, oneTag);
        oneType.add(oneRepo);
        allType.add(oneRepo);

        REPOS.put(oneTag, oneType);

        oneTag = "Android-Image";
        oneType = new ArrayList<>();
        oneRepo = TaggedRepo.create("fresco",
                "An Android library for managing images and the memory they use.",
                "facebook", "", 10563, oneTag);
        oneType.add(oneRepo);
        allType.add(oneRepo);

        oneRepo = TaggedRepo.create("ShapedDraweeView",
                "Fresco custom view with mask shape.",
                "Piasy", "", 42, oneTag);
        oneType.add(oneRepo);
        allType.add(oneRepo);

        oneRepo = TaggedRepo.create("Android-Image-Cropper",
                "Image Cropping Library for Android, optimized for Camera / Gallery.",
                "ArthurHub", "", 1491, oneTag);
        oneType.add(oneRepo);
        allType.add(oneRepo);

        REPOS.put(oneTag, oneType);

        oneTag = "DevOps";
        oneType = new ArrayList<>();
        oneRepo = TaggedRepo.create("fastlane",
                "The easiest way to automate building and releasing your iOS and Android apps",
                "fastlane", "", 11141, oneTag);
        oneType.add(oneRepo);
        allType.add(oneRepo);

        oneRepo = TaggedRepo.create("tracklytics",
                "Annotation based analytic aggregator with aspect oriented programming",
                "orhanobut", "", 303, oneTag);
        oneType.add(oneRepo);
        allType.add(oneRepo);

        oneRepo = TaggedRepo.create("AndroidPerformanceMonitor",
                "A transparent ui-block detection library for Android. (known as BlockCanary)",
                "markzhai", "", 1759, oneTag);
        oneType.add(oneRepo);
        allType.add(oneRepo);

        REPOS.put(oneTag, oneType);

        oneTag = "Hack-MITM";
        oneType = new ArrayList<>();
        oneRepo = TaggedRepo.create("MITM-squid",
                "sharing some files of MITM-squid attack.",
                "linvex", "", 24, oneTag);
        oneType.add(oneRepo);
        allType.add(oneRepo);

        REPOS.put(oneTag, oneType);

        REPOS.put(allTag, allType);
    }
}
