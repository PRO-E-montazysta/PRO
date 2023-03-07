import { Button, CircularProgress, Divider, Grid, MenuItem, Paper, Select, SelectChangeEvent, TextField, Typography } from "@mui/material";
import { Box } from "@mui/system";
import { AxiosError } from "axios";
import { useFormik } from "formik";
import { ChangeEvent, CSSProperties, HTMLInputTypeAttribute, StyleHTMLAttributes, useEffect, useState } from "react";
import { useQuery } from "react-query";
import { useParams } from "react-router-dom";
import { getAllCompanies } from "../../api/company.api";
import { getOrderDetails } from "../../api/order.api";
import { SelectMenuItemProps } from "../../components/base/Multiselect";
import { formatArrayToOptions, formatDate, formatLocation } from "../../helpers/format.helper";
import { priorityName, priorityOptions, statusName, statusOptions } from "../../helpers/enum.helper";
import { theme } from "../../themes/baseTheme";
import { Client } from "../../types/model/Client";
import { Company } from "../../types/model/Company";
import { Order } from "../../types/model/Order";
import { AppUser } from "../../types/model/AppUser";
import { getAllClients } from "../../api/client.api";
import { getAllForemans } from "../../api/foreman.api";
import { getAllLocations } from "../../api/location.api";
import { getAllManagers } from "../../api/manager.api";
import { getAllSalesReprezentatives } from "../../api/salesReprezentatives.api";
import { getAllSpecialists } from "../../api/specialist.api";

import SaveIcon from '@mui/icons-material/Save';
import ReplayIcon from '@mui/icons-material/Replay';
import CloseIcon from '@mui/icons-material/Close';
import EditIcon from '@mui/icons-material/Edit';
import DeleteIcon from '@mui/icons-material/Delete';

const emptyForm = {
    id: -1,
    name: '',
    typeOfStatus: '',
    plannedStart: '',
    plannedEnd: '',
    createdAt: '',
    editedAt: '',
    typeOfPriority: '',
    companyId: -1,
    managerId: -1,
    foremanId: -1,
    specialistId: -1,
    salesRepresentativeId: -1,
    locationId: -1,
    clientId: -1,
    orderStages: [],
    attachments: []
}

