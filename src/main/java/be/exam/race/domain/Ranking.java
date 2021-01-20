package be.exam.race.domain;

public enum Ranking {

    FIRST(1, 25),
    SECOND(2, 18),
    THIRD(3, 15),
    FOURTH(4, 12),
    FIFTH(5, 10),
    SIXTH(6, 8),
    SEVENTH(7, 6),
    EIGHTH(8, 4),
    NINTH(9, 2),
    TENTH(10, 1),
    ELEVENTH(11,0),
    TWELFTH(12,0),
    THIRTEENTH(13,0),
    FOURTEENTH(14,0),
    FIFTEENTH(15,0),
    SIXTEENTH(16,0),
    SEVENTEENTH(17, 0),
    EIGHTEENTH(18,0),
    NINETEENTH(19,0),
    TWENTIETH(20,0);

    private Integer rank;
    private Integer points;

    private Ranking(Integer rank, Integer points) {
        this.rank = rank;
        this.points = points;
    }

    public static Ranking from(Integer rank) {
        return Ranking.values()[rank - 1];
    }

    public Integer getRank() {
        return rank;
    }

    public Integer getPoints() {
        return points;
    }
}
