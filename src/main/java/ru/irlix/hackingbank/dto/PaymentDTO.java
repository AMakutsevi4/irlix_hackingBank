package ru.irlix.hackingbank.dto;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;
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
public class PaymentDTO {
    @Id
    private Long id;
    private LocalDateTime date_time;
    @NotNull
    @DecimalMin(value = "0.1", message = "Сумма должна быть больше нуля")
    private Double amount;
    private Long sender_id;
    private Long recipient_id;
    private String message;

}
