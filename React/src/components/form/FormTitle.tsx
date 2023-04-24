import { Typography } from '@mui/material'
import { theme } from '../../themes/baseTheme'
import useBreakpoints from '../../hooks/useBreakpoints'

type FormTitleProps = {
    text: string
}
const FormTitle = ({ text }: FormTitleProps) => {
    const appSize = useBreakpoints()

    return (
        <Typography
            variant="h4"
            fontWeight="bold"
            padding="5px"
            color={theme.palette.primary.contrastText}
            fontSize={appSize.isMobile || appSize.isTablet ? '22px' : '32px'}
        >
            {text}
        </Typography>
    )
}

export default FormTitle
