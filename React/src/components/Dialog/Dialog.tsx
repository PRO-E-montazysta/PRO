import { Button, TextField } from '@mui/material';
import './style.less';

type DialogTypes = {
    dialogText: string;
    confirmLabel: string;
    inputsInfo: any;
    confirmAction: any;
    cancelAction: any;
}

const Dialog = ({ dialogText, confirmLabel, inputsInfo, confirmAction, cancelAction }: DialogTypes) => {
    const inputsHeight = 90 * inputsInfo.length;
    const dialogHeight = 110;
    const totalHeight = (inputsHeight+dialogHeight)+"px";
    console.log(inputsInfo.lenght)
    console.log(inputsHeight)
    console.log(dialogHeight)
    console.log(totalHeight)
  return (
    <div className="blur-background">
        <div className="dialog" style={{height: totalHeight}}>
            <form>
                <p>
                    {dialogText}
                </p>
                <div>
                    {inputsInfo.map((info: { inputType: string; inputName: string; inputLabel: string; }) => (
                        <TextField
                            name={info.inputName}
                            type={info.inputType}
                            label={info.inputLabel}
                            fullWidth
                            variant='outlined'
                            focused 
                            style=
                            {{ 
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
                        'letter-spacing': '0.2px'
                        }}
                    >
                        {confirmLabel}
                    </Button>
                    <Button
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
                        'letter-spacing': '0.2px'
                        }}
                    >
                        Anuluj
                    </Button>
                </div>
            </form>
        </div>
    </div>
  );
};

export default Dialog;
