package org.egov.collection.model;

import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class IdRequest {

    @JsonProperty("idName")
    @NotNull
    private String idName;

    @NotNull
    @JsonProperty("tenantId")
    private String tenantId;

    @JsonProperty("format")
    private String format;

}
