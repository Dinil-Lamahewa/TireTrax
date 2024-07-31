package dto.tm;

import javafx.scene.control.Button;
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
    private Button Edit;
    private Button Delete;


//    public CustomerTm(String customerId, String name, String contact, String email, String address, String type, double creditLimit, String creditPeriod, Button edit, Button delete) {
//        CustomerId = customerId;
//        Name = name;
//        Contact = contact;
//        Email = email;
//        Address = address;
//        Type = type;
//        CreditLimit = creditLimit;
//        CreditPeriod = creditPeriod;
//        this.edit = edit;
//        Delete = delete;
//    }

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
                ", edit=" + Edit +
                ", Delete=" + Delete +
                '}';
    }

}
