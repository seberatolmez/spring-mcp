package org.example.springmcp;

import io.modelcontextprotocol.server.McpServerFeatures;
import io.modelcontextprotocol.spec.McpSchema;

import java.util.List;
import java.util.Map;

public class LoggingTool {

    public static McpServerFeatures.SyncToolSpecification logPromptTool() {
        McpSchema.JsonSchema inputSchema = new McpSchema.JsonSchema(
                "object",
                Map.of("prompt", String.class),
                List.of("prompt"),
                false,
                null,
                null
                );

        return new McpServerFeatures.SyncToolSpecification(
                new McpSchema.Tool(
                        "logPrompt", "Log Prompt" , "Logs a provided prompt",
                        inputSchema,null,null,null),
                (exchange,args) -> {
                    String prompt = (String) args.get("prompt");
                    return McpSchema.CallToolResult.builder()
                            .content(List.of(new McpSchema.TextContent("Input prompt: " + prompt)))
                            .isError(false)
                            .build();
                }
        );
    }
}
