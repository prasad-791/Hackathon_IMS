package com.example.inventory_management_system.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.lang.Nullable;

import java.time.LocalDate;

@Entity
@Setter @Getter
@AllArgsConstructor
@NoArgsConstructor
public class Borrow {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private Long quantityBorrowed;

    private LocalDate borrowingDate;

    @Nullable
    private LocalDate returnDate;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "asset", referencedColumnName = "id")
    @Nullable
    private Asset asset;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "user", referencedColumnName = "id")
    @Nullable
    private User user;
}
