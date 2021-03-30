package org.egov.collection.model;

import java.util.List;

import org.egov.common.contract.request.RequestInfo;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

@Getter
@AllArgsConstructor
@ToString
public class IdGenerationRequest {

    @JsonProperty("RequestInfo")
    private RequestInfo requestInfo;

    private List<IdRequest> idRequests;

}

