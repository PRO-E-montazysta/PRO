import { Box } from '@mui/material'
import { ReactNode } from 'react'
import useBreakpoints from '../../hooks/useBreakpoints'

type FormBoxProps = {
    children: ReactNode
}
const FormBox = ({ children }: FormBoxProps) => {
    const appSize = useBreakpoints()
    return (
        <Box
            sx={{
                p: appSize.isMobile || appSize.isTablet ? '10px' : '20px',
                maxWidth: '1200px',
                m: 'auto',
                minWidth: '280px',
            }}
        >
            {children}
        </Box>
    )
}

export default FormBox
