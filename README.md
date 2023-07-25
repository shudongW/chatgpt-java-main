it’s an “unofficial" or "community-maintained” library.

这是一个非官方的社区维护的库。
OpenAi官方文档地址：https://platform.openai.com/docs/api-reference
## 此项目不仅仅支持chat对话模型，支持openai官方所有api，包括
- [x] Billing           余额查询
- [x] Models            模型检索
- [x] Completions       chatgpt对话
- [x] Images            图片模型
- [x] Embeddings        模型自定义训练
- [x] Files             文件上传自定义模型
- [x] Fine-tune         微调
- [x] Moderations       文本审核，敏感词鉴别
- [x] Engines           官方已移除
- [x] Chat              gpt-3.5对话模型
- [x] Speech To Text    语音转文字，语音翻译

**国内访问可以看下这个解决方案**：https://github.com/noobnooc/noobnooc/discussions/9

### 整合Spring Boot 实现CahtGPT对话模式，思路可以参考：https://github.com/shudongW/chatgpt-steam-server
此项目支持两种流式输出有完整示例代码可参考 。

流式输出实现方式 | 小程序 | 安卓 | ios | H5   
---|---|---|---|---
SSE参考：[OpenAIEventSourceListener](https://github.com/shudongW/chatgpt-steam-server/blob/main/src/main/java/com/tech/chatgpt/listener/OpenAIEventSourceListener.java) | 不支持| 支持| 支持 | 支持
WebSocket参考：[SocketIOListener](https://github.com/shudongW/chatgpt-steam-server/blob/main/src/main/java/com/tech/chatgpt/listener/SocketIOListener.java) | 支持| 支持| 支持| 支持
### 有bug欢迎朋友们指出，互相学习，所有咨询全部免费。
公众号 | 微信 | 知识星球
---|---|---
<img width="210" height="300" alt="二维码" src="https://raw.githubusercontent.com/shudongW/myself_img/main/gzh.jpg"> | <img width="210" height="300" alt="二维码" src="https://raw.githubusercontent.com/shudongW/myself_img/main/me.png"> | <img width="310" height="210" alt="二维码" src="https://raw.githubusercontent.com/shudongW/myself_img/main/xt.jpg">
---
## 更新日志


- [x] 1.0.14  坐标version：1.0.14-beta1，后续会更新正式版。升级支持最新版Gpt-3.5—0614、Gpt-4.0—0614等模型, 支持function-calling完整使用案例参考：[OpenAiClientFunctionTest](https://github.com/shudongW/chatgpt-java-main/blob/main/src/test/java/com/tech/chatgpt/OpenAiClientFunctionTest.java)
- [x] 1.0.14  ，支持function-calling
- [x] 1.0.14  支持当key异常（失效、过期、封禁）时，自定义动态处理key，参考实现[DynamicKeyOpenAiAuthInterceptor](https://github.com/shudongW/chatgpt-java-main/blob/main/src/test/java/com/tech/chatgpt/interceptor/DynamicKeyOpenAiAuthInterceptor.java) ，支持key异常时的告警处理（钉钉、飞书、email、企业微信等等需要自定义开发）
  
- [x] 1.0.13   支持Azure OpenAI官方接口
- [x] 1.0.8   支持所有的OpenAI官方接口

本项目支持**默认输出**和**流式输出**。完整SDK测试案例参考：

例Q | A
---|---
如何整合SpringBoot实现流式输出的Api接口？ | 参考另外一个项目：[chatgpt-steam-server](https://github.com/shudongW/chatgpt-steam-server)
最新版GPT-3.5-TURBO是否支持？ |参考测试案例：[OpenAiStreamClientTest](https://github.com/shudongW/chatgpt-java-main/blob/main/src/test/java/com/tech/chatgpt/OpenAiStreamClientTest.java) 
最新版语言转文字和语言翻译是否支持？ | 已经支持whisper参考测试案例：[OpenAiStreamClientTest](https://github.com/shudongW/chatgpt-java-main/blob/main/src/test/java/com/tech/chatgpt/OpenAiStreamClientTest.java) 
最新版Azure OpenAI是否支持？ |参考测试案例：[AzureOpenAITest](https://github.com/shudongW/chatgpt-java-main/blob/main/src/test/java/com/tech/chatgpt/AzureOpenAITest.java) 

---
# 工程简介

**ChatGPT的Java客户端**

OpenAI官方Api的Java SDK

目前支持api-keys的方式调用，获取api-keys可以百度或者csdn查一下。

**api-keys的方式调用目前需要用梯子才可访问。**

OpenAi官方文档地址：https://platform.openai.com/docs/api-reference

# 快速开始
本项目支持**默认输出**和**流式输出**
## 1、导入pom依赖
```
<dependency>
    <groupId>com.tech</groupId>
    <artifactId>chatgpt-java-main</artifactId>
    <version>1.0.13</version>
</dependency>
```
## 2、流式客户端使用示例：
更多SDK示例参考：[OpenAiStreamClientTest](https://github.com/shudongW/chatgpt-java-main/blob/main/src/test/java/com/tech/chatgpt/OpenAiStreamClientTest.java) 
### 默认OkHttpClient
```
public class Test {
    public static void main(String[] args) {
        OpenAiStreamClient client = OpenAiStreamClient.builder()
                .apiKey(Arrays.asList("sk-********","sk-********"))
                //自己做了代理就传代理地址，没有可不不传
//                .apiHost("https://自己代理的服务器地址/")
                .build();
        //聊天模型：gpt-3.5
        ConsoleEventSourceListener eventSourceListener = new ConsoleEventSourceListener();
        Message message = Message.builder().role(Message.Role.USER).content("你好啊我的伙伴！").build();
        ChatCompletion chatCompletion = ChatCompletion.builder().messages(Arrays.asList(message)).build();
        client.streamChatCompletion(chatCompletion, eventSourceListener);
        CountDownLatch countDownLatch = new CountDownLatch(1);
        try {
            countDownLatch.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
```
### 自定义OkHttpClient客户端使用示例：
```
public class Test {
    public static void main(String[] args) {
        //国内访问需要做代理，国外服务器不需要
        Proxy proxy = new Proxy(Proxy.Type.HTTP, new InetSocketAddress("127.0.0.1", 7890));
        HttpLoggingInterceptor httpLoggingInterceptor = new HttpLoggingInterceptor(new OpenAILogger());
        httpLoggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient okHttpClient = new OkHttpClient
                .Builder()
                .proxy(proxy)//自定义代理
                .addInterceptor(httpLoggingInterceptor)//自定义日志
                .connectTimeout(30, TimeUnit.SECONDS)//自定义超时时间
                .writeTimeout(30, TimeUnit.SECONDS)//自定义超时时间
                .readTimeout(30, TimeUnit.SECONDS)//自定义超时时间
                .build();
        OpenAiStreamClient client = OpenAiStreamClient.builder()
                .apiKey(Arrays.asList("sk-********","sk-********"))
                .okHttpClient(okHttpClient)
                //自己做了代理就传代理地址，没有可不不传
//                .apiHost("https://自己代理的服务器地址/")
                .build();
    }
}
```
输出日志（text是持续输出的）：
```
23:03:59.158 [省略无效信息] INFO com.unfbx.chatgpt.sse.ConsoleEventSourceListener - OpenAI建立sse连接...
23:03:59.160 [省略无效信息] INFO com.unfbx.chatgpt.sse.ConsoleEventSourceListener - OpenAI返回数据：{"id": "cmpl-6pIHnOOJiiUEVMesXwxzzcSQFoZHj", "object": "text_completion", "created": 1677683039, "choices": [{"text": "\n", "index": 0, "logprobs": null, "finish_reason": null}], "model": "text-davinci-003"}
23:03:59.172 [省略无效信息] INFO com.unfbx.chatgpt.sse.ConsoleEventSourceListener - OpenAI返回数据：{"id": "cmpl-6pIHnOOJiiUEVMesXwxzzcSQFoZHj", "object": "text_completion", "created": 1677683039, "choices": [{"text": "\n", "index": 0, "logprobs": null, "finish_reason": null}], "model": "text-davinci-003"}
23:03:59.251 [省略无效信息] INFO com.unfbx.chatgpt.sse.ConsoleEventSourceListener - OpenAI返回数据：{"id": "cmpl-6pIHnOOJiiUEVMesXwxzzcSQFoZHj", "object": "text_completion", "created": 1677683039, "choices": [{"text": "\u5fc3", "index": 0, "logprobs": null, "finish_reason": null}], "model": "text-davinci-003"}
23:03:59.313 [省略无效信息] INFO com.unfbx.chatgpt.sse.ConsoleEventSourceListener - OpenAI返回数据：{"id": "cmpl-6pIHnOOJiiUEVMesXwxzzcSQFoZHj", "object": "text_completion", "created": 1677683039, "choices": [{"text": "\u60c5", "index": 0, "logprobs": null, "finish_reason": null}], "model": "text-davinci-003"}
23:03:59.380 [省略无效信息] INFO com.unfbx.chatgpt.sse.ConsoleEventSourceListener - OpenAI返回数据：{"id": "cmpl-6pIHnOOJiiUEVMesXwxzzcSQFoZHj", "object": "text_completion", "created": 1677683039, "choices": [{"text": "\u8212", "index": 0, "logprobs": null, "finish_reason": null}], "model": "text-davinci-003"}
23:03:59.439 [省略无效信息] INFO com.unfbx.chatgpt.sse.ConsoleEventSourceListener - OpenAI返回数据：{"id": "cmpl-6pIHnOOJiiUEVMesXwxzzcSQFoZHj", "object": "text_completion", "created": 1677683039, "choices": [{"text": "\u7545", "index": 0, "logprobs": null, "finish_reason": null}], "model": "text-davinci-003"}
23:03:59.532 [省略无效信息] INFO com.unfbx.chatgpt.sse.ConsoleEventSourceListener - OpenAI返回数据：{"id": "cmpl-6pIHnOOJiiUEVMesXwxzzcSQFoZHj", "object": "text_completion", "created": 1677683039, "choices": [{"text": "\uff0c", "index": 0, "logprobs": null, "finish_reason": null}], "model": "text-davinci-003"}
23:03:59.579 [省略无效信息] INFO com.unfbx.chatgpt.sse.ConsoleEventSourceListener - OpenAI返回数据：{"id": "cmpl-6pIHnOOJiiUEVMesXwxzzcSQFoZHj", "object": "text_completion", "created": 1677683039, "choices": [{"text": "\u5fc3", "index": 0, "logprobs": null, "finish_reason": null}], "model": "text-davinci-003"}
23:03:59.641 [省略无效信息] INFO com.unfbx.chatgpt.sse.ConsoleEventSourceListener - OpenAI返回数据：{"id": "cmpl-6pIHnOOJiiUEVMesXwxzzcSQFoZHj", "object": "text_completion", "created": 1677683039, "choices": [{"text": "\u65f7", "index": 0, "logprobs": null, "finish_reason": null}], "model": "text-davinci-003"}
23:03:59.673 [省略无效信息] INFO com.unfbx.chatgpt.sse.ConsoleEventSourceListener - OpenAI返回数据：{"id": "cmpl-6pIHnOOJiiUEVMesXwxzzcSQFoZHj", "object": "text_completion", "created": 1677683039, "choices": [{"text": "\u795e", "index": 0, "logprobs": null, "finish_reason": null}], "model": "text-davinci-003"}
23:03:59.751 [省略无效信息] INFO com.unfbx.chatgpt.sse.ConsoleEventSourceListener - OpenAI返回数据：{"id": "cmpl-6pIHnOOJiiUEVMesXwxzzcSQFoZHj", "object": "text_completion", "created": 1677683039, "choices": [{"text": "\u6021", "index": 0, "logprobs": null, "finish_reason": null}], "model": "text-davinci-003"}
23:03:59.782 [省略无效信息] INFO com.unfbx.chatgpt.sse.ConsoleEventSourceListener - OpenAI返回数据：{"id": "cmpl-6pIHnOOJiiUEVMesXwxzzcSQFoZHj", "object": "text_completion", "created": 1677683039, "choices": [{"text": "\u3002", "index": 0, "logprobs": null, "finish_reason": null}], "model": "text-davinci-003"}
23:03:59.815 [省略无效信息] INFO com.unfbx.chatgpt.sse.ConsoleEventSourceListener - OpenAI返回数据：[DONE]
23:03:59.815 [省略无效信息] INFO com.unfbx.chatgpt.sse.ConsoleEventSourceListener - OpenAI返回数据结束了
23:03:59.815 [省略无效信息] INFO com.unfbx.chatgpt.sse.ConsoleEventSourceListener - OpenAI关闭sse连接...
```
## 3、默认客户端使用示例（支持全部API）：
更多SDK示例参考：[OpenAiClientTest](https://github.com/shudongW/chatgpt-java-main/blob/main/src/test/java/com/tech/chatgpt/OpenAiClientTest.java) 
### 默认OkHttpClient
```
public class Test {
    public static void main(String[] args) {
        OpenAiClient openAiClient = OpenAiClient.builder()
                .apiKey(Arrays.asList("sk-********","sk-********"))
                //自己做了代理就传代理地址，没有可不不传
//                .apiHost("https://自己代理的服务器地址/")
                .build();
                //聊天模型：gpt-3.5
        Message message = Message.builder().role(Message.Role.USER).content("你好啊我的伙伴！").build();
        ChatCompletion chatCompletion = ChatCompletion.builder().messages(Arrays.asList(message)).build();
        ChatCompletionResponse chatCompletionResponse = openAiClient.chatCompletion(chatCompletion);
        chatCompletionResponse.getChoices().forEach(e -> {
            System.out.println(e.getMessage());
        });
    }
}
```
### 自定义OkHttpClient客户端使用示例：
```
public class Test {
    public static void main(String[] args) {
        //国内访问需要做代理，国外服务器不需要
        Proxy proxy = new Proxy(Proxy.Type.HTTP, new InetSocketAddress("127.0.0.1", 7890));
        HttpLoggingInterceptor httpLoggingInterceptor = new HttpLoggingInterceptor(new OpenAILogger());
        httpLoggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient okHttpClient = new OkHttpClient
                .Builder()
                .proxy(proxy)//自定义代理
                .addInterceptor(httpLoggingInterceptor)//自定义日志输出
                .addInterceptor(new OpenAiResponseInterceptor())//自定义返回值拦截
                .connectTimeout(10, TimeUnit.SECONDS)//自定义超时时间
                .writeTimeout(30, TimeUnit.SECONDS)//自定义超时时间
                .readTimeout(30, TimeUnit.SECONDS)//自定义超时时间
                .build();
        //构建客户端
        OpenAiClient openAiClient = OpenAiClient.builder()
                .apiKey(Arrays.asList("sk-********","sk-********"))
                .okHttpClient(okHttpClient)
                //自己做了代理就传代理地址，没有可不不传
//                .apiHost("https://自己代理的服务器地址/")
                .build();
                //聊天模型：gpt-3.5
        Message message = Message.builder().role(Message.Role.USER).content("你好啊我的伙伴！").build();
        ChatCompletion chatCompletion = ChatCompletion.builder().messages(Arrays.asList(message)).build();
        ChatCompletionResponse chatCompletionResponse = openAiClient.chatCompletion(chatCompletion);
        chatCompletionResponse.getChoices().forEach(e -> {
            System.out.println(e.getMessage());
        });
    }
}
```
## 方式二（下载源码直接运行）

### **OpenAI全部接口支持调用**
完整测试案例参考：
com.tech.chatgpt.OpenAiClientTest 
com.tech.chatgpt.OpenAiStreamClientTest

### **Azure OpenAI全部接口支持调用**
com.tech.chatgpt.AzureOpenAITest.java

# Star History

[![Star History Chart](https://api.star-history.com/svg?repos=shudongW/chatgpt-java-main&type=Date)](https://star-history.com/#shudongW/chatgpt-java-main&Date)
