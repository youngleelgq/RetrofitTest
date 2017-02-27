package com.younglee.retrofittest.utils;

/**
 * @author chengxingjiang
 * @version V1.0
 * @Description：字符转换工具类 <p>
 * 创建日期：2013-4-26
 * </p>
 * @see
 */
@SuppressWarnings({"JavaDoc", "unused", "SpellCheckingInspection"})
public class CharUtils {

    private static final char[] HEX = {'0', '1', '2', '3', '4', '5', '6', '7',
            '8', '9', 'A', 'B', 'C', 'D', 'E', 'F'};

    /**
     * @param bytes
     * @return String
     * @Description：字节数组转成字符串 <p>
     * 创建人：yangningbo , 2013-4-26 下午3:25:14
     * </p>
     * <p>
     * 修改人：yangningbo , 2013-4-26 下午3:25:14
     * </p>
     */
    public static String bytesToString(byte[] bytes) {
        return bytesToString(bytes, 0, bytes.length);
    }

    /**
     * @param bytes
     * @param len
     * @return String
     * @Description：从0字节开始转指定长度的字节数组为字符串 <p>
     * 创建人：yangningbo , 2013-4-26 下午3:25:52
     * </p>
     * <p>
     * 修改人：yangningbo , 2013-4-26 下午3:25:52
     * </p>
     */
    public static String bytesToString(byte[] bytes, int len) {
        return bytesToString(bytes, 0, len);
    }

    /**
     * @param bytes
     * @param pos
     * @param len
     * @return String
     * @Description：从pos字节开始转指定长度的字节数组为字符串 <p>
     * 创建人：yangningbo , 2013-4-26 下午3:27:54
     * </p>
     * <p>
     * 修改人：yangningbo , 2013-4-26 下午3:27:54
     * </p>
     */
    public static String bytesToString(byte[] bytes, int pos, int len) {
        return new String(bytes, pos, len);
    }

    /**
     * @param bytes
     * @return String
     * @Description：字节数组转十六进制字符串 <p>
     * 创建人：yangningbo , 2013-4-26 下午3:29:06
     * </p>
     * <p>
     * 修改人：yangningbo , 2013-4-26 下午3:29:06
     * </p>
     */
    public static String bytesToHexString(byte[] bytes) {
        return bytesToHexString(bytes, "");
    }


    public static String bytesToHexString(byte[] bytes, String separator) {
        StringBuilder sb = new StringBuilder();

        for (byte b : bytes) {
            sb.append(String.format("%02X" + separator, b & 0xFF));
        }
        return sb.toString();
    }

    public static String bytesToHexString(byte[] d, int s, int n) {
        final char[] ret = new char[n * 2];
        final int e = s + n;

        int x = 0;
        for (int i = s; i < e; ++i) {
            final byte v = d[i];
            ret[x++] = HEX[0x0F & (v >> 4)];
            ret[x++] = HEX[0x0F & v];
        }
        return new String(ret);
    }

    public static short bytesToShort(byte[] bytes) {
        return (short) (((bytes[0] & 0xFF) << 8) + (bytes[1] & 0xFF));
    }

    public static byte[] shortToBytes(short value) {
        return new byte[]{(byte) (value >>> 8), (byte) (value)};
    }

    public static int bytesToInt(byte[] bytes) {
        int ret = 0;

        for (byte b : bytes) {
            ret <<= 8;
            ret |= b & 0xFF;
        }
        return ret;
    }

    public static int bytesToInt(byte[] bytes, int s, int n) {
        int ret = 0;

        final int e = s + n;
        for (int i = s; i < e; ++i) {
            ret <<= 8;
            ret |= bytes[i] & 0xFF;
        }
        return ret;
    }

    public static byte[] intToBytes(int n) {
        return new byte[]{(byte) (0x000000ff & (n >>> 24)),
                (byte) (0x000000ff & (n >>> 16)),
                (byte) (0x000000ff & (n >>> 8)), (byte) (0x000000ff & (n))};
    }

    public static byte[] hexStringToBytes(String s) {
        int len = s.length();
        byte[] data = new byte[len / 2];
        for (int i = 0; i < len; i += 2) {
            data[i / 2] = (byte) ((Character.digit(s.charAt(i), 16) << 4) + Character
                    .digit(s.charAt(i + 1), 16));
        }
        return data;
    }

      /*-----------------------SW1  SW2------------------------------------*/

    /**
     * @param data
     * @return int
     * @Description：取响应状态 <p>
     * 创建人：yangningbo , 2013-4-26 下午3:49:20
     * </p>
     * <p>
     * 修改人：yangningbo , 2013-4-26 下午3:49:20
     * </p>
     */
    public static int getSW1SW2(byte[] data) {
        if (data.length < 2) {
            return 0;
        }

        int sw1sw2 = (data[data.length - 2] << 8) & 0xFF00;
        sw1sw2 |= data[data.length - 1] & 0x00FF;
        return sw1sw2;
    }

    public static int getSW1(byte[] data) {
        int sw1sw2 = getSW1SW2(data);
        byte[] content = intToBytes(sw1sw2);
        byte[] result = new byte[1];
        result[0] = content[0];
        return bytesToInt(result);
    }

    /**
     * @param data
     * @return byte[]
     * @Description：去除SW1SW2 <p>
     * 创建人：yangningbo , 2013-4-26 下午3:48:44
     * </p>
     * <p>
     * 修改人：yangningbo , 2013-4-26 下午3:48:44
     * </p>
     */
    public static byte[] stripSW1SW2(byte[] data) {
        if (data.length < 2) {
            return new byte[0];
        }

        byte[] strippedData = new byte[data.length - 2];
        System.arraycopy(data, 0, strippedData, 0, data.length - 2);
        return strippedData;
    }


}
