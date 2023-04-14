import { Box } from '@mui/system'

import FormInput from '../../components/form/FormInput'
import FormSelect from '../../components/form/FormSelect'
import { useInputWidth } from '../../hooks/useInputWidth'
import { FormInputProps } from '../../types/form'

type FormStructureParams = {
    formStructure: Array<FormInputProps>
    formik: any
    readonlyMode: boolean
}

export const FormStructure = (params: FormStructureParams) => {
    const { formStructure, formik, readonlyMode } = params

    const inputWidth = useInputWidth()

    return (
        <Box sx={{ display: 'flex', flexWrap: 'wrap', gap: '15px' }}>
            {formStructure.map((e) => {
                switch (e.type) {
                    case 'input':
                        return (
                            <FormInput
                                style={{ width: inputWidth }}
                                label={e.label}
                                formik={formik}
                                id={e.id}
                                readonly={readonlyMode || !!e.readonly}
                                key={e.id}
                            />
                        )
                    case 'date':
                        return (
                            <FormInput
                                style={{ width: inputWidth }}
                                label={e.label}
                                formik={formik}
                                id={e.id}
                                readonly={readonlyMode || !!e.readonly}
                                key={e.id}
                                type={'datetime-local'}
                            />
                        )
                    case 'select':
                        return (
                            <FormSelect
                                style={{ width: inputWidth }}
                                label={e.label}
                                formik={formik}
                                id={e.id}
                                readonly={readonlyMode || !!e.readonly}
                                key={e.id}
                                options={e.options}
                            />
                        )
                }
            })}
        </Box>
    )
}
