package com.example.FirstProject.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "payments")
public class Payment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long paymentId;
    @NotBlank
    @Size(min = 4,message = "Payment method must be more than 4 character")
    private  String paymentMethod;
    @OneToOne(mappedBy = "payment")


    private Order order;
    private String pgPaymentId;
  private String pgStatus;
  private String pgResponseMessage;
  private String pgName;

    public Payment(String paymentMethod, String pgPaymentId, String pgStatus, String pgResponseMessage, String pgName ) {

        this.pgPaymentId = pgPaymentId;
        this.pgStatus = pgStatus;
        this.pgResponseMessage = pgResponseMessage;
        this.pgName = pgName;
        this.paymentMethod = paymentMethod;
    }
}
