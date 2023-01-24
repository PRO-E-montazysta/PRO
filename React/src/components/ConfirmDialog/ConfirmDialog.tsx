import { Button} from '@mui/material';
import './style.less';

const ConfirmDialog = () => {
 
  return (
    <div className="blur-background">
        <div className="confirm-dialog">
            <form>
                <p>
                    Czy na pewno chcesz usunąć konto użytkownika o ID 95259849?
                </p>
                <div className="action-buttons">
                    <Button sx={{ color: 'white' }}>
                        Usuń
                    </Button>
                    <Button sx={{ color: 'white' }}>
                        Anuluj
                    </Button>
                </div>
            </form>
        </div>
    </div>
  );
};

export default ConfirmDialog;
