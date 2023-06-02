package com.tech.chatgpt.entity.images;

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
public class Item implements Serializable {
    private String url;
    @JsonProperty("b64_json")
    private String b64Json;
}
