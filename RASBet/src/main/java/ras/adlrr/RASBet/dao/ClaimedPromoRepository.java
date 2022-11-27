package ras.adlrr.RASBet.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import ras.adlrr.RASBet.model.Promotions.ClaimedPromo;

import java.util.List;

public interface ClaimedPromoRepository extends JpaRepository<ClaimedPromo, ClaimedPromo.ClaimedPromoID> {
    @Query(value = "SELECT cp.promotion_id FROM claimed_promos AS cp WHERE cp.gambler_id = :gambler_id ",nativeQuery = true)
    List<Integer> getAllClaimedPromotionsIdsByGamblerId(int gambler_id);

    @Query(value = "SELECT cp.promotion_id FROM claimed_promos AS cp WHERE cp.gambler_id = :gambler_id AND cp.nr_uses_left > 0",nativeQuery = true)
    List<Integer> getAllClaimedPromotionsIdsWithUsesLeftByGamblerId(int gambler_id);

    @Modifying
    @Query(value = "DELETE FROM claimed_promos cp WHERE cp.promotion_id = :promotion_id", nativeQuery = true)
    void deleteAllByPromotionId(@Param("promotion_id") int promotion_id);
}
