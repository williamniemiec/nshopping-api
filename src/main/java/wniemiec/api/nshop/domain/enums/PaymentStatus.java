package wniemiec.api.nshop.domain.enums;

public enum PaymentStatus {

    PENDING(1, "Pending"),
    FINISHED(2, "Finished"),
    CANCELED(3, "Canceled");

    private int id;
    private String label;

    private PaymentStatus(int id, String label) {
        this.id = id;
        this.label = label;
    }

    public static PaymentStatus toEnum(Integer id) {
        if (id == null)
            return null;

        for (PaymentStatus type : PaymentStatus.values()) {
            if (id.equals(type.getId()))
                return type;
        }

        throw new IllegalArgumentException("Invalid id: " + id);
    }

    public int getId() {
        return id;
    }

    public String getLabel() {
        return label;
    }
}
