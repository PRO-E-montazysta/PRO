import { Box } from '@mui/system'

import FormInput from '../../components/form/FormInput'
import FormSelect from '../../components/form/FormSelect'
import { useInputWidth } from '../../hooks/useInputWidth'
import { FormInputProps, PageMode } from '../../types/form'
import { isAuthorized } from '../../utils/authorize'

type FormStructureParams = {
    formStructure: Array<FormInputProps>
    formik: any
    readonlyMode: boolean //deprecated
    pageMode?: PageMode
}

export const FormStructure = (params: FormStructureParams) => {
    const { formStructure, formik, pageMode } = params

    const inputWidth = useInputWidth()

    //if permission unset then full access
    const setDisplayMode = (element: FormInputProps): 'readonly' | 'available' | 'hidden' => {
        if (!pageMode) console.warn('Page mode not provided')
        if (element.dontIncludeInFormStructure) return 'hidden'
        if (pageMode == 'new') {
            if (!element.addNewPermission) return 'available'
            else if (element.addNewPermission && isAuthorized(element.addNewPermission)) return 'available'
            else return 'hidden'
        }
        if (pageMode == 'edit') {
            if (!element.editPermission) return 'available'
            else if (element.editPermission && isAuthorized(element.editPermission)) return 'available'
            else if (
                element.editPermission &&
                !isAuthorized(element.editPermission) &&
                element.viewPermission &&
                isAuthorized(element.viewPermission)
            )
                return 'readonly'
            else return 'hidden'
        }

        if (pageMode == 'read') {
            if (!element.viewPermission) return 'readonly'
            else if (element.viewPermission && isAuthorized(element.viewPermission)) return 'readonly'
            else return 'hidden'
        }

        console.warn('This warning should never appear. Contact with developer')
        return 'available'
    }

    return (
        <Box sx={{ display: 'flex', flexWrap: 'wrap', gap: '15px' }}>
            {formStructure
                .filter((e) => setDisplayMode(e) != 'hidden')
                .map((e) => {
                    switch (e.type) {
                        case 'input':
                            return (
                                <FormInput
                                    style={{ width: inputWidth }}
                                    label={e.label}
                                    formik={formik}
                                    id={e.id}
                                    readonly={setDisplayMode(e) == 'readonly'}
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
                                    readonly={setDisplayMode(e) == 'readonly'}
                                    key={e.id}
                                    type={'date'}
                                />
                            )
                        case 'date-time':
                            return (
                                <FormInput
                                    style={{ width: inputWidth }}
                                    label={e.label}
                                    formik={formik}
                                    id={e.id}
                                    readonly={setDisplayMode(e) == 'readonly'}
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
                                    readonly={setDisplayMode(e) == 'readonly'}
                                    key={e.id}
                                    options={e.options}
                                />
                            )
                    }
                })}
        </Box>
    )
}
