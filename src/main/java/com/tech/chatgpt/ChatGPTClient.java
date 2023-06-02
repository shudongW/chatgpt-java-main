package com.tech.chatgpt;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.http.*;
import cn.hutool.json.JSONUtil;
import com.tech.chatgpt.config.ChatGPTUrl;
import com.tech.chatgpt.entity.common.Choice;
import com.tech.chatgpt.entity.common.OpenAiResponse;
import com.tech.chatgpt.entity.completions.CompletionResponse;
import com.tech.chatgpt.entity.completions.Completion;
import com.tech.chatgpt.exception.BaseException;
import com.tech.chatgpt.exception.CommonError;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * 描述： chatgpt客户端
 * 已经弃用，请使用下面的实现方式：
 * @see OpenAiClient
 * @see OpenAiStreamClient
 * @author wsd
 *  2023-02-11
 */
@Getter
@Slf4j
public class ChatGPTClient {

    private String apiKey;

    public ChatGPTClient(String apiKey) {
        this.apiKey = apiKey;
    }

    /**
     * 已经弃用，请使用下面的实现方式：
     * @see OpenAiClient
     * @see OpenAiStreamClient
     * @param question
     * @return
     */
    @Deprecated
    public String askQuestion(String question) {
        Completion q = Completion.builder()
                .prompt(question)
                .build();
        HttpResponse response = HttpRequest
                .post(ChatGPTUrl.COMPLETIONS.getUrl())
                .body(JSONUtil.toJsonStr(q))
                .header(Header.AUTHORIZATION.getValue(), "Bearer " + this.apiKey)
                .header(Header.CONTENT_TYPE.getValue(), ContentType.JSON.getValue())
                .execute();
        String body = response.body();
        log.info("调用ChatGPT请求返回值：{}", body);
        if (!response.isOk()) {
            if (response.getStatus() == HttpStatus.HTTP_UNAUTHORIZED) {
                CompletionResponse answer = JSONUtil.toBean(response.body(), CompletionResponse.class);
                throw new BaseException(answer.getError().getMessage());
            }
            throw new BaseException(CommonError.RETRY_ERROR);
        }
        log.info("调用ChatGPT请求返回值：{}", body);
        CompletionResponse answer = JSONUtil.toBean(body, CompletionResponse.class);
        if (Objects.nonNull(answer.getError())) {
            return answer.getError().getMessage();
        }
        List<Choice> choiceList = Arrays.stream(answer.getChoices()).collect(Collectors.toList());
        if (CollectionUtil.isEmpty(choiceList)) {
            throw new BaseException(CommonError.RETRY_ERROR);
        }
        StringBuilder msg = new StringBuilder();
        choiceList.forEach(e -> {
            msg.append(e.getText());
            msg.append("\n");
        });
        return msg.toString();
    }


}
