package net.mbc.shahid.notify.tunnel.service;

import net.mbc.shahid.notify.tunnel.model.NotificationRequest;
import net.mbc.shahid.notify.tunnel.model.NotificationResponse;
import org.jsmpp.InvalidResponseException;
import org.jsmpp.PDUException;
import org.jsmpp.bean.*;
import org.jsmpp.extra.NegativeResponseException;
import org.jsmpp.extra.ResponseTimeoutException;
import org.jsmpp.session.BindParameter;
import org.jsmpp.session.SMPPSession;
import org.jsmpp.util.AbsoluteTimeFormatter;
import org.jsmpp.util.TimeFormatter;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.io.IOException;
import java.util.Date;

@Service
public class TunnelNotificationService {
    private static TimeFormatter timeFormatter = new AbsoluteTimeFormatter();
    public Mono<NotificationResponse> send(final NotificationRequest notificationRequest){
        String server = "172.28.36.61";
        int port = 1035;
        String message = "test";

        // bind(connect)
        SMPPSession session = new SMPPSession();
        try {
            session.connectAndBind(server, port, new BindParameter(BindType.BIND_TRX, "shahid", "shahid", "cp",
                    TypeOfNumber.UNKNOWN, NumberingPlanIndicator.UNKNOWN, null));
        } catch (IOException e) {
            System.err.println("Failed connect and bind to host");
            e.printStackTrace();
        }

        // send Message
        try {
            // set RegisteredDelivery
            final RegisteredDelivery registeredDelivery = new RegisteredDelivery();
            registeredDelivery.setSMSCDeliveryReceipt(SMSCDeliveryReceipt.SUCCESS_FAILURE);

            String messageId = session.submitShortMessage("CMT", TypeOfNumber.INTERNATIONAL,
                    NumberingPlanIndicator.UNKNOWN, "shahid", TypeOfNumber.INTERNATIONAL, NumberingPlanIndicator.UNKNOWN,
                    "1091245885", new ESMClass(), (byte)0, (byte)1, timeFormatter.format(new Date()), null,
                    registeredDelivery, (byte)0, new GeneralDataCoding(Alphabet.ALPHA_DEFAULT, MessageClass.CLASS1,
                            false), (byte)0, message.getBytes()).toString();

            System.out.println("Message submitted, message_id is " + messageId);

        } catch (PDUException e) {
            // Invalid PDU parameter
            System.err.println("Invalid PDU parameter");
            e.printStackTrace();
        } catch (ResponseTimeoutException e) {
            // Response timeout
            System.err.println("Response timeout");
            e.printStackTrace();
        } catch (InvalidResponseException e) {
            // Invalid response
            System.err.println("Receive invalid respose");
            e.printStackTrace();
        } catch (NegativeResponseException e) {
            // Receiving negative response (non-zero command_status)
            System.err.println("Receive negative response");
            e.printStackTrace();
        } catch (IOException e) {
            System.err.println("IO error occur");
            e.printStackTrace();
        }

        // unbind(disconnect)
        session.unbindAndClose();

        System.out.println("finish!");
        return Mono.just(new NotificationResponse("it's working"));
    }
}
