package ru.irlix.hackingbank.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Table("payments")
public class Payment {
    @Id
    private Long id;
    private LocalDateTime date_time;
    private double amount;
    private Long sender_id;
    private Long recipient_id;
    private String message;

}
