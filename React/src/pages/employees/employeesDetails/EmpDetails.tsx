import { useQuery } from 'react-query'
import { AxiosError } from 'axios'
import { useEffect, useState } from 'react'
import { useParams } from 'react-router-dom'
import { Employee } from '../../../types/model/Employee'
import Typography from '@mui/material/Typography'
import { Box, Button, CircularProgress, Divider, Grid, Paper } from '@mui/material'
import { getEmployeeById } from '../../../api/employee.api'
import FormInput from '../../../components/form/FormInput'
import FormLabel from '../../../components/form/FormLabel'
import { theme } from '../../../themes/baseTheme'
import { useFormik } from 'formik'
import { useMutation } from 'react-query'
import { useNavigate } from 'react-router-dom'
import DialogInfo, { DialogInfoParams } from '../../../components/dialogInfo/DialogInfo'
import { deleteEmployee, postEmployee, updateEmployee } from '../../../api/employee.api'
import { emptyForm, validationSchema } from '.././helper'
import SaveIcon from '@mui/icons-material/Save'
import ReplayIcon from '@mui/icons-material/Replay'
import CloseIcon from '@mui/icons-material/Close'
import EditIcon from '@mui/icons-material/Edit'
import DeleteIcon from '@mui/icons-material/Delete'
import FormSelect from '../../../components/form/FormSelect'
import { employeeStatusOptions, userRoleOptions } from '../../../helpers/enum.helper'
import { Role } from '../../../types/roleEnum'
import { getForemanById, postForeman, updateForeman } from '../../../api/foreman.api'
import { getAdminById, postAdmin, updateAdmin } from '../../../api/admin.api'
import { getFitterById, postFitter, updateFitter } from '../../../api/fitter.api'
import { getManagerById, postManager, updateManager } from '../../../api/manager.api'
import { getSalesRepresentativeById, postSalesRepresentative, updateSalesRepresentative } from '../../../api/salesRepresentatives.api'
import { getSpecialistById, postSpecialist, updateSpecialist } from '../../../api/specialist.api'
import { getWarehousemanById, postWarehouseman, updateWarehouseman } from '../../../api/warehouseman.api'
import { getWarehouseManagerById, postWarehouseManager, updateWarehouseManager } from '../../../api/warehousemanager.api'


