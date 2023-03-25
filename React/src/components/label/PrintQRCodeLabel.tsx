import { useRef } from 'react'
import ReactToPrint from 'react-to-print'
import QRCodeLabel from './QRCodeLabel'
import { Box, Button } from '@mui/material'

type LabelTypes = {
    label: string
    code: string
}

const PrintQRCodeLabel = ({ label, code }: LabelTypes) => {
    const componentRef = useRef(null)

    return (
        <Box>
            <ReactToPrint
                trigger={() => (
                    <Button color="secondary" variant="contained">
                        Wydrukuj etykietę
                    </Button>
                )}
                content={() => componentRef.current}
            />
            <Box display="none">
                <Box ref={componentRef}>
                    <QRCodeLabel label={label} code={code} />
                </Box>
            </Box>
        </Box>
    )
}

export default PrintQRCodeLabel
