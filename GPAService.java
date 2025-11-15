import java.util.*;

public class GPAService {
    // simple average of grade points (not weighted by credits)
    public double calculateGPA(GradeRecord gr) {
        Map<String, Double> map = gr.getGrades();
        if (map.isEmpty()) return 0.0;
        double sum = 0.0;
        for (double v : map.values()) sum += v;
        return sum / map.size();
    }
}
