package com.shrunity.Itfirm.entity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "testdb")
public class Product{

    @Id
    @GeneratedValue
    private int id;

    @Column(nullable = false)
    private String name;

    private String email;

    @Column(nullable = false)
    private int contact_num;  //@NonNull // Lombok: Java-side null check // JPA: DB-side NOT NULL



}
