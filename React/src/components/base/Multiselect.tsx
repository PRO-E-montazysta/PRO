import React, { CSSProperties, useEffect, useState } from 'react'
import InputLabel from '@mui/material/InputLabel'
import FormControl from '@mui/material/FormControl'
import { Autocomplete, Checkbox, TextField } from '@mui/material'
import CheckBoxOutlineBlankIcon from '@mui/icons-material/CheckBoxOutlineBlank'
import CheckBoxIcon from '@mui/icons-material/CheckBox'
import styled from '@emotion/styled'

import './style.less'

const icon = <CheckBoxOutlineBlankIcon fontSize="small" />
const checkedIcon = <CheckBoxIcon fontSize="small" />

export type SelectMenuItemProps = {
    key: number | string
    value: string
}

type MultipleSelectProps = {
    label?: string
    menuItems: Array<SelectMenuItemProps>
    id: string
    value: Array<any>
    formikSetFieldValue: (id: string, value: any) => void
    boxStyle?: CSSProperties
}

export default function MultipleSelect(props: MultipleSelectProps) {
    const { id, label, menuItems, formikSetFieldValue, value, boxStyle } = props
    const [selectedValues, setSelectedValues] = useState<Array<any>>([])

    useEffect(() => {
        setSelectedValues(menuItems.filter((m) => value.indexOf(m.key) > -1))
    }, [value])

    const handleChange = (newValues: any) => {
        const values = newValues.map((e: SelectMenuItemProps) => e.key)
        formikSetFieldValue(id, values)
    }

    const StyledAutocomplete = styled(Autocomplete)(() => ({
        [`& .MuiInputBase-input`]: {
            minWidth: '0 !important',
        },
        [`& .MuiInputBase-input:focus`]: {
            minWidth: '30px !important',
        },
    }))

    return (
        <FormControl variant="outlined" style={{ width: '100%', ...boxStyle }}>
            <InputLabel id={`label-${id}`} shrink>
                {label}
            </InputLabel>
            <Autocomplete
                value={selectedValues}
                multiple
                id={id}
                options={menuItems}
                disableCloseOnSelect
                clearOnBlur
                limitTags={2}
                onChange={(event: any, newValue: any) => {
                    handleChange(newValue)
                }}
                getOptionLabel={(option) => option.value}
                isOptionEqualToValue={(option, value) => option.key == value.key}
                renderOption={(props, option, { selected }) => (
                    <li {...props}>
                        <Checkbox icon={icon} checkedIcon={checkedIcon} style={{ marginRight: 8 }} checked={selected} />
                        {option.value}
                    </li>
                )}
                renderInput={(params) => (
                    <TextField
                        {...params}
                        label={label}
                        placeholder=""
                        InputLabelProps={{
                            shrink: true,
                        }}
                    />
                )}
            />
        </FormControl>
    )
}
