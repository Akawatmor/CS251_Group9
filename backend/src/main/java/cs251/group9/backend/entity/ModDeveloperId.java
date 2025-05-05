package cs251.group9.backend.entity;

import java.io.Serializable;

import jakarta.persistence.*;


@Embeddable
public class ModDeveloperId implements Serializable {
    private Long modID;
    private Long devID;
    // equals and hashCode
}
