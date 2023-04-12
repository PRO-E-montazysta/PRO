import { Box } from '@mui/system'

import FormInput from '../../components/form/FormInput'
import FormSelect from '../../components/form/FormSelect'
import { FormInputProps } from '../../pages/orders/helper'

type FormStructureParams = {
    formStructure: Array<FormInputProps>
    formik: any
    readonlyMode: boolean
}

export const FormStructure = (params: FormStructureParams) => {
    const { formStructure, formik, readonlyMode } = params

    return (
        <Box sx={{ p: '20px', display: 'flex', flexWrap: 'wrap', gap: '20px' }}>
            {formStructure.map((e) => {
                switch (e.type) {
                    case 'input':
                        return (
                            <FormInput
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
