package com.pfe.learningplatform.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DashboardStatsResponse {

    /*
     * =========================================
     * DASHBOARD STATISTICS
     * =========================================
     */

    private long totalUsers;

    private long totalCourses;

    private long totalSections;

    private long totalVideos;

    private long totalQuizzes;

    private long totalEnrollments;
}
