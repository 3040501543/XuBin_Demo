package com.nettyDemo.endecoder;

public class CommonTools {

    public static final int FIXEDLENGTHFRAME_LENGTH = 256;//订场解码器对应的消息长度

    /**
     * 生成指定长度的字符串，不足位右侧补空格，否则还回原字符
     * @param str
     * @param assignlength
     * @return
     */
    public static String formatString(String str, int assignlength){
        int intStrLen=0;
        if (str != null) {
            intStrLen=str.length();
        }

        if (intStrLen >= assignlength) {//如果制定长度和字符串长度一致，直接返回原字符串
            return str;
        }
        else {
            String strSpace="";
            for (int i = 0,num=assignlength-intStrLen;i < num; i++) {
                strSpace=strSpace+" ";
            }
            return str+strSpace;
        }

    }
}
