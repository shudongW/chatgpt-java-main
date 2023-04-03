package com.tech.chatgpt.entity.files;

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
public class File implements Serializable {

    private String id;
    private String object;
    private long bytes;
    private long created_at;
    private String filename;
    private String purpose;
    private String status;
    @JsonProperty("status_details")
    private String statusDetails;
}
