package dev.ronse.redalert.orefalerts;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.util.*;

public class OrefAlert {
    private final long id;
    private final OrefAlertType type;
    private final Set<String> affectedAreas;
    private final String desc;

    public OrefAlert(JsonObject jsonObject) {
        var data = jsonObject.get("data").getAsJsonArray();

        this.id = jsonObject.get("id").getAsLong();
        this.type = OrefAlertType.get(jsonObject.get("cat").getAsInt());
        this.affectedAreas = new HashSet<>();
        this.desc = jsonObject.get("desc").getAsString();

        data.forEach((e) -> affectedAreas.add(e.getAsString()));
    }

    public OrefAlert(long id, OrefAlertType type, Set<String> affectedAreas, String desc) {
        this.id = id;
        this.type = type;
        this.affectedAreas = affectedAreas;
        this.desc = desc;
    }

    public long getId() { return this.id; }
    public OrefAlertType alertType() { return this.type; }
    public String description() { return this.desc; }
    public Set<String> getAffectedAreas() { return new HashSet<>(this.affectedAreas); }

    public Set<String> getAffectedAreas(Set<String> list) {
        list.clear();
        list.addAll(this.affectedAreas);

        return list;
    }

    public boolean isUpdated(OrefAlert other) {
        return !this.affectedAreas.equals(other.affectedAreas);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        OrefAlert orefAlert = (OrefAlert) o;
        return (
                id == orefAlert.id &&
                type == orefAlert.type &&
                Objects.equals(affectedAreas, orefAlert.affectedAreas) &&
                Objects.equals(desc, orefAlert.desc)
        );
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, type, affectedAreas, desc);
    }

    @Override
    public String toString() {
        return "OrefAlert{" +
                "type=" + type +
                ", affectedAreas=" + affectedAreas +
                ", desc='" + desc + '\'' +
                '}';
    }
}
