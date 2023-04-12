import { Button } from '@mui/material'
import { Box } from '@mui/system'
import { theme } from '../../themes/baseTheme'

import SaveIcon from '@mui/icons-material/Save'
import ReplayIcon from '@mui/icons-material/Replay'
import CloseIcon from '@mui/icons-material/Close'
import EditIcon from '@mui/icons-material/Edit'
import DeleteIcon from '@mui/icons-material/Delete'

type FormButtonsParams = {
    readonlyMode: boolean
    id: string | undefined
    onEdit: () => void
    onDelete: () => void
    onSubmit: () => void
    onReset: () => void
    onCancel: () => void
}

export const FormButtons = (params: FormButtonsParams) => {
    const { readonlyMode, id, onCancel, onDelete, onEdit, onReset, onSubmit } = params
    return (
        <Box sx={{ margin: '20px', gap: '20px', display: 'flex', flexDirection: 'row-reverse' }}>
            {readonlyMode && id != 'new' ? (
                <>
                    <Button
                        color="primary"
                        startIcon={<EditIcon />}
                        variant="contained"
                        type="submit"
                        style={{ width: 120 }}
                        onClick={onEdit}
                    >
                        Edytuj
                    </Button>
                    <Button
                        color="error"
                        startIcon={<DeleteIcon />}
                        variant="contained"
                        type="submit"
                        style={{ width: 120 }}
                        onClick={onDelete}
                    >
                        Usuń
                    </Button>
                </>
            ) : (
                <>
                    <Button
                        color="primary"
                        startIcon={<SaveIcon />}
                        variant="contained"
                        onClick={onSubmit}
                        style={{ width: 120 }}
                    >
                        Zapisz
                    </Button>
                    <Button
                        color="primary"
                        startIcon={<ReplayIcon style={{ transform: 'rotate(-0.25turn)' }} />}
                        style={{ color: theme.palette.primary.main, width: 120 }}
                        variant="outlined"
                        onClick={onReset}
                    >
                        Reset
                    </Button>
                    {params.id != 'new' && (
                        <Button
                            color="primary"
                            startIcon={<CloseIcon style={{ transform: 'rotate(-0.25turn)' }} />}
                            style={{ color: theme.palette.primary.main, width: 120 }}
                            variant="outlined"
                            onClick={onCancel}
                        >
                            Anuluj
                        </Button>
                    )}
                </>
            )}
        </Box>
    )
}
