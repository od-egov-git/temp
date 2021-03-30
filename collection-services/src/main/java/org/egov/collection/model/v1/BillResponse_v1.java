package org.egov.collection.model.v1;

import java.util.ArrayList;
import java.util.List;

import org.egov.common.contract.response.ResponseInfo;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * BillResponse
 */

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BillResponse_v1 {

    @JsonProperty("ResposneInfo")
    private ResponseInfo responseInfo = null;

    @JsonProperty("Bill")
    private List<Bill_v1> bill = new ArrayList<>();

}