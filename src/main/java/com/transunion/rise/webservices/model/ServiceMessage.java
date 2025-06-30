package com.transunion.rise.webservices.model;

import com.transunion.rise.webservices.enums.ResponseCode;
import lombok.*;

import java.util.*;


@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
public class ServiceMessage {
    private String serviceName;
    @Builder.Default
    private Map<String, Parameter> parameters = new HashMap<>();
    private String result;
    private ResponseCode responseCode;

    public ServiceMessage(String serviceName) {
        this.serviceName = serviceName;
        this.parameters = new HashMap<>();
    }

    public ServiceMessage(List<Parameter> parameters) {
        this.parameters = new HashMap<>();
        setParameterList(parameters);
    }

    public void setParameterValue(String parameterName, String parameterValue) {
        Parameter parameter = new Parameter();
        parameter.setName(parameterName);
        parameter.setValue(parameterValue);
        parameters.put(parameterName, parameter);
    }

    public void deleteParameterValues(String parameterName) {
        parameters.remove(parameterName);
    }

    public Object getParameterValue(String parameterName) {
        Object value = null;
        if (parameters.containsKey(parameterName)) {
            Parameter parameter = parameters.get(parameterName);
            value = parameter.getValue();
        }
        return value;
    }

    public List<String> getParameterNames() {
        return Collections.list(Collections.enumeration(parameters.keySet()));
    }

    public List<Parameter> getParameterList() {
        return Collections.list(Collections.enumeration(parameters.values()));
    }

    public void setParameterList(List<Parameter> parameters) {
        for (Parameter parameter : parameters) {
            setParameterValue(parameter.getName(), parameter.getValue());
        }
    }

}
