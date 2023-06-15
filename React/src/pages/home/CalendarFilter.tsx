import { Box, Paper, Typography } from '@mui/material'
import MultipleSelect from '../../components/base/Multiselect'
import { statusOptions, typeOfUnavailabilityOptions } from '../../helpers/enum.helper'
import useBreakpoints from '../../hooks/useBreakpoints'
import { useEmployeesAsOptions, useOrdersAsOptions } from './hooks'

type CalendarFilterProps = {
    formikFilter: any
}

export type CalendarFilters = {
    orderIdCollection: number[]
    statusIdCollection: number[]
    unavilibilityPersonCollection: number[]
    unavilibilityTypeCollection: string[]
}

export const calendarFilterDefaultValues: CalendarFilters = {
    orderIdCollection: [],
    statusIdCollection: [],
    unavilibilityPersonCollection: [],
    unavilibilityTypeCollection: [],
}

const CalendarFilter = ({ formikFilter }: CalendarFilterProps) => {
    const appSize = useBreakpoints()

    const orderOptions = useOrdersAsOptions()
    const employeeOptions = useEmployeesAsOptions()

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
                        menuItems={orderOptions}
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
                        menuItems={employeeOptions}
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
                        menuItems={typeOfUnavailabilityOptions()}
                        id={'unavilibilityTypeCollection'}
                        value={formikFilter.values.unavilibilityTypeCollection}
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