const OrderDetails = () => {
    const params = useParams();
    const [readonlyMode, setReadonlyMode] = useState(true)
    const [initData, setInitData] = useState(emptyForm)



    const formik = useFormik({
        initialValues: emptyForm,
        onSubmit: values => {
            console.log(values)
        }
    })

    const handleReset = () => {
        formik.setValues(JSON.parse(JSON.stringify(initData)))
    }
    const handleDelete = () => {
        
    }

    const { isLoading, isError, error, isSuccess, data } = useQuery<Order, AxiosError>(
        ['order', { id: params.id }],
        async () => getOrderDetails(params.id),
        {
            enabled: !!params.id && params.id != 'new'
        }
    )

    const queryClient = useQuery<Array<Client>, AxiosError>(
        ['client-list'], getAllClients
    )
    const queryCompany = useQuery<Array<Company>, AxiosError>(
        ['company-list'], getAllCompanies
    )
    const queryForeman = useQuery<Array<AppUser>, AxiosError>(
        ['foreman-list'], getAllForemans
    )
    const queryLocation = useQuery<Array<Location>, AxiosError>(
        ['location-list'], getAllLocations
    )
    const queryManager = useQuery<Array<AppUser>, AxiosError>(
        ['manager-list'], getAllManagers
    )
    const querySalesReprezentative = useQuery<Array<AppUser>, AxiosError>(
        ['sales-reprezentative-list'], getAllSalesReprezentatives
    )
    const querySpecialist = useQuery<Array<AppUser>, AxiosError>(
        ['specialist-list'], getAllSpecialists
    )


    useEffect(() => {
        if (data) {
            formik.setValues(JSON.parse(JSON.stringify(data)))
            setInitData(JSON.parse(JSON.stringify(data)))
            console.log(formik.values)
        }

    }, [data])

    useEffect(() => {
        if (params.id == 'new') setReadonlyMode(false)
        else setReadonlyMode(true)
        console.log("readonly: ", params.id != 'new')
        // setReadonlyMode(false)
    }, [params.id])



    return <>
        <Box maxWidth={800} style={{ margin: 'auto', marginTop: '20px' }}>

            <Paper sx={{ p: '10px' }}>
                {
                    isLoading ? <Box sx={{
                        display: 'flex',
                        minHeight: '200px',
                        justifyContent: 'center',
                        alignItems: 'center'
                    }}>
                        <CircularProgress />
                    </Box>
                        : <>
                            <Grid container>
                                <Grid item xs={6} style={{
                                    textAlign: 'end'
                                }}>
                                    <Typography m={2}>Nazwa zlecenia</Typography>
                                    <Typography m={2}>Firma</Typography>
                                    <Typography m={2}>Priorytet</Typography>
                                    <Typography m={2}>Status</Typography>
                                    <Typography m={2}>Planowany czas rozpoczęcia</Typography>
                                    <Typography m={2}>Planowany czas zakończenia</Typography>
                                    <Typography m={2}>Klient</Typography>
                                    <Typography m={2}>Foreman</Typography>
                                    <Typography m={2}>Lokalizacja</Typography>
                                    <Typography m={2}>Manager</Typography>
                                    <Typography m={2}>Specjalista</Typography>
                                    <Typography m={2}>Sales Reprezentative</Typography>
                                    <Typography m={2}>Czas utworzenia</Typography>
                                    <Typography m={2}>Czas ostatniej edycji</Typography>
                                </Grid>
                                <Divider
                                    orientation="vertical"
                                    variant="middle"
                                    style={{ borderColor: theme.palette.primary.main }}
                                    sx={{ mr: "-1px" }}
                                    flexItem
                                />
                                <Grid item xs={6}>
                                    <FormInput
                                        id={'name'}
                                        readonly={readonlyMode}
                                        value={formik.values.name}
                                        firstChild
                                        onChange={formik.handleChange}
                                    />
                                    <FormSelect
                                        id={'companyId'}
                                        readonly={readonlyMode}
                                        value={formik.values.companyId}
                                        options={formatArrayToOptions('id', (x: Company) => x.companyName, queryCompany.data)}
                                        formikSetFieldValue={formik.setFieldValue}
                                    />
                                    <FormSelect
                                        id={'typeOfPriority'}
                                        readonly={readonlyMode}
                                        value={formik.values.typeOfPriority}
                                        options={priorityOptions()}
                                        formikSetFieldValue={formik.setFieldValue}
                                    />
                                    <FormSelect
                                        id={'typeOfStatus'}
                                        readonly={readonlyMode}
                                        value={formik.values.typeOfStatus}
                                        options={statusOptions()}
                                        formikSetFieldValue={formik.setFieldValue}
                                    />
                                    <FormInput
                                        id={'plannedStart'}
                                        readonly={readonlyMode}
                                        value={formatDate(formik.values.plannedStart)}
                                        type='datetime-local'
                                        onChange={formik.handleChange}
                                    />
                                    <FormInput
                                        id={'plannedEnd'}
                                        readonly={readonlyMode}
                                        value={formatDate(formik.values.plannedEnd)}
                                        type='datetime-local'
                                        onChange={formik.handleChange}
                                    />
                                    <FormSelect
                                        id={'clientId'}
                                        readonly={readonlyMode}
                                        value={formik.values.clientId}
                                        options={formatArrayToOptions('id', (x: Client) => x.name, queryClient.data)}
                                        formikSetFieldValue={formik.setFieldValue}
                                    />
                                    <FormSelect
                                        id={'foremanId'}
                                        readonly={readonlyMode}
                                        value={formik.values.foremanId}
                                        options={formatArrayToOptions('id', (x: AppUser) => x.firstName + " " + x.lastName, queryForeman.data)}
                                        formikSetFieldValue={formik.setFieldValue}
                                    />
                                    <FormSelect
                                        id={'locationId'}
                                        readonly={readonlyMode}
                                        value={formik.values.locationId}
                                        options={formatArrayToOptions('id', formatLocation, queryLocation.data)}
                                        formikSetFieldValue={formik.setFieldValue}
                                    />
                                    <FormSelect
                                        id={'managerId'}
                                        readonly={readonlyMode}
                                        value={formik.values.managerId}
                                        options={formatArrayToOptions('id', (x: AppUser) => x.firstName + " " + x.lastName, queryManager.data)}
                                        formikSetFieldValue={formik.setFieldValue}
                                    />
                                    <FormSelect
                                        id={'specialistId'}
                                        readonly={readonlyMode}
                                        value={formik.values.specialistId}
                                        options={formatArrayToOptions('id', (x: AppUser) => x.firstName + " " + x.lastName, querySpecialist.data)}
                                        formikSetFieldValue={formik.setFieldValue}
                                    />
                                    <FormSelect
                                        id={'salesRepresentativeId'}
                                        readonly={readonlyMode}
                                        value={formik.values.salesRepresentativeId}
                                        options={formatArrayToOptions('id', (x: AppUser) => x.firstName + " " + x.lastName, querySalesReprezentative.data)}
                                        formikSetFieldValue={formik.setFieldValue}
                                    />
                                    <FormInput
                                        id={'createdAt'}
                                        readonly
                                        value={formatDate(formik.values.createdAt)}
                                        style={{ marginTop: !readonlyMode ? '12px' : '' }}
                                    />
                                    <FormInput
                                        id={'editedAt'}
                                        readonly
                                        value={formatDate(formik.values.editedAt)}
                                    />
                                </Grid>
                            </Grid>
                            <Box sx={{ margin: '20px', gap: '20px', display: 'flex', flexDirection: 'row-reverse' }}>

                                {
                                    (readonlyMode && params.id != 'new') ?
                                        <>
                                            <Button color="primary" startIcon={<EditIcon />} variant="contained" type="submit" style={{ width: 120 }}
                                                onClick={() => setReadonlyMode(false)}>
                                                Edytuj
                                            </Button>
                                            <Button color="error" startIcon={<DeleteIcon />} variant="contained" type="submit" style={{ width: 120 }}
                                                onClick={handleDelete}>
                                                Usuń
                                            </Button>
                                        </>
                                        :
                                        <>
                                            <Button color="primary" startIcon={<SaveIcon />} variant="contained" onClick={formik.submitForm} style={{ width: 120 }}>
                                                Zapisz
                                            </Button>
                                            <Button color="primary" startIcon={<ReplayIcon style={{ transform: 'rotate(-0.25turn)' }} />}
                                                style={{ color: theme.palette.primary.main, width: 120 }} variant="outlined" onClick={handleReset} >
                                                Reset
                                            </Button>
                                            <Button color="primary" startIcon={<CloseIcon style={{ transform: 'rotate(-0.25turn)' }} />}
                                                style={{ color: theme.palette.primary.main, width: 120 }} variant="outlined" onClick={() => { handleReset(); setReadonlyMode(true) }} >
                                                Anuluj
                                            </Button>
                                        </>
                                }
                            </Box>
                        </>
                }
            </Paper>
        </Box >
    </>
}

