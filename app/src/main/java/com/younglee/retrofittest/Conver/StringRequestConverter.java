package com.younglee.retrofittest.Conver;

import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.nio.charset.Charset;

import okhttp3.MediaType;
import okhttp3.RequestBody;
import okio.Buffer;
import retrofit2.Converter;

/**
 * @author yangningbo
 * @version V1.0
 * @Description： 将String转换为对象的类<p>
 * 创建日期：2013-9-4
 * </p>
 * @see
 */
@SuppressWarnings("JavaDoc")
public class StringRequestConverter implements Converter<String, RequestBody> {

    private static final MediaType MEDIA_TYPE = MediaType.parse("application/json; charset=UTF-8");
    private static final Charset UTF_8 = Charset.forName("UTF-8");

    StringRequestConverter() {
    }

    @Override
    public RequestBody convert(String value) throws IOException {
        Buffer buffer = new Buffer();
        Writer writer = new OutputStreamWriter(buffer.outputStream(), UTF_8);
        writer.write(value);
        writer.close();
        return RequestBody.create(MEDIA_TYPE, buffer.readByteString());
    }
}
