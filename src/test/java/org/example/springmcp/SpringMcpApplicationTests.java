package org.example.springmcp;

import io.modelcontextprotocol.server.McpServerFeatures;
import io.modelcontextprotocol.spec.McpSchema;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertEquals;

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

}
