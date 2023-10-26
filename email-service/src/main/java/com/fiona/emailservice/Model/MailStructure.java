package com.fiona.emailservice.Model;

import com.fiona.basedomains.dto.OrderEvent;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MailStructure {
    private String subject;
    private String message;
    private OrderEvent orderEvent;
}
