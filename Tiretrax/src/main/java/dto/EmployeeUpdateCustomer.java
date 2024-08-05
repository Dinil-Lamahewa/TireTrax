package dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@NoArgsConstructor
@AllArgsConstructor
@Data
@ToString
public class EmployeeUpdateCustomer {
    private String CustomerId ;
    private String Name ;
    private String Contact ;
    private String Email ;
    private String Address ;
}
