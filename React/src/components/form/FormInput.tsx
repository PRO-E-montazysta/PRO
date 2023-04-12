import { Box, TextField, Typography } from '@mui/material'
import { formatDate } from '../../helpers/format.helper'
import FormLabel from './FormLabel'
import { FormInputParams } from './types'

const FormInput = (params: FormInputParams) => {
    const { id, readonly, style, type, formik, label } = params
    const value = type == 'datetime-local' ? formatDate(formik.values[id]) : String(formik.values[id])

    return (
        <TextField
            InputLabelProps={{
                shrink: true,
            }}
            InputProps={{
                readOnly: readonly,
            }}
            sx={{ minWidth: '300px' }}
            label={label}
            variant="outlined"
            type={type ? type : 'text'}
            value={value}
            onChange={formik.handleChange}
            id={id}
            name={id}
            error={formik.touched[id] && Boolean(formik.errors[id])}
            helperText={formik.touched[id] && formik.errors[id]}
            autoComplete="off"
        />
    )
}

export default FormInput
