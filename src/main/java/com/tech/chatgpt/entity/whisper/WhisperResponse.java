package com.tech.chatgpt.entity.whisper;

import lombok.Data;

import java.io.Serializable;

/**
 * 描述：
 *
 * @author wsd
 * @since 2023-03-02
 */
@Data
public class WhisperResponse implements Serializable {

    private String text;
}
