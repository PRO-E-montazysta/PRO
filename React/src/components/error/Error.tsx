import { Button, useTheme } from '@mui/material'
import { Link } from 'react-router-dom'

import './style.less'

type ErrorPageProps = {
    code: number
    message: string
    description?: string
}

const Error = (props: ErrorPageProps) => {
    const { code, message, description } = props
    const theme = useTheme()

    return (
        <div
            className="container"
            style={{
                color: theme.palette.primary.contrastText,
                borderColor: theme.palette.primary.contrastText,
            }}
        >
            <div className="info">
                <p className="code">{code}</p>
                <p className="divider"></p>
                <p>{message}</p>
            </div>
            <p>{description}</p>
            <div
                style={{
                    marginTop: '50px',
                }}
            >
                <Link to="/" className="link">
                    <Button id={`dialog-error-btn-goHome`} className="button">
                        Przejdź do strony głównej
                    </Button>
                </Link>
            </div>
        </div>
    )
}

export default Error
