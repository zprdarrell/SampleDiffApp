package com.darrellgriffin.gitdiffapp.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.text.Editable;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.method.ScrollingMovementMethod;
import android.text.style.BackgroundColorSpan;
import android.text.style.LineBackgroundSpan;
import android.util.AttributeSet;

import com.darrellgriffin.gitdiffapp.R;

import java.util.Scanner;

import timber.log.Timber;

public class DiffTextView extends androidx.appcompat.widget.AppCompatTextView {

    private boolean isLeftSide;
    public DiffTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }
    public void setText(CharSequence text, BufferType type) {
        super.setText(formatDiff(text,isLeftSide), type);
    }
    public void setLeftSide(boolean isLeftSide){
        this.isLeftSide = isLeftSide;
    }

    private Editable formatDiff(CharSequence diffSequence, boolean leftSide){
        Editable editable = new Editable.Factory().newEditable("");
        int color;
        boolean needsNewLine;
        int lineNumber = 0;
        Iterable<String> sc = () -> new Scanner(diffSequence.toString()).useDelimiter("\n");
        for (String line : sc) {
            Spannable spannable = new SpannableString(line);
            Timber.d("formatDiff %s", line);
            switch (line.charAt(0)){

                case '+' : {
                    needsNewLine = true;
                    lineNumber++;
                    if(!isLeftSide ){
                        color = getContext().getResources().getColor(R.color.diffGreen, null);
                        final int start = editable.length();
                        if(line.length() > 1 && line.charAt(1) != '+' || line.length() == 1){
                            editable.append(String.valueOf(lineNumber));
                        }
                        editable.append(spannable);

                        if(line.length() > 1 && line.charAt(1) != '+'){
                            final int end = editable.length();
                            editable.setSpan(new DiffTextSpan(color), start, end, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
                        }else if(line.length() ==1){
                            needsNewLine = false;
                            editable.append("\n");
                            final int end = editable.length();
                            editable.setSpan(new DiffTextSpan(color), start, end, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
                        }else if(line.charAt(1) == '+'){
                            color = getContext().getColor(android.R.color.white);
                            final int end = editable.length();
                            editable.setSpan(new DiffTextSpan(color), start, end, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
                        }

                    }else{
                        lineNumber--;
                    }
                }
                break;
                case '-' : {
                    needsNewLine = true;
                    lineNumber++;
                    if(isLeftSide){
                        color = getContext().getResources().getColor(R.color.diffRed, null);
                        final int start = editable.length();
                        if(line.length() > 1 && line.charAt(1) != '-' || line.length() == 1){
                            editable.append(String.valueOf(lineNumber));
                        }
                        editable.append(spannable);
                        if(line.length() > 1 && line.charAt(1) != '-'){
                            final int end = editable.length();
                            editable.setSpan(new DiffTextSpan(color), start, end, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
                        }else if(line.length() ==1){
                            needsNewLine = false;
                            editable.append("\n");
                            final int end = editable.length();
                            editable.setSpan(new DiffTextSpan(color), start, end, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
                        }else if(line.charAt(1) == '-'){
                            color = getContext().getColor(android.R.color.white);
                            final int end = editable.length();
                            editable.setSpan(new DiffTextSpan(color), start, end, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
                        }
                    }else{
                        lineNumber--;
                    }
                }
                break;
                case '@' : {
                    needsNewLine = true;
                    color = getContext().getResources().getColor(R.color.diffBlue, null);
                    final int start = editable.length();
                    if(isLeftSide){
                        spannable.setSpan(new BackgroundColorSpan(color), 0, spannable.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                        editable.append(spannable);
                    }else{
                        editable.append("\n");
                        needsNewLine = false;
                    }
                    if(isLeftSide){
                        lineNumber = Integer.valueOf(line.substring(line.indexOf("-") + 1, line.indexOf(",")));
                    }else{
                        lineNumber = Integer.valueOf(line.substring(line.indexOf("+") + 1, line.indexOf(",", line.indexOf("+") +1)));
                    }
                    lineNumber--;
                    final int end = editable.length();
                    editable.setSpan(new DiffTextSpan(color), start, end, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);

                }
                break;
                case ' ' : {


                    if(line.length()>1 && !line.trim().isEmpty()){
                        color = getContext().getColor(android.R.color.white);
                        lineNumber++;
                        needsNewLine = true;
                        final int start = editable.length();
                        editable.append(String.valueOf(lineNumber));
                        editable.append(spannable);
                        final int end = editable.length();
                        editable.setSpan(new DiffTextSpan(color), start, end, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);

                    }else{
                        needsNewLine = false;
                    }
                }
                break;
                default:{
                    needsNewLine = false;
                }
            }
            if(needsNewLine)editable.append("\n");
        }
        return editable;
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
