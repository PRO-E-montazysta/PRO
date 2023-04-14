import React, { CSSProperties, useEffect, useState } from 'react'
import InputLabel from '@mui/material/InputLabel'
import FormControl from '@mui/material/FormControl'
import { Autocomplete, Checkbox, TextField } from '@mui/material'
import CheckBoxOutlineBlankIcon from '@mui/icons-material/CheckBoxOutlineBlank'
import CheckBoxIcon from '@mui/icons-material/CheckBox'

import './style.less'
import useBreakpoints from '../../hooks/useBreakpoints'
import { FormInputParams, SelectMenuItemProps } from './types'

const emptyOption = {
    key: '',
    value: '',
}

const FormAutocomplete = (params: FormInputParams) => {
    const { id, readonly, label, options, style, formik } = params
    const value = formik.values[id]
    const appSize = useBreakpoints()

    const [selectedOption, setSelectedOption] = useState<SelectMenuItemProps>(emptyOption)

    const [optionsWithEmpty, setOptionsWithEmpty] = useState<Array<SelectMenuItemProps>>([])

    useEffect(() => {
        if (options) setOptionsWithEmpty([...options, emptyOption])
    }, [options])

    useEffect(() => {
        const opt = options?.find((m) => m.key == value)
        if (opt) setSelectedOption(opt)
        else setSelectedOption(emptyOption)
    }, [value])

    const handleOptionSelect = (event: any, newValue: any) => {
        formik.setFieldValue(id, newValue ? newValue.key : '')
    }

    return (
        <FormControl variant="outlined" style={{ ...style }}>
            <InputLabel id={`label-${id}`} shrink>
                {label}
            </InputLabel>
            <Autocomplete
                size={appSize.isMobile ? 'small' : 'medium'}
                readOnly={readonly}
                value={selectedOption}
                onChange={handleOptionSelect}
                id={id}
                options={optionsWithEmpty}
                getOptionLabel={(option) => option.value}
                isOptionEqualToValue={(option, value) => option.key == value.key}
                renderInput={(params) => (
                    <TextField
                        error={formik.touched[id] && Boolean(formik.errors[id])}
                        {...params}
                        label={label}
                        placeholder=""
                        InputLabelProps={{
                            shrink: true,
                        }}
                    />
                )}
                renderOption={(props, option) => {
                    if (option.key)
                        return (
                            <li {...props} key={option.key}>
                                {option.value}
                            </li>
                        )
                }}
            />
            {formik.touched[id] && Boolean(formik.errors[id]) && (
                <p style={{ fontSize: '12px', color: '#d32f2f', margin: '3px 14px 0 14px' }}>{formik.errors[id]}</p>
            )}
        </FormControl>
    )
}
export default FormAutocomplete
