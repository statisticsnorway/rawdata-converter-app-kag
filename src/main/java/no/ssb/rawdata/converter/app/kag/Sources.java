package no.ssb.rawdata.converter.app.kag;

import no.ssb.rawdata.converter.app.kag.schema.CsvSchemaBuilder;

import java.util.List;
import java.util.Map;

public class Sources {
    public static final String BEFOLKNING = "befolkning"; // ssb
    public static final String FAGKODE = "fagkode"; // udir
    public static final String GSI = "gsi"; // udir
    public static final String KARAKTER = "karakter"; // udir
    public static final String NASJONALE_PROVER = "nasjonaleProver"; // udir
    public static final String NUDB = "nudb"; // ssb
    public static final String NUSKAT = "nuskat"; // ssb
    public static final String OMKODINGSKATALOG = "omkodingskatalog"; // ssb
    public static final String RESULTAT = "resultat"; // udir
    public static final String SKOLEKATALOG = "skolekatalog"; // ssb
    public static final String STATISTIKK = "statistikk"; // udir

    private final static Map<String, SourceInfo> sourceMap = Map.ofEntries(
      sourceWithFields(BEFOLKNING, List.of(
        "Snr", "Fnr", "Foedselsaar", "Alder", "Kjoenn", "Status", "StatusDnr", "Statborg", "Kommnr", "MorSnr", "MorFnr", "FarSnr", "FarFnr", "Fodeland", "Landbak", "Invkat", "Fodato", "Fraland", "Fralanddato", "Botid"
      )),

      sourceWithFields(FAGKODE, List.of(
        "Fagkode", "Fagnavn", "Harstandpunkt", "Eksamensform", "Forsteeksamen", "Sisteeksamen", "Forsteundervisning", "Sisteundervisning"
      )),

      sourceWithFields(GSI, List.of(
        "Orgnrbed", "Navn", "Kommnr"
      )),

      sourceWithFields(KARAKTER, List.of(
        "Filid", "Radid", "Radnr", "Fødselsnummer", "Skoleår", "Skolenummer", "Programområdekode", "Fagkode", "Fagstatus", "KarakterHalvårsvurdering1", "KarakterHalvårsvurdering2", "KarakterStandpunkt", "KarakterSkriftligEksamen", "KarakterMuntligEksamen", "KarakterAnnen", "Skoleår2", "Skolenummer2", "ErLinjaAktiv", "Elevtimer", "ForrigeFagstatus", "FagmerknadKode", "Karakterstatus"
      )),

      sourceWithFields(NASJONALE_PROVER, List.of(
        "SnrNudb", "Aargang", "Orgnr", "Orgnrbed", "Skolekom", "Skolefylke", "Skoletype", "AvgiverskoleOrgnr", "AvgiverskoleOrgnrbed", "Deltattstatus", "Prove", "Mestringsnivaa", "Poeng", "Oppgavesett", "Andelpoeng", "Gruppenummer", "Skalapoeng"
      )),

      sourceWithFields(NUDB, List.of(
        "SnrNudb", "Nus2000Far16", "Nus2000Mor16", "Sosbak"
      )),

      sourceWithFields(NUSKAT, List.of(
        "Nus2000", "Tekstl", "Kodetype", "Lkltrinn", "Hkltrinn", "Varighet", "FagskPoeng", "Fagskoleutd", "Studp", "Studretn", "Kurstrin", "Komp", "Uhgruppe", "Gradmerk", "Hist", "I97isced", "I97destn", "I97orien", "I97varig", "I97grads", "I2011P", "I2011A", "I2013F"
      )),

      sourceWithFields(OMKODINGSKATALOG, List.of(
        "Fskolenr", "Skolekom", "Skolenavn", "Orgnr", "Orgnrbed", "Naering", "Eierf", "FeilOrgnr", "AntallElever", "KoblKilde"
      )),

      sourceWithFields(RESULTAT, List.of(
        "Filid", "Radid", "Radnr", "Fødselsnummer", "Skoleår", "Skolenummer", "Programområdekode", "Elevnavn", "Skolenavn", "Organisasjonsnummer", "Startdato", "Avbruddsdato", "Fullførtkode", "RettstypeVedInntak", "Elevstatus", "GodkjentRealkompetansevurderingDato", "Karakterpoeng", "Kursprosent", "AntallDagerFravær", "AntallEnkelttimerFravær", "AntallFagPåElevkurset", "AntallStrykPåElevkurset", "Bevistype", "ForrigeElevstatus", "MålformNorskHovedmål", "RettstypeIHjemfylket", "Påbyggingsrett"
      )),

      sourceWithFields(SKOLEKATALOG, List.of(
        "Orgnr", "Orgnrforetak", "Orgnrbed", "Nace1Sn07", "Nace2Sn07", "Nace3Sn07", "Skole1", "Skole2", "Skole3", "Eierf", "Rectype", "Bnr", "Eiernavn", "NavnInst", "Skolekom", "RegType", "Mdelr", "DelregMerke", "OrgForm", "GmlOrgForm", "Status", "Skoletype", "OrgnrEier"
      )),

      sourceWithFields(STATISTIKK, List.of(
        "Fnr", "SnrNudb", "Skolear", "Fskolenr", "Skolenavn", "Orgnr", "Orgnrbed", "FvSnavn", "Skolekom", "Eierf", "Nus2000Far", "Sosbak", "Nus2000Mor", "Nus2000", "Kjoenn", "Statborg", "Kommnr", "Fodeland", "Landbak", "Invkat", "Alder", "Grunnskolepoeng", "Studretn", "Kurstrin", "Ktrinn", "Kltrinn", "Naering", "Utd", "Kilde", "Komp", "FvElevstatus", "Koblet", "Trinnst", "MedPub3", "MedPub2", "Insttype", "MedPubKar", "MedPubRes", "Stpeng0012", "Stpeng0013", "Stpeng0029", "Stpfsp0042", "Stpfsp0132", "Stpfsp0162", "Stpkhv0010", "Stpkro0020", "Stpmat0010", "Stpmhe0010", "Stpmus0010", "Stpnat0010", "Stpnor0068", "Stpnor0214", "Stpnor0215", "Stpnor0216", "Stprle0030", "Stpsaf0010", "Skreng0012", "Muneng0013", "Munfsp0132", "Skrmat0010", "Munmat0011", "Munnat0010", "Skrnor0214", "Munnor0216", "Munsaf0010", "MnivaaEng05", "MnivaaLes05", "MnivaaReg05", "MnivaaEng08", "MnivaaLes08", "MnivaaReg0"
      ))

    );

    private static Map.Entry<String, SourceInfo> sourceWithFields(String sourceName, List<String> fields) {
        return Map.entry(sourceName, SourceInfo.builder()
          .sourceName(sourceName)
          .csvSchemaInfo(new CsvSchemaBuilder()
            .targetFieldname(sourceName)
            .fields(fields))
          .build());
    }

    public static SourceInfo of(String sourceName) {
        if (! sourceMap.containsKey(sourceName)) {
            throw new IllegalArgumentException("Unknown KAG source: " + sourceName);
        }

        return sourceMap.get(sourceName);
    }
}
