package org.egov.collection.web.contract;

import java.util.List;

import org.egov.common.contract.response.ResponseInfo;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class ChartOfAccountsResponse {

    public ResponseInfo responseInfo;

    public List<ChartOfAccount> chartOfAccounts;
}
