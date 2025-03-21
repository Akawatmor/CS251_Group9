package cs251.group9.backend;

import java.time.LocalDateTime;
import jakarta.persistence.*;

@Entity
public class GameTable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long gameID;

    private int gPrice;
    private String gDesc;
    private LocalDateTime gPublishDate;
    private int rating;
}
