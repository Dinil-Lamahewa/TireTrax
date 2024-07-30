package entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@NoArgsConstructor
@AllArgsConstructor
@Data
@ToString
public class Customer {
    private String CustomerId ;
    private String Name ;
    private String Contact ;
    private String Email ;
    private String Address ;
    private String Type ;
    private double CreditLimit ;
    private String CreditPeriod ;
}
