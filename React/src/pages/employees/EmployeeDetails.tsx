import { useFormik } from 'formik'
import { useContext, useEffect, useState } from 'react'
import { useParams } from 'react-router-dom'
import { useFormStructure } from './helper'
import { DialogGlobalContext } from '../../providers/DialogGlobalProvider'
import { getInitValues, getValidatinSchema } from '../../helpers/form.helper'
import {
    useAddEmployee,
    useEmployeeData,
    useDeleteEmployee,
    useEditEmployee,
    useHireEmployee,
    useDismissEmployee,
} from './hooks'
import { useQueriesStatus } from '../../hooks/useQueriesStatus'
import FormBox from '../../components/form/FormBox'
import FormTitle from '../../components/form/FormTitle'
import FormPaper from '../../components/form/FormPaper'
import QueryBoxStatus from '../../components/base/QueryStatusBox'
import { FormStructure } from '../../components/form/FormStructure'
import { FormButtons } from '../../components/form/FormButtons'
import { PageMode } from '../../types/form'
import { roleName } from '../../helpers/enum.helper'
import DisplayEmploymentHistory from '../../components/history/DisplayEmploymentHistory'
import { isAuthorized } from '../../utils/authorize'
import { Role } from '../../types/roleEnum'

const EmployeeDetails = () => {
    const params = useParams()
    const [pageMode, setPageMode] = useState<PageMode>('read')
    const { showDialog } = useContext(DialogGlobalContext)
    const formStructure = useFormStructure()
    const [initData, setInitData] = useState(getInitValues(formStructure))

    //mutations and queries
    const addEmployeeMutation = useAddEmployee()
    const editEmployeeMutation = useEditEmployee((data) => handleOnEditSuccess(data))
    const deleteEmployeeMutation = useDeleteEmployee(() => employeeData.remove())
    const employeeData = useEmployeeData(params.id)
    const hireEmployeeMutation = useHireEmployee(params.id!)
    const dismissEmployeeMutation = useDismissEmployee(params.id!)

    //status for all mutations and queries
    const queriesStatus = useQueriesStatus(
        [employeeData],
        [addEmployeeMutation, editEmployeeMutation, deleteEmployeeMutation],
    )

    const [hired, setHired] = useState<boolean>(employeeData.data?.active!)

    const handleSubmit = (values: any) => {
        if (pageMode == 'new') {
            addEmployeeMutation.mutate(values)
            setHired(true)
        } else if (pageMode == 'edit') editEmployeeMutation.mutate(values)
        else console.warn('Try to submit while read mode')
    }

    const handleDelete = () => {
        showDialog({
            title: 'Czy na pewno chcesz usunąć pracownika?',
            btnOptions: [
                { text: 'Usuń', value: 1, variant: 'contained' },
                { text: 'Anuluj', value: 0, variant: 'outlined' },
            ],
            callback: (result: number) => {
                if (result == 1 && params.id && Number.isInteger(params.id)) deleteEmployeeMutation.mutate(params.id)
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
        employeeData.refetch({
            queryKey: ['employee', { id: data.id }],
        })
        setPageMode('read')
    }

    const handleHireEmployee = () => {
        showDialog({
            title: 'Czy na pewno chcesz zatrudnić tego pracownika?',
            btnOptions: [
                { text: 'Tak', value: 1, variant: 'contained' },
                { text: 'Anuluj', value: 0, variant: 'outlined' },
            ],
            callback: (result: number) => {
                if (result == 1 && params.id) {
                    hireEmployeeMutation.mutate(params.id)

                    setHired(true)
                }
            },
        })
    }

    const handleDismissEmployee = () => {
        showDialog({
            title: 'Czy na pewno chcesz zwolnić tego pracownika?',
            btnOptions: [
                { text: 'Tak', value: 1, variant: 'contained' },
                { text: 'Anuluj', value: 0, variant: 'outlined' },
            ],
            callback: (result: number) => {
                if (result == 1 && params.id) {
                    dismissEmployeeMutation.mutate(params.id)
                    setHired(false)
                }
            },
        })
    }

    useEffect(() => {
        if (employeeData.data) {
            formik.setValues(employeeData.data)
            setInitData(employeeData.data)
        }
    }, [employeeData.data])

    useEffect(() => {
        if (params.id == 'new') {
            setPageMode('new')
            formik.setValues(getInitValues(formStructure))
            setInitData(getInitValues(formStructure))
        } else {
            setPageMode('read')
        }
    }, [params.id])

    useEffect(() => {
        setHired(employeeData.data?.active!)
    }, [employeeData.data?.active])

    const canDisplayEmploymentHistory = () => {
        return pageMode !== 'new' && isAuthorized([Role.ADMIN])
    }

    return (
        <FormBox>
            <FormTitle
                mainTitle={pageMode == 'new' ? 'Nowy pracownik' : 'Pracownik'}
                subTitle={
                    pageMode == 'new'
                        ? ''
                        : String(formik.values['roles'] === null ? '' : roleName(formik.values['roles'][0]) + ' - ') +
                          formik.values['firstName'] +
                          ' ' +
                          formik.values['lastName']
                }
            />
            <FormPaper>
                {queriesStatus.result != 'isSuccess' ? (
                    <QueryBoxStatus queriesStatus={queriesStatus} />
                ) : (
                    <>
                        <FormStructure formStructure={formStructure} formik={formik} pageMode={pageMode} />
                        <FormButtons
                            id={params.id}
                            onCancel={handleCancel}
                            onDelete={handleDelete}
                            onEdit={() => setPageMode('edit')}
                            onReset={handleReset}
                            onSubmit={formik.submitForm}
                            readonlyMode={pageMode == 'read'}
                            hireDismissEmp={
                                !isAuthorized([Role.ADMIN])
                                    ? undefined
                                    : hired
                                    ? [handleDismissEmployee, 'dismiss']
                                    : [handleHireEmployee, 'hire']
                            }
                        />
                        {canDisplayEmploymentHistory() ? (
                            <DisplayEmploymentHistory employeeId={params.id!}></DisplayEmploymentHistory>
                        ) : (
                            ''
                        )}
                    </>
                )}
            </FormPaper>
        </FormBox>
    )
}

export default EmployeeDetails
