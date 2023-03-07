import { Button, Divider, Grid, MenuItem, Paper, Select, TextField, Typography } from "@mui/material";
import { Box } from "@mui/system";
import { AxiosError } from "axios";
import { useFormik } from "formik";
import { ChangeEvent, useEffect, useState } from "react";
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


const OrderDetails = () => {
    const params = useParams();
    const [readonlyMode, setReadonlyMode] = useState(true)
    const formik = useFormik({
        initialValues: {
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
        },
        onSubmit: values => {
            console.log(values)
        },
        onReset: () => {

        }
    })


    const { isLoading, isError, error, data } = useQuery<Order, AxiosError>(
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
        <Box maxWidth={800} style={{ margin: 'auto' }}>

            <Paper sx={{ p: '10px' }}>
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
                        <Typography m={2}>Czas ostatniej edycji</Typography>
                        <Typography m={2}>Czas utworzenia</Typography>
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
                            readOnlyMode={readonlyMode}
                            value={formik.values.name}
                            firstChild
                        />
                        <FormSelect
                            readOnlyMode={readonlyMode}
                            value={formik.values.companyId}
                            options={formatArrayToOptions('id', (x: Company) => x.companyName, queryCompany.data)}
                        />
                        <FormSelect
                            readOnlyMode={readonlyMode}
                            value={formik.values.typeOfPriority}
                            options={priorityOptions()}
                        />
                        <FormSelect
                            readOnlyMode={readonlyMode}
                            value={formik.values.typeOfStatus}
                            options={statusOptions()}
                        />
                        <FormInput
                            readOnlyMode={readonlyMode}
                            value={formatDate(formik.values.plannedStart)}
                        />
                        <FormInput
                            readOnlyMode={readonlyMode}
                            value={formatDate(formik.values.plannedEnd)}
                        />
                        <FormSelect
                            readOnlyMode={readonlyMode}
                            value={formik.values.clientId}
                            options={formatArrayToOptions('id', (x: Client) => x.name, queryClient.data)}
                        />
                        <FormSelect
                            readOnlyMode={readonlyMode}
                            value={formik.values.foremanId}
                            options={formatArrayToOptions('id', (x: AppUser) => x.firstName + " " + x.lastName, queryForeman.data)}
                        />

                        <FormSelect
                            readOnlyMode={readonlyMode}
                            value={formik.values.locationId}
                            options={formatArrayToOptions('id', formatLocation, queryLocation.data)}
                        />
                        <FormSelect
                            readOnlyMode={readonlyMode}
                            value={formik.values.managerId}
                            options={formatArrayToOptions('id', (x: AppUser) => x.firstName + " " + x.lastName, queryManager.data)}
                        />
                        <FormSelect
                            readOnlyMode={readonlyMode}
                            value={formik.values.specialistId}
                            options={formatArrayToOptions('id', (x: AppUser) => x.firstName + " " + x.lastName, querySpecialist.data)}
                        />
                        <FormSelect
                            readOnlyMode={readonlyMode}
                            value={formik.values.salesRepresentativeId}
                            options={formatArrayToOptions('id', (x: AppUser) => x.firstName + " " + x.lastName, querySalesReprezentative.data)}
                        />
                        <FormInput
                            readOnlyMode={readonlyMode}
                            value={formatDate(formik.values.createdAt)}
                        />
                        <FormInput
                            readOnlyMode={readonlyMode}
                            value={formatDate(formik.values.editedAt)}
                        />
                    </Grid>
                </Grid>
                <Box sx={{ margin: '20px', gap: '20px', display: 'flex', flexDirection: 'row-reverse' }}>

                    {
                        (readonlyMode && params.id != 'new') ?
                            <Button color="primary" startIcon={<EditIcon />} variant="contained" type="submit" style={{ width: 120 }}
                                onClick={() => setReadonlyMode(false)}>
                                Edytuj
                            </Button>
                            :
                            <>
                                <Button color="primary" startIcon={<SaveIcon />} variant="contained" type="submit" style={{ width: 120 }}>
                                    Zapisz
                                </Button>
                                <Button color="primary" startIcon={<ReplayIcon style={{ transform: 'rotate(-0.25turn)' }} />}
                                    style={{ color: theme.palette.primary.main, width: 120 }} variant="outlined" type="reset" >
                                    Reset
                                </Button>
                                <Button color="primary" startIcon={<CloseIcon style={{ transform: 'rotate(-0.25turn)' }} />}
                                    style={{ color: theme.palette.primary.main, width: 120 }} variant="outlined" type="reset" onClick={() => setReadonlyMode(true)} >
                                    Anuluj
                                </Button>
                            </>
                    }
                </Box>
            </Paper>
        </Box>
        {JSON.stringify(queryCompany.data)}
        {
            isLoading ? <p>Ładowanie...</p>
                : isError ? <p>{error.message}</p>
                    : JSON.stringify(data)
        }
    </>
}

export default OrderDetails

type FormInputParams = {
    readOnlyMode: boolean
    value: string | number | undefined
    onChange?: (value: ChangeEvent<HTMLInputElement>) => void
    firstChild?: boolean
    options?: Array<SelectMenuItemProps>
}

const FormInput = (params: FormInputParams) => {
    const { readOnlyMode, value, onChange, firstChild } = params
    if (readOnlyMode) {
        if (value) return <Typography m={2} style={{
            textOverflow: 'ellipsis',
            overflow: 'hidden',
            whiteSpace: 'nowrap'
        }}
        >{value}</Typography>
        else return <Typography m={2} style={{ opacity: 0 }}>{'null'}</Typography>
    }
    else {
        return <TextField style={{
            display: 'block', margin: '8px', lineHeight: '32px',
            marginTop: firstChild ? '12px' : '8px'
        }}
            inputProps={{
                style: {
                    padding: '4px 0 4px 10px', lineHeight: '24px', width: '300px'
                }

            }}
            value={value} onChange={onChange}></TextField >
    }
}


const FormSelect = (params: FormInputParams) => {
    const { readOnlyMode, value, onChange, firstChild, options } = params
    if (readOnlyMode) {
        if (value) {
            const thisOpt = options?.find(o => o.key == value)
            return <Typography m={2} style={{
                textOverflow: 'ellipsis',
                overflow: 'hidden',
                whiteSpace: 'nowrap'
            }}>{thisOpt?.value}</Typography>
        }
        else return <Typography m={2} style={{ opacity: 0 }}>{'null'}</Typography>
    }
    else {
        return <TextField select

            style={{
                display: 'block', margin: '8px', lineHeight: '32px', height: '32px',
                marginTop: firstChild ? '12px' : '8px'
            }}

            sx={{
                '& .MuiSelect-select': { m: 0, p: '4px 0 4px 10px', width: '268px' },
            }}

            value={value} onChange={onChange} >
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
