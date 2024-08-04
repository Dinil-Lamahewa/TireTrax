package dto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.Date;
@AllArgsConstructor
@Data
@NoArgsConstructor
@ToString
public class InvoiceMaster {
    private String InvoNumber;
    private String CustomerId;
    private Date InvoDate;
    private String InvoComment;
    private double GrossAmount;
    private int VatRate;
    private double NetAmount;
    private int PrintCount;
}
