package com.foursys.fourbank.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "tb_debit_cards")
@SequenceGenerator(name="tb_debit_cards", sequenceName = "tb_sq_debit_cards", allocationSize = 1, initialValue = 1)

public class DebitCard extends Card{
    @Column(name = "daily_limit", nullable = false)
    private Double dailyLimit;
}