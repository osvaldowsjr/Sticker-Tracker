package com.osvaldo.stickerstracker.data.model

import com.osvaldo.stickerstracker.R

enum class NationEnum {
    QAT,
    ECU,
    SEN,
    NED,
    ENG,
    IRN,
    USA,
    WAL,
    ARG,
    KSA,
    MEX,
    POL,
    FRA,
    AUS,
    DEN,
    TUN,
    ESP,
    CRC,
    GER,
    JPN,
    BEL,
    CAN,
    MAR,
    CRO,
    BRA,
    SRB,
    SUI,
    CMR,
    POR,
    GHA,
    URU,
    KOR,
    FWC,
}

fun NationEnum.getFlag(): Int {
    return when (this) {
        NationEnum.QAT -> R.drawable.qat
        NationEnum.ECU -> R.drawable.ecu
        NationEnum.SEN -> R.drawable.sen
        NationEnum.NED -> R.drawable.ned
        NationEnum.ENG -> R.drawable.eng
        NationEnum.IRN -> R.drawable.irn
        NationEnum.USA -> R.drawable.usa
        NationEnum.WAL -> R.drawable.wal
        NationEnum.ARG -> R.drawable.arg
        NationEnum.KSA -> R.drawable.ksa
        NationEnum.MEX -> R.drawable.mex
        NationEnum.POL -> R.drawable.pol
        NationEnum.FRA -> R.drawable.fra
        NationEnum.AUS -> R.drawable.aus
        NationEnum.DEN -> R.drawable.den
        NationEnum.TUN -> R.drawable.tun
        NationEnum.ESP -> R.drawable.esp
        NationEnum.CRC -> R.drawable.crc
        NationEnum.GER -> R.drawable.ger
        NationEnum.JPN -> R.drawable.jpn
        NationEnum.BEL -> R.drawable.bel
        NationEnum.CAN -> R.drawable.can
        NationEnum.MAR -> R.drawable.mar
        NationEnum.CRO -> R.drawable.cro
        NationEnum.BRA -> R.drawable.bra
        NationEnum.SRB -> R.drawable.srb
        NationEnum.SUI -> R.drawable.sui
        NationEnum.CMR -> R.drawable.cmr
        NationEnum.POR -> R.drawable.por
        NationEnum.GHA -> R.drawable.gha
        NationEnum.URU -> R.drawable.uru
        NationEnum.KOR -> R.drawable.kor
        NationEnum.FWC -> R.drawable.fwc
    }
}

fun NationEnum.getName(): String {
    return when (this) {
        NationEnum.QAT -> "Qatar"
        NationEnum.ECU -> "Ecuador"
        NationEnum.SEN -> "Senegal"
        NationEnum.NED -> "Netherlands"
        NationEnum.ENG -> "England"
        NationEnum.IRN -> "IR Iran"
        NationEnum.USA -> "USA"
        NationEnum.WAL -> "Wales"
        NationEnum.ARG -> "Argentina"
        NationEnum.KSA -> "Saudi Arabia"
        NationEnum.MEX -> "Mexico"
        NationEnum.POL -> "Poland"
        NationEnum.FRA -> "France"
        NationEnum.AUS -> "Australia"
        NationEnum.DEN -> "Denmark"
        NationEnum.TUN -> "Tunisia"
        NationEnum.ESP -> "Spain"
        NationEnum.CRC -> "Costa Rica"
        NationEnum.GER -> "Germany"
        NationEnum.JPN -> "Japan"
        NationEnum.BEL -> "Belgium"
        NationEnum.CAN -> "Canada"
        NationEnum.MAR -> "Morocco"
        NationEnum.CRO -> "Croatia"
        NationEnum.BRA -> "Brazil"
        NationEnum.SRB -> "Serbia"
        NationEnum.SUI -> "Switzerland"
        NationEnum.CMR -> "Cameroon"
        NationEnum.POR -> "Portugal"
        NationEnum.GHA -> "Ghana"
        NationEnum.URU -> "Uruguay"
        NationEnum.KOR -> "Korea Republic"
        NationEnum.FWC -> "Fifa World Cup"
    }
}

fun NationEnum.generateListOfPlayers(): List<Player> {
    val list = ArrayList<Player>()
    when (this) {
        NationEnum.FWC -> createFifaList(list)
        else -> createNationList(this, list)
    }
    return list
}

private fun createFifaList(list: ArrayList<Player>) {
    for (i in 0..29) {
        list.add(
            Player(
                number = "FWC $i"
            )
        )
    }
}

private fun createNationList(nationEnum: NationEnum, list: ArrayList<Player>) {
    for (i in 1..20) {
        if (i < 10) {
            list.add(
                Player(
                    number = "$nationEnum 0$i"
                )
            )
        } else {
            list.add(
                Player(
                    number = "$nationEnum $i"
                )
            )
        }

    }
}