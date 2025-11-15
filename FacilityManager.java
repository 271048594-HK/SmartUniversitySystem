import java.util.*;

public class FacilityManager {
    private List<Classroom> classrooms = new ArrayList<>();
    private List<Lab> labs = new ArrayList<>();
    private List<Office> offices = new ArrayList<>();

    public void addClassroom(Classroom c) { if (c != null) classrooms.add(c); }
    public void addLab(Lab l) { if (l != null) labs.add(l); }
    public void addOffice(Office o) { if (o != null) offices.add(o); }

    public void listFacilities() {
        System.out.println("\n--- Classrooms ---");
        for (Classroom c : classrooms) System.out.println(" - " + c.getRoomNumber() + " (cap " + c.getCapacity() + ")");
        System.out.println("\n--- Labs ---");
        for (Lab l : labs) l.displayLabInfo();
        System.out.println("\n--- Offices ---");
        for (Office o : offices) {
            System.out.println(" - " + o.getOfficeNumber() + " assigned to " + (o.getAssignedFaculty() != null ? o.getAssignedFaculty().getName() : "None"));
        }
    }
}
