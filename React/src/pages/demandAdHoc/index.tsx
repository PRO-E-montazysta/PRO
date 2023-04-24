import { Filter } from '../../components/table/filter/TableFilter'
import FatTable from '../../components/table/FatTable'
import { useState } from 'react'
import { useQuery } from 'react-query'
import { AxiosError } from 'axios'
import { filterInitStructure, headCells } from './helper'
import { useNavigate } from 'react-router-dom'
import { getFilterParams, getInputs, setNewFilterValues } from '../../helpers/filter.helper'
import { DemandAdHocFilterDto } from '../../types/model/DemandAdHoc'
import { getFilteredDemandsAdHoc } from '../../api/demandAdHoc.api'
import { useFormik } from 'formik'

const DemandAdHoc = () => {
    const [filterStructure, setFilterStructure] = useState(filterInitStructure)
    const [filterParams, setFilterParams] = useState(getFilterParams(filterInitStructure))
    const { initialValues, inputs } = getInputs(filterInitStructure)
    const navigation = useNavigate()

    const queryDemandsAdHoc = useQuery<Array<DemandAdHocFilterDto>, AxiosError>(
        ['demandsAdHoc', filterParams],
        async () => getFilteredDemandsAdHoc({ queryParams: filterParams }),
    )

    const filter: Filter = {
        formik: useFormik({
            initialValues: initialValues,
            // validationSchema={{}}
            onSubmit: () => {
                setFilterStructure(setNewFilterValues(filter.formik.values, filterStructure))
                setFilterParams(getFilterParams(filterStructure))
            },
            onReset: () => filter.formik.setValues(initialValues),
        }),
        inputs: inputs,
    }

    return (
        <FatTable
            query={queryDemandsAdHoc}
            filterProps={filter}
            headCells={headCells}
            initOrderBy={'createdAt'}
            onClickRow={(e, row) => {
                navigation(`/demands-adhoc/${row.id}`)
            }}
            pageHeader="Lista zapotrzebowań AdHoc"
        />
    )
}

export default DemandAdHoc
