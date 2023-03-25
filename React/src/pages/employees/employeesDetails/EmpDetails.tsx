import { useQuery } from 'react-query'
import { AxiosError } from 'axios'
import { useEffect, useState } from 'react'
import { useParams } from 'react-router-dom'
import { Employee } from '../../../types/model/Employee'
import Card from '@mui/material/Card'
import CardHeader from '@mui/material/CardHeader'
import Avatar from '@mui/material/Avatar'
import IconButton from '@mui/material/IconButton'
import Typography from '@mui/material/Typography'
import MoreVertIcon from '@mui/icons-material/MoreVert'
import WorkHistoryIcon from '@mui/icons-material/WorkHistory'
import { Box, Button, Divider, Grid, Paper } from '@mui/material'
import PermContactCalendarIcon from '@mui/icons-material/PermContactCalendar'
import { getUserById } from '../../../api/user.api'
import ExpandMore from '../../../components/expandMore/ExpandMore'
import { getEmployeeById } from '../../../api/employee.api'
import FormInput from '../../../components/form/FormInput'
import FormLabel from '../../../components/form/FormLabel'
import { theme } from '../../../themes/baseTheme'
import { useFormik } from 'formik'
import { useMutation } from 'react-query'
import { useNavigate } from 'react-router-dom'
import DialogInfo, { DialogInfoParams } from '../../../components/dialogInfo/DialogInfo'
import { deleteEmployee, getEmployeeDetails, postEmployee, updateEmployee } from '../../../api/employee.api'
import { emptyForm, validationSchema } from '.././helper'
import SaveIcon from '@mui/icons-material/Save'
import ReplayIcon from '@mui/icons-material/Replay'
import CloseIcon from '@mui/icons-material/Close'
import EditIcon from '@mui/icons-material/Edit'
import DeleteIcon from '@mui/icons-material/Delete'
import FormSelect from '../../../components/form/FormSelect'
import { employeeStatusOptions, roleStatusOptions } from '../../../helpers/enum.helper'


const EmpDetails = () => {
    const params = useParams()
    const [readonlyMode, setReadonlyMode] = useState(true)
    const [initData, setInitData] = useState(emptyForm)
    const navigation = useNavigate()

    const [dialog, setDialog] = useState({
        open: false,
        dialogText: [''],
        confirmAction: () => {},
        confirmLabel: '',
    })

    const displayInfo = (params: DialogInfoParams) => {
        setDialog({
            ...params,
            open: true,
        })
    }

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
            console.error(error)
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

    const queryData = useQuery<Employee, AxiosError>(
        ['employee', { id: params.id }], 
        async () => getEmployeeById(params.id!&& params.id != 'new' ? params.id : ''), 
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
            return 'Pracownik jest dostępny'
        } else {
            return <p>{queryData.data?.unavailbilityDescription}</p>
        }
    }

    const mutationPost = useMutation({
        mutationFn: postEmployee,
        onSuccess(data) {
            displayInfo({
                dialogText: ['Nowy pracownik utworzony pomyślnie'],
                confirmAction: () => {
                    setDialog({ ...dialog, open: false })
                    if (data.id) navigation(`/employees/${data.id}`)
                    else navigation(`/employees`)
                },
                confirmLabel: 'Ok',
            })
        },
        onError(error: Error) {
            console.error(error)
            displayInfo({
                dialogText: ['Błąd poczas tworzenia nowego pracownika', error.message],
                confirmAction: () => {
                    setDialog({ ...dialog, open: false })
                },
                confirmLabel: 'Ok',
            })
        },
    })

    const mutationUpdate = useMutation({
        mutationFn: updateEmployee,
        onSuccess(data) {
            queryData.refetch({
                queryKey: ['employee', { id: data.id }],
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
            console.error(error)
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

    return params.id == 'new' ? 
    (<>
    <Box maxWidth={800} style={{ margin: 'auto', marginTop: '20px' }}>
        <Paper sx={{ p: '10px' }}>
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
            <FormLabel label="E-Mail" formik={formik} id={'email'} />
            <FormLabel label="Status" formik={formik} id={'status'} />
            <FormLabel label="Role" formik={formik} id={'roles'} />
        </Grid>
        <Divider
            orientation="vertical"
            variant="middle"
            style={{ borderColor: theme.palette.primary.main }}
            sx={{ mr: '-1px' }}
            flexItem
        />
        <Grid item xs={6}>
            <FormInput id={'firstName'} formik={formik} readonly={false} firstChild />
            <FormInput id={'lastName'} formik={formik} readonly={false} />
            <FormInput id={'email'} formik={formik} readonly={false} />
            <FormSelect
                                        id={'status'}
                                        formik={formik}
                                        readonly={readonlyMode}
                                        options={employeeStatusOptions()}
                                    />
            <FormSelect
                                        id={'role'}
                                        formik={formik}
                                        readonly={readonlyMode}
                                        options={roleStatusOptions()}
                                    />
        </Grid>
    </Grid>
    <Box sx={{ margin: '20px', gap: '20px', display: 'flex', flexDirection: 'row-reverse' }}>
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
    </Box>
        </Paper>
    </Box>
    </>) : (
        <Grid container alignItems="center" justifyContent="center" marginTop={2}>
            <Card sx={{ minWidth: 500, left: '50%' }}>
                <CardHeader
                    avatar={
                        <Avatar sx={{ bgcolor: 'red' }} aria-label="recipe">
                            R
                        </Avatar>
                    }
                    action={
                        <IconButton aria-label="settings">
                            <MoreVertIcon />
                        </IconButton>
                    }
                    title={`${queryData.data?.firstName} ${queryData.data?.lastName}`}
                    subheader={getEmployeeRoles()}
                />

                <ExpandMore
                    titleIcon={<PermContactCalendarIcon />}
                    title="Informacje kontaktowe"
                    cardContent={
                        <Typography variant="body2" color="text.secondary">
                            Numer telefonu: {queryData.data?.phone}, E-mail: {queryData.data?.email}
                        </Typography>
                    }
                />

                <ExpandMore
                    titleIcon={<WorkHistoryIcon />}
                    title={`Status: ${queryData.data?.status}`}
                    cardContent={getStatusDescription() || <p>historia</p>}
                />

                <ExpandMore
                    titleIcon={<WorkHistoryIcon />}
                    title="Historia pracy w tabeli"
                    cardContent={<p>Możemy tu wyświetlać historię pracy. Zlecenie, etap, data.</p>}
                />
            </Card>
        </Grid>
    );
}

export default EmpDetails
