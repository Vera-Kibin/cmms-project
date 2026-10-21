package pl.edu.ug.cmms;
import java.time.LocalDate;
public record Machine(String id, String name, MachineType type,
                      String hall, LocalDate data) {
    public enum MachineType {
        CNC_LATHE, CONVEYOR, MILLING_MACHINE, WELDING_ROBOT,
        INJECTION_MOLDER, HYDRAULIC_PRESS, COMPRESSOR
    }
    public Machine {
        if (id == null || id.isBlank()) {
            throw new IllegalArgumentException("id must not be blank");
        }
    }
}