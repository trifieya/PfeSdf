package tn.sdf.pfesdf.entities;

public enum Situation {
    CELIBATAIRE("Célibataire"),
    MARIE("Marié(e)"),
    DIVORCE("Divorcé(e)"),
    VEUF("Veuf(ve)"),
    ADOPTE("Adopté(e)");

    private final String displayName;

    Situation(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }
}

