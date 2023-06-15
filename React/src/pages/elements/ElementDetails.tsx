import { useFormik } from 'formik'
import { useContext, useEffect, useState } from 'react'
import { useParams } from 'react-router-dom'
import { useFormStructure } from './helper'
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
import Error from '../../components/error/Error'
import Grid from '@mui/material/Grid'
import Card from '@mui/material/Card'
import ExpandMore from '../../components/expandMore/ExpandMore'
import EqualizerIcon from '@mui/icons-material/Equalizer'
import ElementInWarehouseView from '../elementInWarehouse'
import { Role } from '../../types/roleEnum'
import { isAuthorized } from '../../utils/authorize'

const ElementDetails = () => {
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
                if (result == 1 && params.id) deleteElementMutation.mutate(params.id)
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

    const canPrintLabel = () => {
        return isAuthorized([Role.WAREHOUSE_MAN, Role.WAREHOUSE_MANAGER])
    }

    return elementData.data?.deleted ? (
        <>
            <Error code={404} message={'Ten obiekt został usunięty'} />
        </>
    ) : (
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
                                        titleIcon={<EqualizerIcon />}
                                        title="Stan magazynowy"
                                        cardContent={<ElementInWarehouseView elementId={Number(params.id)} />}
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
                                printLabel={
                                    canPrintLabel()
                                        ? [elementData.data?.name as string, elementData.data?.code as string]
                                        : undefined
                                }
                                editPermissionRoles={[Role.WAREHOUSE_MANAGER]}
                                deletePermissionRoles={[Role.WAREHOUSE_MANAGER]}
                            />
                        </>
                    )}
                </FormPaper>
            </FormBox>
        </>
    )
}

export default ElementDetails
