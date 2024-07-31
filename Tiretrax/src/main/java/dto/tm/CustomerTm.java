package dto.tm;

import lombok.*;

import java.awt.*;
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
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
    private Button Delete;

    public CustomerTm(String string, String string1, String string2, String string3, String string4, String string5, double aDouble, String string6, javafx.scene.control.Button ebtn, javafx.scene.control.Button dbtn) {

    }


    @Override
    public String toString() {
        return "CustomerTm{" +
                "CustomerId='" + CustomerId + '\'' +
                ", Name='" + Name + '\'' +
                ", Contact='" + Contact + '\'' +
                ", Email='" + Email + '\'' +
                ", Address='" + Address + '\'' +
                ", Type='" + Type + '\'' +
                ", CreditLimit=" + CreditLimit +
                ", CreditPeriod='" + CreditPeriod + '\'' +
                ", edit=" + edit +
                ", Delete=" + Delete +
                '}';
    }

}
