import { Box, Button, Paper, TextField } from "@mui/material";
import { useFormik, ErrorMessage, useField, Form, Formik, Field } from "formik";
import { CSSProperties, HTMLInputTypeAttribute, useState } from "react";
import { theme } from "../../../themes/baseTheme";

import { getInputs } from "./helper";
import './style.less'


export type FilterFormProps = {
    form: Array<FilterInputType>
    onSearch: (filters: any) => void
    onResetFilter: () => void
    formStyle?: CSSProperties
    resetBtnStyle?: CSSProperties
    submitBtnStyle?: CSSProperties
}


export type FilterInputType = {
    id: string
    type: HTMLInputTypeAttribute
    placefolder?: string
    label?: string

    value: string | number | Date
    typeValue?: 'string' | 'number' | 'date' | 'select'
    // options?: Array<Opt>
    // validations?: Array<Validation>

    style?: CSSProperties
}



const TableFilter = (props: FilterFormProps) => {
    const { form, onSearch, onResetFilter, formStyle, resetBtnStyle, submitBtnStyle } = props

    const { initialValues, inputs } = getInputs(form)

    const formik = useFormik({
        initialValues: initialValues,
        // validationSchema={{}}
        onSubmit: onSearch,
        onReset: onResetFilter
    })



    return <div>
        <Box sx={{ width: '100%', minWidth: '250px', }}>
            <Paper sx={{ width: '100%', borderRadius: '5px', padding: '20px' }}>
                <form
                    noValidate
                    onSubmit={formik.handleSubmit}
                    style={{
                        display: 'flex',
                        flexDirection: 'column',
                        gap: '15px',
                        ...formStyle
                    }}
                    onReset={formik.handleReset}
                    className='filter'>
                    {
                        inputs.map(({ id, type, value, ...props }) => {
                            return <TextField
                                key={id}
                                id={id}
                                name={id}
                                label={props.label}
                                value={formik.values[id]}
                                type={type}
                                onChange={formik.handleChange}
                                error={formik.touched[id] && Boolean(formik.errors[id])}
                                style={props.style}
                                variant={'outlined'}
                                InputLabelProps={{
                                    shrink: true
                                }}
                                className={'filter-form'}
                            />
                        })
                    }

                    <Box sx={{
                        display: 'flex',
                        flexDirection: 'column'
                    }}>
                        <Button color="primary" style={submitBtnStyle} variant="contained" type="submit">
                            Szukaj
                        </Button>
                        <Button color="primary" style={{ marginTop: '10px', color: theme.palette.primary.main, ...resetBtnStyle }} variant="outlined" type="reset" >
                            Reset
                        </Button>
                    </Box>
                </form>
            </Paper>
        </Box>
    </div>



}

export default TableFilter;









// type Opt = {
//     value: string | number
//     desc: string
// }
// type Validation = {
//     type: 'required' | 'isEmail' | 'minLength' | 'isTrue'
//     value?: string | number | boolean
//     message: string
// }











