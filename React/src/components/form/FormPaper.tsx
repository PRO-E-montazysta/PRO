import { Paper, SxProps } from '@mui/material'
import useBreakpoints from '../../hooks/useBreakpoints'
import { ReactNode } from 'react'

type FormPaperProps = {
    children: ReactNode
    sx?: SxProps
}

const FormPaper = ({ children,sx }: FormPaperProps) => {
    const appSize = useBreakpoints()
    return (
        <Paper
            sx={{
                mt: appSize.isMobile || appSize.isTablet ? '10px' : '20px',
                p: appSize.isMobile ? '20px 10px' : '20px',
                pt: appSize.isMobile ? '30px' : '40px',
                ...sx
            }}
        >
            {children}
        </Paper>
    )
}

export default FormPaper
