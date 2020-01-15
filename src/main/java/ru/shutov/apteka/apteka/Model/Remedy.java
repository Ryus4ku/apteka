package ru.shutov.apteka.apteka.Model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.*;

import javax.persistence.*;
import java.util.List;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "REMEDY")
public class Remedy {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String price;

    @OneToMany(mappedBy = "remedy", cascade = CascadeType.ALL)
    private List<Offer> offers;
}
