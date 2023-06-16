import { TextField, styled } from '@mui/material'
import { formatDate, formatShortDate } from '../../helpers/format.helper'
import useBreakpoints from '../../hooks/useBreakpoints'
import { FormInputParams } from './types'
import { useEffect, useLayoutEffect, useState } from 'react'

type CustomTextFieldProps = {
    readOnly: boolean
}

export const CustomTextField = styled(TextField)((props: CustomTextFieldProps) => ({
    '& fieldset': {
        border: props.readOnly ? 'none' : '',
    },
    '& .MuiInputBase-input.Mui-disabled': {
        WebkitTextFillColor: '#000000',
    },
}))

const FormInput = (params: FormInputParams) => {
    const { id, readonly, style, type, formik, label, formatFn, placeholder } = params

    const formattedValue =
        type == 'datetime-local'
            ? formatDate(formik.values[id])
            : type == 'date'
            ? formatShortDate(formik.values[id])
            : type == 'can-be-deleted' && formatFn
            ? formatFn(formik.values[id])
            : formik.values[id]

    const appSize = useBreakpoints()
    return (
        <CustomTextField
            readOnly={readonly}
            size={appSize.isMobile ? 'small' : 'medium'}
            InputLabelProps={{
                shrink: true,
            }}
            InputProps={{
                readOnly: readonly,
            }}
            style={style}
            label={label}
            variant="outlined"
            type={type ? type : 'text'}
            value={formattedValue}
            onChange={formik.handleChange}
            id={id}
            name={id}
            error={formik.touched[id] && Boolean(formik.errors[id])}
            helperText={formik.touched[id] && formik.errors[id]}
            autoComplete="off"
            placeholder={placeholder ? placeholder : ''}
        />
    )
}

export default FormInput
