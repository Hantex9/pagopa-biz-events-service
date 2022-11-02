package it.gov.pagopa.bizeventsservice.model.response;

import java.io.Serializable;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import it.gov.pagopa.bizeventsservice.model.response.enumeration.EntityUniqueIdentifierType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Payer implements Serializable{
    /**
	 * generated serialVersionUID
	 */
	private static final long serialVersionUID = -1069568268303542892L;
	
	@NotNull(message = "entityUniqueIdentifierType is required")
	private EntityUniqueIdentifierType entityUniqueIdentifierType;    
	@NotBlank(message = "entityUniqueIdentifierValue is required")
	private String entityUniqueIdentifierValue;
	@NotBlank(message = "fullName is required")
	private String fullName;
	private String streetName;
	private String civicNumber;
	private String postalCode;
	private String city;
	private String stateProvinceRegion;
	private String country;
	private String eMail;
}
