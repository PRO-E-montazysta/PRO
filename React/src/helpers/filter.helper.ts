import { FilterInputType } from "../components/table/filter/TableFilter"


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


export const getFilterParams = (filterStructure: Array<FilterInputType>) => {
    const filterParams: any = {}
    filterStructure.forEach((f: FilterInputType) => {
        if (!!f.value) {
            switch (f.typeValue) {
                case 'Array':
                    if (Array.isArray(f.value) && f.value.length > 0) {
                        const arr: Array<any> = f.value
                        filterParams[f.id] = arr.join(',')
                    }
                    break
                default:
                    filterParams[f.id] = f.value
                    break
            }
        }
    })

    return filterParams;
}



export const setNewFilterValues = (filterForm: any, filterInitValues: Array<FilterInputType>) => {
    let filtersWithNewValues = filterInitValues;

    for (const filter of filtersWithNewValues)
        filter.value = filterForm[filter.id]

    return filtersWithNewValues
}