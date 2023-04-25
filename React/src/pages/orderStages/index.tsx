import { Grid, Paper, Box, Button } from '@mui/material'
import TextField from '@mui/material/TextField'
import dayjs, { Dayjs } from 'dayjs'
import * as yup from 'yup'
import { useFormik, useFormikContext } from 'formik'
import { useNavigate, useParams } from 'react-router-dom'
import { useMutation } from 'react-query'
import { MouseEvent, useEffect, useState } from 'react'
import { DatePicker } from '@mui/x-date-pickers'
import { LocalizationProvider } from '@mui/x-date-pickers/LocalizationProvider'

const OrderStages = () => {
    // const navigation = useNavigate()
    // const params = useParams()
    // const [plannedStartDate, setPlannedStartDate] = useState<Dayjs | null>(null)
    // const [plannedEndDate, setPlannedEndDate] = useState<Dayjs | null>(null)
    // // const { setFieldValue } = useFormikContext()

    // useEffect(() => {
    //     console.log('here4', plannedStartDate)
    //     const x = dayjs(plannedStartDate).format('YYYY-MM-DDTHH:mm:ss')
    // }, [plannedStartDate])

    // const { isLoading, isError, error, mutate } = useMutation({
    //     // mutationFn: (variables: any) => console.log(variables),
    //     mutationFn: createOrderStage,
    //     onSuccess(data) {
    //         console.log('navigation')
    //         navigation('/', { replace: true })
    //     },
    //     onError(error: Error) {
    //         alert(error.message)
    //         console.error(error)
    //     },
    // })

    // const validationSchema = yup.object({
    //     name: yup.string().min(4, 'Nazwa powinna składać się z minium 4 znaków ').required('Nazwa jest wymagana'),
    //     price: yup.number().required('Proszę podać cenę'),
    //     order: yup.number().required('Proszę podać kolejność'),
    //     // plannedStartDate: yup.string().required('Proszę podać planowaną datę staru'),
    //     plannedDurationTime: yup.number().required('Proszę podać planowany czas trwania w dniach'),
    //     plannedFittersNumber: yup.number().required('Proszę podać planowaną liczbę montażystów'),
    //     minimumImagesNumber: yup.number().required('Proszę podać minimalną liczbę zdjęć'),
    //     foremanId: yup.number().required('Proszę przypisać brygadzistę'),
    //     // tools: yup.number().required('Proszę podać listę narzędzi'),
    //     // elements: yup.number().required('Proszę podać listę elementów'),
    // })

    // const formatDate = (date: Dayjs) => {
    //     const check = dayjs(date).format('YYYY-MM-DDTHH:mm:ss.SSS')
    //     console.log('data check', check)
    //     return check
    // }

    // const formik = useFormik({
    //     initialValues: {
    //         orderId: Number(params.id!),
    //         name: '',
    //         status: 'TODO',
    //         price: 0,
    //         order: 0,
    //         plannedDurationTime: 0,
    //         plannedFittersNumber: 0,
    //         minimumImagesNumber: 0,
    //         // fitters: '',
    //         foremanId: 0,
    //         tools: [],
    //         elements: [],
    //         attachments: [],
    //         //niepotrzebne ale tylko do endpointa
    //         plannedStartDate: null,
    //         plannedEndDate: null,
    //         startDate: null,
    //         endDate: null,
    //         fitters: [],
    //         comments: [],
    //         toolReleases: [],
    //         elementReturnReleases: [],
    //         notifications: [],
    //         demandAdHocs: [],
    //     },
    //     validationSchema: validationSchema,
    //     onSubmit: async (values) => {
    //         console.log('dziala?', values)
    //         return mutate(values)
    //     },
    // })

    return (
        <h1>to delete</h1>
        // <Box maxWidth="90%" style={{ margin: 'auto', marginTop: '20px' }}>
        //     <Paper sx={{ p: '10px' }}>
        //         <Box
        //             component="h2"
        //             sx={{
        //                 display: 'flex',
        //                 minHeight: '30px',
        //                 justifyContent: 'center',
        //                 alignContent: 'center',
        //                 borderBottom: '1px solid',
        //             }}
        //         >
        //             Dodaj etap zlecenia
        //         </Box>
        //         <Grid
        //             component="form"
        //             noValidate
        //             onSubmit={formik.handleSubmit}
        //             container
        //             spacing={{ xs: 2, md: 3 }}
        //             columns={{ xs: 2, sm: 8, md: 10 }}
        //         >
        //             <Grid item xs={2}>
        //                 <TextField
        //                     sx={{ width: '100%' }}
        //                     required
        //                     id="outlined-required"
        //                     label="Nazwa"
        //                     name="name"
        //                     value={formik.values.name}
        //                     onChange={formik.handleChange}
        //                     error={formik.touched.name && Boolean(formik.errors.name)}
        //                     helperText={formik.touched.name && formik.errors.name}
        //                 />
        //             </Grid>
        //             <Grid item xs={2}>
        //                 <TextField
        //                     sx={{ width: '100%' }}
        //                     required
        //                     id="outlined-required"
        //                     label="Cena"
        //                     name="price"
        //                     type="number"
        //                     value={formik.values.price}
        //                     onChange={formik.handleChange}
        //                     error={formik.touched.price && Boolean(formik.errors.price)}
        //                     helperText={formik.touched.price && formik.errors.price}
        //                 />
        //             </Grid>
        //             <Grid item xs={2}>
        //                 <TextField
        //                     sx={{ width: '100%' }}
        //                     required
        //                     id="outlined-required"
        //                     label="Kolejność"
        //                     name="order"
        //                     type="number"
        //                     value={formik.values.order}
        //                     onChange={formik.handleChange}
        //                     error={formik.touched.order && Boolean(formik.errors.order)}
        //                     helperText={formik.touched.order && formik.errors.order}
        //                 />
        //             </Grid>
        //             <Grid item xs={2}>
        //                 <TextField
        //                     sx={{ width: '100%' }}
        //                     required
        //                     type="number"
        //                     id="outlined-required"
        //                     label="Planowany czas trwania (w dniach)"
        //                     name="plannedDurationTime"
        //                     value={formik.values.plannedDurationTime}
        //                     onChange={formik.handleChange}
        //                     error={formik.touched.plannedDurationTime && Boolean(formik.errors.plannedDurationTime)}
        //                     helperText={formik.touched.plannedDurationTime && formik.errors.plannedDurationTime}
        //                 />
        //             </Grid>
        //             {/* <Grid item xs={2}>
        //                 <LocalizationProvider dateAdapter={AdapterDayjs}>
        //                     <DatePicker
        //                         label="Planowana data startu"
        //                         value={plannedStartDate}
        //                         onChange={(data) => {
        //                             const formattedDate = dayjs(data).format('YYYY-MM-DDTHH:mm:ss.SSS')
        //                             setPlannedStartDate(data)
        //                             setFieldValue(plannedStartDate, formattedDate)
        //                         }}
        //                     />
        //                 </LocalizationProvider>
        //             </Grid>
        //             <Grid item xs={2}>
        //                 <LocalizationProvider dateAdapter={AdapterDayjs}>
        //                     <DatePicker
        //                         label="Planowana data końca"
        //                         value={plannedEndDate}
        //                         onChange={(data) => {
        //                             const formattedDate = dayjs(data).format('YYYY-MM-DDTHH:mm:ssZ[Z]')
        //                             setPlannedEndDate(data)
        //                             setFieldValue(plannedStartDate, formattedDate)

        //                         }}
        //                     />
        //                 </LocalizationProvider>
        //             </Grid> */}
        //             {/* <Grid item xs={2}>
        //                 <TextField
        //                     sx={{ width: '100%' }}
        //                     required
        //                     id="outlined-required"
        //                     label="Planowany czas trwania"
        //                     name="plannedDurationTime"
        //                     value={formik.values.plannedDurationTime}
        //                     onChange={formik.handleChange}
        //                 />
        //             </Grid> */}

        //             <Grid item xs={2}>
        //                 <TextField
        //                     sx={{ width: '100%' }}
        //                     required
        //                     id="outlined-required"
        //                     label="Planowana liczba montażystów"
        //                     name="plannedFittersNumber"
        //                     type="number"
        //                     value={formik.values.plannedFittersNumber}
        //                     onChange={formik.handleChange}
        //                     error={formik.touched.plannedFittersNumber && Boolean(formik.errors.plannedFittersNumber)}
        //                     helperText={formik.touched.plannedFittersNumber && formik.errors.plannedFittersNumber}
        //                 />
        //             </Grid>
        //             <Grid item xs={2}>
        //                 <TextField
        //                     sx={{ width: '100%' }}
        //                     required
        //                     id="outlined-required"
        //                     label="Minimalna liczba zdjęć"
        //                     name="minimumImagesNumber"
        //                     type="number"
        //                     value={formik.values.minimumImagesNumber}
        //                     onChange={formik.handleChange}
        //                     error={formik.touched.minimumImagesNumber && Boolean(formik.errors.minimumImagesNumber)}
        //                     helperText={formik.touched.minimumImagesNumber && formik.errors.minimumImagesNumber}
        //                 />
        //             </Grid>
        //             <Grid item xs={2}>
        //                 <TextField
        //                     sx={{ width: '100%' }}
        //                     required
        //                     id="outlined-required"
        //                     label="Narzędzia"
        //                     name="tools"
        //                     type="number"
        //                     value={formik.values.tools}
        //                     onChange={formik.handleChange}
        //                     error={formik.touched.tools && Boolean(formik.errors.tools)}
        //                     helperText={formik.touched.tools && formik.errors.tools}
        //                 />
        //             </Grid>
        //             <Grid item xs={2}>
        //                 <TextField
        //                     sx={{ width: '100%' }}
        //                     required
        //                     id="outlined-required"
        //                     label="Elementy"
        //                     name="elements"
        //                     type="number"
        //                     value={formik.values.elements}
        //                     onChange={formik.handleChange}
        //                     error={formik.touched.elements && Boolean(formik.errors.elements)}
        //                     helperText={formik.touched.elements && formik.errors.elements}
        //                 />
        //             </Grid>
        //             <Grid item xs={2}>
        //                 <TextField sx={{ width: '100%' }} label="Załączniki" name="attachments" />
        //             </Grid>

        //             <Grid spacing={2} container justifyContent="flex-end" sx={{ marginTop: '20px' }}>
        //                 <Grid item>
        //                     <Button type="submit" color="primary" variant="contained" disabled={isLoading}>
        //                         Dodaj etap
        //                     </Button>
        //                 </Grid>
        //                 <Grid item>
        //                     <Button
        //                         color="info"
        //                         // startIcon={< />}
        //                         variant="contained"
        //                         type="submit"
        //                     >
        //                         Wróc do zlecenia
        //                     </Button>
        //                 </Grid>
        //             </Grid>
        //         </Grid>
        //     </Paper>
        // </Box>
    )
}

export default OrderStages
