import React, { useState } from 'react';
import { Theme, useTheme } from '@mui/material/styles';
import Box from '@mui/material/Box';
import OutlinedInput from '@mui/material/OutlinedInput';
import InputLabel from '@mui/material/InputLabel';
import MenuItem from '@mui/material/MenuItem';
import FormControl from '@mui/material/FormControl';
import Select, { SelectChangeEvent } from '@mui/material/Select';
import Chip from '@mui/material/Chip';

export type SelectMenuItemProps = {
    key: number | string
    value: string
}


type MultipleSelectProps = {
    label?: string
    initValues?: Array<any>
    menuItems: Array<SelectMenuItemProps>
    id: string
    name: string
    value: Array<any>
    formikSetFieldValue: (id: string, value: any) => void
}

export default function MultipleSelect(props: MultipleSelectProps) {
    const { id, label, menuItems, initValues, name, formikSetFieldValue, value } = props
    const [selectedValues, setSelectedValues] = useState<Array<any>>(initValues ? initValues : []);

    const handleChange = (event: SelectChangeEvent<typeof selectedValues>) => {
        const { target: { value } } = event;
        if (Array.isArray(value))
            formikSetFieldValue(id, value);
    };

    const handleRenderValue = (value: Array<any>) => {
        return <Box sx={{ display: 'flex', flexWrap: 'wrap', gap: 0.5 }}>
            {value.map(v => {
                return menuItems.find(e => e.key == v)
            }).map((v?: SelectMenuItemProps) => (
                v && <Chip key={v.key} label={v.value} />
            ))}
        </Box>
    }

    return (
        <div>
            <FormControl variant='outlined' fullWidth>
                <InputLabel id={`label-${id}`} shrink>{label}</InputLabel>
                <Select
                    name={name}
                    labelId={`label-${id}`}
                    multiple
                    value={value ? value : []}
                    onChange={handleChange}
                    input={<OutlinedInput notched label={label} />}
                    renderValue={handleRenderValue}
                >
                    {menuItems.map((item) => (
                        <MenuItem
                            key={item.key}
                            value={item.key}
                        >
                            {item.value}
                        </MenuItem>
                    ))}
                </Select>
            </FormControl>
        </div>
    );
}