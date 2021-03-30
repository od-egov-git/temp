package org.egov.collection.web.contract;

import java.util.List;

import org.egov.common.contract.request.RequestInfo;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Builder
public class BusinessDetailsRequest {

    private String tenantId;

    @JsonProperty("RequestInfo")
    private RequestInfo requestInfo;

    @JsonProperty("businessDetailsCodes")
    private List<String> businessCodes;
}
