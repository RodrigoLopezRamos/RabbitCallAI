package com.analia.vapi.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;
import java.util.Map;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class VapiMessage {

    @JsonProperty("message_type")
    private String messageType;

    @JsonProperty("call_id")
    private String callId;

    @JsonProperty("assistant_id")
    private String assistantId;

    private String text;

    @JsonProperty("function_call")
    private FunctionCall functionCall;

    @JsonProperty("end_of_call_report")
    private EndOfCallReport endOfCallReport;

    @JsonProperty("request_id")
    private String requestId;

    private Map<String, Object> metadata;

    // Getters and setters

    public String getMessageType() {
        return messageType;
    }

    public void setMessageType(String messageType) {
        this.messageType = messageType;
    }

    public String getCallId() {
        return callId;
    }

    public void setCallId(String callId) {
        this.callId = callId;
    }

    public String getAssistantId() {
        return assistantId;
    }

    public void setAssistantId(String assistantId) {
        this.assistantId = assistantId;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public FunctionCall getFunctionCall() {
        return functionCall;
    }

    public void setFunctionCall(FunctionCall functionCall) {
        this.functionCall = functionCall;
    }

    public EndOfCallReport getEndOfCallReport() {
        return endOfCallReport;
    }

    public void setEndOfCallReport(EndOfCallReport endOfCallReport) {
        this.endOfCallReport = endOfCallReport;
    }

    public String getRequestId() {
        return requestId;
    }

    public void setRequestId(String requestId) {
        this.requestId = requestId;
    }

    public Map<String, Object> getMetadata() {
        return metadata;
    }

    public void setMetadata(Map<String, Object> metadata) {
        this.metadata = metadata;
    }

    // Inner classes for message components

    @JsonIgnoreProperties(ignoreUnknown = true)
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public static class FunctionCall {
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
    public static class EndOfCallReport {
        @JsonProperty("call_duration_seconds")
        private double callDurationSeconds;

        @JsonProperty("customer_talk_time_seconds")
        private double customerTalkTimeSeconds;

        @JsonProperty("assistant_talk_time_seconds")
        private double assistantTalkTimeSeconds;

        @JsonProperty("conversation_turns")
        private int conversationTurns;

        private List<MessageMetrics> messages;

        private Transcript transcript;

        // Getters and setters

        public double getCallDurationSeconds() {
            return callDurationSeconds;
        }

        public void setCallDurationSeconds(double callDurationSeconds) {
            this.callDurationSeconds = callDurationSeconds;
        }

        public double getCustomerTalkTimeSeconds() {
            return customerTalkTimeSeconds;
        }

        public void setCustomerTalkTimeSeconds(double customerTalkTimeSeconds) {
            this.customerTalkTimeSeconds = customerTalkTimeSeconds;
        }

        public double getAssistantTalkTimeSeconds() {
            return assistantTalkTimeSeconds;
        }

        public void setAssistantTalkTimeSeconds(double assistantTalkTimeSeconds) {
            this.assistantTalkTimeSeconds = assistantTalkTimeSeconds;
        }

        public int getConversationTurns() {
            return conversationTurns;
        }

        public void setConversationTurns(int conversationTurns) {
            this.conversationTurns = conversationTurns;
        }

        public List<MessageMetrics> getMessages() {
            return messages;
        }

        public void setMessages(List<MessageMetrics> messages) {
            this.messages = messages;
        }

        public Transcript getTranscript() {
            return transcript;
        }

        public void setTranscript(Transcript transcript) {
            this.transcript = transcript;
        }
    }

    @JsonIgnoreProperties(ignoreUnknown = true)
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public static class MessageMetrics {
        private String role;
        private String text;

        @JsonProperty("start_time")
        private double startTime;

        @JsonProperty("end_time")
        private double endTime;

        // Getters and setters

        public String getRole() {
            return role;
        }

        public void setRole(String role) {
            this.role = role;
        }

        public String getText() {
            return text;
        }

        public void setText(String text) {
            this.text = text;
        }

        public double getStartTime() {
            return startTime;
        }

        public void setStartTime(double startTime) {
            this.startTime = startTime;
        }

        public double getEndTime() {
            return endTime;
        }

        public void setEndTime(double endTime) {
            this.endTime = endTime;
        }
    }

    @JsonIgnoreProperties(ignoreUnknown = true)
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public static class Transcript {
        private List<TranscriptMessage> messages;

        public List<TranscriptMessage> getMessages() {
            return messages;
        }

        public void setMessages(List<TranscriptMessage> messages) {
            this.messages = messages;
        }
    }

    @JsonIgnoreProperties(ignoreUnknown = true)
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public static class TranscriptMessage {
        private String role;
        private String text;
        private double timestamp;

        // Getters and setters

        public String getRole() {
            return role;
        }

        public void setRole(String role) {
            this.role = role;
        }

        public String getText() {
            return text;
        }

        public void setText(String text) {
            this.text = text;
        }

        public double getTimestamp() {
            return timestamp;
        }

        public void setTimestamp(double timestamp) {
            this.timestamp = timestamp;
        }
    }
}