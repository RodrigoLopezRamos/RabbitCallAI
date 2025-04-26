package com.analia.vapi;
import com.analia.vapi.model.VapiMessage;
import com.analia.vapi.model.VapiResponse;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import org.jboss.logging.Logger;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@ApplicationScoped
public class VapiMessageService {

    @Inject
    Logger logger;

    /**
     * Process incoming messages from VAPI.AI
     */
    public VapiResponse processMessage(VapiMessage message) {
        if (message == null) {
            logger.warn("Received null message");
            return createErrorResponse(null, "Invalid message");
        }

        logger.info("Processing message of type: " + message.getMessageType() + " for call: " + message.getCallId());

        try {
            // Handle different message types
            switch (message.getMessageType()) {
                case "function_call":
                    return handleFunctionCall(message);
                case "context_request":
                    return handleContextRequest(message);
                case "end_of_call_report":
                    return handleEndOfCallReport(message);
                default:
                    logger.warn("Unsupported message type: " + message.getMessageType());
                    return createErrorResponse(message.getRequestId(), "Unsupported message type");
            }
        } catch (Exception e) {
            logger.error("Error processing message", e);
            return createErrorResponse(message.getRequestId(), "Error: " + e.getMessage());
        }
    }

    /**
     * Handle function calls from the assistant
     */
    private VapiResponse handleFunctionCall(VapiMessage message) {
        if (message.getFunctionCall() == null) {
            return createErrorResponse(message.getRequestId(), "Missing function call details");
        }

        String functionName = message.getFunctionCall().getName();
        Map<String, Object> arguments = message.getFunctionCall().getArguments();

        logger.info("Function call: " + functionName + " with arguments: " + arguments);

        // Execute the function based on the name
        switch (functionName) {
            case "get_customer_info":
                return getCustomerInfo(message.getRequestId(), arguments);
            case "schedule_appointment":
                return scheduleAppointment(message.getRequestId(), arguments);
            case "check_order_status":
                return checkOrderStatus(message.getRequestId(), arguments);
            default:
                logger.warn("Unknown function: " + functionName);
                return createErrorResponse(message.getRequestId(), "Unknown function: " + functionName);
        }
    }

    /**
     * Handle context requests from the assistant
     */
    private VapiResponse handleContextRequest(VapiMessage message) {
        // Provide context information for the assistant
        logger.info("Context request for call: " + message.getCallId());

        // In a real implementation, you would fetch context from your database
        // based on the call ID or other identifiers

        // For demonstration, we'll return a static context
        Map<String, Object> metadata = new HashMap<>();

        // Include any custom metadata that would be useful for the assistant
        metadata.put("customer_id", "CUST-12345");
        metadata.put("account_type", "premium");
        metadata.put("last_interaction", "2025-03-15");
        metadata.put("language_preference", "en-US");

        // Create a response with tools that your assistant can use
        VapiResponse response = new VapiResponse();
        response.setRequestId(message.getRequestId());
        response.setMetadata(metadata);

        // Define available tools
        List<VapiResponse.Tool> tools = new ArrayList<>();
        tools.add(createFunctionTool(
                "get_customer_info",
                "Get information about a customer",
                List.of(
                        createParameter("customer_id", "string", "The customer's ID", true)
                )
        ));

        tools.add(createFunctionTool(
                "schedule_appointment",
                "Schedule an appointment for a customer",
                List.of(
                        createParameter("customer_id", "string", "The customer's ID", true),
                        createParameter("date", "string", "The appointment date (YYYY-MM-DD)", true),
                        createParameter("time", "string", "The appointment time (HH:MM)", true),
                        createParameter("service_type", "string", "The type of service", true)
                )
        ));

        tools.add(createFunctionTool(
                "check_order_status",
                "Check the status of an order",
                List.of(
                        createParameter("order_id", "string", "The order ID", true)
                )
        ));

        response.setTools(tools);

        return response;
    }

