package wniemiec.api.nshop.domain.enums;


/**
 * Responsible for representing person type.
 */
public enum ClientType {

    //-------------------------------------------------------------------------
    //		Enumerations
    //-------------------------------------------------------------------------
    NATURAL_PERSON(1, "Natural Person"),
    LEGAL_PERSON(2, "Legal Person");


    //-------------------------------------------------------------------------
    //		Attributes
    //-------------------------------------------------------------------------
    private int id;
    private String label;


    //-------------------------------------------------------------------------
    //		Constructor
    //-------------------------------------------------------------------------
    private ClientType(int id, String label) {
        this.id = id;
        this.label = label;
    }


    //-------------------------------------------------------------------------
    //		Methods
    //-------------------------------------------------------------------------
    public static ClientType toEnum(Integer id) {
        if (id == null)
            return null;

        for (ClientType type : ClientType.values()) {
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
