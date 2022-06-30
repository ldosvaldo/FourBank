package com.foursys.fourbank.model;

import com.foursys.fourbank.enums.CardFlag;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDate;
import javax.persistence.*;
import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
public class Card {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(nullable = false)
    private Long id;

    @Column(name = "user_name", nullable = false)
    private String userName;

    @Column(name = "card_number", nullable = false)
    private String cardNumber;

    @Column(name = "security_code", nullable = false)
    private String securityCode;

    @Column(name = "is_blocked", nullable = false)
    private Boolean isBlocked;

	@Column(nullable = false)
	private String cardPassword;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private CardFlag cardFlag;
    
    @Column(name = "expiration_date", nullable = false)
    private LocalDate expirationDate;
    
    @ManyToOne
    @JoinColumn(name = "id_account",referencedColumnName = "id")
    private FourBankAccount account;
}