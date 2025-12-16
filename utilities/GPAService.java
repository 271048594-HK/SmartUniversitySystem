package utilities;

import database.GradingDAO;
import java.util.Map;

public class GPAService {

    private GradingDAO gradingDao = new GradingDAO(); // Composition: GPAService uses the DAO

    // Calculates simple average GPA based on all grades found in the database
    public double calculateGPA(String studentId) {

        // 1. Retrieve all grades using the DAO
        Map<String, Double> grades = gradingDao.getGradesForStudent(studentId);

        if (grades.isEmpty()) {
            return 0.0;
        }

        // 2. Calculate the average
        double sum = 0.0;
        for (double gradePoint : grades.values()) {
            sum += gradePoint;
        }

        // NOTE: For a more accurate W-GPA (Weighted GPA), you would need to fetch
        return sum / grades.size();
    }
}