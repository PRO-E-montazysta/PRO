import { Box, Typography } from '@mui/material'
import QRCode from 'react-qr-code'

type LabelTypes = {
    label: string
    code: string
}

const QRCodeLabel = ({ label, code }: LabelTypes) => {
    return (
        <Box
            bgcolor="white"
            width={500}
            textAlign="center"
            marginLeft="auto"
            marginRight="auto"
            padding="10px"
            borderRadius="5px"
        >
            <Typography
                fontWeight="bold"
                color="#15171F"
                fontSize={40}
                lineHeight={1}
                marginBottom="10px"
                marginTop={0}
                maxWidth="500px"
                style={{ wordWrap: 'break-word' }}
            >
                {label}
            </Typography>
            <QRCode value={code} size={192} />
        </Box>
    )
}

export default QRCodeLabel
