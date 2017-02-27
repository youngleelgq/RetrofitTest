package com.younglee.retrofittest.safe;

/**
 * @author chengxingjiang
 * @version V1.0
 * @Description： 加密文件so回调类中方法
 */
@SuppressWarnings({"JavaDoc", "SpellCheckingInspection", "unused"})
public class Tag {

    @SuppressWarnings("FieldCanBeLocal")
    private static String sTag;

    /**
     * callback
     *
     * @param
     * @return void    返回类型
     * @throws
     * @Title: callback
     * @Description: 无参数，如果加了参数，c层调不上来
     */
    public static void callback() {

        sTag = "com.younglee.retrofittest";
    }

    public static native String getTag(boolean debug);

    static {
        // System.loadLibrary 参数为库文件名，不包含库文件的扩展名。
        System.loadLibrary("tag");
    }

}
