package ru.shutov.apteka.apteka.DTOs;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class PurchaseDto implements Serializable {
    private Long id;
    private List<RemedyDto> baskets = new ArrayList<>();
    private String fio;
    private String phoneNumber;
    private String comment;
    private BigDecimal totalPrice;
}
