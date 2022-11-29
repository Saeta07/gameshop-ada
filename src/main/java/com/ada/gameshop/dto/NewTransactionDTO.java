package com.ada.gameshop.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

<<<<<<< HEAD
import java.time.LocalDate;
=======
>>>>>>> 51d99ac3142d5528ba289408d458a40f4d705d32
import java.util.List;

@NoArgsConstructor
@Getter
@Setter
public class NewTransactionDTO {
    private Long customerId;
<<<<<<< HEAD
    private LocalDate date;
    private List<ProductDTO> productDTOS;

    public NewTransactionDTO (Long id, LocalDate date, List<ProductDTO> productDTOS) {
        this.customerId= id;
        this.date = date;
        this.productDTOS = productDTOS;
    }

    public List<ProductDTO> getProductDTOS() {
        return productDTOS;
    }
=======
    private String date;
    private List<Long> productIdList;
>>>>>>> 51d99ac3142d5528ba289408d458a40f4d705d32
}
