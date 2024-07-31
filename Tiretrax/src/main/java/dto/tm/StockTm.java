package dto.tm;

import javafx.scene.control.Button;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.Date;
@AllArgsConstructor
@NoArgsConstructor
@Data
@ToString
public class StockTm {
    private String ItemCode ;
    private String Name ;
    private String Category ;
    private String Company ;
    private String  PurchaseQty ;
    private String  ExpiredDate ;
    private String SellingUnitPrice ;
    private String PurchaseDate ;
    private String PurchasePrice ;
    private Button Edit;
    private Button Delete;
}
