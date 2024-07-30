package ru.irlix.hackingbank.dto;


import jakarta.validation.constraints.Min;

import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class ClientDTO {
    @Id
    private Long id;
   @Size(min = 2, max = 100)
    private String name;
    @Size(min = 10, max = 15)
    private String phone_number;
    @Min(0)
    private double balance;
}
