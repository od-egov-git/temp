package org.egov.collection.web.contract;

import java.util.List;

import org.egov.collection.model.Instrument;
import org.egov.common.contract.response.ResponseInfo;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class InstrumentResponse {
    private ResponseInfo responseInfo;
    private List<Instrument> instruments;
}
