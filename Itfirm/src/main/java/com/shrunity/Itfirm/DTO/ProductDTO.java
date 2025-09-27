package com.shrunity.Itfirm.DTO;

import lombok.*;

@Data
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ProductDTO {
    private int id;
    private String name;
    private String email;
    private int contact_num;
    public ProductDTO(int id, String name) {
        this.id = id;
        this.name = name;
    }

}