    /**
     * Handle end-of-call reports
     */
    private VapiResponse handleEndOfCallReport(VapiMessage message) {
        // Process the end-of-call report
        logger.info("End-of-call report for call: " + message.getCallId());

        if (message.getEndOfCallReport() != null) {
            // Log key metrics
            logger.info("Call duration: " + message.getEndOfCallReport().getCallDurationSeconds() + "s");
            logger.info("Conversation turns: " + message.getEndOfCallReport().getConversationTurns());

            // In a real implementation, you would store this data in your database
            // or send it to your analytics system

            // Process transcript if available
            if (message.getEndOfCallReport().getTranscript() != null) {
                // Store or analyze the transcript
                logger.info("Transcript available with " +
                        message.getEndOfCallReport().getTranscript().getMessages().size() + " messages");
            }
        }

        // Return an acknowledgment
        return VapiResponse.createResponse(message.getRequestId(), "Report received successfully");
    }

    // Function implementations

    private VapiResponse getCustomerInfo(String requestId, Map<String, Object> arguments) {
        String customerId = (String) arguments.get("customer_id");

        // In a real implementation, fetch customer data from your database

        // Sample customer info
        Map<String, Object> customerInfo = new HashMap<>();
        customerInfo.put("id", customerId);
        customerInfo.put("name", "John Doe");
        customerInfo.put("email", "john.doe@example.com");
        customerInfo.put("phone", "+1234567890");
        customerInfo.put("membership_status", "Gold");
        customerInfo.put("account_balance", 1250.75);

        return VapiResponse.createFunctionResponse(requestId, "get_customer_info", customerInfo);
    }

    private VapiResponse scheduleAppointment(String requestId, Map<String, Object> arguments) {
        String customerId = (String) arguments.get("customer_id");
        String date = (String) arguments.get("date");
        String time = (String) arguments.get("time");
        String serviceType = (String) arguments.get("service_type");

        // In a real implementation, create an appointment in your system

        // Sample response
        Map<String, Object> result = new HashMap<>();
        result.put("success", true);
        result.put("appointment_id", "APT-" + System.currentTimeMillis());
        result.put("customer_id", customerId);
        result.put("date", date);
        result.put("time", time);
        result.put("service_type", serviceType);
        result.put("confirmation_number", "CONF-" + (int)(Math.random() * 100000));

        return VapiResponse.createFunctionResponse(requestId, "schedule_appointment", result);
    }

    private VapiResponse checkOrderStatus(String requestId, Map<String, Object> arguments) {
        String orderId = (String) arguments.get("order_id");

        // In a real implementation, fetch order information from your system

        // Sample response
        Map<String, Object> result = new HashMap<>();
        result.put("order_id", orderId);
        result.put("status", "shipped");
        result.put("estimated_delivery", "2025-04-22");
        result.put("tracking_number", "TRK12345678");
        result.put("items", 3);
        result.put("total_amount", 129.99);

        return VapiResponse.createFunctionResponse(requestId, "check_order_status", result);
    }

    // Helper methods

    private VapiResponse createErrorResponse(String requestId, String errorMessage) {
        VapiResponse response = new VapiResponse();
        response.setRequestId(requestId);
        Map<String, Object> metadata = new HashMap<>();
        metadata.put("error", errorMessage);
        response.setMetadata(metadata);
        return response;
    }

    private VapiResponse.Tool createFunctionTool(String name, String description, List<VapiResponse.Parameter> parameters) {
        VapiResponse.Tool tool = new VapiResponse.Tool();
        tool.setType("function");

        VapiResponse.Function function = new VapiResponse.Function();
        function.setName(name);
        function.setDescription(description);
        function.setParameters(parameters);

        tool.setFunction(function);
        return tool;
    }

    private VapiResponse.Parameter createParameter(String name, String type, String description, boolean required) {
        VapiResponse.Parameter parameter = new VapiResponse.Parameter();
        parameter.setName(name);
        parameter.setType(type);
        parameter.setDescription(description);
        parameter.setRequired(required);
        return parameter;
    }
}