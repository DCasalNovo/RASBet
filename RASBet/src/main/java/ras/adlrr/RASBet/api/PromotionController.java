package ras.adlrr.RASBet.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ras.adlrr.RASBet.api.auxiliar.ResponseEntityBadRequest;
import ras.adlrr.RASBet.dao.ClaimedPromoRepository;
import ras.adlrr.RASBet.dao.PromotionRepository;
import ras.adlrr.RASBet.model.Promotions.ClaimedPromo;
import ras.adlrr.RASBet.model.Promotions.Promotion;
import ras.adlrr.RASBet.service.PromotionServices.ClientPromotionService;
import ras.adlrr.RASBet.service.PromotionServices.PromotionService;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/promotions")
@CrossOrigin
public class PromotionController {

    private final PromotionService promotionService;
    private final ClientPromotionService clientPromotionService;

    @Autowired
    public PromotionController(PromotionService promotionService, ClientPromotionService clientPromotionService) {
        this.promotionService = promotionService;
        this.clientPromotionService = clientPromotionService;
    }

    // ------------- Promotion Methods ------------- //

    @PostMapping
    public ResponseEntity<Promotion> createPromotion(@RequestBody Promotion promotion){
        try {
            return ResponseEntity.ok().body(promotionService.createPromotion(promotion));
        } catch (Exception e){
            return new ResponseEntityBadRequest<Promotion>().createBadRequest(e.getMessage());
        }
    }

    @DeleteMapping(path = "/{id}")
    public ResponseEntity removePromotion(@PathVariable("id") int promotion_id){
        try {
            promotionService.removePromotion(promotion_id);
            return new ResponseEntity(HttpStatus.OK);
        } catch (Exception e){
            return new ResponseEntityBadRequest().createBadRequest(e.getMessage());
        }
    }

    @GetMapping(path = "/id/{id}")
    public ResponseEntity<Promotion> getPromotionById(@PathVariable("id") int promotion_id){
        try {
            return ResponseEntity.ok().body(promotionService.getPromotionById(promotion_id));
        } catch (Exception e){
            return new ResponseEntityBadRequest<Promotion>().createBadRequest(e.getMessage());
        }
    }

    @GetMapping(path = "/id/{id}/exists")
    public ResponseEntity<Boolean> existsPromotionById(@PathVariable("id") int promotion_id){
        try {
            return ResponseEntity.ok().body(promotionService.existsPromotionById(promotion_id));
        } catch (Exception e){
            return new ResponseEntityBadRequest<Boolean>().createBadRequest(e.getMessage());
        }
    }

    @GetMapping(path = "/coupon/{coupon}")
    public ResponseEntity<Promotion> getPromotionByCoupon(@PathVariable("coupon") String coupon){
        try {
            return ResponseEntity.ok().body(promotionService.getPromotionByCoupon(coupon));
        } catch (Exception e){
            return new ResponseEntityBadRequest<Promotion>().createBadRequest(e.getMessage());
        }
    }


    @GetMapping(path = "/coupon/{coupon}/exists")
    public ResponseEntity<Boolean> existsPromotionByCoupon(@PathVariable("coupon") String coupon){
        try {
            return ResponseEntity.ok().body(promotionService.existsPromotionByCoupon(coupon));
        } catch (Exception e){
            return new ResponseEntityBadRequest<Boolean>().createBadRequest(e.getMessage());
        }
    }

    @GetMapping
    public ResponseEntity<List<Promotion>> getAllPromotions(){
        try {
            return ResponseEntity.ok().body(promotionService.getAllPromotions());
        } catch (Exception e){
            return new ResponseEntityBadRequest<List<Promotion>>().createBadRequest(e.getMessage());
        }
    }

    @GetMapping("/ordered")
    public ResponseEntity<List<Promotion>> getAllPromotionsOrderedByDate(@RequestParam("which_date") String whichDate,
                                                                         @RequestParam("order") String order,
                                                                         @RequestParam(value = "start_date", required = false) LocalDateTime startDate,
                                                                         @RequestParam(value = "end_date", required = false) LocalDateTime endDate){
        try {
            if(whichDate.equals("begin"))
                whichDate = "begin_date";
            else if(whichDate.equals("end"))
                whichDate = "expiration_date";
            else
                throw new Exception("Parameter 'which_date' must be 'begin' or 'end'");

            Sort.Direction direction;
            if (order.equals("ASC"))
                direction = Sort.Direction.ASC;
            else if (order.equals("DESC"))
                direction = Sort.Direction.DESC;
            else
                throw new Exception("Parameter 'direction' must be 'ASC' or 'DESC'");

            List<Promotion> promotions;

            if(startDate != null && endDate != null)
                promotions = promotionService.getPromotionsBetweenDatesOrderedByDate(whichDate, startDate, endDate, direction);
            else if(startDate == null && endDate == null)
                promotions = promotionService.getAllPromotionsOrderedByDate(whichDate, direction);
            else
                throw new Exception("Either 'start_date' and 'end_date' are mentioned, or non of them are.");

            return ResponseEntity.ok().body(promotions);

        } catch (Exception e){
            return new ResponseEntityBadRequest<List<Promotion>>().createBadRequest(e.getMessage());
        }
    }


    // ------------- Gambler Promotion Methods ------------- //

    @PostMapping(path = "/gambler/signUp")
    public ResponseEntity<ClaimedPromo> signUpToPromotion(@RequestParam("gambler_id") int gambler_id, @RequestParam("promotion_id") int promotion_id){
        try {
            return ResponseEntity.ok().body(clientPromotionService.signUpToPromotion(gambler_id, promotion_id));
        } catch (Exception e){
            return new ResponseEntityBadRequest<ClaimedPromo>().createBadRequest(e.getMessage());
        }
    }

    @GetMapping(path = "/gambler/claimedPromos")
    public ResponseEntity<List<Promotion>> getGamblerListOfClaimedPromotions(@RequestParam("gambler_id") int gambler_id, @RequestParam(value = "uses_left", required = false) Boolean uses_left){
        try {
            List<Promotion> promotions;

            if(uses_left != null && uses_left)
                promotions = clientPromotionService.getGamblerListOfClaimedPromotionsWithUsesLeft(gambler_id);
            else
                promotions = clientPromotionService.getGamblerListOfClaimedPromotions(gambler_id);

            return ResponseEntity.ok().body(promotions);

        } catch (Exception e){
            return new ResponseEntityBadRequest<List<Promotion>>().createBadRequest(e.getMessage());
        }
    }

    @PutMapping(path = "/gambler/claimPromo")
    public ResponseEntity claimPromotion(@RequestParam("gambler_id") int gambler_id, @RequestParam("promotion_id") int promotion_id) {
        try {
            clientPromotionService.claimPromotion(gambler_id, promotion_id);
            return new ResponseEntity(HttpStatus.OK);
        } catch (Exception e){
            return new ResponseEntityBadRequest().createBadRequest(e.getMessage());
        }
    }

    @PutMapping(path = "/gambler/claimPromoWithCoupon")
    public ResponseEntity claimPromotionWithCoupon(@RequestParam("gambler_id") int gambler_id, @RequestParam("coupon") String coupon) {
        try {
            clientPromotionService.claimPromotionWithCoupon(gambler_id, coupon);
            return new ResponseEntity(HttpStatus.OK);
        } catch (Exception e){
            return new ResponseEntityBadRequest().createBadRequest(e.getMessage());
        }
    }
}
