import { MenuItem, FormControl, InputLabel, Select, SelectChangeEvent, OutlinedInput } from '@mui/material'
import { FormInputParams } from './types'

const FormSelect = (params: FormInputParams) => {
    const { id, readonly, label, options, style, formik } = params
    const value = formik.values[id]

    const handleOnChange = (event: SelectChangeEvent<HTMLInputElement>) => {
        const newValue = event.target.value
        formik.setFieldValue(id, newValue)
    }

    return (
        <FormControl variant="outlined" error={formik.touched[id] && Boolean(formik.errors[id])}>
            <InputLabel id={`label-${id}`} shrink>
                {label}
            </InputLabel>
            <Select
                fullWidth
                readOnly={readonly}
                name={id}
                labelId={`label-${id}`}
                input={<OutlinedInput notched label={label} />}
                style={{
                    minWidth: '300px',
                    maxWidth: '500px',
                }}
                value={value ? value : -1}
                onChange={handleOnChange}
            >
                <MenuItem key={-1} value={-1} disabled hidden style={{ display: 'none' }}>
                    -- WYBIERZ --
                </MenuItem>
                {options?.map((o) => (
                    <MenuItem key={o.key} value={o.key}>
                        {o.value}
                    </MenuItem>
                ))}
            </Select>
            {formik.touched[id] && Boolean(formik.errors[id]) && (
                <p style={{ fontSize: '12px', color: '#d32f2f', margin: '3px 14px 0 14px' }}>{formik.errors[id]}</p>
            )}
        </FormControl>
    )
}

export default FormSelect
