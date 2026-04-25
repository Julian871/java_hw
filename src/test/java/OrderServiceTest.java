import com.education.hw.tests.Order;
import com.education.hw.tests.OrderRepository;
import com.education.hw.tests.OrderService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

public class OrderServiceTest {

    private OrderRepository orderRepositoryMock;
    private OrderService orderService;

    @BeforeEach
    void setUp() {
        orderRepositoryMock = mock(OrderRepository.class);
        orderService = new OrderService(orderRepositoryMock);
    }

    @Test
    void processOrder_Success() {
        Order order = new Order(10.0, "Milk", 5);

        when(orderRepositoryMock.saveOrder(order)).thenReturn(1);

        String result = orderService.processOrder(order);

        assertEquals("Order processed successfully", result);

        verify(orderRepositoryMock, times(1)).saveOrder(order);
    }

    @Test
    void failCreateOrderWhenRepositoryThrowsException() {
        Order order = new Order(10.0, "Milk", 5);

        when(orderRepositoryMock.saveOrder(order))
                .thenThrow(new RuntimeException("Database connection failed"));

        String result = orderService.processOrder(order);

        assertEquals("Order processing failed", result);
        verify(orderRepositoryMock, times(1)).saveOrder(order);
    }

    @Test
    void calculateTotal_Success() {
        Order order = new Order(100.0, "Milk", 3);

        when(orderRepositoryMock.getOrderById(order.getOrderId())).thenReturn(Optional.of(order));

        Double result = orderService.calculateTotal(order.getOrderId());

        assertEquals(300.0, result);

        verify(orderRepositoryMock, times(1)).getOrderById(order.getOrderId());
    }

    @Test
    void calculateTotal_Return_OrderNotFound() {
        int nonExistentId = 100;

        when(orderRepositoryMock.getOrderById(nonExistentId)).thenReturn(Optional.empty());

        assertThrows(IllegalArgumentException.class, () -> orderService.calculateTotal(nonExistentId));

        verify(orderRepositoryMock, times(1)).getOrderById(nonExistentId);
    }

    @Test
    void calculateTotal_Return_CorrectTotalPrice_WithQuantityZero() {
        Order order = new Order(100.0, "Milk", 0);

        when(orderRepositoryMock.getOrderById(order.getOrderId())).thenReturn(Optional.of(order));

        Double result = orderService.calculateTotal(order.getOrderId());

        assertEquals(0.0, result);

        verify(orderRepositoryMock, times(1)).getOrderById(order.getOrderId());
    }
}
