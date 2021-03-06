package com.mnclimbingcoop.service

import com.mnclimbingcoop.domain.Door
import com.mnclimbingcoop.domain.Doors
import com.mnclimbingcoop.domain.EdgeSoloState
import com.mnclimbingcoop.domain.VertXRequest
import com.mnclimbingcoop.domain.VertXResponse
import com.mnclimbingcoop.request.DoorRequest

import groovy.transform.CompileStatic
import groovy.util.logging.Slf4j

import javax.inject.Inject
import javax.inject.Named

@CompileStatic
@Named
@Slf4j
class DoorService {

    // TODO:  Support "Unlock Until" command states that ensure door is unlocked

    protected final HidService hidService
    protected final HealthService healthService

    @Inject
    DoorService(HealthService healthService, HidService hidService) {
        this.hidService = hidService
        this.healthService = healthService
    }

    Map<String, Doors> list() {
        VertXRequest request = new DoorRequest().list()
        return hidService.getAll(request) { String name, VertXResponse resp ->
            if (resp.doors) {
                updateState(name, resp.doors)
            }
            return [ name, resp.doors ]
        }
    }

    Doors list(String name) {
        VertXRequest request = new DoorRequest().list()
        Doors doors = hidService.get(name, request)?.doors
        if (doors) {
            updateState(name, doors)
        }
        return doors
    }

    void updateState(String name, Doors doors) {
        healthService.checkedDoor(name)
        // Only 1 door to support
        Door current = doors.doors[0]
        Door last = hidService.hidStates[name].doors[0]
        if (current.changed(last)) {
            log.info "Door state changed, sending notification."
            sync(name, current)
            healthService.updatedDoor(name)
        }
        hidService.hidStates[name].doors.addAll(current)
    }

    void sync(String name, Door door) {
        EdgeSoloState state = new EdgeSoloState(doorName: name)
        state.doors << door
        hidService.sync(state)
    }

    void unlockDoor(String name) {
        VertXRequest request = new DoorRequest().unlock()
        hidService.get(name, request)
    }

    void lockDoor(String name) {
        VertXRequest request = new DoorRequest().lock()
        hidService.get(name, request)
    }

    void openDoor(String name) {
        VertXRequest request = new DoorRequest().grantAccess()
        hidService.get(name, request)
    }

}
