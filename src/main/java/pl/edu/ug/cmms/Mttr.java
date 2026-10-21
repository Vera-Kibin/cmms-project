package pl.edu.ug.cmms;
import java.time.Duration;
import java.util.stream.Collectors;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import pl.edu.ug.cmms.WorkOrder;
import pl.edu.ug.cmms.WorkOrder.WorkOrderStatus;
import pl.edu.ug.cmms.WorkOrder.WorkOrderType;

public class Mttr {
    public static Duration one_time(WorkOrder order){
            return Duration.between(order.reportedAt(), order.closedAt());
    }
    public static boolean count(WorkOrder order){
        return order.type() == WorkOrderType.BREAKDOWN && order.status() == WorkOrderStatus.DONE;
    }
    public static String hall(String machineId, List<Machine> machines){
        for (Machine m : machines) {
            if (m.id().equals(machineId)) {
                return m.hall();
            }
        }
        return null;
    }
    public static void average_time(List<WorkOrder> orders, List<Machine> machines){
        Map<String, Duration> total = new TreeMap<>();
        Map<String, Integer> number = new TreeMap<>();
        for (WorkOrder o : orders) {
            if (count(o)) {
                String h = hall(o.machineId(), machines);
                Duration d = one_time(o);
                total.merge(h, d, Duration::plus);
                number.merge(h, 1, Integer::sum);
            }
        }
        for (String t : total.keySet()) {
            Duration avg = total.get(t).dividedBy(number.get(t));
            System.out.println(t + " " + avg.toHours() + " h " + avg.toMinutesPart() + " min");
        }
    }

    // metoda dla testów
    public static Duration average(List<WorkOrder> orders) {
        Duration sum = Duration.ZERO;
        int n = 0;
        for (WorkOrder order : orders) {
            if (count(order)) {
                sum = sum.plus(one_time(order));
                n++;
            }
        }
        return sum.dividedBy(n);
    }
}