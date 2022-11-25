package it.gov.pagopa.bizeventsservice.mapper;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import javax.validation.Valid;

import org.modelmapper.Converter;
import org.modelmapper.spi.MappingContext;

import it.gov.pagopa.bizeventsservice.entity.BizEvent;
import it.gov.pagopa.bizeventsservice.entity.Transfer;
import it.gov.pagopa.bizeventsservice.model.response.CtReceiptModelResponse;
import it.gov.pagopa.bizeventsservice.model.response.Debtor;
import it.gov.pagopa.bizeventsservice.model.response.Payer;
import it.gov.pagopa.bizeventsservice.model.response.TransferPA;
import it.gov.pagopa.bizeventsservice.model.response.enumeration.EntityUniqueIdentifierType;

public class ConvertBizEventEntityToCtReceiptModelResponse implements Converter<BizEvent, CtReceiptModelResponse> {

    @Override
    public CtReceiptModelResponse convert(MappingContext<BizEvent, CtReceiptModelResponse> mappingContext) {
        @Valid BizEvent be = mappingContext.getSource();
        
        DateTimeFormatter  dfDate     = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        DateTimeFormatter  dfDateTime = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSSSSS");
        Debtor ctReceiptDebtor = null;
        Payer ctReceiptPayer   = null;
        List<TransferPA> ctTransferListPA = null;
        
        if (null != be.getDebtor()) {
        	ctReceiptDebtor = Debtor.builder()
        			.entityUniqueIdentifierType(EntityUniqueIdentifierType.valueOf(be.getDebtor().getEntityUniqueIdentifierType()))
        			.entityUniqueIdentifierValue(be.getDebtor().getEntityUniqueIdentifierValue())
        			.fullName(be.getDebtor().getFullName())
        			.streetName(be.getDebtor().getStreetName())
        			.civicNumber(be.getDebtor().getCivicNumber())
        			.postalCode(be.getDebtor().getPostalCode())
        			.city(be.getDebtor().getCity())
        			.stateProvinceRegion(be.getDebtor().getStateProvinceRegion())
        			.country(be.getDebtor().getCountry())
        			.eMail(be.getDebtor().getEMail())
        			.build();
        }

        if (null != be.getPayer()) {
        	ctReceiptPayer = Payer.builder()
        			.entityUniqueIdentifierType(EntityUniqueIdentifierType.valueOf(be.getPayer().getEntityUniqueIdentifierType()))
        			.entityUniqueIdentifierValue(be.getPayer().getEntityUniqueIdentifierValue())
        			.fullName(be.getPayer().getFullName())
        			.streetName(be.getPayer().getStreetName())
        			.civicNumber(be.getPayer().getCivicNumber())
        			.postalCode(be.getPayer().getPostalCode())
        			.city(be.getPayer().getCity())
        			.stateProvinceRegion(be.getPayer().getStateProvinceRegion())
        			.country(be.getPayer().getCountry())
        			.eMail(be.getPayer().getEMail())
        			.build();
        }

        if (null != be.getTransferList() && !be.getTransferList().isEmpty()) {
        	ctTransferListPA = new ArrayList<>();
        	for (Transfer t : be.getTransferList()) {
        		ctTransferListPA.add(TransferPA.builder()
        				.idTransfer(Integer.valueOf(t.getIdTransfer()))
        				.transferAmount(BigDecimal.valueOf(Double.valueOf(t.getAmount())))
        				.fiscalCodePA(t.getFiscalCodePA())
        				.iban(t.getIban())
        				// TODO check if this mapping is valid
        				.mbdAttachment(null != t.getMbd() ? t.getMbd().getMbdAttachment() : null)
        				.remittanceInformation(t.getRemittanceInformation())
        				.transferCategory(t.getTransferCategory())
        				// TODO check for metadata format
        				//.metadata(t.getMetadata());
        				.build());
        	}
        }
        
        
        return CtReceiptModelResponse.builder()
        		// TODO confirm if receiptId (or id)
        		.receiptId(be.getReceiptId())
        		.noticeNumber(be.getDebtorPosition().getNoticeNumber())
        		// TODO check if idPa or a new paFiscalCode field
        		.fiscalCode(be.getCreditor().getIdPA())
        		.outcome("OK") // default hardcoded
        		.creditorReferenceId(be.getDebtorPosition().getIuv())
        		.paymentAmount(BigDecimal.valueOf(Double.valueOf(be.getPaymentInfo().getAmount())))
        		.description(be.getPaymentInfo().getRemittanceInformation())
        		
        		.companyName(be.getCreditor().getCompanyName())
        		.officeName(be.getCreditor().getOfficeName())
        		
        		.debtor(ctReceiptDebtor)
        		.transferList(ctTransferListPA)
        		.idPSP(be.getPsp().getIdPsp())
        		.pspFiscalCode(be.getPsp().getPspFiscalCode())
                .pspPartitaIVA(be.getPsp().getPspPartitaIVA())
                .pspCompanyName(be.getPsp().getPspCompanyName())
                .idChannel(be.getPsp().getIdChannel())
                .channelDescription(be.getPsp().getChannelDescription())
                
                .payer(ctReceiptPayer)
                .paymentMethod(be.getPaymentInfo().getPaymentMethod())
                
                .fee(BigDecimal.valueOf(Double.valueOf(be.getPaymentInfo().getFee())))
                .primaryCiIncurredFee(BigDecimal.valueOf(Double.valueOf(be.getPaymentInfo().getPrimaryCiIncurredFee())))
                .idBundle(be.getPaymentInfo().getIdBundle())
                .idCiBundle(be.getPaymentInfo().getIdCiBundle())
                .paymentDateTime(LocalDate.parse(be.getPaymentInfo().getPaymentDateTime(), dfDateTime))	
                .applicationDate(LocalDate.parse(be.getPaymentInfo().getApplicationDate(), dfDate))
                .transferDate(LocalDate.parse(be.getPaymentInfo().getTransferDate(), dfDate))
                // TODO check for metadata format
                //.metadata(be.getPaymentInfo().getMetadata())
                .build();
    }
}
