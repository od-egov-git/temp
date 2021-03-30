package org.egov.collection.model.v1;
import java.util.ArrayList;
import java.util.List;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.egov.collection.model.Instrument;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Setter
@Getter
@ToString
@EqualsAndHashCode(of = {"receiptNumber"})
public class Receipt_v1 {

    @NotNull
    private String tenantId;

    private String transactionId;

    // Read only, populated during search
    private String receiptNumber;

    // Read only, populated during search
    private String consumerCode;

    // Read only, populated during search
    private Long receiptDate;

    @NotNull
    @Size(min = 1, max = 1)
    @Valid
    @JsonProperty("Bill")
    private List<Bill_v1> bill = new ArrayList<>();

    private AuditDetails_v1 auditDetails;

    @Valid
    private Instrument instrument;

}