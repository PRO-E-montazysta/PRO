import { TextField, styled } from '@mui/material'
import { formatDate, formatShortDate } from '../../helpers/format.helper'
import useBreakpoints from '../../hooks/useBreakpoints'
import { FormInputParams } from './types'

type CustomTextFieldProps = {
    readOnly: boolean
}

const CustomTextField = styled(TextField)((props: CustomTextFieldProps) => ({
    '& fieldset': {
        border: props.readOnly ? 'none' : '',
    },
}))

const FormInput = (params: FormInputParams) => {
    const { id, readonly, style, type, formik, label, placeholder } = params
    const value =
        type == 'datetime-local'
            ? formatDate(formik.values[id])
            : type == 'date'
            ? formatShortDate(formik.values[id])
            : String(formik.values[id])

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
            // sx={{ minWidth: '300px' }}
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
