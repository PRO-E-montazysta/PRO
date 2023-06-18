import { FormControl, FormControlLabel, InputLabel, Switch, TextField, styled } from '@mui/material'
import { formatDate } from '../../helpers/format.helper'
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

const FormSwitch = (params: FormInputParams) => {
    const { id, readonly, style, type, formik, label } = params

    const appSize = useBreakpoints()
    return (
        <FormControl variant="outlined" style={{ justifyContent: 'center', ...style }}>
            <InputLabel id={`label-${id}`} shrink>
                {label}
            </InputLabel>
            <Switch
                size={appSize.isMobile ? 'small' : 'medium'}
                readOnly={readonly}
                checked={formik.values[id] == 1}
                onChange={(e) => {
                    if (!readonly) formik.setFieldValue(id, e.target.checked ? 1 : 0)
                }}
                id={id}
            />
            {formik.touched[id] && Boolean(formik.errors[id]) && (
                <p style={{ fontSize: '12px', color: '#d32f2f', margin: '3px 14px 0 14px' }}>{formik.errors[id]}</p>
            )}
        </FormControl>
    )
}

export default FormSwitch
