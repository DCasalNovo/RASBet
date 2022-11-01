package ras.adlrr.RASBet.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIncludeProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Game {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "game_id")
    private Set<Participant> participants;

    @ManyToOne(optional = false)
    @JoinColumn(updatable = false, nullable = false)
    @JsonIncludeProperties({"id"})
    private Sport sport;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "game", fetch = FetchType.LAZY)
    @JsonIgnore
    private List<GameChoice> gameChoices;

    private String extID;

    private LocalDateTime date;

    private int state;

    public static final int SUSPENDED = 1;
    public static final int CLOSED = 2;
    public static final int OPEN = 3;

    public Game(@JsonProperty("id") int id, @JsonProperty("extID") String extID, @JsonProperty("date") LocalDateTime date,
                @JsonProperty("state") int state, @JsonProperty("sport_id") int sport_id,
                @JsonProperty("participants") Set<Participant> participants){
        this.id = id;
        this.extID = extID;
        this.date = date;
        this.state = state;
        this.sport = new Sport(); sport.setId(sport_id);
        this.participants = participants;
    }

    public Game(int id, String extID, LocalDateTime date,
                int state, int sport_id, Set<Participant> participants,
                List<GameChoice> gameChoices){
        this.id = id;
        this.extID = extID;
        this.date = date;
        this.state = state;
        this.sport = new Sport(); sport.setId(sport_id);
        this.participants = participants;
        this.gameChoices = gameChoices;
    }

    // ------ Additional Methods ------

    public void addParticipantToGame(Participant p){
        if(participants == null)
            participants = new HashSet<>();
        this.participants.add(p);
    }
}
