package Data;

import java.lang.reflect.Field;
import java.util.HashMap;

public interface CRUD {
    DAO data = new DAO("localhost", "3306", "starCapital", "root", "");

    default String getName() {
        return this.getClass().getName().substring(this.getClass().getName().lastIndexOf('.') + 1);
    }

    default HashMap<String, String> getDetails() {
        Field f;
        try {
            f = this.getClass().getDeclaredField("details");
            f.setAccessible(true);
            return ((HashMap<String, String>) f.get(this));
        } catch (NoSuchFieldException | IllegalAccessException e) {
            e.printStackTrace();
        }
        return null;
    }

    default HashMap<String, String> create() {
        getDetails().remove("id");
        getDetails().put("id", "" + data.insert(getName(), getDetails()));
        return getDetails();
    }

    default void delete() throws NullPointerException {
        if (getDetails().get("id") == null)
            throw new NullPointerException();
        data.delete(getName(), " id=" + getDetails().get("id"));
    }

    default HashMap<String, String> update() throws NullPointerException {
        System.out.println(getDetails().toString());
        if (getDetails().get("id") == null)
            throw new NullPointerException();
        String id = getDetails().get("id");
        getDetails().remove("id");
        data.update(getName(), getDetails(), " id=" + id);
        getDetails().put("id", id);
        return getDetails();
    }

    default HashMap<String, String> read(String condition) throws NullPointerException {
        if (getDetails().get("id") == null)
            throw new NullPointerException();
        data.select(" * ", getName(), condition).get(0).forEach((k, v) -> {
            getDetails().put(k, v);
        });
        return getDetails();
    }
}
