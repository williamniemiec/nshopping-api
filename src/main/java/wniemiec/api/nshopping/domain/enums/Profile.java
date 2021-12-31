package wniemiec.api.nshopping.domain.enums;


/**
 * Responsible for representing profile types.
 */
public enum Profile {

    //-------------------------------------------------------------------------
    //		Enumerations
    //-------------------------------------------------------------------------
    ADMIN(1, "ROLE_ADMIN"),
    CLIENT(2, "ROLE_CLIENT");


    //-------------------------------------------------------------------------
    //		Attributes
    //-------------------------------------------------------------------------
    private int id;
    private String label;


    //-------------------------------------------------------------------------
    //		Constructor
    //-------------------------------------------------------------------------
    private Profile(int id, String label) {
        this.id = id;
        this.label = label;
    }


    //-------------------------------------------------------------------------
    //		Methods
    //-------------------------------------------------------------------------
    public static Profile toEnum(Integer id) {
        if (id == null)
            return null;

        for (Profile type : Profile.values()) {
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
