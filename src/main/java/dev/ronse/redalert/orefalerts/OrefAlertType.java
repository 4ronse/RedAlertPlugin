package dev.ronse.redalert.orefalerts;

public enum OrefAlertType {
    DEFAULT(Integer.MAX_VALUE),
    MISSILE_ALERT(1),
    UAV(2),
    NON_CONVENTIONAL(3),
    WARNING(4),
    MEMORIAL_DAY_1(5),
    MEMORIAL_DAY_2(6),
    EARTHQUAKE_ALERT_1(7),
    EARTHQUAKE_ALERT_2(8),
    CBRNE(9),
    TERROR_ATTACK(10),
    TSUNAMI(11),
    HAZMAT(12),
    UPDATE(13),
    FLASH(14),
    MISSILE_ALERT_DRILL(15),
    UAV_DRILL(16),
    NON_CONVENTIONAL_DRILL(17),
    WARNING_DRILL(18),
    MEMORIAL_DAY_1_DRILL(19),
    MEMORIAL_DAY_2_DRILL(20),
    EARTHQUAKE_1_DRILL(21),
    EARTHQUAKE_2_DRILL(22),
    CBRNE_DRILL(24),
    TSUNAMI_DRILL(25),
    HAZMAT_DRILL(26),
    UPDATE_DRILL(27),
    FLASH_DRILL(28);

    private final int id;

    OrefAlertType(int id) {
        this.id = id;
    }

    public int getId() { return this.id; }

    public static OrefAlertType get(int id) {
        for(var type : OrefAlertType.values()) if(type.getId() == id) return type;
        return null;
    }
}
