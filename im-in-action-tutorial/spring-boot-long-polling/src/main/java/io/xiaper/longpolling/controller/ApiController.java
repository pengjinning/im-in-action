package io.xiaper.longpolling.controller;

import io.xiaper.longpolling.util.LongPollingEventSimulator;
import io.xiaper.longpolling.util.LongPollingSession;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.async.DeferredResult;

@Slf4j
@RestController
public class ApiController {

    @Autowired
    LongPollingEventSimulator simulator;

    @RequestMapping("/register/{dossierId}")
    @ResponseBody
    public DeferredResult<String> registerClient(@PathVariable("dossierId") final long dossierId) {
        log.info("Registering client for dossier id: " + dossierId);
        final DeferredResult<String> deferredResult = new DeferredResult<>();
        // Add paused http requests to event queue
        simulator.getPollingQueue().add(new LongPollingSession(dossierId, deferredResult));
        return deferredResult;
    }

    @RequestMapping("/simulate/{dossierId}")
    @ResponseBody
    public String simulateEvent(@PathVariable("dossierId") final long dossierId) {
        log.info("Simulating event for dossier id: " + dossierId);
        simulator.simulateIncomingNotification(dossierId);
        return "Simulating event for dossier Id: " + dossierId;
    }
}


