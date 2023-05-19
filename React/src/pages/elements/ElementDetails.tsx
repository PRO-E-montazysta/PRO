import { useFormik } from 'formik'
import { useContext, useEffect, useState } from 'react'
import { useNavigate, useParams } from 'react-router-dom'

import { elementInWarehouseHeadCells, useFormStructure, elementInWarehouseFilterInitStructure } from './helper'
import FormBox from '../../components/form/FormBox'
import FormTitle from '../../components/form/FormTitle'
import FormPaper from '../../components/form/FormPaper'
import QueryBoxStatus from '../../components/base/QueryStatusBox'
import { FormStructure } from '../../components/form/FormStructure'
import { FormButtons } from '../../components/form/FormButtons'
import { PageMode } from '../../types/form'
import { DialogGlobalContext } from '../../providers/DialogGlobalProvider'
import { getInitValues, getValidatinSchema } from '../../helpers/form.helper'
import { useAddElement, useDeleteElement, useEditElement, useElementData } from './hooks'
import { useQueriesStatus } from '../../hooks/useQueriesStatus'
import Grid from '@mui/material/Grid'
import Card from '@mui/material/Card'
import ExpandMore from '../../components/expandMore/ExpandMore'
import HistoryIcon from '@mui/icons-material/History'
import FatTable from '../../components/table/FatTable'
import { AxiosError } from 'axios'
import { useQuery } from 'react-query'
import { getFilteredElements } from '../../api/element.api'
import { setNewFilterValues, getFilterParams, getInputs } from '../../helpers/filter.helper'
import { Filter, FilterFormProps } from '../../components/table/filter/TableFilter'
import { Element } from '../../types/model/Element'
import { getElementInWarehouseCounts } from '../../api/elementInWarehouse.api'
import { ElementInWarehouse, ElementInWarehouseFilterDto } from '../../types/model/ElementInWarehouse'

const ElementDetails = () => {
    const [filterStructure, setFilterStructure] = useState(elementInWarehouseFilterInitStructure)
    const [filterParams, setFilterParams] = useState(getFilterParams(elementInWarehouseFilterInitStructure))
    const { initialValues, inputs } = getInputs(elementInWarehouseFilterInitStructure)
    const navigation = useNavigate()

    const params = useParams()
    const [pageMode, setPageMode] = useState<PageMode>('read')
    const { showDialog } = useContext(DialogGlobalContext)
    const formStructure = useFormStructure()
    const [initData, setInitData] = useState(getInitValues(formStructure))

    //mutations and queries
    const addElementMutation = useAddElement()
    const editElementMutation = useEditElement((data) => handleOnEditSuccess(data))
    const deleteElementMutation = useDeleteElement(() => elementData.remove())
    const elementData = useElementData(params.id)
    //status for all mutations and queries
    const queriesStatus = useQueriesStatus(
        [elementData],
        [addElementMutation, editElementMutation, deleteElementMutation],
    )

    const handleSubmit = (values: any) => {
        if (pageMode == 'new') addElementMutation.mutate(values)
        else if (pageMode == 'edit') editElementMutation.mutate(values)
        else console.warn('Try to submit while read mode')
    }

    const handleDelete = () => {
        showDialog({
            title: 'Czy na pewno chcesz usunąć element?',
            btnOptions: [
                { text: 'Usuń', value: 1, variant: 'contained' },
                { text: 'Anuluj', value: 0, variant: 'outlined' },
            ],
            callback: (result: number) => {
                if (result == 1 && params.id && Number.isInteger(params.id)) deleteElementMutation.mutate(params.id)
            },
        })
    }

    const formik = useFormik({
        initialValues: initData,
        validationSchema: getValidatinSchema(formStructure, pageMode),
        onSubmit: handleSubmit,
    })

    const handleReset = () => {
        formik.resetForm()
        formik.setValues(initData)
    }

    const handleCancel = () => {
        handleReset()
        setPageMode('read')
    }

    const handleOnEditSuccess = (data: any) => {
        elementData.refetch({
            queryKey: ['element', { id: data.id }],
        })
        setPageMode('read')
    }

    useEffect(() => {
        if (elementData.data) {
            formik.setValues(elementData.data)
            setInitData(elementData.data)
        }
    }, [elementData.data])

    useEffect(() => {
        if (params.id == 'new') {
            setPageMode('new')
            formik.setValues(getInitValues(formStructure))
            setInitData(getInitValues(formStructure))
        } else {
            setPageMode('read')
        }
    }, [params.id])

    console.log(filterParams)
    const queryElementInWarehouse = useQuery<Array<ElementInWarehouseFilterDto>, AxiosError>(
        ['element-in-warehouse', filterParams],
        async () => getElementInWarehouseCounts(filterParams, Number(params.id)),
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
        <>
            <FormBox>
                <FormTitle
                    mainTitle={pageMode == 'new' ? 'Nowy element' : 'Element'}
                    subTitle={pageMode == 'new' ? '' : String(formik.values['name'] + ' - ' + formik.values['code'])}
                />
                <FormPaper>
                    {queriesStatus.result != 'isSuccess' ? (
                        <QueryBoxStatus queriesStatus={queriesStatus} />
                    ) : (
                        <>
                            <FormStructure formStructure={formStructure} formik={formik} pageMode={pageMode} />

                            <Grid container alignItems="center" justifyContent="center" marginTop={2}>
                                <Card sx={{ width: '100%', left: '50%' }}>
                                    <ExpandMore
                                        titleIcon={<HistoryIcon />}
                                        title="Stan magazynowy"
                                        cardContent={
                                            <FatTable
                                                idPropName="id"
                                                query={queryElementInWarehouse}
                                                filterProps={filter}
                                                headCells={elementInWarehouseHeadCells}
                                                initOrderBy={'inWarehouseCount'}
                                                onClickRow={(e, row) => {
                                                    navigation(`/elements/${row.id}`)
                                                }}
                                            />
                                        }
                                    />
                                </Card>
                            </Grid>

                            <FormButtons
                                id={params.id}
                                onCancel={handleCancel}
                                onDelete={handleDelete}
                                onEdit={() => setPageMode('edit')}
                                onReset={handleReset}
                                onSubmit={formik.submitForm}
                                readonlyMode={pageMode == 'read'}
                                printLabel={[elementData.data?.name as string, elementData.data?.code as string]}
                            />
                        </>
                    )}
                </FormPaper>
            </FormBox>
        </>
    )
}

export default ElementDetails
