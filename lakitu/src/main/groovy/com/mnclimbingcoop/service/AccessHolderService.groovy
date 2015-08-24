
package com.mnclimbingcoop.service

import com.amazonaws.services.sqs.model.SendMessageResult
import com.mnclimbingcoop.domain.AccessHolder
import com.mnclimbingcoop.domain.VertXRequest

import groovy.transform.CompileStatic

import javax.inject.Inject
import javax.inject.Named

@CompileStatic
@Named
class AccessHolderService {

    protected final CloudSyncService cloudSyncService
    protected final DoorStateService doorStateService

    @Inject
    AccessHolderService(CloudSyncService cloudSyncService, DoorStateService doorStateService) {
        this.cloudSyncService = cloudSyncService
        this.doorStateService = doorStateService
    }

    SendMessageResult setAccess(String door, AccessHolder accessHolder) {
        return cloudSyncService.sendSqsMessage(request(door, accessHolder))
    }

    List<SendMessageResult> setAccess(AccessHolder accessHolder) {
        List<SendMessageResult> results = []
        doorStateService.doorNames.each{ String door ->
            results << cloudSyncService.sendSqsMessage(request(door, accessHolder))
        }
        return results
    }

    //~ BEGIN PROTECTED METHODS ===============================================

    protected VertXRequest request(String door, AccessHolder accessHolder) {
        VertXRequest request = new VertXRequest().forDoor(door)
        request.accessHolder = accessHolder
        return request
    }

}
