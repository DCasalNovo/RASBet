package ras.adlrr.RASBet.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;
import ras.adlrr.RASBet.model.ClientNotifier;
import ras.adlrr.RASBet.model.IClientNotifier;
import ras.adlrr.RASBet.model.IGameSubject;

import java.awt.*;
import java.io.IOException;

@RestController
@CrossOrigin
@RequestMapping("/sse")
public class SSEController {
    private final IGameSubject gameSubject;

    @Autowired
    public SSEController(IGameSubject gameSubject) {
        this.gameSubject = gameSubject;
    }

    @GetMapping(value = "/subscribe", consumes = MediaType.ALL_VALUE)
    public SseEmitter subscribe(@RequestParam int gambler_id){
        SseEmitter sseEmitter = new SseEmitter(8 * 60 * 60 * 1000L); //Timeout of 8 hours
        try{
            sseEmitter.send(SseEmitter.event().name("INIT"));
        }catch (IOException e){
            e.printStackTrace();
        }

        IClientNotifier clientNotifier = new ClientNotifier(gambler_id, sseEmitter, gameSubject);

        sseEmitter.onCompletion(clientNotifier::close);
        sseEmitter.onTimeout(clientNotifier::close);
        sseEmitter.onError(e -> clientNotifier.close());

        return sseEmitter;
    }
}
