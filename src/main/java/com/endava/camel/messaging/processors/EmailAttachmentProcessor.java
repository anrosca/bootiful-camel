package com.endava.camel.messaging.processors;

import lombok.extern.slf4j.Slf4j;
import org.apache.camel.Exchange;
import org.apache.camel.Message;
import org.apache.camel.Processor;
import org.apache.camel.attachment.AttachmentMessage;
import org.springframework.stereotype.Component;

import javax.activation.DataHandler;
import java.util.Map;

@Component
@Slf4j
public class EmailAttachmentProcessor implements Processor {
    @Override
    public void process(Exchange exchange) throws Exception {
        Message in = exchange.getIn();
        String body = in.getBody(String.class);
        log.info("Received email body: " + body);
        AttachmentMessage attachmentMessage = exchange.getMessage(AttachmentMessage.class);
        Map<String, DataHandler> attachments = attachmentMessage.getAttachments();
        if (attachments.size() > 0) {
            for (String name : attachments.keySet()) {
                DataHandler dh = attachments.get(name);
                byte[] data = exchange.getContext()
                        .getTypeConverter().convertTo(byte[].class, dh.getInputStream());
                String attachmentBody = new String(data);
                String fileName = dh.getName();
                log.info("Processing email attachment: fileName: {}, attachmentBody: {}", fileName, attachmentBody);
                in.setBody(attachmentBody);
                in.setHeader("FileName", fileName);
            }
        }
    }
}
