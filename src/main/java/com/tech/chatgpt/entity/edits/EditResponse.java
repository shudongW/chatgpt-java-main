package com.tech.chatgpt.entity.edits;


import com.tech.chatgpt.entity.common.Choice;
import com.tech.chatgpt.entity.common.Usage;
import lombok.Data;

import java.io.Serializable;

/**
 * 描述：
 *
 * @author wsd
 *  
 */
@Data
public class EditResponse implements Serializable {
    private String id;
    private String object;
    private long created;
    private String model;
    private Choice[] choices;
    private Usage usage;
}
