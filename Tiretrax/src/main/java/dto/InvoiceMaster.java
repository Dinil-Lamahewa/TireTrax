package dto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
@AllArgsConstructor
@Data
@NoArgsConstructor
@ToString
public class InvoiceMaster {
    private String InvoNumber;
    private String CustomerId;
    private LocalDateTime InvoDate;
    private String InvoComment;
    private double GrossAmount;
    private int VatRate;
    private double NetAmount;
    private int PrintCount;
}
