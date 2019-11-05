package com.jtjt.qtgl.http;

import android.content.Context;

import com.alibaba.fastjson.JSON;
import com.jtjt.qtgl.bean.base.OrderBean;
import com.jtjt.qtgl.ui.login.Constant;
import com.jtjt.qtgl.util.AppUtil;
import com.jtjt.qtgl.util.MyLog;

import java.io.EOFException;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.Locale;

import okhttp3.Interceptor;
import okhttp3.MediaType;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.ResponseBody;
import okio.Buffer;
import okio.BufferedSource;

/**
 * Created by 董凯强 on 2017/6/30.
 */

public class LogInterceptor implements Interceptor {

    public static final String TAG = "LogInterceptor.java";
    private Context context;

    public LogInterceptor(Context context) {
        this.context = context;
    }

    @Override
    public Response intercept(Chain chain) throws IOException {
        Request request = chain.request();
//        //头部
        Request.Builder builder = request.newBuilder();
        OrderBean bean = JSON.parseObject(MyApp.myShare.getString(Constant.USER_DATA), OrderBean.class);
        if (bean != null && AppUtil.isNoEmpty(bean.getLogintoken())) {
            builder.addHeader("logintoken", bean.getLogintoken());
        }
        Request.Builder requestBuilder = builder.method(request.method(), request.body());
        request = requestBuilder.build();

        /**
         * 动态增加MultipartBody post参数
         */
//        if (request.body() != null) {
//            if (request.body() instanceof MultipartBody) {
//                MultipartBody multipartBody = (MultipartBody) request.body();
//                MultipartBody.Builder builder = new MultipartBody.Builder().setType(MultipartBody.FORM);
//                Map<String, String> extraFormBodyParamMap = new HashMap<>();
//                String token = MyApplication.myShare.getString(Constant.TOKEN);
//                if (AppUtil.isNoEmpty(token)) {
//                    extraFormBodyParamMap.put("logintoken", token);
//                }
//                for (Map.Entry<String, String> entry : extraFormBodyParamMap.entrySet()) {
//                    builder.addFormDataPart(entry.getKey(), entry.getValue());
//                }
//                List<MultipartBody.Part> parts = multipartBody.parts();
//                for (MultipartBody.Part part : parts) {
//                    builder.addPart(part);
//                }
//                request = request.newBuilder().post(builder.build()).build();
//            }
//        }

        /**
         * 动态增加FormBody post参数
         */
//        if (request.body() != null) {
//            if (request.body() instanceof FormBody) {
//                //默认添加formBody后不能添加新的form表单，需要先将RequestBody转成string去拼接
//                String postBodyString = bodyToString(request.body());
//                FormBody.Builder form = new FormBody.Builder()//form表单
//                        .add("apiid", "sDHMNfPbdSu5drHmlSjg");
//                String token = MyApplication.myShare.getString(Constant.TOKEN);
//                if (!postBodyString.contains("logintoken=") && AppUtil.isNoEmpty(token)) {
//                    form.add("logintoken", token);
//                }
//                RequestBody formBody = form.build();
//                postBodyString += ((postBodyString.length() > 0) ? "&" : "") + bodyToString(formBody);
//                request = request.newBuilder()
//                        .method(request.method(), request.body())
//                        //添加到请求里
//                        //string转回成RequestBody
//                        .post(RequestBody.create(MediaType.parse("application/x-www-form-urlencoded"),
//                                postBodyString))
//                        .build();
//            }
//        }
        /**
         * 动态增加get参数
         */
//        if (request.method().equals("GET")) {
//            HttpUrl url = request.url().newBuilder()
//                    .addQueryParameter("device", "2")
//                    .addQueryParameter("apiid", "sDHMNfPbdSu5drHmlSjg")
//                    .build();
//            request = request.newBuilder()
//                    .method(request.method(), request.body())
//                    //添加到请求里
//                    .url(url)
//                    .build();
//        }

        //the request url
        String url = request.url().toString();
        //the request method
        String method = request.method();
        long t1 = System.nanoTime();
        MyLog.d(TAG, String.format(Locale.getDefault(), "Sending %s request [url = %s]", method, url));


        //the request body
        RequestBody requestBody = request.body();
        if (requestBody != null) {
            StringBuilder sb = new StringBuilder("Request Body [");
            Buffer buffer = new Buffer();
            requestBody.writeTo(buffer);
            Charset charset = Charset.forName("UTF-8");
            MediaType contentType = requestBody.contentType();
            if (contentType != null) {
                charset = contentType.charset(charset);
            }
            if (isPlaintext(buffer)) {
                sb.append(buffer.readString(charset));
                sb.append(" (Content-Type = ").append(contentType.toString()).append(",")
                        .append(requestBody.contentLength()).append("-byte body)");
            } else {
                sb.append(" (Content-Type = ").append(contentType.toString())
                        .append(",binary ").append(requestBody.contentLength()).append("-byte body omitted)");
            }
            sb.append("]");
            MyLog.d(TAG, String.format(Locale.getDefault(), "%s %s", method, sb.toString()));
        }
        Response response = chain.proceed(request);
        long t2 = System.nanoTime();
        //the response time
        MyLog.d(TAG, String.format(Locale.getDefault(), "Received response for [url = %s] in %.1fms", url, (t2 - t1) / 1e6d));

        //the response state
        MyLog.d(TAG, String.format(Locale.CHINA, "Received response is %s ,message[%s],code[%d]", response.isSuccessful() ? "success" : "fail", response.message(), response.code()));

        //the response data
        ResponseBody body = response.body();

        BufferedSource source = body.source();
        source.request(Long.MAX_VALUE); // Buffer the entire body.
        Buffer buffer = source.buffer();
        Charset charset = Charset.defaultCharset();
        MediaType contentType = body.contentType();
        if (contentType != null) {
            charset = contentType.charset(charset);
        }
        String bodyString = buffer.clone().readString(charset);
        MyLog.d(TAG, String.format("Received response json string [%s]", bodyString));

        return response;
    }

    static boolean isPlaintext(Buffer buffer) {
        try {
            Buffer prefix = new Buffer();
            long byteCount = buffer.size() < 64 ? buffer.size() : 64;
            buffer.copyTo(prefix, 0, byteCount);
            for (int i = 0; i < 16; i++) {
                if (prefix.exhausted()) {
                    break;
                }
                int codePoint = prefix.readUtf8CodePoint();
                if (Character.isISOControl(codePoint) && !Character.isWhitespace(codePoint)) {
                    return false;
                }
            }
            return true;
        } catch (EOFException e) {
            return false; // Truncated UTF-8 sequence.
        }
    }

    private static String bodyToString(final RequestBody request) {
        try {
            final RequestBody copy = request;
            final Buffer buffer = new Buffer();
            if (copy != null)
                copy.writeTo(buffer);
            else
                return "";
            return buffer.readUtf8();
        } catch (final IOException e) {
            return "did not work";
        }
    }

}
