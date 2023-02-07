package com.architjoshi.nyschools.network.model;

import com.google.gson.annotations.SerializedName;

public class SchoolSATResults {
    @SerializedName("dbn")
    private String schoolId;
    @SerializedName("school_name")
    private String name;
    @SerializedName("num_of_sat_test_takers")
    private String numberOfSATTestTakers;
    @SerializedName("sat_critical_reading_avg_score")
    private String readingScore;
    @SerializedName("sat_math_avg_score")
    private String mathScore;
    @SerializedName("sat_writing_avg_score")
    private String writingScore;

    public SchoolSATResults(String schoolId, String name, String numberOfSATTestTakers,
                            String readingScore, String mathScore, String writingScore) {
        this.schoolId = schoolId;
        this.name = name;
        this.numberOfSATTestTakers = numberOfSATTestTakers;
        this.readingScore = readingScore;
        this.mathScore = mathScore;
        this.writingScore = writingScore;
    }

    public String getSchoolId() {
        return schoolId;
    }

    public String getName() {
        return name;
    }

    public String getNumberOfSATTestTakers() {
        return numberOfSATTestTakers;
    }

    public String getReadingScore() {
        return readingScore;
    }

    public String getMathScore() {
        return mathScore;
    }

    public String getWritingScore() {
        return writingScore;
    }
}
