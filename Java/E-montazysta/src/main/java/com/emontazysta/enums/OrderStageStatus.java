package com.emontazysta.enums;

public enum OrderStageStatus {

    PLANNING, //Planowanie | Etap utworzony przez specjalistę | Automatycznie po stworzeniu
    ADDING_FITTERS, //Przypisywanie | Brygadzista przypisuje montażystów | Specjalista
    PICK_UP, //Odbieranie | Magazynier wydaje elementy i narzędzia | Brygadzista
    REALESED, //Wydano | Magazynier wydał narzędzia | Magazynier
    ON_WORK, //W realizacji | Brygadzista zaakceptował wydanie i rozpoczął prace | Brygadzista
    FINISHED, //Zakończono | Zakończono etap | Brygadzista
}
