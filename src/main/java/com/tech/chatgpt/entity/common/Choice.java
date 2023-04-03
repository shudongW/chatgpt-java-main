package com.tech.chatgpt.entity.common;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * 描述：
 *
 * @author wsd
 *  
 */
@Data
public class Choice implements Serializable {
    private String text;
    private long index;
    private Object logprobs;
    @JsonProperty("finish_reason")
    private String finishReason;
}