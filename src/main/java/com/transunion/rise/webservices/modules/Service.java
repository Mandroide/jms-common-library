package com.transunion.rise.webservices.modules;

import com.transunion.rise.webservices.exceptions.ServiceRequestException;
import com.transunion.rise.webservices.model.Parameter;
import org.springframework.lang.NonNull;

import java.util.Map;

public interface Service {
    @NonNull
    String[] getRequiredParameters();
    @NonNull
    String getServiceName();
    String processRequest(@NonNull Map<String, Parameter> params) throws ServiceRequestException;
}
