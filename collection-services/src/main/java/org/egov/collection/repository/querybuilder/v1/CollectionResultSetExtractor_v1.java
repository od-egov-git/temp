package org.egov.collection.repository.querybuilder.v1;


import java.io.IOException;
import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import org.apache.commons.lang3.StringUtils;
import org.egov.collection.model.Instrument;
import org.egov.collection.model.InstrumentType;
import org.egov.collection.model.SurrenderReason;
import org.egov.collection.model.TransactionType;
import org.egov.collection.model.enums.CollectionType;
import org.egov.collection.model.enums.InstrumentStatusEnum;
import org.egov.collection.model.enums.Purpose;
import org.egov.collection.model.v1.AuditDetails_v1;
import org.egov.collection.model.v1.BillAccountDetail_v1;
import org.egov.collection.model.v1.BillDetail_v1;
import org.egov.collection.model.v1.Bill_v1;
import org.egov.collection.model.v1.Receipt_v1;
import org.egov.collection.web.contract.BankContract;
import org.egov.tracer.model.CustomException;
import org.postgresql.util.PGobject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

@Service
public class CollectionResultSetExtractor_v1 implements ResultSetExtractor<List<Receipt_v1>> {

    private ObjectMapper objectMapper;

    @Autowired
    CollectionResultSetExtractor_v1(ObjectMapper objectMapper){
        this.objectMapper = objectMapper;
    }

    @Override
    public List<Receipt_v1> extractData(ResultSet resultSet) throws SQLException, DataAccessException {

        Map<String, Receipt_v1> receipts = new LinkedHashMap<>();

        while(resultSet.next()){
            String receiptHeader = resultSet.getString("rh_id");
            Receipt_v1 receipt;

            if(!receipts.containsKey(receiptHeader)){
                BillDetail_v1 billDetail = BillDetail_v1.builder()
                        .id(receiptHeader)
                        .billNumber(resultSet.getString("rh_referenceNumber"))
                        .billDate(resultSet.getLong("rh_referencedate"))
                        .consumerCode(resultSet.getString("rh_consumerCode"))
                        .consumerType(resultSet.getString("rh_consumerType"))
                        .collectionModesNotAllowed(resultSet.getString("rh_collModesNotAllwd") != null
                                ? Arrays.asList(resultSet.getString("rh_collModesNotAllwd").split("\\s*,\\s*"))
                                : Collections.emptyList())
                        .tenantId(resultSet.getString("rh_tenantId"))
                        .businessService(resultSet.getString("rh_businessDetails"))
                        .receiptNumber(resultSet.getString("rh_receiptNumber"))
                        .receiptType(resultSet.getString("rh_receiptType"))
                        .channel(resultSet.getString("rh_channel"))
                        .voucherHeader(resultSet.getString("rh_voucherheader"))
                        .collectionType(!StringUtils.isEmpty(resultSet.getString("rh_collectionType"))
                                ? CollectionType.valueOf(resultSet.getString("rh_collectionType")) : null)
                        .boundary(resultSet.getString("rh_boundary"))
                        .reasonForCancellation(resultSet.getString("rh_reasonForCancellation"))
                        .status(resultSet.getString("rh_status"))
                        .receiptDate(resultSet.getLong("rh_receiptDate"))
                        .manualReceiptNumber(resultSet.getString("rh_manualReceiptNumber"))
                        .manualReceiptDate(resultSet.getLong("rh_manualreceiptdate"))
                        .fund(resultSet.getString("rh_fund"))
                        .function(resultSet.getString("rh_function"))
                        .department(resultSet.getString("rh_department"))
                        .demandId(resultSet.getString("rh_demandid"))
                        .fromPeriod(resultSet.getLong("rh_demandfromdate"))
                        .toPeriod(resultSet.getLong("rh_demandtodate"))
                        .amountPaid(BigDecimal.ZERO)
                        .totalAmount(getBigDecimalValue(resultSet.getBigDecimal("rh_totalAmount")))
                        .collectedAmount(getBigDecimalValue(resultSet.getBigDecimal("rh_collectedamount")))
                        .minimumAmount(getBigDecimalValue(resultSet.getBigDecimal("rh_minimumAmount")))
                        .additionalDetails(getJsonValue((PGobject) resultSet.getObject("rh_additionalDetails")))
                        .billAccountDetails(new ArrayList<>())
                        .build();

                Bill_v1 billInfo = Bill_v1.builder()
                        .id(resultSet.getString("rh_id"))
                        .payerName(resultSet.getString("rh_payername"))
                        .payerAddress(resultSet.getString("rh_payerAddress"))
                        .payerEmail(resultSet.getString("rh_payerEmail"))
                        .mobileNumber(resultSet.getString("rh_payermobile"))
                        .paidBy(resultSet.getString("rh_paidBy"))
                        .tenantId(resultSet.getString("rh_tenantId"))
                        .billDetails(Collections.singletonList(billDetail))
                        .build();

                AuditDetails_v1 auditDetailsIns = AuditDetails_v1.builder()
                        .createdBy(resultSet.getString("rh_createdBy"))
                        .createdDate(resultSet.getLong("rh_createdDate"))
                        .lastModifiedBy(resultSet.getString("rh_lastModifiedBy"))
                        .lastModifiedDate(resultSet.getLong("rh_lastModifiedDate"))
                        .build();



                Instrument instrument = Instrument.builder()
                        .id(resultSet.getString("ins_instrumentheader"))
                        .amount(resultSet.getBigDecimal("ins_amount"))
                        .transactionDateInput(resultSet.getLong("ins_transactiondate"))
                        .transactionNumber(resultSet.getString("ins_transactionNumber"))
                        .instrumentType(InstrumentType.builder().name(resultSet.getString("ins_instrumenttype"))
                                .build())
                        .ifscCode(resultSet.getString("ins_ifsccode"))
                        .bank(BankContract.builder().name(resultSet.getString("ins_bankid")).build())
                        .transactionType(TransactionType.valueOf(resultSet.getString("ins_transactiontype")))
                        .payee(resultSet.getString("ins_payee"))
                        .drawer(resultSet.getString("ins_drawer"))
                        .surrenderReason(SurrenderReason.builder().name(resultSet.getString("ins_surrenderreason")).build())
                        .serialNo(resultSet.getString("ins_serialno"))
                        .additionalDetails(getJsonValue((PGobject) resultSet.getObject("ins_additionalDetails")))
                        .auditDetails(auditDetailsIns)
                        .instrumentDate(resultSet.getLong("ins_instrumentDate"))
                        .instrumentNumber(resultSet.getString("ins_instrumentNumber"))
                        .tenantId(resultSet.getString("ins_tenantid"))
                        .build();

                if(resultSet.getString("ins_instrumentstatus").equals("NEW")) {
                    instrument.setInstrumentStatus(InstrumentStatusEnum.TO_BE_SUBMITTED);
                }else if((resultSet.getString("ins_instrumentstatus").equals("CANCELLED"))){
                    instrument.setInstrumentStatus(InstrumentStatusEnum.CANCELLED);
                }else if((resultSet.getString("ins_instrumentstatus").equals("DEPOSITED"))){
                    instrument.setInstrumentStatus(InstrumentStatusEnum.REMITTED);
                }

                AuditDetails_v1 auditDetails = AuditDetails_v1.builder()
                        .createdBy(resultSet.getString("rh_createdBy"))
                        .createdDate(resultSet.getLong("rh_createdDate"))
                        .lastModifiedBy(resultSet.getString("rh_lastModifiedBy"))
                        .lastModifiedDate(resultSet.getLong("rh_lastModifiedDate"))
                        .build();

                receipt = Receipt_v1.builder()
                        .tenantId(resultSet.getString("rh_tenantId"))
                        .bill(Collections.singletonList(billInfo))
                        .receiptNumber(billDetail.getReceiptNumber())
                        .consumerCode(billDetail.getConsumerCode())
                        .receiptDate(billDetail.getReceiptDate())
                        .transactionId(resultSet.getString("rh_transactionid"))
                        .instrument(instrument)
                        .auditDetails(auditDetails)
                        .build();

                receipts.put(receiptHeader, receipt);

            } else {
                receipt = receipts.get(receiptHeader);
            }

            BillDetail_v1 billDetail = receipt.getBill().get(0).getBillDetails().get(0);
            BillAccountDetail_v1 billAccountDetail = populateAccountDetail(resultSet, billDetail);

            /*
             * adding paid amount only when data is not duplicate
             */
            if (billDetail.addBillAccountDetail(billAccountDetail))
                billDetail.setAmountPaid(billDetail.getAmountPaid().add(billAccountDetail.getAdjustedAmount()));
        }

        return new ArrayList<>(receipts.values());
    }

