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
    const [value, setValue] = useState<any>(formik.values[id] || undefined)
    useLayoutEffect(() => {
        formatValue()
    }, [formik.values[id]])

    const formatValue = async () => {
        let w
        if (type == 'can-be-deleted' && formatFn) w = await formatFn(formik.values[id])
        const formattedValue = !formik.values[id]
            ? undefined
            : type == 'datetime-local'
            ? formatDate(formik.values[id])
            : type == 'date'
            ? formatShortDate(formik.values[id])
            : w
            ? w
            : formik.values[id]

        setValue(formattedValue)
    }

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
            value={value}
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
