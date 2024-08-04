package dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.Date;
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Data
public class SystemUsers {
   private String userID;
   private String userName;
   private String password;
   private int jobRole;
   private Date lastLoginDate;
}
