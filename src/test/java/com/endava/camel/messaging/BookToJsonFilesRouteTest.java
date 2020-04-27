package com.endava.camel.messaging;

import com.endava.camel.book.domain.Book;
import org.apache.camel.Exchange;
import org.apache.camel.Message;
import org.apache.camel.RoutesBuilder;
import org.apache.camel.component.mock.MockEndpoint;
import org.apache.camel.model.dataformat.JsonDataFormat;
import org.apache.camel.test.junit4.CamelTestSupport;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.mock;

public class BookToJsonFilesRouteTest extends CamelTestSupport {

    private JsonDataFormat jsonDataFormat = new JsonDataFormat();
    private BookRecipientList bookRecipientList = mock(BookRecipientList.class);

    @Override
    protected RoutesBuilder createRouteBuilder() throws Exception {
        return new BookToJsonFilesRoute(jsonDataFormat, bookRecipientList);
    }

    @Override
    @BeforeEach
    public void setUp() throws Exception {
        super.setUp();
        doAnswer(invocationOnMock -> {
            Exchange exchange = invocationOnMock.getArgument(0);
            Message in = exchange.getIn();
            in.setHeader("recipients", "mock:final_destination");
            return null;
        }).when(bookRecipientList).setRecipientListHeader(any());
    }

    @Test
    public void test() throws InterruptedException {
        MockEndpoint mockEndpoint = getMockEndpoint("mock:final_destination");
        mockEndpoint.expectedMessageCount(1);

        template().sendBody("direct:books_to_json", Book.builder()
                .title("Thinking in Java")
                .build());

        mockEndpoint.assertIsSatisfied();
        List<Exchange> receivedExchanges = mockEndpoint.getReceivedExchanges();
        Message in = receivedExchanges.get(0).getIn();
        assertEquals("Thinking in Java", in.getHeader("title", String.class));
    }
}