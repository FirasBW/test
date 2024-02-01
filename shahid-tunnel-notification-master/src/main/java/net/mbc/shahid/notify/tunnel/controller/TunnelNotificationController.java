package net.mbc.shahid.notify.tunnel.controller;

import jakarta.validation.Valid;
import net.mbc.shahid.notify.tunnel.model.NotificationRequest;
import net.mbc.shahid.notify.tunnel.model.NotificationResponse;
import net.mbc.shahid.notify.tunnel.service.TunnelNotificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping
public class TunnelNotificationController {

    @Autowired
    private TunnelNotificationService tunnelNotificationService;

    @PostMapping("/send")
    public Mono<NotificationResponse> send(@RequestBody @Valid final NotificationRequest notificationRequest) {
        return tunnelNotificationService.send(notificationRequest);
    }
}
