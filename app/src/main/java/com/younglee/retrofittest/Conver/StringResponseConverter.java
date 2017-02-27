package com.younglee.retrofittest.Conver;

import java.io.IOException;

import okhttp3.ResponseBody;
import retrofit2.Converter;

/**
 * @author yangningbo
 * @version V1.0
 * @Description： 将对象转换为String的类<p>
 * 创建日期：2013-9-4
 * </p>
 * @see
 */
@SuppressWarnings("JavaDoc")
public class StringResponseConverter implements Converter<ResponseBody, String> {

    @Override
    public String convert(ResponseBody value) throws IOException {

        try {
            return value.string();
        } finally {
            value.close();
        }

    }
}
