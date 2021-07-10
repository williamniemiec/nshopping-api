package wniemiec.api.nshop.domain.enums;

public enum ClientType {

    NATURAL_PERSON(0, "Natural Person"),
    LEGAL_PERSON(1, "Legal Person");

    private int id;
    private String label;

    private ClientType(int id, String label) {
        this.id = id;
        this.label = label;
    }

    public static ClientType toEnum(Integer id) {
        if (id == null)
            return null;

        for (ClientType type : ClientType.values()) {
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
