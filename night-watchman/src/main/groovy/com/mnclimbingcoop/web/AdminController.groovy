package com.mnclimbingcoop.web

import com.mnclimbingcoop.domain.Cardholder
import com.mnclimbingcoop.domain.Credential
import com.mnclimbingcoop.domain.EdgeSoloState
import com.mnclimbingcoop.service.HidService

import groovy.util.logging.Slf4j

import javax.inject.Inject

import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestMethod
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.bind.annotation.RequestBody

@RestController
@Slf4j
class AdminController {

    protected final HidService hidService

    final Boolean enabled = true

    @Inject
    AdminController(HidService hidService) {
        this.hidService = hidService
    }

    @RequestMapping(value = '/credentials', method = RequestMethod.GET, produces = 'application/json')
    List<EdgeSoloState> getCredentials() {
        if (!enabled) { return null }
        return hidService.hidStates.collectEntries{ String name, EdgeSoloState state ->
            new EdgeSoloState(doorName: name, credentials: state.credentials)
        }
    }

    @RequestMapping(value = '/cardholders', method = RequestMethod.GET, produces = 'application/json')
    List<EdgeSoloState> getCardholders() {
        if (!enabled) { return null }
        return hidService.hidStates.collectEntries{ String name, EdgeSoloState state ->
            new EdgeSoloState(doorName: name, cardholders: state.cardholders)
        }
    }

    /** will seed cardholders with a predefined list */
    @RequestMapping(value = '/cardholders',
                    method = RequestMethod.POST,
                    produces = 'application/json')
    Integer createCardholders(@RequestBody Map<String, List<Cardholder>> allCardholders) {
        if (!enabled) { return null }
        log.info "incoming doors: ${allCardholders.size()}"

        Integer added = 0
        allCardholders.each{ String name, List<Cardholder> cardholders ->
            log.info "reading cardholders for '${name}'"
            EdgeSoloState state = hidService.hidStates[name]
            if (state) {
                cardholders.each{ Cardholder cardholder ->
                    state.cardholders << cardholder
                    added++
                }
            } else {
                log.warn "State not found for door '${name}'"
            }
        }
        return added
    }

    @RequestMapping(value = '/state', method = RequestMethod.GET, produces = 'application/json')
    Map<String, EdgeSoloState> getState() {
        if (!enabled) { return null }
        return hidService.hidStates
    }

}

