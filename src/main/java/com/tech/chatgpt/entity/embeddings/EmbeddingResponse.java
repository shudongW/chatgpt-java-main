package com.tech.chatgpt.entity.embeddings;

import com.tech.chatgpt.entity.common.Usage;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

/**
 * 描述：
 *
 * @author wsd
 *  
 */
@Data
public class EmbeddingResponse implements Serializable {

    private String object;
    private List<Item> data;
    private String model;
    private Usage usage;
}
