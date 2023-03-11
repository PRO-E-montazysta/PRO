import { Button, TextField } from '@mui/material';
import './style.less';

import { v4 as uuidv4 } from 'uuid'
import { CSSProperties } from 'react';
import { theme } from '../../themes/baseTheme';

export type DialogInfoParams = {
    dialogText: Array<string>
    confirmLabel: string
    confirmAction: () => void
    cancelLabel?: string
    cancelAction?: () => void
}

const DialogInfo = (params: DialogInfoParams) => {
    const { dialogText, confirmLabel, confirmAction, cancelAction, cancelLabel } = params
    return (
        <div className="blur-background">
            <div className="confirm-dialog">
                <form>
                    {
                        dialogText.map(d => (
                            <p style={{}} key={uuidv4()}>{d}</p>
                        ))
                    }
                    <div className="action-buttons">
                        <Button
                            onClick={confirmAction}
                            style={{
                                color: theme.palette.primary.main,
                                background: theme.palette.primary.contrastText,
                            }}
                        >
                            {confirmLabel}
                        </Button>
                        {
                            !!cancelLabel &&
                            <Button
                                onClick={cancelAction}
                                style={{
                                    color: theme.palette.primary.contrastText,
                                    border: '1px solid ' + theme.palette.primary.contrastText
                                }}
                            >
                                {cancelLabel}
                            </Button>
                        }
                    </div>
                </form>
            </div>
        </div>
    );
};

export default DialogInfo;