const EmpDetails = () => {
    const params = useParams()
    const [readonlyMode, setReadonlyMode] = useState(true)
    const [initData, setInitData] = useState(emptyForm)
    const navigation = useNavigate()

    const [dialog, setDialog] = useState({
        open: false,
        dialogText: [''],
        confirmAction: () => { },
        confirmLabel: '',
    })

    const mutationDelete = useMutation({
        mutationFn: deleteEmployee,
        onSuccess(data) {
            displayInfo({
                dialogText: ['Pracownik został usunięty'],
                confirmAction: () => {
                    setDialog({ ...dialog, open: false })
                    queryData.remove()
                    navigation('/employees')
                },
                confirmLabel: 'Ok',
            })
        },
        onError(error: Error) {
            displayInfo({
                dialogText: ['Błąd poczas usuwania pracownika', error.message],
                confirmAction: () => {
                    setDialog({ ...dialog, open: false })
                },
                confirmLabel: 'Ok',
            })
        },
    })

    const handleDelete = () => {
        displayInfo({
            dialogText: ['Czy na pewno chcesz usunąć pracownika?'],
            confirmAction: () => {
                setDialog({ ...dialog, open: false })
                if (formik.values.id) mutationDelete.mutate(formik.values.id)
            },
            confirmLabel: 'Usuń',
            cancelAction: () => {
                setDialog({ ...dialog, open: false })
            },
            cancelLabel: 'Anuluj',
        })
    }

    const [expandedHistory, setExpandedHistory] = useState(false)
    const [expandedInformation, setExpandedInformation] = useState(false)
    const [expandedStatus, setExpandedStatus] = useState(false)

    async function sendRoleBasedGetById(id: string) {
        const user: Employee = await getEmployeeById(id);
        const role = user.roles[0];

        switch (role) {
            case Role.ADMIN: return getAdminById(id);
            case Role.FITTER: return getFitterById(id);
            case Role.FOREMAN: return getForemanById(id);
            case Role.MANAGER: return getManagerById(id);
            case Role.SALES_REPRESENTATIVE: return getSalesRepresentativeById(id);
            case Role.SPECIALIST: return getSpecialistById(id);
            case Role.WAREHOUSE_MAN: return getWarehousemanById(id);
            case Role.WAREHOUSE_MANAGER: return getWarehouseManagerById(id);
            default: return getEmployeeById(id); // TODO: NS: Domyślnie powinno rzucać błedem o nieznanej roli
        }
    }

    const queryData = useQuery<Employee, AxiosError>(
        ['users', { id: params.id }],
        async () => sendRoleBasedGetById(params.id! && params.id != 'new' ? params.id : ''),
        {
            enabled: !!params.id && params.id != 'new',
        },
    )

    const handleExpandHistoryClick = () => {
        setExpandedHistory(!expandedHistory)
    }

    const getEmployeeRoles = () => {
        return queryData.data?.roles.map((role) => role)
    }

    const getStatusDescription = () => {
        if (queryData.data?.status === 'AVAILABLE') {
            return <p>'Pracownik jest dostępny'</p>
        } else {
            return <p>{queryData.data?.unavailbilityDescription}</p>
        }
    }

    const sendRoleBasedPost = (data: Employee) => {
        const role = data.roles[0];

        switch (role) {
            case Role.ADMIN: return postAdmin(data);
            case Role.FITTER: return postFitter(data);
            case Role.FOREMAN: return postForeman(data);
            case Role.MANAGER: return postManager(data);
            case Role.SALES_REPRESENTATIVE: return postSalesRepresentative(data);
            case Role.SPECIALIST: return postSpecialist(data);
            case Role.WAREHOUSE_MAN: return postWarehouseman(data);
            case Role.WAREHOUSE_MANAGER: return postWarehouseManager(data);
            default: return postEmployee(data); // TODO: NS: Domyślnie powinno rzucać błedem o nieznanej roli
        }
    }

    const mutationPost = useMutation({
        mutationFn: sendRoleBasedPost,
        onSuccess(data) {
            displayInfo({
                dialogText: ['Nowy pracownik utworzony pomyślnie'],
                confirmAction: () => {
                    setDialog({ ...dialog, open: false })
                    if (data && data.id) navigation(`/employees/${data.id}`)
                },
                confirmLabel: 'Ok',
            })
        },
        onError(error: Error) {
            displayInfo({
                dialogText: ['Błąd poczas tworzenia nowego pracownika', error.message],
                confirmAction: () => {
                    setDialog({ ...dialog, open: false })
                },
                confirmLabel: 'Ok',
            })
        },
    })

    const sendRoleBasedUpdate = (data: Employee) => {
        const role = data.roles[0];

        switch (role) {
            case Role.ADMIN: return updateAdmin(data);
            case Role.FITTER: return updateFitter(data);
            case Role.FOREMAN: return updateForeman(data);
            case Role.MANAGER: return updateManager(data);
            case Role.SALES_REPRESENTATIVE: return updateSalesRepresentative(data);
            case Role.SPECIALIST: return updateSpecialist(data);
            case Role.WAREHOUSE_MAN: return updateWarehouseman(data);
            case Role.WAREHOUSE_MANAGER: return updateWarehouseManager(data);
            default: return updateEmployee(data); // TODO: NS: Domyślnie powinno rzucać błedem o nieznanej roli
        }
    }

    const mutationUpdate = useMutation({
        mutationFn: sendRoleBasedUpdate,
        onSuccess(data) {
            queryData.refetch({
                queryKey: ['users', { id: data.id }],
            })
            setReadonlyMode(true)
            displayInfo({
                dialogText: ['Zmiany w pracowniku zostały zapisane'],
                confirmAction: () => {
                    setDialog({ ...dialog, open: false })
                },
                confirmLabel: 'Ok',
            })
        },
        onError(error: Error) {
            displayInfo({
                dialogText: ['Błąd poczas zapisywania pracownika', error.message],
                confirmAction: () => {
                    setDialog({ ...dialog, open: false })
                },
                confirmLabel: 'Ok',
            })
        },
    })

    const handleSubmit = () => {
        let tmp: any = formik.values;

        if (!Array.isArray(tmp.roles)) {
            tmp.roles = [tmp.roles];
        }

        formik.setValues(tmp);

        if (params.id == 'new') mutationPost.mutate(JSON.parse(JSON.stringify(formik.values)))
        else mutationUpdate.mutate(JSON.parse(JSON.stringify(formik.values)))
    }

    const formik = useFormik({
        initialValues: initData,
        validationSchema: validationSchema,
        onSubmit: handleSubmit,
    })

    const handleReset = () => {
        formik.resetForm()
        formik.setValues(JSON.parse(JSON.stringify(initData)))
    }

    const handleCancel = () => {
        handleReset()
        setReadonlyMode(true)
    }

    useEffect(() => {
        if (queryData.data) {
            formik.setValues(JSON.parse(JSON.stringify(queryData.data)))
            setInitData(JSON.parse(JSON.stringify(queryData.data)))
        }
    }, [queryData.data])

    useEffect(() => {
        if (params.id == 'new') {
            setReadonlyMode(false)
            formik.setValues(JSON.parse(JSON.stringify(emptyForm)))
            setInitData(JSON.parse(JSON.stringify(emptyForm)))
        } else setReadonlyMode(true)
    }, [params.id])

    const displayInfo = (params: DialogInfoParams) => {
        setDialog({
            ...params,
            open: true,
        })
    }

    return (<>
        <Box maxWidth={800} style={{ margin: 'auto', marginTop: '20px' }}>
            <Paper sx={{ p: '10px' }}>
                {queryData.isLoading || queryData.isError ? (
                    <Box
                        sx={{
                            display: 'flex',
                            minHeight: '200px',
                            justifyContent: 'center',
                            alignItems: 'center',
                        }}
                    >
                        {queryData.isLoading ? (
                            <CircularProgress />
                        ) : (
                            <Typography>Nie znaleziono pracownika</Typography>
                        )}
                    </Box>
                ) : (
                    <>
                        <Grid container>
                            <Grid
                                item
                                xs={6}
                                style={{
                                    textAlign: 'end',
                                }}
                            >
                                <FormLabel label="Imię" formik={formik} id={'firstName'} />
                                <FormLabel label="Nazwisko" formik={formik} id={'lastName'} />
                                <FormLabel label="E-mail" formik={formik} id={'email'} />
                                <FormLabel label="Hasło" formik={formik} id={'password'} />
                                <FormLabel label="Nazwa użytkownika" formik={formik} id={'username'} />
                                <FormLabel label="Status" formik={formik} id={'status'} />
                                <FormLabel label="Stanowisko" formik={formik} id={'roles'} />
                                <FormLabel label="Telefon" formik={formik} id={'phone'} />
                                <FormLabel label="Pesel" formik={formik} id={'pesel'} />
                            </Grid>
                            <Divider
                                orientation="vertical"
                                variant="middle"
                                style={{ borderColor: theme.palette.primary.main }}
                                sx={{ mr: '-1px' }}
                                flexItem
                            />
                            <Grid item xs={6}>
                                <FormInput id={'firstName'} formik={formik} readonly={readonlyMode} firstChild />
                                <FormInput id={'lastName'} formik={formik} readonly={readonlyMode} />
                                <FormInput id={'email'} formik={formik} readonly={readonlyMode} />
                                <FormInput id={'password'} formik={formik} readonly={readonlyMode} />
                                <FormInput id={'username'} formik={formik} readonly={readonlyMode} />
                                <FormSelect
                                    id={'status'}
                                    formik={formik}
                                    readonly={readonlyMode}
                                    options={employeeStatusOptions()}
                                />
                                <FormSelect
                                    id={'roles'}
                                    formik={formik}
                                    readonly={readonlyMode}
                                    options={userRoleOptions()}
                                />
                                <FormInput id={'phone'} formik={formik} readonly={readonlyMode} />
                                <FormInput id={'pesel'} formik={formik} readonly={readonlyMode} />
                            </Grid>
                        </Grid>
                        <Box sx={{ margin: '20px', gap: '20px', display: 'flex', flexDirection: 'row-reverse' }}>
                            {readonlyMode && params.id != 'new' ? (
                                <>
                                    <Button
                                        color="primary"
                                        startIcon={<EditIcon />}
                                        variant="contained"
                                        type="submit"
                                        style={{ width: 120 }}
                                        onClick={() => setReadonlyMode(false)}
                                    >
                                        Edytuj
                                    </Button>
                                    <Button
                                        color="error"
                                        startIcon={<DeleteIcon />}
                                        variant="contained"
                                        type="submit"
                                        style={{ width: 120 }}
                                        onClick={handleDelete}
                                    >
                                        Usuń
                                    </Button>
                                </>
                            ) : (
                                <>
                                    <Button
                                        color="primary"
                                        startIcon={<SaveIcon />}
                                        variant="contained"
                                        onClick={formik.submitForm}
                                        style={{ width: 120 }}
                                    >
                                        Zapisz
                                    </Button>
                                    <Button
                                        color="primary"
                                        startIcon={<ReplayIcon style={{ transform: 'rotate(-0.25turn)' }} />}
                                        style={{ color: theme.palette.primary.main, width: 120 }}
                                        variant="outlined"
                                        onClick={handleReset}
                                    >
                                        Reset
                                    </Button>
                                    {params.id != 'new' && (
                                        <Button
                                            color="primary"
                                            startIcon={<CloseIcon style={{ transform: 'rotate(-0.25turn)' }} />}
                                            style={{ color: theme.palette.primary.main, width: 120 }}
                                            variant="outlined"
                                            onClick={handleCancel}
                                        >
                                            Anuluj
                                        </Button>
                                    )}
                                </>
                            )}
                        </Box>
                    </>
                )}
            </Paper>
        </Box>
        {dialog.open && <DialogInfo {...dialog} />}
    </>)
}

export default EmpDetails
