package pl.edu.ug.cmms;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import pl.edu.ug.cmms.WorkOrder.Priority;
import pl.edu.ug.cmms.WorkOrder.WorkOrderStatus;
import pl.edu.ug.cmms.WorkOrder.WorkOrderType;

public class Reports{
    public static void unfinished(List<WorkOrder> orders){
        Map<Priority, Long> counts = orders.stream()
                .filter(o -> o.status() == WorkOrderStatus.OPEN
                        || o.status() == WorkOrderStatus.IN_PROGRESS)
                .collect(Collectors.groupingBy(WorkOrder::priority, Collectors.counting()));

        for (Priority p : Priority.values()) {
            System.out.println(p + " " + counts.getOrDefault(p, 0L));
        }
    }
    public static void breakdown(List<WorkOrder> orders){
        Map<String, Long> cars = orders.stream()
                .filter(o -> o.type() == WorkOrderType.BREAKDOWN
                        && o.status() != WorkOrderStatus.CANCELLED)
                .collect(Collectors.groupingBy(WorkOrder::machineId, Collectors.counting()));
        //System.out.println(cars);
        cars.entrySet().stream()
                .sorted(Map.Entry.<String, Long>comparingByValue().reversed()
                        .thenComparing(Map.Entry.comparingByKey()))
                .limit(3)
                .forEach(e -> System.out.println(e.getKey() + " (" + e.getValue() + ")"));
    }
}