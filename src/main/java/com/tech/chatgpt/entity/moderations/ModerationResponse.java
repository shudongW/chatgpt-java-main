package com.tech.chatgpt.entity.moderations;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * 描述：
 *
 * @author wsd
 *  
 */
@Data
public class ModerationResponse implements Serializable {
    private String id;
    private String model;
    private List<Result> results;
}
