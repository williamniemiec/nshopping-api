package wniemiec.api.nshopping.domain.enums;


/**
 * Responsible for representing payment status.
 */
public enum PaymentStatus {

    //-------------------------------------------------------------------------
    //		Enumerations
    //-------------------------------------------------------------------------
    PENDING(1, "Pending"),
    FINISHED(2, "Finished"),
    CANCELED(3, "Canceled");


    //-------------------------------------------------------------------------
    //		Attributes
    //-------------------------------------------------------------------------
    private int id;
    private String label;


    //-------------------------------------------------------------------------
    //		Constructor
    //-------------------------------------------------------------------------
    private PaymentStatus(int id, String label) {
        this.id = id;
        this.label = label;
    }


    //-------------------------------------------------------------------------
    //		Methods
    //-------------------------------------------------------------------------
    public static PaymentStatus toEnum(Integer id) {
        if (id == null)
            return null;

        for (PaymentStatus type : PaymentStatus.values()) {
            if (id.equals(type.getId()))
                return type;
        }

        throw new IllegalArgumentException("Invalid id: " + id);
    }


    //-------------------------------------------------------------------------
    //		Getters
    //-------------------------------------------------------------------------
    public int getId() {
        return id;
    }

    public String getLabel() {
        return label;
    }
}