export default OrderDetails

type FormInputParams = {
    readonly: boolean
    id: string
    value: string | number | undefined
    onChange?: (value: ChangeEvent<HTMLInputElement>) => void
    formikSetFieldValue?: (id: string, value: any) => void
    firstChild?: boolean
    options?: Array<SelectMenuItemProps>
    style?: CSSProperties
    type?: HTMLInputTypeAttribute
}

const FormInput = (params: FormInputParams) => {
    const { id, readonly, value, onChange, firstChild, style, type } = params
    if (readonly) {
        if (value) return <Typography m={2} style={{
            textOverflow: 'ellipsis',
            overflow: 'hidden',
            whiteSpace: 'nowrap',
            ...style
        }}
        >{value}</Typography>
        else return <Typography m={2} style={{ opacity: 0, ...style }}>{'null'}</Typography>
    }
    else {
        return <TextField
            type={type ? type : 'text'}
            value={value}
            onChange={onChange}
            id={id}
            name={id}
            style={{
                display: 'block', margin: '8px', lineHeight: '32px',
                marginTop: firstChild ? '12px' : '8px',
                ...style
            }}
            inputProps={{
                style: {
                    padding: '4px 10px', lineHeight: '24px', width: '290px'
                }

            }}
        />
    }
}


const FormSelect = (params: FormInputParams) => {
    const { id, readonly, value, formikSetFieldValue, firstChild, options, style } = params

    const handleOnChange = (event: ChangeEvent<HTMLInputElement>) => {
        const newValue = event.target.value;
        if (formikSetFieldValue) formikSetFieldValue(id, newValue);
    }

    if (readonly) {
        if (value) {
            const thisOpt = options?.find(o => o.key == value)
            return <Typography m={2} style={{
                textOverflow: 'ellipsis',
                overflow: 'hidden',
                whiteSpace: 'nowrap',
                ...style
            }}>{thisOpt?.value}</Typography>
        }
        else return <Typography m={2} style={{ opacity: 0, ...style }}>{'null'}</Typography>
    }
    else {
        return <TextField select

            style={{
                display: 'block', margin: '8px', lineHeight: '32px', height: '32px',
                marginTop: firstChild ? '12px' : '8px', ...style
            }}

            sx={{
                '& .MuiSelect-select': { m: 0, p: '4px 0 4px 10px', width: '268px' },
            }}

            value={value ? value : -1} onChange={handleOnChange} >
            <MenuItem key={-1} value={-1} disabled hidden style={{ display: 'none' }}>
                -- WYBIERZ --
            </MenuItem>
            {
                options?.map(o => (
                    <MenuItem key={o.key} value={o.key}>
                        {o.value}
                    </MenuItem>
                ))
            }

        </TextField >
    }
}
