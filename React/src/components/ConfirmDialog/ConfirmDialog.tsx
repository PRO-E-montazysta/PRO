import { Button} from '@mui/material';
import './style.less';

type ConfirmDialogTypes = {
    dialogText: string;
    confirmAction: string;
}

const ConfirmDialog = ({ dialogText, confirmAction }: ConfirmDialogTypes) => {
 
  return (
    <div className="blur-background">
        <div className="confirm-dialog">
            <form>
                <p>
                    {dialogText}
                </p>
                <div className="action-buttons">
                    <Button sx={{
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
                        {confirmAction}
                    </Button>
                    <Button sx={{
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
