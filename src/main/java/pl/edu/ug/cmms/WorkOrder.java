package pl.edu.ug.cmms;
import java.time.LocalDateTime;
public record WorkOrder(String id, String machineId, WorkOrderType type,
                        Priority priority, WorkOrderStatus status,
                        LocalDateTime reportedAt, LocalDateTime closedAt){
    public enum Priority {
        NORMAL, LOW, CRITICAL, HIGH
    }
    public enum WorkOrderStatus {
        OPEN, DONE, CANCELLED, IN_PROGRESS
    }
    public enum WorkOrderType {
        PREVENTIVE, INSPECTION, BREAKDOWN
    }
    public WorkOrder {
        if (id == null || id.isBlank()) {
            throw new IllegalArgumentException("id must not be blank");
        }
        if(status == WorkOrderStatus.DONE && closedAt == null){
            throw new IllegalArgumentException("DONE order needs a closing date");
        }
        if(closedAt != null && closedAt.isBefore(reportedAt)){
            throw new IllegalArgumentException("Closed date is before the repord date");
        }
    }
}