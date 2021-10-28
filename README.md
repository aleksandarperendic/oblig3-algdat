# Obligatorisk oppgave 3 i Algoritmer og Datastrukturer

Denne oppgaven er en innlevering i Algoritmer og Datastrukturer. 
Oppgaven er levert av følgende student:
* Aleksandar Perendic, S351906, s351906@oslomet.no


# Oppgavebeskrivelse

**I oppgave 1** så gikk jeg frem ved å kopiere leggInn fra kompendiet, og så adde "q" på riktig plass.

**Oppgave 2** løste jeg ved hjelp av kompendiet og comparator. Definerte en int som blir enten -1, 0 eller 1 når den går gjennom alle tall. Oppdaterte antall når int cmp var lik 0, altså når den fant samme tall i treet vi søkte med antall. 

**I oppgave 3** gikk jeg frem ved å bruke teori fra kompendiet, og så forsøke å kode det på java. Det som hjalp mest var jo da "Den siste i preorden er lik den første i speilvendt postorden.". Utfordring var å skjønne hvordan man finner nestPostorden, og da tegnet jeg selv et par trær for å se hvor blir det andre elementet i postorder. 

**Oppgave 4** startet jeg med å bruke en del av oppgave 3. _Iterativ metode_ kalte inn førstePostorden og så i while-løkken kalte den nestePostorden. For _rekursjon_ var det ganske likt som forrige oppgaven, bare at metoden kalte seg selv fra venstre/høyre side. 

**Oppgave 5** fikk jeg help fra siste forelesningsvideo i uke 42, bare endre rekkefølgen slik at den oppfyller krav fra oppgaven. Oppretter en liste og en ArrayDeque, gikk gjennom treet og satt elementer på riktig plass i arrayet. Altså nivåorden.

**I oppgave 6** kopierte jeg metoden fra kompendiet, som forklart i oppgaven. Videre brukte jeg en while-løkke på fjernAlle() og på nullstill() bruke jeg stack til å pushe/pope som forklart i forelesningen. Til slutt ble alt satt på 'null' og antall = 0.

**Alle oppgaver består testen jeg har laget selv, og _Oblig3Test_.**
