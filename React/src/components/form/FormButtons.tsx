import { Button } from '@mui/material'
import { Box } from '@mui/system'
import { theme } from '../../themes/baseTheme'

import SaveIcon from '@mui/icons-material/Save'
import ReplayIcon from '@mui/icons-material/Replay'
import CloseIcon from '@mui/icons-material/Close'
import EditIcon from '@mui/icons-material/Edit'
import DeleteIcon from '@mui/icons-material/Delete'
import AssignmentIcon from '@mui/icons-material/Assignment'
import ArrowForwardIosIcon from '@mui/icons-material/ArrowForwardIos'
import ArrowBackIosIcon from '@mui/icons-material/ArrowBackIos'
import useBreakpoints from '../../hooks/useBreakpoints'
import PrintQRCodeLabel from '../label/PrintQRCodeLabel'
import { useState } from 'react'
import { Role } from '../../types/roleEnum'
import { isAuthorized } from '../../utils/authorize'

type FormButtonsParams = {
    readonlyMode: boolean
    id: string | undefined
    onEdit: () => void
    onDelete?: () => void
    onSubmit: () => void
    onReset: () => void
    onCancel: () => void
    printLabel?: [string, string]
    orderStageButton?: boolean
    handleAddOrderStage?: () => void
    isAddOrderStageVisible?: boolean
    hireDismissEmp?: [() => void, 'hire' | 'dismiss']
    nextStatus?: () => void
    previousStatus?: () => void

    //if not provided then denied
    deletePermissionRoles?: Array<Role>
    editPermissionRoles?: Array<Role>
}

export const FormButtons = (params: FormButtonsParams) => {
    const {
        readonlyMode,
        id,
        onCancel,
        onDelete,
        onEdit,
        onReset,
        onSubmit,
        printLabel,
        orderStageButton,
        handleAddOrderStage,
        isAddOrderStageVisible,
        hireDismissEmp,
        nextStatus,
        previousStatus,
        deletePermissionRoles,
        editPermissionRoles,
    } = params

    const appSize = useBreakpoints()

    const canDelete = (): boolean => {
        return !!deletePermissionRoles && isAuthorized(deletePermissionRoles)
    }

    const canEdit = (): boolean => {
        return !!editPermissionRoles && isAuthorized(editPermissionRoles)
    }

    return (
        <Box
            sx={{
                mt: '15px',
                gap: '15px',
                display: appSize.isMobile ? 'grid' : 'flex',
                flexDirection: 'row-reverse',
            }}
        >
            {readonlyMode && id != 'new' ? (
                <>
                    {nextStatus ? (
                        <Button
                            id={`formButton-nextStatus`}
                            color="primary"
                            startIcon={<ArrowForwardIosIcon />}
                            variant="contained"
                            type="submit"
                            style={{ width: appSize.isMobile ? 'auto' : 190 }}
                            onClick={nextStatus}
                        >
                            Następny status
                        </Button>
                    ) : null}
                    {previousStatus ? (
                        <Button
                            id={`formButton-nextStatus`}
                            color="primary"
                            startIcon={<ArrowBackIosIcon />}
                            variant="contained"
                            type="submit"
                            style={{ width: appSize.isMobile ? 'auto' : 170 }}
                            onClick={previousStatus}
                        >
                            Cofnij status
                        </Button>
                    ) : null}
                    {printLabel ? <PrintQRCodeLabel label={printLabel[0]} code={printLabel[1]} /> : null}
                    {hireDismissEmp && (
                        <Button
                            color="primary"
                            startIcon={<AssignmentIcon />}
                            variant="contained"
                            type="submit"
                            onClick={hireDismissEmp[0]}
                        >
                            {hireDismissEmp[1] == 'hire' ? 'Zatrudnij' : 'Zwolnij'}
                        </Button>
                    )}
                    {canEdit() ? (
                        <Button
                            id={`formButton-edit`}
                            color="primary"
                            startIcon={<EditIcon />}
                            variant="contained"
                            type="submit"
                            style={{ width: appSize.isMobile ? 'auto' : 120 }}
                            onClick={onEdit}
                        >
                            Edytuj
                        </Button>
                    ) : null}
                    {onDelete && canDelete() ? (
                        <Button
                            id={`formButton-delete`}
                            color="error"
                            startIcon={<DeleteIcon />}
                            variant="contained"
                            type="submit"
                            style={{ width: appSize.isMobile ? 'auto' : 120 }}
                            onClick={onDelete}
                        >
                            Usuń
                        </Button>
                    ) : null}
                    {orderStageButton && (
                        <Button
                            color="primary"
                            startIcon={<EditIcon />}
                            variant="contained"
                            type="submit"
                            // style={{ width: 160 }}
                            onClick={handleAddOrderStage}
                        >
                            {isAddOrderStageVisible ? 'Anuluj dodawanie etapu' : 'Dodaj etap'}
                        </Button>
                    )}
                </>
            ) : (
                <>
                    <Button
                        id={`formButton-save`}
                        color="primary"
                        startIcon={<SaveIcon />}
                        variant="contained"
                        onClick={onSubmit}
                        style={{ width: appSize.isMobile ? 'auto' : 120 }}
                    >
                        Zapisz
                    </Button>
                    <Button
                        id={`formButton-reset`}
                        color="primary"
                        startIcon={<ReplayIcon style={{ transform: 'rotate(-0.25turn)' }} />}
                        style={{ color: theme.palette.primary.main, width: appSize.isMobile ? 'auto' : 120 }}
                        variant="outlined"
                        onClick={onReset}
                    >
                        Reset
                    </Button>
                    {params.id != 'new' && (
                        <Button
                            id={`formButton-cancel`}
                            color="primary"
                            startIcon={<CloseIcon style={{ transform: 'rotate(-0.25turn)' }} />}
                            style={{ color: theme.palette.primary.main, width: appSize.isMobile ? 'auto' : 120 }}
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
