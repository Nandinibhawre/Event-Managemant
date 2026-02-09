package com.eventmanagement.Model;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Setter
@Getter
@Document(collection = "orders")
public class Order {
    @Id
    private String orderId;

    private String userId;
    private String eventId;

    private int quantity;
    private double totalAmount;

    private String qrCode;        // QR string
    private boolean checkedIn;

    private LocalDateTime orderTime;
   }
