package dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@NoArgsConstructor
@AllArgsConstructor
@Data
@ToString
public class InvoiceDetails {
    private String InvoNumber;
    private int Seq;
    private String ItemCode;
    private int Qty;
    private double PoPrice;
}
