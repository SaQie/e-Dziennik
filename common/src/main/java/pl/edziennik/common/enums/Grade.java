package pl.edziennik.common.enums;

import jakarta.persistence.EntityNotFoundException;

public enum Grade {

    ZERO("0", 0),
    ONE("1", 1),
    ONE_PLUS("1+", 1.25),
    TWO("2", 2),
    TWO_MINUS("2-", 1.75),
    TWO_PLUS("2+", 2.25),
    THREE("3", 3),
    THREE_MINUS("3-", 2.75),
    THREE_PLUS("3+", 3.25),
    FOUR("4", 4),
    FOUR_MINUS("4-", 3.75),
    FOUR_PLUS("4+", 4.25),
    FIVE("5", 5),
    FIVE_PLUS("5+", 5.25),
    FIVE_MINUS("5-", 4.75),
    SIX("6", 6),
    SIX_MINUS("6-", 5.75);

    public final String grade;
    public final double decimalGrade;

    Grade(String grade, double decimalGrade) {
        this.grade = grade;
        this.decimalGrade = decimalGrade;
    }

    public static Grade getByGrade(String grade) {
        for (Grade ratingConst : Grade.values()) {
            if (ratingConst.grade.equals(grade)) {
                return ratingConst;
            }
        }
        throw new EntityNotFoundException("Grade " + grade + " not found");
    }
}
