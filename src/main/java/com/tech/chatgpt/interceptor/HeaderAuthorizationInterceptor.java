package com.tech.chatgpt.interceptor;

import cn.hutool.core.util.RandomUtil;
import cn.hutool.http.ContentType;
import cn.hutool.http.Header;
import lombok.Getter;
import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * 描述：请求增加header apikey
 *
 * @author grt
 */
@Getter
public class HeaderAuthorizationInterceptor implements Interceptor {

    private List<String> apiKey;
    /**
     * key是否有效
     */
    private Map<String,Boolean> apiKeyMap;

    public HeaderAuthorizationInterceptor(List<String> apiKey) {
        this.apiKey = apiKey;
        apiKeyMap = apiKey.stream().collect(Collectors.toMap(Function.identity(), s -> true));
    }

    @Override
    public Response intercept(Chain chain) throws IOException {
        Request original = chain.request();
        Request request = original.newBuilder()
                .header(Header.AUTHORIZATION.getValue(), "Bearer " + RandomUtil.randomEle(apiKey))
                .header(Header.CONTENT_TYPE.getValue(), ContentType.JSON.getValue())
                .method(original.method(), original.body())
                .build();
        return chain.proceed(request);
    }
}
