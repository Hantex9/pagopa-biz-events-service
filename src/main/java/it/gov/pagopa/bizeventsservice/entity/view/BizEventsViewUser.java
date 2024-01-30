package it.gov.pagopa.bizeventsservice.entity.view;

import com.azure.spring.data.cosmos.core.mapping.Container;
import lombok.*;
import org.springframework.data.annotation.Id;

/**
 * Entity model for biz-events-view-user
 */
@Container(containerName = "${azure.cosmos.biz-events-view-user-container-name}", autoCreateContainer = false, ru="1000")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Builder
public class BizEventsViewUser {
    @Id
    private String fiscalCode;
    private String transactionId;
    private String transactionDate;
    private boolean hidden;
}
