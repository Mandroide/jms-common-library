package com.transunion.rise.webservices.converters;

import com.transunion.rise.webservices.enums.ResponseCode;
import com.transunion.rise.webservices.model.ServiceMessage;
import org.apache.activemq.command.ActiveMQMapMessage;
import org.springframework.jms.support.converter.MessageConversionException;
import org.springframework.jms.support.converter.MessageConverter;
import org.springframework.lang.NonNull;

import javax.jms.JMSException;
import javax.jms.MapMessage;
import javax.jms.Message;
import javax.jms.Session;
import java.util.Collections;
import java.util.Enumeration;
import java.util.List;

// TODO 2025-06-29 Reemplazar por un mejor convertidor de mensaje
public class ServiceMessageConverter implements MessageConverter {
    private static final String SERVICE_NAME_PROP = "TUServiceName";
    private static final String RESPONSE_CODE_PROP = "TUResponseCode";
    private static final String RESPONSE_PROP = "TUResponse";

    @Override
    public Message toMessage(@NonNull Object object, @NonNull Session session) throws JMSException, MessageConversionException {
        if (!(object instanceof ServiceMessage)) {
            throw new MessageConversionException(String.format("ServiceMessage instance is required, got %s",
                    object.getClass().getName()));
        }
        ServiceMessage req = (ServiceMessage) object;

        MapMessage msg = session.createMapMessage();
        msg.setStringProperty(SERVICE_NAME_PROP, req.getServiceName());

        for (String param : req.getParameterNames()) {
            msg.setString(param, (String) req.getParameterValue(param));
        }

        if (req.getResponseCode() != null) {
            msg.setStringProperty(RESPONSE_CODE_PROP, req.getResponseCode().toString());
        }
        msg.setStringProperty(RESPONSE_PROP, req.getResult());
        return msg;
    }

    @Override
    public Object fromMessage(@NonNull Message message) throws JMSException, MessageConversionException {
        ServiceMessage result = new ServiceMessage(message.getStringProperty(SERVICE_NAME_PROP));

        if (!(message instanceof MapMessage)) {
            throw new MessageConversionException("Message type is not MapMessage");
        }
        ActiveMQMapMessage msg = (ActiveMQMapMessage) message;
        Enumeration<String> mapNames = msg.getMapNames();
        List<String> properties = Collections.list(mapNames);
        for (String property : properties) {
            String value = msg.getString(property);
            result.setParameterValue(property, value);
        }

        if (message.propertyExists(RESPONSE_CODE_PROP)) {
            result.setResponseCode(ResponseCode.valueOf(message.getStringProperty(RESPONSE_CODE_PROP)));
        }
        if (message.propertyExists(RESPONSE_PROP)) {
            result.setResult(message.getStringProperty(RESPONSE_PROP));
        }
        return result;
    }
}
