package pl.edu.ug.cmms; // must match the folder pl/edu/ug/pantry

public class App {

    public static void main(String[] args) {
        System.out.println(SampleData.machines().size() + " machines");
        System.out.println(SampleData.workOrders().size() + " work orders");
        Reports.unfinished(SampleData.workOrders());
        Reports.breakdown(SampleData.workOrders());
        Mttr.average_time(SampleData.workOrders(), SampleData.machines());
    }
}
