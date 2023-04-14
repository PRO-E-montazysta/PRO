import { Paper } from '@mui/material'
import useBreakpoints from '../../hooks/useBreakpoints'
import { ReactNode } from 'react'

type FormPaperProps = {
    children: ReactNode
}

const FormPaper = ({ children }: FormPaperProps) => {
    const appSize = useBreakpoints()
    return (
        <Paper
            sx={{
                mt: appSize.isMobile || appSize.isTablet ? '10px' : '20px',
                p: appSize.isMobile ? '10px' : '20px',
            }}
        >
            {children}
        </Paper>
    )
}

export default FormPaper
