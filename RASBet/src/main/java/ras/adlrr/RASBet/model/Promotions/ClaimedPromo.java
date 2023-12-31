package ras.adlrr.RASBet.model.Promotions;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ras.adlrr.RASBet.model.Gambler;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Table(name = "claimed_promos")
public class ClaimedPromo {
    @Embeddable
    @NoArgsConstructor
    @AllArgsConstructor
    @Getter
    @Setter
    public static class ClaimedPromoID implements Serializable {
        private int promotionId;
        private int gamblerId;

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            ClaimedPromoID that = (ClaimedPromoID) o;
            return promotionId == that.promotionId && gamblerId == that.gamblerId;
        }

        @Override
        public int hashCode() {
            return Objects.hash(promotionId, gamblerId);
        }

        @Override
        public String toString() {
            return "ClaimedPromoID{" +
                    "promotionId=" + promotionId +
                    ", gamblerId=" + gamblerId +
                    '}';
        }
    }

    @EmbeddedId
    private ClaimedPromoID id;

    @ManyToOne(optional = false)
    @JoinColumn(name = "promotion_id", updatable = false, nullable = false)
    @MapsId("promotionId")
    private Promotion promotion;

    @ManyToOne(optional = false)
    @JoinColumn(name = "gambler_id", updatable = false, nullable = false)
    @MapsId("gamblerId")
    private Gambler gambler;

    private int nr_uses_left; //Number of times left to use the coupon

    public ClaimedPromo(int promotionId, int gamblerId, int nr_uses_left) {
        this.id = new ClaimedPromoID(promotionId, gamblerId);
        this.promotion = new Promotion(); promotion.setId(promotionId);
        this.gambler = new Gambler(); gambler.setId(gamblerId);
        this.nr_uses_left = nr_uses_left;
    }

    @Override
    public String toString() {
        return "ClaimedPromo{" +
                "id=" + id +
                ", promotion=" + promotion +
                ", gambler=" + gambler +
                ", nr_uses_left=" + nr_uses_left +
                '}';
    }
}
