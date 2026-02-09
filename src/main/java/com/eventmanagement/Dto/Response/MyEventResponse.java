package com.eventmanagement.Dto.Response;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
public class MyEventResponse
{
    private String orderId;
    private String eventName;
    private LocalDateTime eventStartDate;
    private boolean checkedIn;
}
