import { Button} from '@mui/material';
import './style.less';

type ConfirmDialogTypes = {
    dialogText: string;
    confirmLabel: string;
    confirmAction: any;
    cancelAction: any;
}

const ConfirmDialog = ({ dialogText, confirmLabel, confirmAction, cancelAction }: ConfirmDialogTypes) => {
 
  return (
    <div className="blur-background">
        <div className="confirm-dialog">
            <form>
                <p>
                    {dialogText}
                </p>
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

export default ConfirmDialog;
