import { Button, TextField } from '@mui/material'
import { styled } from '@mui/material/styles'
import './style.less'

type DialogTypes = {
    dialogText: string
    confirmLabel: string
    inputsInfo: any
    confirmAction: any
    cancelAction: any
}

const CustomTextField = styled(TextField)(({ theme }) => ({
    label: { shrink: true, color: theme.palette.secondary.contrastText },
    input: { color: theme.palette.secondary.contrastText },
    '& label.Mui-focused': {
        color: theme.palette.secondary.contrastText,
    },
    '& .MuiOutlinedInput-root': {
        '& fieldset': {
            borderColor: '#96C0FB',
        },
        '&:hover fieldset': {
            borderColor: theme.palette.secondary.contrastText,
        },
    },
    '&.Mui-focused fieldset': {
        borderColor: 'green',
    },
}))

const Dialog = ({ dialogText, confirmLabel, inputsInfo, confirmAction, cancelAction }: DialogTypes) => {
    const inputsHeight = 90 * inputsInfo.length
    const dialogHeight = 110
    const totalHeight = inputsHeight + dialogHeight + 'px'

    return (
        <div className="blur-background">
            <div className="dialog" style={{ height: totalHeight }}>
                <form>
                    <p className="dialogText">{dialogText}</p>
                    <div>
                        {inputsInfo.map((info: { inputName: string; inputType: string; inputLabel: string }) => (
                            <CustomTextField
                                id={`dialog-${info.inputName}`}
                                name={info.inputName}
                                type={info.inputType}
                                label={info.inputLabel}
                                fullWidth
                                style={{
                                    display: 'block',
                                    width: '490px',
                                    height: '55px',
                                    marginLeft: 'auto',
                                    marginRight: 'auto',
                                    marginBottom: '25px',
                                }}
                            />
                        ))}
                    </div>
                    <div className="action-buttons">
                        <Button
                            id={`dialog-btn-confirm`}
                            onClick={confirmAction}
                            sx={{
                                color: 'white',
                                background: '#282A3A',
                                width: '100px',
                                height: '40px',
                                'border-radius': '3px',
                                'font-weight': '600',
                                'font-size': '13px',
                                'line-height': '32px',
                                'letter-spacing': '0.2px',
                            }}
                        >
                            {confirmLabel}
                        </Button>
                        <Button
                            id={`dialog-btn-cancel`}
                            onClick={cancelAction}
                            sx={{
                                color: 'white',
                                background: '#15171F',
                                width: '100px',
                                height: '40px',
                                'border-radius': '3px',
                                'font-weight': '600',
                                'font-size': '13px',
                                'line-height': '32px',
                                'letter-spacing': '0.2px',
                            }}
                        >
                            Anuluj
                        </Button>
                    </div>
                </form>
            </div>
        </div>
    )
}

export default Dialog
