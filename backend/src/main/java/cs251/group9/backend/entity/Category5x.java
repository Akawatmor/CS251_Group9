package cs251.group9.backend.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "category")
public class Category5x {
    @Id
    @Column(length = 10)
    private Long cid;
    
    @Column(nullable = false)
    private String cName;
    
    // Getters and setters
    public Long getCid() {
        return cid;
    }
    
    public void setCid(Long cid) {
        this.cid = cid;
    }
    
    public String getcName() {
        return cName;
    }
    
    public void setcName(String cName) {
        this.cName = cName;
    }
}
