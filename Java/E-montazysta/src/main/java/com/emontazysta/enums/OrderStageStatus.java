package com.emontazysta.enums;

public enum OrderStageStatus {

    PLANNING, //Planowanie | Etap utworzony przez specjalistę | Automatycznie po stworzeniu
    ADDING_FITTERS, //Przypisywanie | Brygadzista przypisuje montażystów | Specjalista
    PICK_UP, //Odbieranie | Magazynier wydaje elementy i narzędzia | Brygadzista
    REALESED, //Wydano | Magazynier wydał narzędzia i elementy | Magazynier
    ON_WORK, //W realizacji | Brygadzista zaakceptował wydanie i rozpoczął prace | Brygadzista
    RETURN, //Zwrot | Brygadzista zwraca narzędzia i elementy | Brygadzista
    RETURNED, //Zwrócono | Brygadzista zwrócił wszystkie narzędzia | Magazynier
    FINISHED, //Zakończono | Zakończono etap | Brygadzista
}
