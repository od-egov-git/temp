package org.egov.collection.web.contract;

import org.egov.common.contract.request.RequestInfo;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class RequestInfoBody {

    @JsonProperty("RequestInfo")
    private RequestInfo requestInfo;

}