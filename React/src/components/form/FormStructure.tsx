import { Box } from '@mui/system'

import FormInput from '../../components/form/FormInput'
import FormSelect from '../../components/form/FormSelect'
import { useInputWidth } from '../../hooks/useInputWidth'
import { FormInputProps, PageMode } from '../../types/form'
import { isAuthorized } from '../../utils/authorize'

export type FormStructureParams = {
    formStructure: Array<FormInputProps>
    formik: any
    pageMode?: PageMode
    readonlyMode?: boolean //deprecated
    thin?: boolean
}

export type InputDisplayMode = 'readonly' | 'available' | 'hidden'

export const FormStructure = (params: FormStructureParams) => {
    const { formStructure, formik, pageMode, thin } = params

    const inputWidth = useInputWidth(thin)

    //if permission unset then full access
    const setDisplayMode = (element: FormInputProps): InputDisplayMode => {
        if (element.customPermission) {
            const customPermission = element.customPermission(formik.values[element.id])
            if (customPermission) return customPermission
        }
        if (!pageMode) console.warn('Page mode not provided')
        if (element.dontIncludeInFormStructure) return 'hidden'
        if (pageMode == 'new') {
            if (!element.addNewPermissionRoles) return 'available'
            else if (element.addNewPermissionRoles && isAuthorized(element.addNewPermissionRoles)) return 'available'
            else return 'hidden'
        }
        if (pageMode == 'edit') {
            if (!element.editPermissionRoles) return 'available'
            else if (element.editPermissionRoles && isAuthorized(element.editPermissionRoles)) return 'available'
            else if (
                element.editPermissionRoles &&
                !isAuthorized(element.editPermissionRoles) &&
                element.viewPermissionRoles &&
                isAuthorized(element.viewPermissionRoles)
            )
                return 'readonly'
            else return 'hidden'
        }

        if (pageMode == 'read') {
            if (!element.viewPermissionRoles) return 'readonly'
            else if (element.viewPermissionRoles && isAuthorized(element.viewPermissionRoles)) return 'readonly'
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
                        default:
                            return (
                                <FormInput
                                    style={{ width: inputWidth }}
                                    label={e.label}
                                    formik={formik}
                                    id={e.id}
                                    readonly={setDisplayMode(e) == 'readonly'}
                                    key={e.id}
                                    type={e.type}
                                />
                            )
                    }
                })}
        </Box>
    )
}
