import { MenuItem, FormControl, InputLabel, Select, SelectChangeEvent, OutlinedInput } from '@mui/material'
import useBreakpoints from '../../hooks/useBreakpoints'
import { FormInputParams } from './types'
import styled from '@emotion/styled'

type CustomSelectProps = {
    readOnly: boolean
}

const CustomSelect = styled(Select)((props: CustomSelectProps) => ({
    '& fieldset': {
        border: props.readOnly ? 'none' : '',
    },
    '& svg': {
        display: props.readOnly ? 'none' : '',
    },
    '& div': {
        cursor: props.readOnly ? 'unset' : 'pointer',
    },
}))

const FormSelect = (params: FormInputParams) => {
    const { id, readonly, label, options, style, formik } = params
    const value = formik.values[id]

    const handleOnChange = (event: SelectChangeEvent<HTMLInputElement | unknown>) => {
        const newValue = event.target.value
        formik.setFieldValue(id, newValue)
    }

    const appSize = useBreakpoints()
    return (
        <FormControl variant="outlined" error={formik.touched[id] && Boolean(formik.errors[id])} style={{ ...style }}>
            <InputLabel id={`label-${id}`} shrink>
                {label}
            </InputLabel>
            <CustomSelect
                size={appSize.isMobile ? 'small' : 'medium'}
                fullWidth
                readOnly={readonly}
                name={id}
                labelId={`label-${id}`}
                input={<OutlinedInput notched label={label} />}
                value={value ? value : -1}
                onChange={handleOnChange}
            >
                <MenuItem key={-1} value={-1} disabled hidden style={{ display: 'none' }}>
                    -- WYBIERZ --
                </MenuItem>

                <MenuItem key={-2} value={''} disabled hidden style={{ display: 'none' }}>
                    -- WYBIERZ --
                </MenuItem>
                {options?.map((o) => (
                    <MenuItem key={o.key} value={o.key} id={`formSelect-${id}-opt-${o.key}`}>
                        {o.value}
                    </MenuItem>
                ))}
            </CustomSelect>
            {formik.touched[id] && Boolean(formik.errors[id]) && (
                <p style={{ fontSize: '12px', color: '#d32f2f', margin: '3px 14px 0 14px' }}>{formik.errors[id]}</p>
            )}
        </FormControl>
    )
}

export default FormSelect
