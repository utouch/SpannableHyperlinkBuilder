package com.hyperlink;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.text.SpannableStringBuilder;
import android.text.TextPaint;
import android.text.TextUtils;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.util.TypedValue;
import android.view.View;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by zs on 18/1/23.
 * <p>
 * Description
 */

public class SpannableHyperlinkBuilder {
    private TextView textView;
    private ArrayList<Hyperlink> hyperlinks = new ArrayList<>();
    private OnTextSpannableClick mOnTextSpannableClick;

    public SpannableHyperlinkBuilder() {
    }

    public SpannableHyperlinkBuilder(TextView textView, OnTextSpannableClick click) {
        this.textView = textView;
        this.mOnTextSpannableClick = click;
    }

    /**
     * @param hyperlink
     */
    public void add(Hyperlink hyperlink) {
        hyperlinks.add(hyperlink);
    }

    /**
     * @param hyperlink
     */
    public void add(List<Hyperlink> hyperlink) {
        hyperlinks.clear();
        hyperlinks.addAll(hyperlink);
    }

    public ArrayList<Hyperlink> getHyperlinks() {
        return hyperlinks;
    }

    public int size() {
        return hyperlinks.size();
    }

    public Hyperlink getHyperlink() {
        return new Hyperlink();
    }

    public void builder() {
        SpannableStringBuilder ssb = new SpannableStringBuilder();
        for (int i = 0; i < size(); i++) {
            final Hyperlink hyper = hyperlinks.get(i);
            String hyperText = hyper.getHlText();
            int length = ssb.length();
            String linkText = ssb.append(hyperText).toString();
            int start = linkText.indexOf(hyperText, length);
            int end = start + hyperText.length();
            ssb.setSpan(new ClickableSpan() {
                @Override
                public void onClick(View widget) {
                    if (null != mOnTextSpannableClick && !TextUtils.isEmpty(hyper.getHlUrl())) {
                        mOnTextSpannableClick.onSpannableClick(widget, hyper);
                    }
                }
                @SuppressLint("ResourceType")
                @Override
                public void updateDrawState(TextPaint ds) {
                    super.updateDrawState(ds);
                    if (!TextUtils.isEmpty(hyper.getHlUrl())) {
                        ds.setColor(hyper.getFgColor() != 0 ? textView.getResources().getColor(hyper.getFgColor()) : Color.parseColor("#0097f4"));
                    } else {
                        if (hyper.getFgColor() != 0) {
                            ds.setColor(textView.getResources().getColor(hyper.getFgColor()));
                        } else {
                            ds.setColor(textView.getCurrentTextColor() != 0 ? textView.getResources().getColor(textView.getCurrentTextColor()) : Color.parseColor("#909090"));
                        }
                    }
                    ds.bgColor = hyper.getBgColor();
                    if (hyper.getTextSize() != 0 && textView != null)
                        ds.setTextSize(textSize(hyper.getTextSize()));
                    ds.setUnderlineText(hyper.isUnderline());
                }
            }, start, end, 0);

        }
        if (null != textView) {
            textView.setMovementMethod(LinkMovementMethod.getInstance());
            textView.setText(ssb);
        }
    }

    public static class Hyperlink {
        private String hlText;
        private String hlDesc;
        private String hlUrl;
        private int fgColor;
        private int bgColor;
        private int textSize;
        private boolean underline;

        public Hyperlink() {
        }

        public Hyperlink(String hlText) {
            this.hlText = hlText;
        }

        public Hyperlink(String hlText, int fgColor) {
            this.hlText = hlText;
            this.fgColor = fgColor;
        }

        public Hyperlink(String hlText, String hlUrl) {
            this.hlText = hlText;
            this.hlUrl = hlUrl;
        }

        public Hyperlink(String hlText, String hlUrl, int fgColor) {
            this.hlText = hlText;
            this.hlUrl = hlUrl;
            this.fgColor = fgColor;
        }

        public Hyperlink(String hlText, String hlDesc, String hlUrl, int fgColor, int bgColor, int textSize, boolean underline) {
            this.hlText = hlText;
            this.hlDesc = hlDesc;
            this.hlUrl = hlUrl;
            this.fgColor = fgColor;
            this.bgColor = bgColor;
            this.textSize = textSize;
            this.underline = underline;
        }

        public String getHlText() {
            return hlText;
        }

        public void setHlText(String hlText) {
            this.hlText = hlText;
        }

        public String getHlDesc() {
            return hlDesc;
        }

        public void setHlDesc(String hlDesc) {
            this.hlDesc = hlDesc;
        }

        public String getHlUrl() {
            return hlUrl;
        }

        public void setHlUrl(String hlUrl) {
            this.hlUrl = hlUrl;
        }

        public int getFgColor() {
            return fgColor;
        }

        public void setFgColor(int fgColor) {
            this.fgColor = fgColor;
        }

        public int getBgColor() {
            return bgColor;
        }

        public void setBgColor(int bgColor) {
            this.bgColor = bgColor;
        }

        public int getTextSize() {
            return textSize;
        }

        public void setTextSize(int textSize) {
            this.textSize = textSize;
        }

        public boolean isUnderline() {
            return underline;
        }

        public void setUnderline(boolean underline) {
            this.underline = underline;
        }

        @Override
        public String toString() {
            return "Hyperlink{" +
                              "hlText='" + hlText + '\'' +
                              ", hlDesc='" + hlDesc + '\'' +
                              ", hlUrl='" + hlUrl + '\'' +
                              ", fgColor=" + fgColor +
                              ", bgColor=" + bgColor +
                              ", textSize=" + textSize +
                              ", underline=" + underline +
                              '}';
        }
    }

    private float textSize(float pxValue) {
        return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, pxValue, textView.getResources().getDisplayMetrics());
    }

    public void setTextView(TextView textView) {
        this.textView = textView;
    }

    public void setOnTextSpannableClick(OnTextSpannableClick mOnTextSpannableClick) {
        this.mOnTextSpannableClick = mOnTextSpannableClick;
    }

    public interface OnTextSpannableClick {
        void onSpannableClick(View widget, Hyperlink hyperlink);
    }

}
