package com.foursys.fourbank.model;

import com.foursys.fourbank.enums.PixType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "tb_pix")
@SequenceGenerator(name="tb_pix", sequenceName = "tb_sq_pix", allocationSize = 1, initialValue = 1)
public class Pix {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_pix", nullable = false)
    private Long id;

    @Column(name = "pix_key_value", nullable = false)
    private String pixKeyValue;

    @Enumerated(EnumType.STRING)
    @Column(name = "pix_type", nullable = false)
    private PixType pixType;

    @ManyToOne
    @JoinColumn(name = "id_account", nullable = false, referencedColumnName = "id")
    private Account account;
}