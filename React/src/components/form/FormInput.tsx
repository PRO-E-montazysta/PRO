import { TextField, Typography } from '@mui/material'
import { formatDate, formatShortDate } from '../../helpers/format.helper'
import { FormInputParams } from './types'

const FormInput = (params: FormInputParams) => {
    const { id, readonly, firstChild, style, type, formik } = params
    const value =
        type == 'datetime-local'
            ? formatDate(formik.values[id])
            : type == 'date'
            ? formatShortDate(formik.values[id])
            : String(formik.values[id])

    if (readonly) {
        if (value)
            return (
                <Typography
                    m={2}
                    style={{
                        textOverflow: 'ellipsis',
                        overflow: 'hidden',
                        whiteSpace: 'nowrap',
                        ...style,
                    }}
                >
                    {value}
                </Typography>
            )
        else
            return (
                <Typography m={2} style={{ opacity: 0, ...style }}>
                    {'null'}
                </Typography>
            )
    } else {
        return (
            <TextField
                type={type ? type : 'text'}
                value={value}
                onChange={formik.handleChange}
                id={id}
                name={id}
                error={formik.touched[id] && Boolean(formik.errors[id])}
                helperText={formik.touched[id] && formik.errors[id]}
                autoComplete="off"
                style={{
                    display: 'block',
                    margin: '8px',
                    lineHeight: '32px',
                    marginTop: firstChild ? '12px' : '8px',
                    ...style,
                }}
                inputProps={{
                    style: {
                        padding: '4px 10px',
                        lineHeight: '24px',
                        width: '290px',
                    },
                }}
            />
        )
    }
}

export default FormInput
