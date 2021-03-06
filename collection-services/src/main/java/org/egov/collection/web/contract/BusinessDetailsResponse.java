package org.egov.collection.web.contract;

import java.util.List;

import org.egov.common.contract.response.ResponseInfo;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@ToString
public class BusinessDetailsResponse {

	@JsonProperty("ResponseInfo")
    @JsonIgnore
	private ResponseInfo responseInfo;

	@JsonProperty("BusinessDetails")
	private List<BusinessDetailsRequestInfo> businessDetails;
}
