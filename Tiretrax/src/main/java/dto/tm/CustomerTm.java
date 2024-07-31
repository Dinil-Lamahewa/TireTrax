package dto.tm;

import javafx.scene.control.Button;


@AllArgsConstructor
@NoArgsConstructor
@Data
@ToString
public class CustomerTm {
    private String CustomerId ;
    private String Name ;
    private String Contact ;
    private String Email ;
    private String Address ;
    private String Type ;
    private double CreditLimit ;
    private String CreditPeriod ;

    private Button edit;
    private Button delete;
}

