package net.mbc.shahid.notify.tunnel.model;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Data
public class NotificationRequest {
    @NotBlank(message = "type field is required")
    protected String type;
    @NotBlank(message = "messageType field is required")
    protected String messageType;
    @NotBlank(message = "providerLabel field is required")
    protected String providerLabel;
    @NotBlank(message = "message field is required")
    protected String message;
    @NotEmpty(message = "to field can't be empty")
    protected List<String> to;
    protected Map<String, Object> messageParams = new HashMap<>();
}
