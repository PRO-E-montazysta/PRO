import React from "react";

import { FilterInputType } from "./TableFilter"

export const getInputs = (form: Array<FilterInputType>) => {
    let initialValues: { [key: string]: any } = {}
    // let validationFields = {}
    for (const field of form) {
        initialValues[field.id] = field.value
    }
    return {
        // validationSchema: Yup.object({ ...validationsFields }),
        initialValues,
        inputs: form
    }
}



export const removeEmptyFilterValues = (filterForm: Object) => {
    let filterParams: { [key: string]: any } = {};
    for (const [key, value] of Object.entries(filterForm))
        if (value != '')
            filterParams[key] = value

    return filterParams
}

export const setNewFilterValues = (filterForm: any, filterInitValues: Array<FilterInputType>) => {
    let filtersWithNewValues = filterInitValues;

    for (const filter of filtersWithNewValues)
        filter.value = filterForm[filter.id]

    return filtersWithNewValues
}