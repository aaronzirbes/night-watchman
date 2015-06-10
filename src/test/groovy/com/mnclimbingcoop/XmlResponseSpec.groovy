package com.mnclimbingcoop

import com.mnclimbingcoop.domain.EventMessage
import com.mnclimbingcoop.domain.EventMessages
import com.mnclimbingcoop.domain.State
import com.mnclimbingcoop.domain.VertXMessage

import java.time.LocalDateTime

class XmlResponseSpec extends XmlSpecification {

    void 'xml marshalling from read door'() {
        given:
        String expected = xmlFromFixture('response/read-door1')

        when:
        VertXMessage parsed = objectMapper.readValue(expected, VertXMessage)

        then:
        parsed.doors.action == 'RD'
        parsed.doors.doors[0].doorName == 'HID Edge Solo'
        parsed.doors.doors[0].relayState == State.set
    }

    void 'xml marhalling from read log'() {
        given:
        String expected = xmlFromFixture('response/read-log1')
        VertXMessage message = new VertXMessage(
            eventMessages: new EventMessages(
                action: 'RL',
                historyRecordMarker: 2232,
                historyTimestamp:  1428535349,
                recordCount: 1,
                moreRecords: false,
                eventMessages: [
                    new EventMessage(
                        cardholderID: 2,
                        forename: 'Aaron',
                        surname: 'Zirbes',
                        eventType: 2020,
                        timestamp: LocalDateTime.parse('2015-04-08T20:02:37')
                    )
                ]

            )
        )

        when:
        String xml = objectMapper.writeValueAsString(message)

        and:
        VertXMessage parsed = objectMapper.readValue(expected, VertXMessage)
        String back = objectMapper.writeValueAsString(parsed)

        then:
        xml == back
    }
}