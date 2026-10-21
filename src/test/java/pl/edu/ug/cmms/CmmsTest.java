package pl.edu.ug.cmms;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import java.time.LocalDateTime;
import java.time.Duration;
import java.util.List;

import org.junit.jupiter.api.Test;
import pl.edu.ug.cmms.WorkOrder;
import pl.edu.ug.cmms.WorkOrder.Priority;
import pl.edu.ug.cmms.WorkOrder.WorkOrderStatus;
import pl.edu.ug.cmms.WorkOrder.WorkOrderType;

class CmmsTest {
    LocalDateTime t1 = LocalDateTime.parse("2026-03-02T08:00");
    LocalDateTime t2 = LocalDateTime.parse("2026-03-02T10:00");
    // mttr test 2h
    LocalDateTime h2_t1 = LocalDateTime.parse("2026-03-02T08:00");
    LocalDateTime h2_t2 = LocalDateTime.parse("2026-03-02T10:00");
    // mttr test 3h
    LocalDateTime h3_t1 = LocalDateTime.parse("2026-03-03T08:00");
    LocalDateTime h3_t2 = LocalDateTime.parse("2026-03-03T11:00");
    @Test
    void rejectsBlankId() {
        assertThrows(IllegalArgumentException.class,
                () -> new WorkOrder(" ", "M-01", WorkOrderType.BREAKDOWN,
                        Priority.HIGH, WorkOrderStatus.OPEN, t1, null));
    }
    @Test
    void rejectsDoneWithoutClosedDate() {
        assertThrows(IllegalArgumentException.class,
                () -> new WorkOrder("WO-1", "M-01", WorkOrderType.BREAKDOWN,
                        Priority.HIGH, WorkOrderStatus.DONE, t1, null));
    }
    @Test
    void rejectsClosedBeforeReported() {
        assertThrows(IllegalArgumentException.class,
                () -> new WorkOrder("WO-1", "M-01", WorkOrderType.BREAKDOWN,
                        Priority.HIGH, WorkOrderStatus.DONE, t2, t1));
    }
    @Test
    void acceptsValidDoneOrder() {
        assertDoesNotThrow(() -> new WorkOrder("WO-1", "M-01", WorkOrderType.BREAKDOWN,
                Priority.HIGH, WorkOrderStatus.DONE, t1, t2));
    }
    @Test
    void acceptsOpenOrderWithoutClosedDate() {
        WorkOrder order = new WorkOrder("WO-2", "M-01", WorkOrderType.BREAKDOWN,
                Priority.HIGH, WorkOrderStatus.OPEN, t1, null);
        assertEquals("WO-2", order.id());
    }
    // MTTR
    @Test
    void mttrIsAverageOfRepairTimes() {
        WorkOrder a = new WorkOrder("WO-1", "M-01", WorkOrderType.BREAKDOWN,
                Priority.HIGH, WorkOrderStatus.DONE,h2_t1, h2_t2);
        WorkOrder b = new WorkOrder("WO-2", "M-01", WorkOrderType.BREAKDOWN,
                Priority.HIGH, WorkOrderStatus.DONE, h3_t1, h3_t2);
        Duration result = Mttr.average(List.of(a, b));
        assertEquals(Duration.ofMinutes(150), result);
    }
}