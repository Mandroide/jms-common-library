package com.transunion.rise.webservices.modules;

import com.transunion.rise.webservices.enums.ResponseCode;
import com.transunion.rise.webservices.exceptions.ServiceRequestException;
import com.transunion.rise.webservices.model.ServiceMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.listener.SessionAwareMessageListener;
import org.springframework.jms.support.converter.MessageConverter;
import org.springframework.lang.NonNull;

import javax.jms.*;
import java.util.Objects;


@RequiredArgsConstructor
public abstract class AbstractService implements SessionAwareMessageListener<Message>, Service {
    private final JmsTemplate jmsTemplate;

    @Override
    public void onMessage(@NonNull Message message, @NonNull Session session) throws JMSException {
        MessageConverter messageConverter = Objects.requireNonNull(jmsTemplate.getMessageConverter());
        ServiceMessage request = (ServiceMessage) messageConverter.fromMessage(message);
        boolean hasRequiredParameters = true;
        request.setServiceName(getServiceName());
        for (String requiredParameter : getRequiredParameters()) {
            if (!request.getParameters().containsKey(requiredParameter)) {
                request.setResponseCode(ResponseCode.ERR_INVALID_PARAMS);
                request.setResult(String.format("Parameter [%s] is required.", requiredParameter));
                hasRequiredParameters = false;
                break;
            }
        }

        if (hasRequiredParameters) {
            try {
                String response = processRequest(request.getParameters());
                request.setResult(response);
                request.setResponseCode(ResponseCode.OK);
            } catch (ServiceRequestException e) {
                ResponseCode responseCode = (e.getResponseCode() != null) ? e.getResponseCode() : ResponseCode.ERR_GENERAL_ERROR;
                request.setResponseCode(responseCode);
                request.setResult(e.getMessage());
            }
        }

        Destination replyAddress = message.getJMSReplyTo();
        MessageProducer producer = session.createProducer(replyAddress);
        Message response = messageConverter.toMessage(request, session);
        response.setJMSCorrelationID(message.getJMSCorrelationID());
        producer.send(response);
    }

}
