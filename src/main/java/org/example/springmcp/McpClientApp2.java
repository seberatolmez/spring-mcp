package org.example.springmcp;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.modelcontextprotocol.client.McpClient;
import io.modelcontextprotocol.client.McpSyncClient;
import io.modelcontextprotocol.client.transport.ServerParameters;
import io.modelcontextprotocol.client.transport.StdioClientTransport;
import io.modelcontextprotocol.json.jackson.JacksonMcpJsonMapper;
import io.modelcontextprotocol.spec.McpClientTransport;
import io.modelcontextprotocol.spec.McpSchema;
import org.slf4j.LoggerFactory;

import java.util.Map;
import java.util.logging.Logger;

public class McpClientApp2 {

    private static final Logger log = (Logger) LoggerFactory.getLogger(McpClientApp2.class);

    public static void main(String[] args){

        String jarPath = new java.io.File("java-mcp/target/java-mcp-1.0.0-SNAPSHOT.jar").getAbsolutePath();
        ServerParameters params = ServerParameters.builder("java")
                .args("-jar",jarPath)
                .build();

        JacksonMcpJsonMapper jsonMapper = new JacksonMcpJsonMapper(new ObjectMapper());
        McpClientTransport transport = new StdioClientTransport(params, jsonMapper);

        McpSyncClient client = McpClient.sync(transport).build();
        client.initialize();

        McpSchema.ListToolsResult tools = client.listTools();
        McpClientApp2.log.info("Tools exposed by the server");
        tools
                .tools()
                .forEach(tool -> System.out.println(" - "+tool));

        McpClientApp2.log.info("\nCalling 'logPrompt' tool..." );
        McpSchema.CallToolResult result = client.callTool(
                new McpSchema.CallToolRequest("logPrompt", Map.of("prompt","Hello from MCP client!")));

        McpClientApp2.log.info("Result: " + result);
        client.closeGracefully();
    }
}
