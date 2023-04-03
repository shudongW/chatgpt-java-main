package com.tech.chatgpt.entity.common;

import lombok.Data;

import java.io.Serializable;

/**
 * 描述：
 *
 * @author wsd
 *  
 */
@Data
public class DeleteResponse implements Serializable {
    private String id;
    private String object;
    private boolean deleted;
}