    private BillAccountDetail_v1 populateAccountDetail(ResultSet resultSet, BillDetail_v1 billDetail) throws SQLException,
            DataAccessException{


        return BillAccountDetail_v1.builder()
                .id(resultSet.getString("rd_id"))
                .isActualDemand((Boolean) resultSet.getObject("rd_isActualDemand"))
                .tenantId(resultSet.getString("rd_tenantId"))
                .billDetail(resultSet.getString("rh_id"))
                .order(resultSet.getInt("rd_ordernumber"))
                .purpose(!StringUtils.isEmpty(resultSet.getString("rd_purpose")) ?
                        Purpose.valueOf(resultSet.getString("rd_purpose")) : null)
                .additionalDetails(getJsonValue((PGobject) resultSet.getObject("rd_additionalDetails")))
                .amount(getBigDecimalValue(resultSet.getBigDecimal("rd_amount")))
                .adjustedAmount(getBigDecimalValue(resultSet.getBigDecimal("rd_adjustedamount")))
                .taxHeadCode(resultSet.getString("rd_taxheadcode"))
                .demandDetailId(resultSet.getString("rd_demanddetailid"))
                .build();
    }

    private BigDecimal getBigDecimalValue(BigDecimal amount){
        return Objects.isNull(amount) ? BigDecimal.ZERO : amount;
    }

    private JsonNode getJsonValue(PGobject pGobject){
        try {
            if(Objects.isNull(pGobject) || Objects.isNull(pGobject.getValue()))
                return null;
            else
                return objectMapper.readTree( pGobject.getValue());
        } catch (IOException e) {
            throw new CustomException("SERVER_ERROR","Exception occurred while parsing the draft json : "+ e
                    .getMessage());
        }
    }

}
