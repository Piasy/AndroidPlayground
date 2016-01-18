package com.github.piasy.wechatluckymoneyeffect;

/*
from: https://gist.github.com/methodin/5678214

Original code found at:
https://code.google.com/p/android-stocker/source/browse/trunk/src/com/twofuse/stocker/Rotate3dAnimation.java?r=2
Use (in set):
Rotate3dAnimation skew = new Rotate3dAnimation(20, 0, 0, 0, 0, 0);
set.addAnimation(skew);
animation = new TranslateAnimation(0, 0, 0, 0, Animation.RELATIVE_TO_SELF, 0.5f, 0, 0);
set.addAnimation(animation);
// set.setStartOffset((position ) * 10);
Use (single view):
Rotate3dAnimation skew = new Rotate3dAnimation(20, 0, 0, 0, 0, 0);
view.startAnimation(skew);
*/

import android.view.animation.Animation;

import android.view.animation.Transformation;
import android.graphics.Camera;
import android.graphics.Matrix;

public class Rotate3dAnimation extends Animation {
    private final float fromXDegrees;
    private final float toXDegrees;
    private final float fromYDegrees;
    private final float toYDegrees;
    private final float fromZDegrees;
    private final float toZDegrees;
    private Camera camera;
    private int width = 0;
    private int height = 0;

    public Rotate3dAnimation(float fromXDegrees, float toXDegrees, float fromYDegrees, float toYDegrees, float fromZDegrees, float toZDegrees) {
        this.fromXDegrees = fromXDegrees;
        this.toXDegrees = toXDegrees;
        this.fromYDegrees = fromYDegrees;
        this.toYDegrees = toYDegrees;
        this.fromZDegrees = fromZDegrees;
        this.toZDegrees = toZDegrees;
    }

    @Override
    public void initialize(int width, int height, int parentWidth, int parentHeight) {
        super.initialize(width, height, parentWidth, parentHeight);
        this.width = width / 2;
        this.height = height / 2;
        camera = new Camera();
    }

    @Override
    protected void applyTransformation(float interpolatedTime, Transformation t) {
        float xDegrees = fromXDegrees + ((toXDegrees - fromXDegrees) * interpolatedTime);
        float yDegrees = fromYDegrees + ((toYDegrees - fromYDegrees) * interpolatedTime);
        float zDegrees = fromZDegrees + ((toZDegrees - fromZDegrees) * interpolatedTime);

        final Matrix matrix = t.getMatrix();

        camera.save();
        camera.rotateX(xDegrees);
        camera.rotateY(yDegrees);
        camera.rotateZ(zDegrees);
        camera.getMatrix(matrix);
        camera.restore();

        matrix.preTranslate(-this.width, -this.height);
        matrix.postTranslate(this.width, this.height);
    }

}
