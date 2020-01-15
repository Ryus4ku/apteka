package ru.shutov.apteka.apteka.Model;

import lombok.*;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "purchase")
public class Purchase {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToMany(mappedBy = "purchase", cascade = CascadeType.ALL)
    private List<Offer> offer = new ArrayList<>();

    private String fio;
    private String phoneNumber;
    private String comment;
    private BigDecimal totalPrice;
    private boolean actual;
}
