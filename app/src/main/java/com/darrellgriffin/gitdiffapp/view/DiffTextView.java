package com.darrellgriffin.gitdiffapp.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.text.Editable;
import android.text.Spanned;
import android.text.style.LineBackgroundSpan;
import android.util.AttributeSet;

import com.darrellgriffin.gitdiffapp.R;

import java.util.Scanner;

public class DiffTextView extends androidx.appcompat.widget.AppCompatTextView {

    private boolean isLeftSide;
    public DiffTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public void setText(CharSequence text, BufferType type) {
        super.setText(formatDiff(text, isLeftSide));
    }
    public void setLeftSide(boolean isLeftSide){
        this.isLeftSide = isLeftSide;
    }

    private CharSequence formatDiff(CharSequence diffSequence, boolean leftSide){
        Editable editable = new Editable.Factory().newEditable("");
        int color;
        Iterable<String> sc = () -> new Scanner(diffSequence.toString()).useDelimiter("\n");
        for (String line : sc) {
            switch (line.charAt(0)){
                case '+' : {
                    if(!leftSide){
                        color = R.color.diffGreen;
                        final int start = editable.length();
                        editable.append(line);
                        final int end = editable.length();
                        editable.setSpan(new DiffTextSpan(color), start, end, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
                    }else{
                        editable.append("\n");
                    }
                }
                break;
                case '-' : {
                    if(leftSide){
                        color = R.color.diffRed;
                        final int start = editable.length();
                        editable.append(line);
                        final int end = editable.length();
                        editable.setSpan(new DiffTextSpan(color), start, end, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
                    }else{
                        editable.append("\n");
                    }
                }
                break;
                case '@' : {
                    color = R.color.diffBlue;
                    final int start = editable.length();
                    if(leftSide){
                        editable.append(line);
                    }else{
                        editable.append("\n");
                    }
                    final int end = editable.length();
                    editable.setSpan(new DiffTextSpan(color), start, end, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
                }
                break;
                case ' ' : {
                    editable.append(line);
                }
                break;
            }
        }
        return editable.toString();
    }

    private class DiffTextSpan implements LineBackgroundSpan {
        private final int mColor;
        private Rect mRect;
        DiffTextSpan(int color){
            this.mColor = color;
            mRect = new Rect();
        }

        @Override
        public void drawBackground(Canvas c, Paint p, int left, int right, int top, int baseline, int bottom, CharSequence text, int start, int end, int lnum) {
            final int paintColor = p.getColor();
            p.setColor(mColor);
            mRect.set(left, top, right, bottom);
            c.drawRect(mRect, p);
            p.setColor(paintColor);
        }
    }
}
