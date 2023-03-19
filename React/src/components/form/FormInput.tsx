import { Box, TextField, Typography } from '@mui/material'
import { formatDate } from '../../helpers/format.helper'
import FormLabel from './FormLabel'
import { FormInputParams } from './types'

const FormInput = (params: FormInputParams) => {
    const { id, readonly, style, type, formik } = params
    const value = type == 'datetime-local' ? formatDate(formik.values[id]) : String(formik.values[id])
    if (readonly) {
        const Lbl = <FormLabel label="Nazwa zlecenia" formik={formik} id={'name'} />
        if (value)
            return (
                <Box>
                    {Lbl}
                    <Typography
                        m={2}
                        // style={{
                        //     textOverflow: 'ellipsis',
                        //     overflow: 'hidden',
                        //     whiteSpace: 'nowrap',
                        //     ...style,
                        // }}
                    >
                        {value}
                    </Typography>
                </Box>
            )
        else
            return (
                <Box>
                    {Lbl}
                    <Typography m={2} style={{ opacity: 0, ...style }}>
                        {'null'}
                    </Typography>
                </Box>
            )
    } else {
        return (
            <TextField
                label={'lel'}
                variant="outlined"
                type={type ? type : 'text'}
                value={value}
                onChange={formik.handleChange}
                id={id}
                name={id}
                error={formik.touched[id] && Boolean(formik.errors[id])}
                helperText={formik.touched[id] && formik.errors[id]}
                autoComplete="off"
                // style={{
                //     display: 'block',
                //     margin: '8px',
                //     lineHeight: '32px',
                //     marginTop: firstChild ? '12px' : '8px',
                //     ...style,
                // }}
                // inputProps={{
                //     style: {
                //         padding: '4px 10px',
                //         lineHeight: '24px',
                //         width: '290px',
                //     },
                // }}
            />
        )
    }
}

export default FormInput
