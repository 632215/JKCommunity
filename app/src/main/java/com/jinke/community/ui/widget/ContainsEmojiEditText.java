package com.jinke.community.ui.widget;

import android.content.Context;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.widget.EditText;
import android.widget.Toast;

/**
 * @Description EditText限制输入表情符号
 * @Authour zhoujp
 * @Time 2016年11月18日 下午2:09:58
 */

public class ContainsEmojiEditText extends EditText {

    // 输入表情前的光标位置
    private int cursorPos;
    // 输入表情前EditText中的文本
    private String inputAfterText;
    // 是否重置了EditText的内容
    private boolean resetText;
    private Context mContext;

    public ContainsEmojiEditText(Context context) {
        super(context);
        this.mContext = context;
        initEditText();
    }

    public ContainsEmojiEditText(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.mContext = context;
        initEditText();
    }

    public ContainsEmojiEditText(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.mContext = context;
        initEditText();

    }

    private void initEditText() {
        addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int before, int count) {
                if (!resetText) {
                    cursorPos = getSelectionEnd();
                    // 这里用s.toString()而不直接用s是因为如果用s，
                    // 那么，inputAfterText和s在内存中指向的是同一个地址，s改变了，
                    // inputAfterText也就改变了，那么表情过滤就失败了

                    inputAfterText = s.toString();
                }
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable editable) {
                int index = getSelectionStart() - 1;
                if (index > 0) {
                    if (isEmojiCharacter(editable.charAt(index))) {
                        Editable edit = getText();
                        edit.delete(editable.length() - 2, editable.length());
                        Toast.makeText(mContext, "请输入小于16字符的汉字/字母/数字", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
    }

    /**
     * 检测是否有emoji表情
     *
     * @param source
     * @return
     */

    public static boolean containsEmoji(String source) {
        int len = source.length();
        for (int i = 0; i < len; i++) {
            char codePoint = source.charAt(i);
            if (!isEmojiCharacter(codePoint)) { // 如果不能匹配,则该字符是Emoji表情
                return true;
            }
        }
        return false;
    }

    /**
     * 判断是否是Emoji
     *
     * @param codePoint             比较的单个字符
     * @return
     */
    public static boolean isEmojiCharacter(char codePoint) {
        return !((codePoint == 0x0) || (codePoint == 0x9) || (codePoint == 0xA) || (codePoint == 0xD) || ((codePoint >= 0x20) && codePoint <= 0xD7FF)) || ((codePoint >= 0xE000) && (codePoint <= 0xFFFD)) || ((codePoint >= 0x10000) && (codePoint <= 0x10FFFF));
    }

}

