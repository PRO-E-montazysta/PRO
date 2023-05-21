import { Typography } from '@mui/material'
import { theme } from '../../themes/baseTheme'
import useBreakpoints from '../../hooks/useBreakpoints'

type FormTitleProps = {
    mainTitle: string
    subTitle?: string
}
const FormTitle = ({ mainTitle, subTitle }: FormTitleProps) => {
    const appSize = useBreakpoints()
    if (subTitle) {
        return (
            <>
                <Typography
                    variant="h4"
                    fontWeight="bold"
                    padding="5px 5px 0px"
                    color={theme.palette.primary.contrastText}
                    fontSize={appSize.isMobile || appSize.isTablet ? '22px' : '32px'}
                >
                    {mainTitle}
                </Typography>
                <Typography
                    variant="h5"
                    fontWeight="bold"
                    padding="0px 5px 5px"
                    color={theme.palette.primary.contrastText}
                    fontSize={appSize.isMobile || appSize.isTablet ? '12px' : '22px'}
                >
                    {subTitle}
                </Typography>
            </>
        )
    } else {
        return (
            <Typography
                variant="h4"
                fontWeight="bold"
                padding="5px"
                color={theme.palette.primary.contrastText}
                fontSize={appSize.isMobile || appSize.isTablet ? '22px' : '32px'}
            >
                {mainTitle}
            </Typography>
        )
    }
}

export default FormTitle
