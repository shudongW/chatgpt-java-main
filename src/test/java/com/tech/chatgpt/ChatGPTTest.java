package com.tech.chatgpt;

import com.tech.chatgpt.entity.images.ImageEdit;

/**
 * 描述： 测试类
 *
 * @author wsd
 *  2023-02-11
 */
public class ChatGPTTest {
    public static void main(String[] args) {
        ChatGPTClient client = new ChatGPTClient("*********************");
        String body = client.askQuestion("简单描述下三体这本书");
        System.out.println(body);
    }
}
