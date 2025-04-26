package com.analia.vapi.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;
import java.util.Map;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class VapiResponse {

    @JsonProperty("request_id")
    private String requestId;

    private String content;

    @JsonProperty("function_call")
    private FunctionCallResponse functionCall;

    @JsonProperty("function_response")
    private FunctionResponse functionResponse;

    private List<Tool> tools;

    private Map<String, Object> metadata;

    // Getters and setters

    public String getRequestId() {
        return requestId;
    }

    public void setRequestId(String requestId) {
        this.requestId = requestId;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public FunctionCallResponse getFunctionCall() {
        return functionCall;
    }

    public void setFunctionCall(FunctionCallResponse functionCall) {
        this.functionCall = functionCall;
    }

    public FunctionResponse getFunctionResponse() {
        return functionResponse;
    }

    public void setFunctionResponse(FunctionResponse functionResponse) {
        this.functionResponse = functionResponse;
    }

    public List<Tool> getTools() {
        return tools;
    }

    public void setTools(List<Tool> tools) {
        this.tools = tools;
    }

    public Map<String, Object> getMetadata() {
        return metadata;
    }

    public void setMetadata(Map<String, Object> metadata) {
        this.metadata = metadata;
    }

    // Inner classes

    @JsonIgnoreProperties(ignoreUnknown = true)
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public static class FunctionCallResponse {
        private String name;
        private Map<String, Object> arguments;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public Map<String, Object> getArguments() {
            return arguments;
        }

        public void setArguments(Map<String, Object> arguments) {
            this.arguments = arguments;
        }
    }

    @JsonIgnoreProperties(ignoreUnknown = true)
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public static class FunctionResponse {
        private String name;
        private Object result;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public Object getResult() {
            return result;
        }

        public void setResult(Object result) {
            this.result = result;
        }
    }

    @JsonIgnoreProperties(ignoreUnknown = true)
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public static class Tool {
        private String type;
        private Function function;

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public Function getFunction() {
            return function;
        }

        public void setFunction(Function function) {
            this.function = function;
        }
    }

    @JsonIgnoreProperties(ignoreUnknown = true)
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public static class Function {
        private String name;
        private String description;
        private List<Parameter> parameters;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
        }

        public List<Parameter> getParameters() {
            return parameters;
        }

        public void setParameters(List<Parameter> parameters) {
            this.parameters = parameters;
        }
    }

    @JsonIgnoreProperties(ignoreUnknown = true)
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public static class Parameter {
        private String name;
        private String type;
        private String description;
        private boolean required;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
        }

        public boolean isRequired() {
            return required;
        }

        public void setRequired(boolean required) {
            this.required = required;
        }
    }

    // Static builder method for creating responses
    public static VapiResponse createResponse(String requestId, String content) {
        VapiResponse response = new VapiResponse();
        response.setRequestId(requestId);
        response.setContent(content);
        return response;
    }

    public static VapiResponse createFunctionResponse(String requestId, String functionName, Object result) {
        VapiResponse response = new VapiResponse();
        response.setRequestId(requestId);

        FunctionResponse functionResponse = new FunctionResponse();
        functionResponse.setName(functionName);
        functionResponse.setResult(result);

        response.setFunctionResponse(functionResponse);
        return response;
    }
}