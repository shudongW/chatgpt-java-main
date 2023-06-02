package com.tech.chatgpt;

import com.tech.chatgpt.entity.billing.Subscription;
import com.tech.chatgpt.entity.chat.AzureChatCompletion;
import com.tech.chatgpt.entity.chat.ChatCompletion;
import com.tech.chatgpt.entity.chat.ChatCompletionResponse;
import com.tech.chatgpt.entity.chat.Message;
import com.tech.chatgpt.entity.completions.Completion;
import com.tech.chatgpt.interceptor.OpenAILogger;
import com.tech.chatgpt.interceptor.OpenAiResponseInterceptor;
import com.tech.chatgpt.sse.ConsoleEventSourceListener;
import com.tech.chatgpt.utils.TikTokensUtil;
import lombok.extern.slf4j.Slf4j;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import org.junit.Before;
import org.junit.Test;

import java.net.InetSocketAddress;
import java.net.Proxy;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

@Slf4j
public class AzureOpenAITest {

    private AzureOpenAISteamClient client;

    @Before
    public void before() {
        //可以为null
//        Proxy proxy = new Proxy(Proxy.Type.HTTP, new InetSocketAddress("127.0.0.1", 7890));
        HttpLoggingInterceptor httpLoggingInterceptor = new HttpLoggingInterceptor(new OpenAILogger());
        //！！！！千万别再生产或者测试环境打开BODY级别日志！！！！
        //！！！生产或者测试环境建议设置为这三种级别：NONE,BASIC,HEADERS,！！！
        httpLoggingInterceptor.setLevel(HttpLoggingInterceptor.Level.HEADERS);
        OkHttpClient okHttpClient = new OkHttpClient
                .Builder()
//                .proxy(proxy)
                .addInterceptor(httpLoggingInterceptor)
                .addInterceptor(new OpenAiResponseInterceptor())
                .connectTimeout(10, TimeUnit.SECONDS)
                .writeTimeout(30, TimeUnit.SECONDS)
                .readTimeout(30, TimeUnit.SECONDS)
                .build();
        client = AzureOpenAISteamClient.builder()
                //支持多key传入，请求时候随机选择
                .apiKey(Arrays.asList("14c768144*******3c5d5b"))
                //自定义key的获取策略：默认KeyRandomStrategy
                //.keyStrategy(new KeyRandomStrategy())
                .keyStrategy(new FirstKeyStrategy())
                .okHttpClient(okHttpClient)
                //自己做了代理就传代理地址，没有可不不传,(关注公众号回复：openai ，获取免费的测试代理地址)
                .apiHost("https://azure-openai.openai.azure.com/")
                .build();
    }

    @Test
    public void chatStreamTest() {
        //聊天模型：gpt-3.5
        ConsoleEventSourceListener eventSourceListener = new ConsoleEventSourceListener();
        Message message = Message.builder().role(Message.Role.USER).content("random one word！").build();
        ChatCompletion chatCompletion = ChatCompletion
                .builder()
                .model(ChatCompletion.Model.GPT_3_5_TURBO.getName())
                .temperature(0.2)
                .maxTokens(2048)
                .messages(Arrays.asList(message))
                .stream(true)
                .build();
        client.streamChatCompletion(chatCompletion, eventSourceListener, "openai/deployments/[ModelName]/chat/completions?api-version=2023-03-15-preview");
        CountDownLatch countDownLatch = new CountDownLatch(1);
        try {
            countDownLatch.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void chatTest() {
        //聊天模型：davinci--003
        ConsoleEventSourceListener eventSourceListener = new ConsoleEventSourceListener();
        Completion completion = Completion
                .builder()
                .model("text-davinci-003")
                .prompt("random one word!")
                .temperature(0.2)
                .maxTokens(2048)
                .stream(true)
                .build();
        client.streamCompletions(completion, eventSourceListener, "openai/deployments/[ModelName]/completions?api-version=2022-12-01");
        CountDownLatch countDownLatch = new CountDownLatch(1);
        try {
            countDownLatch.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
