package com.jinke.community.utils;
import android.text.InputFilter;
import android.text.Spanned;
import android.text.TextUtils;

/**
 * Created by 32 on 18-1-22.
 */
public class DecimalInputFilter implements InputFilter {

    private String mRegularExpression;

    public DecimalInputFilter() {
        this(5);
    }

    public DecimalInputFilter(int firstLength) {
        this(firstLength, 2);
    }

    public DecimalInputFilter(int firstLength, int lastLength) {
        mRegularExpression = String.format("(\\d{0,%d}(\\.\\d{0,%d})?)", firstLength, lastLength);
    }

    @Override
    public CharSequence filter(CharSequence source, int start, int end, Spanned dest, int dstart, int dend) {

        boolean delete = false;
        StringBuilder builder = new StringBuilder(dest);

        if (TextUtils.isEmpty(source)) {
            delete = true;
            builder.delete(dstart, dend);
        } else {
            builder.insert(dstart, source);
        }

        String value = builder.toString();

        return value.matches(mRegularExpression) ? null : delete ? "." : "";
    }
}