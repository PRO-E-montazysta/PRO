import { TextField } from "@mui/material";
import { useFormik, ErrorMessage, useField, Form, Formik, Field } from "formik";

type FilterTextInputProps = {
    name: string
    type: string
    label?: string
    [x: string]: any
}



const FilterTextInput = (props: FilterTextInputProps) => {
    // const [field] = useField(props)
    return <>
        <TextField
            // {...field}
            {...props}
            label={props.label}
            variant={'outlined'}
            InputLabelProps={{
                shrink: true
            }}

        />
        <ErrorMessage name={props.name} component='span' />


    </>
}


export default FilterTextInput;