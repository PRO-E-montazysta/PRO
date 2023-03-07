import { Typography } from "@mui/material"
import { FormLabelProps } from "./types"

const FormLabel = (props: FormLabelProps) => {
    const { label, formik, id } = props
    const hasError = formik.touched[id] && Boolean(formik.errors[id])
    return <Typography m={2} style={{ marginBottom: hasError ? '39px' : '' }}>{label}</Typography>
}

export default FormLabel