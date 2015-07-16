package com.mnclimbingcoop.domain

class CardLookup {

    /** From manage.mnclimbingcoop.com */
    Integer mnccMemberId

    LocalDate memberSince

    LocalDate waiverExpires

    /** From HID Doors */
    Integer cardholderID

    String forename
    String middleName
    String surname

    String notes

    List<Card> cards

    static class Card {

        String rawCardNumber
        LocalDateTime endtime

    }
}
