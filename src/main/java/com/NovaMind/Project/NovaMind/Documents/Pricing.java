package com.NovaMind.Project.NovaMind.Documents;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;
@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Pricing {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)

    private Long id;
    private float price;
    @Enumerated(EnumType.STRING)

    private TypePricing  typepricing;
    private Date start_date;
    private Date end_date;
    private float reduction;
    @OneToOne
    private MemberShip memberShip;
}
