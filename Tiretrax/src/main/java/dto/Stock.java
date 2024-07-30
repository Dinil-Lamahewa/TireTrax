package entity;

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
    private double PurchaseQty ;
    private int InStockQty ;
    private Date ExpiredDate ;
    private double SellingUnitPrice ;
    private Date PurchaseDate ;
    private double PurchasePrice ;
}
