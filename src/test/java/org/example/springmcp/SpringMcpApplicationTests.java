package org.example.springmcp;

import io.modelcontextprotocol.client.McpClient;
import io.modelcontextprotocol.server.McpServerFeatures;
import io.modelcontextprotocol.spec.McpSchema;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class SpringMcpApplicationTests {

	@Test
	void contextLoads() {
	}

	@Test
	void whenLogPromptToolCalled_thenReturnsResult(){
		McpSchema.CallToolRequest request =
				new McpSchema.CallToolRequest("", Map.of("prompt","Unit test message"));

		McpServerFeatures.SyncToolSpecification toolSpec = LoggingTool.logPromptTool();
		McpSchema.CallToolResult result = toolSpec.callHandler().apply(null, request);
		assertNotNull(result);
		assertFalse(result.isError());
		assertEquals(
				"Input Prompt: Unit test message",
				((McpSchema.TextContent) result.content().getFirst()).text()
		);

	}

	@Test
	void whenCalledViaClient_thenReturnsLoggedResult() {

		McpSchema.CallToolRequest request = new McpSchema.CallToolRequest(
				"echo", Map.of("message", "Client-server test message"));
		McpSchema.CallToolResult result = McpClientApp.getClient().callTool(request);
		assertNotNull(result);
		assertNull(result.isError());
		assertEquals("Echo: Client-server test message",
				((McpSchema.TextContent) (result.content()
						.getFirst())).text());
	}

}
