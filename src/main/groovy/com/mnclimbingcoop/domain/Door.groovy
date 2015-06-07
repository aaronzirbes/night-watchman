package com.mnclimbingcoop.domain

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty

import java.time.LocalDateTime

class Door {

    @JacksonXmlProperty(isAttribute=true)
    String doorName

    @JacksonXmlProperty(isAttribute=true)
    Integer doorUnlockScheduleID

    @JacksonXmlProperty(isAttribute=true)
    Integer doorUnlockGuardTime

    @JacksonXmlProperty(isAttribute=true)
    State guardTimeFlag

    @JacksonXmlProperty(isAttribute=true)
    State tamperAlarmState

    @JacksonXmlProperty(isAttribute=true)
    State acAlarmState

    @JacksonXmlProperty(isAttribute=true)
    State batteryAlarmState

    @JacksonXmlProperty(isAttribute=true)
    State doorHeldAlarmState

    @JacksonXmlProperty(isAttribute=true)
    State doorForcedAlarmState

    @JacksonXmlProperty(isAttribute=true)
    State relayState

    @JacksonXmlProperty(isAttribute=true)
    State auxRelayState

    @JacksonXmlProperty(isAttribute=true)
    LocalDateTime currentDateTime

    @JacksonXmlProperty(isAttribute=true)
    State relayAlertState

}
