package ras.adlrr.RASBet.api;

import java.util.AbstractMap;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import ras.adlrr.RASBet.api.auxiliar.AuxiliarMethods;
import ras.adlrr.RASBet.model.Coin;
import ras.adlrr.RASBet.model.Wallet;
import ras.adlrr.RASBet.service.WalletService;

@RequestMapping("/api/wallets")
@RestController
public class WalletController {
    private final WalletService walletService;

    @Autowired
    public WalletController(WalletService walletService){
        this.walletService = walletService;
    }

    // ---------- Wallet Methods ----------

    @PostMapping("/wallet")
    public ResponseEntity<Wallet> createWallet(@RequestBody Wallet wallet){
        try {
            return new ResponseEntity<>(walletService.createWallet(wallet),HttpStatus.OK);
        }
        catch (Exception e){
            return AuxiliarMethods.createClassBadRequest(e.getMessage(), Wallet.class);
        }
    }

    @GetMapping(path = "/wallet/{id}")
    public ResponseEntity<Wallet> getWallet(@PathVariable("id") int id){
        return ResponseEntity.ok().body(walletService.getWallet(id));
    }

    @DeleteMapping(path = "/wallet/{id}")
    public ResponseEntity removeWallet(@PathVariable int id){
        try {
            walletService.removeWallet(id);
            return new ResponseEntity(HttpStatus.OK);
        }
        catch (Exception e){
            return AuxiliarMethods.createBadRequest(e.getMessage());
        }
    }

    @GetMapping("/wallet")
    public ResponseEntity<List<Wallet>> getListOfWallets(){
        return ResponseEntity.ok().body(walletService.getListOfWallets());
    }

    @PutMapping("/wallet/deposit")
    public ResponseEntity<Wallet> deposit(@RequestParam int wallet_id, @RequestParam float value){
        try {
            return ResponseEntity.ok().body(walletService.deposit(wallet_id, value));
        }catch (Exception e){
            return AuxiliarMethods.createClassBadRequest(e.getMessage(), Wallet.class);
        }
    }

    @PutMapping("/wallet/withdraw")
    public ResponseEntity withdraw(@RequestParam int wallet_id, @RequestParam float value){
        try {
            return ResponseEntity.ok().body(walletService.withdraw(wallet_id, value));
        }catch (Exception e){
            return AuxiliarMethods.createClassBadRequest(e.getMessage(), Wallet.class);
        }
    }

    // ---------- Coin Methods ----------

    @PostMapping("/coin")
    public ResponseEntity<Coin> addCoin(@RequestBody Coin coin){
        try{ return ResponseEntity.ok().body(walletService.addCoin(coin)); }
        catch (Exception e) {
            return AuxiliarMethods.createClassBadRequest(e.getMessage(), Coin.class);
        }
    }

    @GetMapping(path = "/coin/{id}")
    public ResponseEntity<Coin> getCoin(@PathVariable("id") int id){
        return ResponseEntity.ok().body(walletService.getCoin(id));
    }

    @DeleteMapping(path = "/coin/{id}")
    public ResponseEntity removeCoin(@PathVariable int id){
        try {
            walletService.removeCoin(id);
            return new ResponseEntity(HttpStatus.OK); }
        catch (Exception e){
            return AuxiliarMethods.createBadRequest(e.getMessage());
        }
    }

    @GetMapping("/coin")
    public ResponseEntity<List<Coin>> getListOfCoins(){
        return ResponseEntity.ok().body(walletService.getListOfCoins());
    }
}