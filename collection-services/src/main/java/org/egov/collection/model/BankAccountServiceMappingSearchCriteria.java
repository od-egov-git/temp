package org.egov.collection.model;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class BankAccountServiceMappingSearchCriteria {

    private String tenantId;

    private List<String> businessDetails;

    private String bankAccount;
    
    private String bank;
    
    private String bankBranch;

}
