import { useFormik } from 'formik'
import { useContext, useEffect, useRef, useState } from 'react'
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
import { useAddDemandAdHoc, useDeleteDemandAdHoc, useEditDemandAdHoc, useDemandAdHocData } from './hooks'
import { useQueriesStatus } from '../../hooks/useQueriesStatus'
import { Divider } from '@mui/material'
import DemandAdHocTables from './DemandAdHocTables'
import { Role } from '../../types/roleEnum'
import { AxiosError } from 'axios'
import { useQuery } from 'react-query'
import { getAboutMeInfo } from '../../api/employee.api'
import NavActions from '../../components/navbar/NavActions'

const DemandAdHocDetails = () => {
    const params = useParams()
    const [pageMode, setPageMode] = useState<PageMode>('read')
    const { showDialog } = useContext(DialogGlobalContext)
    const formStructure = useFormStructure()
    const [initData, setInitData] = useState(getInitValues(formStructure))

    //użyć refów i w useeffectcie przypisać do formika, w formiku już są jako initValues,
    //zmienić typy tablic, przemyśleć jak to zrobić przy edycji - w butonie on edit zrobić metode ustawiającą refy
    const plannedElementsRef = useRef<{ numberOfElements: number; elementId: string }[]>([])
    const plannedToolsTypesRef = useRef<{ numberOfTools: number; toolTypeId: string }[]>([])

    //mutations and queries
    const addDemandAdHocMutation = useAddDemandAdHoc()
    const editDemandAdHocMutation = useEditDemandAdHoc((data) => handleOnEditSuccess(data))
    const deleteDemandAdHocMutation = useDeleteDemandAdHoc(() => demandAdHocData.remove())
    const demandAdHocData = useDemandAdHocData(params.id)
    //status for all mutations and queries
    const queriesStatus = useQueriesStatus(
        [demandAdHocData],
        [addDemandAdHocMutation, editDemandAdHocMutation, deleteDemandAdHocMutation],
    )

    const handleSubmit = (values: any) => {
        //dołożyć dane do formika
        values.listOfElementsPlannedNumber = plannedElementsRef.current!
        values.listOfToolsPlannedNumber = plannedToolsTypesRef.current!
        console.log('values', values)
        if (pageMode == 'new') addDemandAdHocMutation.mutate(values)
        else if (pageMode == 'edit') editDemandAdHocMutation.mutate(values)
        else console.warn('Try to submit while read mode')
    }

    const handleDelete = () => {
        showDialog({
            title: 'Czy na pewno chcesz usunąć zapotrzebowanie AdHoc?',
            btnOptions: [
                { text: 'Usuń', value: 1, variant: 'contained' },
                { text: 'Anuluj', value: 0, variant: 'outlined' },
            ],
            callback: (result: number) => {
                if (result == 1 && params.id && Number.isInteger(params.id)) deleteDemandAdHocMutation.mutate(params.id)
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
        demandAdHocData.refetch({
            queryKey: ['demandAdHoc', { id: data.id }],
        })
        setPageMode('read')
    }

    useEffect(() => {
        if (demandAdHocData.data) {
            console.log('demand', demandAdHocData.data)
            formik.setValues(demandAdHocData.data)
            setInitData(demandAdHocData.data)
        }
    }, [demandAdHocData.data])

    useEffect(() => {
        if (params.id == 'new') {
            setPageMode('new')
            formik.setValues(getInitValues(formStructure))
            setInitData(getInitValues(formStructure))
        } else {
            setPageMode('read')
        }
    }, [params.id])

    const aboutMeQuery = useQuery<any, AxiosError>(['about-me'], async () => getAboutMeInfo())

    return (
        <>
            <FormBox>
                <FormTitle mainTitle={pageMode == 'new' ? 'Nowe zapotrzebowanie AdHoc' : 'Zapotrzebowanie AdHoc'} />
                <FormPaper>
                    {queriesStatus.result != 'isSuccess' ? (
                        <QueryBoxStatus queriesStatus={queriesStatus} />
                    ) : (
                        <>
                            <FormStructure formStructure={formStructure} formik={formik} pageMode={pageMode} />
                            <DemandAdHocTables
                                plannedElementsRef={plannedElementsRef}
                                plannedToolsTypesRef={plannedToolsTypesRef}
                                pageMode={pageMode}
                            />
                            <FormButtons
                                id={params.id}
                                onCancel={handleCancel}
                                onDelete={handleDelete}
                                onEdit={() => setPageMode('edit')}
                                onReset={handleReset}
                                onSubmit={formik.submitForm}
                                readonlyMode={pageMode == 'read'}
                                deletePermissionRoles={
                                    aboutMeQuery.data.userId == formik.values['createdById']
                                        ? [Role.FOREMAN]
                                        : [Role.NOBODY]
                                }
                                editPermissionRoles={
                                    aboutMeQuery.data.userId == formik.values['createdById']
                                        ? [Role.FOREMAN]
                                        : [Role.NOBODY]
                                }
                            />
                        </>
                    )}
                </FormPaper>
            </FormBox>
        </>
    )
}

export default DemandAdHocDetails
