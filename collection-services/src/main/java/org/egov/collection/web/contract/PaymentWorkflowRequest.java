package org.egov.collection.web.contract;

import java.util.List;

import javax.validation.Valid;
import javax.validation.constraints.Size;

import org.egov.common.contract.request.RequestInfo;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

@Data
public class PaymentWorkflowRequest {

    @JsonProperty("RequestInfo")
    private RequestInfo requestInfo;

    @JsonProperty("PaymentWorkflows")
    @Size(min = 1)
    @Valid
    private List<PaymentWorkflow> paymentWorkflows;


}
