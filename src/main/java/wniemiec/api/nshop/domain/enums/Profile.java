package wniemiec.api.nshop.domain.enums;

public enum Profile {

    ADMIN(1, "ROLE_ADMIN"),
    CLIENT(2, "ROLE_CLIENT");

    private int id;
    private String label;

    private Profile(int id, String label) {
        this.id = id;
        this.label = label;
    }

    public static Profile toEnum(Integer id) {
        if (id == null)
            return null;

        for (Profile type : Profile.values()) {
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
