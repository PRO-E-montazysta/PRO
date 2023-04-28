import { Box, Button, Paper, TextField } from '@mui/material'
import { CSSProperties, HTMLInputTypeAttribute, useMemo } from 'react'
import { theme } from '../../../themes/baseTheme'
import MultipleSelectChip from '../../base/Multiselect'
import SearchIcon from '@mui/icons-material/Search'
import ReplayIcon from '@mui/icons-material/Replay'

import './style.less'
import useBreakpoints from '../../../hooks/useBreakpoints'
import { useInputWidth } from '../../../hooks/useInputWidth'
import { SelectMenuItemProps } from '../../form/types'

export type Filter = {
    formik: any
    inputs: Array<any>
    structureStyle?: CSSProperties
    resetBtnStyle?: CSSProperties
    submitBtnStyle?: CSSProperties
}
export type FilterFormProps = {
    filterStructure: Array<FilterInputType>
    onSearch: (filters: any) => void
    onResetFilter: () => void
    structureStyle?: CSSProperties
    resetBtnStyle?: CSSProperties
    submitBtnStyle?: CSSProperties
}

export type FilterInputType = {
    id: string
    inputType: HTMLInputTypeAttribute | 'multiselect'
    placeholder?: string
    label?: string

    value: string | number | Date | Array<any>
    typeValue?: 'string' | 'number' | 'date' | 'Array'
    options?: Array<SelectMenuItemProps>
    // validations?: Array<Validation>

    style?: CSSProperties
}

const TableFilter = (props: Filter) => {
    const { formik, inputs, structureStyle, resetBtnStyle, submitBtnStyle } = props
    const appSize = useBreakpoints()

    const inputWidth = useInputWidth()
    return (
        <Box sx={{ width: '100%', minWidth: '280px' }}>
            <Paper sx={{ width: '100%', borderRadius: '5px', p: appSize.isMobile ? '10px' : '20px' }}>
                <form
                    noValidate
                    onSubmit={formik.handleSubmit}
                    style={{
                        ...structureStyle,
                        display: 'flex',
                        flexWrap: 'wrap',
                        gap: '15px',
                    }}
                    onReset={formik.handleReset}
                    className="filter"
                >
                    {inputs.map(({ id, inputType, value, ...props }) => {
                        switch (inputType) {
                            case 'multiselect':
                                return (
                                    <MultipleSelectChip
                                        key={id}
                                        menuItems={props.options ? props.options : []}
                                        id={id}
                                        label={props.label}
                                        value={formik.values[id]}
                                        formikSetFieldValue={formik.setFieldValue}
                                        boxStyle={{ width: inputWidth }}
                                    />
                                )

                            default:
                                return (
                                    <TextField
                                        key={id}
                                        id={id}
                                        name={id}
                                        label={props.label}
                                        value={formik.values[id]}
                                        type={inputType}
                                        onChange={formik.handleChange}
                                        error={formik.touched[id] && Boolean(formik.errors[id])}
                                        style={{ ...props.style, width: inputWidth }}
                                        variant={'outlined'}
                                        InputLabelProps={{
                                            shrink: true,
                                        }}
                                        className={'filter-form'}
                                        autoComplete="off"
                                    />
                                )
                        }
                    })}

                    <Box
                        sx={{
                            display: appSize.isMobile ? 'grid' : 'flex',
                            gap: '15px',
                            margin: 'auto 0 0 auto',
                            width: appSize.isMobile ? '100%' : 'auto',
                        }}
                    >
                        <Button
                            id="tableFilter-submit"
                            color="primary"
                            startIcon={<SearchIcon />}
                            style={{ ...submitBtnStyle, width: appSize.isMobile ? 'auto' : 120 }}
                            variant="contained"
                            type="submit"
                        >
                            Szukaj
                        </Button>
                        <Button
                            id="tableFilter-reset"
                            color="primary"
                            startIcon={<ReplayIcon style={{ transform: 'rotate(-0.25turn)' }} />}
                            style={{
                                width: appSize.isMobile ? 'auto' : 120,
                                color: theme.palette.primary.main,
                                ...resetBtnStyle,
                            }}
                            variant="outlined"
                            type="reset"
                        >
                            Reset
                        </Button>
                    </Box>
                </form>
            </Paper>
        </Box>
    )
}

export default TableFilter
