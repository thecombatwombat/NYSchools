package com.architjoshi.nyschools.domain.model;

public class SchoolSATResults {
    private String schoolId;
    private String name;
    private String readingScore;
    private String mathScore;
    private String writingScore;

    public SchoolSATResults(String schoolId, String name, String readingScore,
                            String mathScore, String writingScore) {
        this.schoolId = schoolId;
        this.name = name;
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
