package com.emontazysta.enums;

public enum OrderStatus {

    CREATED, //Utworzony | Zlecenie utworzone przez handlowca | Automatycznie po stworzeniu
    PLANNING, //Planowanie | Specjalista dzieli zlecenie na etapy | Handlowiec
    TO_ACCEPT, //Do zaakceptowania | Oczekuje na zatwierdzenie przez managera | Specjalista
    ACCEPTED, //Zaakceptowane | Manager zaakceptował zlecenie, brygadzista może przypisywać montażystów (zmienia status OrderStages na ADDING_FITTERS | Manager
    IN_PROGRESS, //W trakcie | Brygadzista przypisał montażystów i rozpoczął prace | Brygadzista
    FINISHED //Zakończono | Zlecenie wykonane | Brygadzista
}
