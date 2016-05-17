/**
 * Copyright 2015 jack wang

 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at

 * http://www.apache.org/licenses/LICENSE-2.0

 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * */
package com.github.piasy.fancytransitiondemo.indicator;

import android.graphics.Canvas;
import android.graphics.Paint;
import com.nineoldandroids.animation.Animator;
import com.nineoldandroids.animation.ValueAnimator;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Jack on 2015/10/16.
 */
public class BallPulseIndicator extends BaseIndicatorController {

    public static final float SCALE = 1.0f;
    private static final int CIRCLE_NUM = 6;
    private static final int CIRCLE_SPACE = 8;
    private static final int BASE_DELAY_MILLIS = 120;

    //scale x ,y
    private final float[] scaleFloats;
    private final int[] delays;

    public BallPulseIndicator() {
        scaleFloats = new float[CIRCLE_NUM];
        for (int i = 0; i < CIRCLE_NUM; i++) {
            scaleFloats[i] = SCALE;
        }
        delays = new int[CIRCLE_NUM];
        for (int i = 0; i < CIRCLE_NUM; i++) {
            delays[i] = BASE_DELAY_MILLIS * (i + 1);
        }
    }

    @Override
    public void draw(Canvas canvas, Paint paint) {
        float radius = (getWidth() - CIRCLE_SPACE * (CIRCLE_NUM - 1)) / (CIRCLE_NUM * 2);
        // locate to the first circle's center point
        float x = getWidth() / 2 - (radius + CIRCLE_SPACE / 2) * (CIRCLE_NUM - 1);
        float y = getHeight() / 2;
        for (int i = 0; i < CIRCLE_NUM; i++) {
            canvas.save();
            float translateX = x + (radius * 2 + CIRCLE_SPACE) * i;
            canvas.translate(translateX, y);
            canvas.scale(scaleFloats[i], scaleFloats[i]);
            canvas.drawCircle(0, 0, radius, paint);
            canvas.restore();
        }
    }

    @Override
    public List<Animator> createAnimation() {
        List<Animator> animators = new ArrayList<>();
        for (int i = 0; i < CIRCLE_NUM; i++) {
            final int index = i;

            ValueAnimator scaleAnim = ValueAnimator.ofFloat(1, 0.3f, 1);

            scaleAnim.setDuration(BASE_DELAY_MILLIS * CIRCLE_NUM * 3 / 2);
            scaleAnim.setRepeatCount(-1);
            scaleAnim.setStartDelay(delays[i]);

            scaleAnim.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                @Override
                public void onAnimationUpdate(ValueAnimator animation) {
                    scaleFloats[index] = (float) animation.getAnimatedValue();
                    postInvalidate();
                }
            });
            scaleAnim.start();
            animators.add(scaleAnim);
        }
        return animators;
    }
}
