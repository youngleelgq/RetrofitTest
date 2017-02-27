package com.younglee.retrofittest.safe;

import com.younglee.retrofittest.common.Const;
import com.younglee.retrofittest.exception.MyHttpException;
import com.younglee.retrofittest.utils.CharUtils;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.UUID;


/**
 * @author chengxingjiang
 * @version V1.0
 * @Description： 请求字段加密工具类
 */
@SuppressWarnings("ALL")
public class SafeUtil {

    /**
     * @return String 返回类型
     * @throws MyHttpException toEncData
     */
    public static String getEncData(String jsonStr) throws MyHttpException {
        try {
            // 生成对称秘钥
            String key = UUID.randomUUID().toString();
            key = key.replace("-", "");

//            LogUtils.e("toEncKey(key)：",toEncKey(key));
//            LogUtils.e("toEncKey(key)：",toEncKey(key).length()+"");

//            LogUtils.e("SafeUtil.encObj2Str(jsonStr, key)：",SafeUtil.encObj2Str(jsonStr, key));
            return "umpay" + toEncKey(key) + SafeUtil.encObj2Str(jsonStr, key);

        } catch (Exception e) {
            e.printStackTrace();
            throw new MyHttpException(MyHttpException.CODE_ENC_EXCEPTION);
        }
    }


    /**
     * getDecData
     *
     * @return String    返回类型
     * @Description: 解密，从256字符中解出对称秘钥；然后对后续报文做des解密
     */
    public static String getDecData(String encData) throws MyHttpException {
        try {
            String key = encData.substring(0, 256);
            key = decKey(key);
            String content = encData.substring(256);
            return (String) decStr2Obj(content, key);
        } catch (Exception e) {
            e.printStackTrace();
            throw new MyHttpException(MyHttpException.CODE_DEC_EXCEPTION);
        }

    }

    /**
     * getKey
     *
     * @param @param  desKey
     * @param @return
     * @param @throws Exception 设定文件
     * @return String 返回类型
     * @throws
     * @Title: getKey
     * @Description: 解析得到对称秘钥
     */
    private static String decKey(String desKey) throws Exception {

        byte[] data = CharUtils.hexStringToBytes(desKey);

        byte[] decodedData = RSACoder.decryptByPublicKey(data,
                Tag.getTag(Const.Config.DEV_DEBUG));
        return new String(decodedData);
    }

    //public static String publicKey = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQCigwlB4DdiofJiedeeCNjPcFAmlq4wH7r9qt9dGEcvQJXRBrc0ce3M2PM7dc/Da3ZY7nRUrSCqbAjpnNsXqR8ehUOpCDr/cCczuBwnLQ1Iaxqvj59IY7IoXaQedH4/81lnVRFkDutphfC+CsA9oimxPenzPB7VG5D9yAv18BLXHwIDAQAB";

    private static String toEncKey(String desKey) throws Exception {

        byte[] data = desKey.getBytes();

//        LogUtils.e("Tag.getTag(Conts.Config.DEV_DEBUG)",Tag.getTag(Conts.Config.DEV_DEBUG));
        byte[] encodedData = RSACoder.encryptByPublicKey(data, Tag.getTag(Const.Config.DEV_DEBUG));

        return CharUtils.bytesToHexString(encodedData);
    }


    /**
     * @param obj
     * @return
     * @throws Exception String
     * @Description：对象序列化成字符串，并对该字符串DES加密 <p>
     * 创建人：baichenxi , 2014-4-4 下午3:49:14
     * </p>
     * <p>
     * 修改人：baichenxi , 2014-4-4 下午3:49:14
     * </p>
     */
    public static String encObj2Str(Object obj, String key) throws Exception {
        if (obj == null) {
            return null;
        }

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ObjectOutputStream oos = null;
        try {
            oos = new ObjectOutputStream(baos);
            oos.writeObject(obj);
            byte[] encStr = DesAlaorithm.symmetricEncrypto(baos.toByteArray(),
                    key);
            // return new String(Base64.encode(encStr, Base64.DEFAULT));
            return CharUtils.bytesToHexString(encStr);
        } finally {
            if (oos != null) {
                try {
                    oos.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }

    /**
     * @param str
     * @return
     * @throws Exception Object
     * @Description：对字符串进行Base64解码,DES解密，并对该字符串反序列化成对象 <p>
     * 创建人：baichenxi , 2014-4-4
     * 下午3:49:14
     * </p>
     * <p>
     * 修改人：baichenxi , 2014-4-4
     * 下午3:49:14
     * </p>
     */
    public static Object decStr2Obj(String str, String key) throws Exception {
        if (str == null) {
            throw new IllegalArgumentException("str can not null.");
        }

        ObjectInputStream ois = null;
        try {

            // byte[] data = Base64.decode(str.getBytes(), Base64.DEFAULT);
            byte[] decoder = DesAlaorithm.symmetricDecrypto(
                    CharUtils.hexStringToBytes(str), key);

            ByteArrayInputStream bais = new ByteArrayInputStream(decoder);
            ois = new ObjectInputStream(bais);
            return ois.readObject();
        } finally {
            if (ois != null) {
                try {
                    ois.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

}