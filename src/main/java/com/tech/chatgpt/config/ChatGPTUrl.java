package com.tech.chatgpt.config;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 描述： api地址
 *
 * @author wsd
 */
@Getter
@AllArgsConstructor
public enum ChatGPTUrl {

    COMPLETIONS("https://api.openai.com/v1/chat/completions"),
    ;

    private String url;

}
