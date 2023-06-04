import { Box, Button, Paper, Switch, Typography } from '@mui/material'
import FormSwitch from '../../components/form/FormSwitch'
import { useFormik } from 'formik'
import MultipleSelect from '../../components/base/Multiselect'
import { statusOptions } from '../../helpers/enum.helper'
import useBreakpoints from '../../hooks/useBreakpoints'

const CalendarFilter = () => {
    const appSize = useBreakpoints()
    const formikFilter = useFormik({
        initialValues: {
            filterByOrder: false,
            orderIdCollection: [-1],
            statusIdCollection: [],
            filterByUnavilibility: false,
            unavilibilityPersonCollection: [-1],
            unavilibilityTypeCollection: [],
        },
        onSubmit: (data: any) => {
            console.log(data)
        },
    })
    return (
        <Paper
            sx={{
                p: '25px',
                mb: '20px',
                display: appSize.isMobile || appSize.isTablet ? '' : 'grid',
                gridTemplateColumns: '1fr 1fr',
                gap: '20px',
            }}
        >
            <Box sx={{ display: 'grid' }}>
                <Box sx={{ display: 'flex' }}>
                    <Typography variant="h6">Zlecenia</Typography>
                </Box>
                <Box sx={{ display: 'grid', gap: '20px', mt: '15px' }}>
                    <MultipleSelect
                        menuItems={[
                            {
                                key: -1,
                                value: 'Moje zlecenia',
                            },
                            {
                                key: -2,
                                value: 'Wszystkie zlecenia',
                            },
                            {
                                key: 1,
                                value: 'Zlecenie 1',
                            },
                            {
                                key: 2,
                                value: 'Zlecenie 2',
                            },
                            {
                                key: 3,
                                value: 'Zlecenie 3',
                            },
                        ]}
                        id={'orderIdCollection'}
                        value={formikFilter.values.orderIdCollection}
                        formikSetFieldValue={function (id: string, value: any): void {
                            formikFilter.setFieldValue(id, value)
                        }}
                        label="Zlecenia"
                        boxStyle={{
                            width: '100%',
                        }}
                    />
                    <MultipleSelect
                        menuItems={statusOptions()}
                        id={'statusIdCollection'}
                        value={formikFilter.values.statusIdCollection}
                        formikSetFieldValue={function (id: string, value: any): void {
                            formikFilter.setFieldValue(id, value)
                        }}
                        label="Status"
                        boxStyle={{
                            width: '100%',
                        }}
                    />
                </Box>
            </Box>

            <Box sx={{ display: 'grid' }}>
                <Box sx={{ display: 'flex' }}>
                    <Typography variant="h6">Nieobecności</Typography>
                </Box>
                <Box
                    sx={{
                        display: 'grid',
                        gap: '20px',
                        mt: '15px',
                    }}
                >
                    <MultipleSelect
                        menuItems={[
                            {
                                key: -1,
                                value: 'Moje nieobecności',
                            },
                            {
                                key: -2,
                                value: 'Nieobecności wszystkich',
                            },
                            {
                                key: 1,
                                value: 'Ania',
                            },
                            {
                                key: 2,
                                value: 'Hania',
                            },
                            {
                                key: 3,
                                value: 'Bania',
                            },
                        ]}
                        id={'unavilibilityPersonCollection'}
                        value={formikFilter.values.unavilibilityPersonCollection}
                        formikSetFieldValue={function (id: string, value: any): void {
                            formikFilter.setFieldValue(id, value)
                        }}
                        label="Nieobecności"
                        boxStyle={{
                            width: '100%',
                        }}
                    />
                    <MultipleSelect
                        menuItems={[
                            {
                                key: 1,
                                value: 'Urlop',
                            },
                            {
                                key: 2,
                                value: 'Delegacja',
                            },
                        ]}
                        id={'statusIdCollection'}
                        value={formikFilter.values.statusIdCollection}
                        formikSetFieldValue={function (id: string, value: any): void {
                            formikFilter.setFieldValue(id, value)
                        }}
                        label="Rodzaj"
                        boxStyle={{
                            width: '100%',
                        }}
                    />
                </Box>
            </Box>
        </Paper>
    )
}

export default CalendarFilter
