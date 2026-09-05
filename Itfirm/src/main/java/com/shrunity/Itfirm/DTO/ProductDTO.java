package com.shrunity.Itfirm.DTO;

import jakarta.persistence.Column;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProductDTO {

    private Long id;
   @NonNull private String productName;
    @NonNull  private int price;
    private String mfd;
    private String exp;
}
