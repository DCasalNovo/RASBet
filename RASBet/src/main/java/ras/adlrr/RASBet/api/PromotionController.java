package ras.adlrr.RASBet.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ras.adlrr.RASBet.dao.ClaimedPromoRepository;
import ras.adlrr.RASBet.dao.PromotionRepository;
import ras.adlrr.RASBet.model.Promotions.Promotion;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/promotions")
public class PromotionController {

    private final PromotionRepository promotionRepository;
    private final ClaimedPromoRepository couponRepository;

    @Autowired
    public PromotionController(PromotionRepository promotionRepository, ClaimedPromoRepository couponRepository) {
        this.promotionRepository = promotionRepository;
        this.couponRepository = couponRepository;
    }

    @PostMapping
    public void testToInsertSubclass(){
        //Promotion boostOdd = new BoostOddPromotion(1,20);
        //promotionRepository.save(boostOdd);
    }

    @PostMapping(path = "/coupon")
    public void testToInsertCoupon(){
        //Coupon coupon = new Coupon(1, 1, 1);
        //couponRepository.save(coupon);
    }

    @GetMapping(path = "/coupon")
    public void testToGetCoupon(){
        //Coupon coupon = couponRepository.findById(new Coupon.CouponID(1,1)).orElse(null);
        //return coupon;
    }

    @GetMapping
    public List<Promotion> getPromotionsTest(){
        //return promotionRepository.getPromotionsStartedBetweenIntervalOrdered(LocalDateTime.of(2022, 11, 18, 19, 50),
        //        LocalDateTime.of(2022, 11, 18, 19, 59), null/*Sort.by(Sort.Direction.DESC, "expiration_date")*/);
        return null;
    }
}
