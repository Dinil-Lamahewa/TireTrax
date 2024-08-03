package dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.Date;
@NoArgsConstructor
@AllArgsConstructor
@Data
@ToString
public class Stock {
    private String ItemCode ;
    private String Name ;
    private  String Category ;
    private String Company ;
    private String PurchaseQty ;
    private Date ExpiredDate ;
    private String SellingUnitPrice ;
    private Date PurchaseDate ;
    private String PurchasePrice ;
}
