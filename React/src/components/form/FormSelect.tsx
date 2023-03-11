import { TextField, Typography, MenuItem } from "@mui/material";
import { ChangeEvent } from "react";
import { FormInputParams } from "./types";

const FormSelect = (params: FormInputParams) => {
    const { id, readonly, firstChild, options, style, formik } = params
    const value = formik.values[id]


    const handleOnChange = (event: ChangeEvent<HTMLInputElement>) => {
        const newValue = event.target.value;
        formik.setFieldValue(id, newValue);
    }

    if (readonly) {
        if (value) {
            const thisOpt = options?.find(o => o.key == value)
            return <Typography m={2} style={{
                textOverflow: 'ellipsis',
                overflow: 'hidden',
                whiteSpace: 'nowrap',
                ...style
            }}>{thisOpt?.value}</Typography>
        }
        else return <Typography m={2} style={{ opacity: 0, ...style }}>{'null'}</Typography>
    }
    else {
        return <TextField select


            style={{
                display: 'block', margin: '8px', lineHeight: '32px',
                marginTop: firstChild ? '12px' : '8px', ...style
            }}

            sx={{
                '& .MuiSelect-select': { m: 0, p: '4px 0 4px 10px', width: '268px' },
            }}
            error={formik.touched[id] && Boolean(formik.errors[id])}
            helperText={formik.touched[id] && formik.errors[id]}

            value={value ? value : -1} onChange={handleOnChange} >
            <MenuItem key={-1} value={-1} disabled hidden style={{ display: 'none' }}>
                -- WYBIERZ --
            </MenuItem>
            {
                options?.map(o => (
                    <MenuItem key={o.key} value={o.key}>
                        {o.value}
                    </MenuItem>
                ))
            }

        </TextField >
    }
}

export default FormSelect